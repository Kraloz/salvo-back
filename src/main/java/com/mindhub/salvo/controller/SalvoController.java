package com.mindhub.salvo.controller;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mindhub.salvo.model.Game;
import com.mindhub.salvo.model.GamePlayer;
import com.mindhub.salvo.model.Player;
import com.mindhub.salvo.model.Ship;
import com.mindhub.salvo.repository.GamePlayerRepository;
import com.mindhub.salvo.repository.GameRepository;
import com.mindhub.salvo.repository.PlayerRepository;
import com.mindhub.salvo.repository.ShipRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SalvoController {
	
	@Autowired
	GameRepository gameRepository;
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	GamePlayerRepository gamePlayerRepository;
	@Autowired
	ShipRepository shipRepository;
	
	/* routes definitions */
	
	// List all games
	@RequestMapping(value = "/games", method = RequestMethod.GET)
	public Map<String, Object> getAllGames() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("games", gameRepository.findAll().stream().map(Game::gameDTO).collect(Collectors.toList()));
        
        return dto;
	}

	// Create a new game and relation creator player with game
	@RequestMapping(value = "/games", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> createGame() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Player player = playerRepository.findByNickName(authentication.getName());
		if(player==null) {
			return ResponseEntity.badRequest().build();
		}
		
		Game game = gameRepository.save(new Game());
		GamePlayer gp = gamePlayerRepository.save(new GamePlayer(game, player));

	    URI location = URI.create(String.format("/api/game_view/%d", gp.getId()));
		return ResponseEntity.created(location).build();
	}
	
	// Allows a player to Join a game creating the relationship between them
	@RequestMapping(value = "/games/{gameId}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> enrollGame(@PathVariable("gameId") Long gameId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Player player = playerRepository.findByNickName(authentication.getName());
		if(player==null) {
			return ResponseEntity.badRequest().build();
		}
		
		Map<String, Object> dto = new LinkedHashMap<String, Object>(); 
		
		Optional<Game> game = gameRepository.findById(gameId);

		// Checks if the game exist
		if(!game.isPresent()) {
			dto.put("error", "Game doesn't exist");
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND); 
		}
		
		// Get the game
		Game gameAux = game.get();
		
		// Check if the player is already inside the game
        if (gameAux.getGamePlayers().stream().anyMatch(e -> e.getPlayer().getId() == player.getId())) {
            dto.put("error", "Player already joined the game");
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
        }
		
		// Check if the game is full
		if(gameAux.isFull()) {
			dto.put("error", "Game is full");
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
		}
	
        GamePlayer gp = new GamePlayer(game.get(), player);
        gamePlayerRepository.save(gp);
        
	    URI location = URI.create(String.format("/rest/game_view/%d", (int)gp.getId()));
        return ResponseEntity.created(location).build();
	}

	// Returns the authenticated player's game view of the selected game
	@RequestMapping(value = "/games/{gameId}/game_view", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getGameGameView(@PathVariable("gameId") Long gameId) {
		Map<String, Object> dto = new LinkedHashMap<String, Object>();
		Optional<Game> game = gameRepository.findById(gameId);
		
		// Checks if the game exist
		if(!game.isPresent()) {
			dto.put("error", "Game doesn't exist");
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND); 
		}
		
		Game gameAux = game.get();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Player player = playerRepository.findByNickName(authentication.getName());

		Optional<GamePlayer> gpReponse = gameAux.getGamePlayers()
				.stream()
				.filter(gp -> gp.getPlayer().getId() == player.getId())
				.findFirst();
		
		if(!gpReponse.isPresent()) {
			dto.put("error", "Player didn't join the game");
			return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(gpReponse.get().gamePlayerDTO(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/games/{gameId}/ships",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> setShipsGameView(
			@PathVariable("gameId") Long gameId,
			@RequestBody Set<Ship> ships) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> dto = new LinkedHashMap<String, Object>(); 
		
		Player player = playerRepository.findByNickName(authentication.getName());
		if(player==null) {
			return ResponseEntity.badRequest().build();
		}
		
		Optional<GamePlayer> gp = gamePlayerRepository.findByGameIdAndPlayerId(gameId, player.getId());
		// Checks if the gamePlayer exist
		if(!gp.isPresent()) {
			dto.put("error", "Game doesn't exist");
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
		}
		if(!gp.get().getShips().isEmpty()) {
			dto.put("error", "Ships Already deployed");
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
		}
		// Checks if there are only 5 ships to deploy
		if (ships.size() != 5) {
			dto.put("error", "You can only deploy 5 ships!");
			return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
		}
		
		ships.forEach(ship -> {
			shipRepository.save(ship);
			gp.get().addShip(ship);
		});
		gamePlayerRepository.save(gp.get());
		
		//System.out.println(gp.get());
		URI location = URI.create(String.format("/api/games/%d/ships", gameId));
		return ResponseEntity.created(location).build();
	}
	
	
	// @return Info of the authenticated player
	@CrossOrigin(origins = "*")
	@RequestMapping("/playerInfo")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Map<String, Object> getPlayerSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Player player = playerRepository.findByNickName(authentication.getName());
		
		Map<String, Object> response = player.playerDTO();

		return response;
	}
	
	// @returns *unordered* leaderboard
	@CrossOrigin(origins = "*")
	@RequestMapping("/leaderboard")
	public List<Map<String,Object>> getLeaderBoard() {
		List<Player> players = playerRepository.findAll();
		List<Map<String, Object>> response =
				players.stream()
				.map(Player::scoresDTO)
				.collect(Collectors.toList());
		
		return response;
	}
	
    private GamePlayer enemyGamePlayer(GamePlayer gamePlayer) {
        return gamePlayer.getGame().getGamePlayers().stream().filter(e -> e.getId() != gamePlayer.getId()).findFirst().orElse(null);
    }
}
