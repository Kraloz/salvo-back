package com.mindhub.salvo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindhub.salvo.model.GamePlayer;
import com.mindhub.salvo.model.Player;
import com.mindhub.salvo.repository.GamePlayerRepository;
import com.mindhub.salvo.repository.GameRepository;
import com.mindhub.salvo.repository.PlayerRepository;

@RestController
@RequestMapping("/api")
public class SalvoController {
	
	@Autowired
	GameRepository gameRepository;
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	GamePlayerRepository gamePlayerRepository;
	
	/* routes definitions */
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/game_view")
	public List<Map<String, Object>> getGamePlayers() {
		List<GamePlayer> gamePlayers = gamePlayerRepository.findAll();
		List<Map<String, Object>> response =
				gamePlayers.stream()
				.map(GamePlayer::gamePlayerDTO)
				.collect(Collectors.toList());
			
		return response;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/game_view/{gamePlayerId}")
	public ResponseEntity<Map<String, Object>> getGamePlayers(@PathVariable Long gamePlayerId) {
		Optional<GamePlayer> gamePlayer = gamePlayerRepository.findById(gamePlayerId);
		
        return new ResponseEntity<>(gamePlayer.get().gamePlayerDTO(), HttpStatus.OK);
    }
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/players")
	public List<Map<String, Object>> getPlayers() {
		List<Player> players = playerRepository.findAll();
		List<Map<String, Object>> response =
				players.stream()
				.map(Player::playerDTO)
				.collect(Collectors.toList());
		
		return response;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/player/{nickName}")
	public Map<String,Object> getPlayerByNickname(@PathVariable String nickName) {
		Player player = playerRepository.findByNickName(nickName);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("data", player.playerDTO());
		
		return response;
		
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/player/{nickName}/game_views")
	public Set<Map<String,Object>> getPlayerGamePlayers(@PathVariable String nickName) {
		Player player = playerRepository.findByNickName(nickName);
		
		Set<GamePlayer> gamePlayers =  player.getGamePlayers();
		Set<Map<String,Object>> response = gamePlayers.stream()
				.map(GamePlayer::gamePlayerDTO)
				.collect(Collectors.toSet());

		return response;
	}
}
