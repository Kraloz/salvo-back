package com.mindhub.salvo;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.mindhub.salvo.model.*;
import com.mindhub.salvo.model.Ship.ShipType;
import com.mindhub.salvo.repository.*;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@SuppressWarnings("unused")
	@Bean
	public CommandLineRunner initData(
			RoleRepository roleRepository,
			PlayerRepository playerRepository,
			GameRepository gameRepository,
			GamePlayerRepository gamePlayerRepository,
			ShipRepository shipRepository,
			SalvoRepository salvoRepository,
			ScoreRepository scoreRepository,
			PasswordEncoder encoder)
	{
		return (args) -> {
            // Roles
			Role user = roleRepository.save(new Role(RoleType.ROLE_USER));
			Role moderator = roleRepository.save(new Role(RoleType.ROLE_MODERATOR));
			Role admin = roleRepository.save(new Role(RoleType.ROLE_ADMIN));
			
			// Lista de roles para setear
			Set<Role> userRoles = new LinkedHashSet<>();
			Set<Role> adminRoles = new LinkedHashSet<>();
			
			userRoles.add(user);
			
			adminRoles.add(user);
			adminRoles.add(admin);
			
			// Players
			Player admin_acc = playerRepository.save(new Player("adm@adm.com", "admin", encoder.encode("admin")));
			Player player1 = new Player("awa@nyc.gov", "awa", encoder.encode("123456"));
			
			admin_acc.setRoles(adminRoles);
			player1.setRoles(userRoles);
			
			playerRepository.save(admin_acc);
			playerRepository.save(player1);

//			// Games
//			Game game1 = gameRepository.save(new Game());
//			
//			// GamePlayers
//			GamePlayer gp1 = gamePlayerRepository.save(new GamePlayer(game1, player1));
//			GamePlayer gp2 = gamePlayerRepository.save(new GamePlayer(game1, admin_acc));
//
//			game1.setStatus(GameStatus.PREPARE);
//			gameRepository.save(game1);
			
			// Ships
//			Ship ship1 = shipRepository.save(new Ship(gp1, ShipType.BATTLESHIP, Set.of("A1","A2","A3","A4")));
//			Ship ship2 = shipRepository.save(new Ship(gp1, ShipType.DESTROYER, Set.of("B1","B2","B3")));
//			Ship ship3 = shipRepository.save(new Ship(gp1, ShipType.CARRIER, Set.of("C1","C2","C3","C4","C5")));
//			Ship ship4 = shipRepository.save(new Ship(gp1, ShipType.SUBMARINE, Set.of("D1","D2","D3")));
//			Ship ship5 = shipRepository.save(new Ship(gp1, ShipType.PATROL_BOAT, Set.of("E1","E2")));
//			
//			Ship ship6 = shipRepository.save(new Ship(gp2, ShipType.BATTLESHIP, Set.of("A1","A2","A3","A4")));
//			Ship ship7 = shipRepository.save(new Ship(gp2, ShipType.DESTROYER, Set.of("B1","B2","B3")));
//			Ship ship8 = shipRepository.save(new Ship(gp2, ShipType.CARRIER, Set.of("C1","C2","C3","C4","C5")));
//			Ship ship9 = shipRepository.save(new Ship(gp2, ShipType.SUBMARINE, Set.of("D1","D2","D3")));
//			Ship ship0 = shipRepository.save(new Ship(gp2, ShipType.PATROL_BOAT, Set.of("E1","E2")));

//			Salvo salvo1 = salvoRepository.save(new Salvo(gp1, Set.of("A1", "A2", "A3", "A4", "A5")));
//			Salvo salvo2 = salvoRepository.save(new Salvo(gp2, Set.of("A1", "A2", "A3", "A4", "A5")));
	
			// Scores
//			Score score1 = scoreRepository.save(new Score(player1, game1, 1, LocalDateTime.now()));
//			Score score2 = scoreRepository.save(new Score(player2, game1, 0, LocalDateTime.now()));
		};
	}
}
