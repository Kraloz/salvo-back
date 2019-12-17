package com.mindhub.salvo.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
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
import com.mindhub.salvo.model.GameStatus;
import com.mindhub.salvo.model.Player;
import com.mindhub.salvo.model.Salvo;
import com.mindhub.salvo.model.Ship;
import com.mindhub.salvo.repository.GamePlayerRepository;
import com.mindhub.salvo.repository.GameRepository;
import com.mindhub.salvo.repository.PlayerRepository;
import com.mindhub.salvo.repository.SalvoRepository;
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
	@Autowired
	SalvoRepository salvoRepository;

	/* routes definitions */

	// List all games
	@RequestMapping(value = "/games", method = RequestMethod.GET)
	public Map<String, Object> getAllGames() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("games", gameRepository.findAll().stream().map(Game::gameDTO).collect(Collectors.toList()));
        
        return dto;
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
		Optional<Player> player = playerRepository.findByNickName(authentication.getName());
		
		if (!player.isPresent()) {
			return ResponseEntity.badRequest().build();
		};

		Optional<GamePlayer> gpReponse = gameAux.getGamePlayers()
				.stream()
				.filter(gp -> gp.getPlayer().getId() == player.get().getId())
				.findFirst();
		
		if(!gpReponse.isPresent()) {
			dto.put("error", "Player didn't join the game");
			return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(gpReponse.get().gamePlayerDTO(), HttpStatus.OK);
	}
	
	
	// Create a new game and relation creator player with game
	@RequestMapping(value = "/games", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> createGame() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<Player> player = playerRepository.findByNickName(authentication.getName());
		
		if (!player.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		
		Game game = gameRepository.save(new Game());
		GamePlayer gp = gamePlayerRepository.save(new GamePlayer(game, player.get()));

	    URI location = URI.create(String.format("/api/game_view/%d", gp.getId()));
		return ResponseEntity.created(location).build();
	}

	
	// Allows a player to Join a game creating the relationship between them
	@RequestMapping(value = "/games/{gameId}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> enrollGame(@PathVariable("gameId") Long gameId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<Player> player = playerRepository.findByNickName(authentication.getName());
		
		if (!player.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		
		Optional<Game> game = gameRepository.findById(gameId);
		Map<String, Object> dto = new LinkedHashMap<String, Object>(); 
		
		// Checks if the game exist
		if (!game.isPresent()) {
			dto.put("error", "Game doesn't exist");
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND); 
		}
		
		// Get the game
		Game gameAux = game.get();
		
		// Check if the game is freshly created
		if (gameAux.getStatus() != GameStatus.CREATED) {
			dto.put("error", "Can't join now to the game");
			return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
		}
		
		// Check if the player is already inside the game
        if (gameAux.getGamePlayers().stream().anyMatch(e -> e.getPlayer().getId() == player.get().getId())) {
            dto.put("error", "Player already joined the game");
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
        }
		
		// Check if the game is full
		if (gameAux.isFull()) {
			dto.put("error", "Game is full");
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
		}
		
        GamePlayer gp = new GamePlayer(game.get(), player.get());
        gamePlayerRepository.save(gp);
        
        gameAux.setStatus(GameStatus.PREPARE);
        gameRepository.save(gameAux);
        
	    URI location = URI.create(String.format("/rest/game_view/%d", (int)gp.getId()));
        return ResponseEntity.created(location).build();
	}

	
	@RequestMapping(value = "/games/{gameId}/ships",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> deployShips(
			@PathVariable("gameId") Long gameId,
			@RequestBody Set<Ship> ships) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> dto = new LinkedHashMap<String, Object>(); 
		
		Optional<Player> player = playerRepository.findByNickName(authentication.getName());
		
		if (!player.isPresent()) {
			return ResponseEntity.badRequest().build();
		};
		
		Optional<GamePlayer> gp = gamePlayerRepository.findByGameIdAndPlayerId(gameId, player.get().getId());
		// Checks if the gamePlayer exist
		if (!gp.isPresent()) {
			dto.put("error", "Game doesn't exist");
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
		}
		
		// Get the game
		Game game = gp.get().getGame();
		
		// Check if the game isn't freshly created
		if (game.getStatus() != GameStatus.PREPARE) {
			dto.put("error", "Can't deploy ships now");
			System.out.println("Status :" + game.getStatus());
			return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
		}
		
		// Checks if there aren't deployed ships yet
		if (!gp.get().getShips().isEmpty()) {
			dto.put("error", "Ships Already deployed");
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
		}
		
		// Checks if there are only 5 ships to deploy
		if (ships.size() != 5) {
			dto.put("error", "You can only deploy 5 ships!");
			return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
		}
		
		// Checks if there are repeated ships location
		List<String> allLocations = ships.stream().flatMap(ship -> ship.getCells().stream()).collect(Collectors.toList());
		List<String> disTinctLocations = allLocations.stream().distinct().collect(Collectors.toList()); 
		if (allLocations.size() != disTinctLocations.size()) {
			dto.put("error", "Can't deploy 2 ships in the same location!");
            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
		}
		
		ships.forEach(ship -> {
			shipRepository.save(ship);
			gp.get().addShip(ship);
		});
		gamePlayerRepository.save(gp.get());
		
		
		// Set gameStatus to COURSE if both player ships' are deployed
		List<Boolean> shipsSetInGame = game.getGamePlayers().stream().map(o -> o.areShipsDeployed()).collect(Collectors.toList());
		
		boolean areAllShipsDeployed = shipsSetInGame.stream().allMatch(Boolean::valueOf);
		
		if (areAllShipsDeployed) {
			game.setStatus(GameStatus.COURSE);
			gameRepository.save(game);		
		}
		
		URI location = URI.create(String.format("/api/games/%d/ships", gameId));
		return ResponseEntity.created(location).build();
	}

	
	@RequestMapping(value = "/games/{gameId}/salvoes",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> fireSalvoes(
			@PathVariable("gameId") Long gameId,
			@RequestBody Salvo salvo) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> dto = new LinkedHashMap<String, Object>();

		// Checks if game exist
		Optional<Game> game = gameRepository.findById(gameId);
		if (!game.isPresent()) {
			dto.put("error", "The game doesn't exist");
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
		}		

		// Checks if the player exist
		Optional<Player> player = playerRepository.findByNickName(authentication.getName());
		if (!player.isPresent()) {
			return new ResponseEntity<>("Player doesn't exist" ,HttpStatus.NOT_FOUND);
		};

		
		// Checks if the player belongs to the game requested
		Optional<GamePlayer> optGp = gamePlayerRepository.findByGameIdAndPlayerId(gameId, player.get().getId());		
		if (!optGp.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		// Checks if game is in course
		if(game.get().getStatus() != GameStatus.COURSE) {
			dto.put("error", "Can't shoot now!");
			return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
		}
		
		// Checks if the game is full | no haría falta porque gameStatus lo verifica, pero ya fué xd
		if (game.get().getGamePlayers().size() != 2) {
			dto.put("error", "There is no enemy player");
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
		}

		// Checks if all ships has been deployed
		GamePlayer playerGp = optGp.get();
		GamePlayer enemyGp = enemyGamePlayer(playerGp);
		
		if (!(playerGp.getShips().size() == 5 && enemyGp.getShips().size() == 5)) {
			dto.put("error", "Ships hasn't been deployed");
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
		}
		
		
		if (playerGp.getActualTurn() != salvo.getTurn()) {
			dto.put("error", "You can't skip turns");
            return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
		}
		
		if (!(playerGp.getActualTurn() <= enemyGp.getActualTurn())) {
			dto.put("error", "You can't shoot if your enemy is behind your turn");
			return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
		}
		
		
        Set<Salvo> salvoSet = new HashSet<>();
        salvoSet.add(salvo);
        playerGp.addSalvoes(salvoSet);
        gamePlayerRepository.save(playerGp);
		
        /*
		// Checks if the firing player is in the same turn or one turn behind enemy 
		int playerTurn = playerGp.getActualTurn();
		int enemyTurn = playerGp.getActualTurn();
         */

        return ResponseEntity.accepted().build();
	}

	
	// @return Info of the authenticated player
	@CrossOrigin(origins = "*")
	@RequestMapping("/playerInfo")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getPlayerSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		
		Optional<Player> player = playerRepository.findByNickName(authentication.getName());
		
		if (!player.isPresent()) {
			return ResponseEntity.badRequest().build();
		};
		
		Map<String, Object> response = player.get().playerDTO();

		return ResponseEntity.ok(response);
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
