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
			Player player2 = new Player("ewe@nyc.gov", "ewe", encoder.encode("123456"));
			Player player3 = new Player("iwi@nyc.gov", "iwi", encoder.encode("123456"));
			Player player4 = new Player("owo@nyc.gov", "owo", encoder.encode("123456"));
			Player player6 = new Player("uwu@nyc.gov", "uwu", encoder.encode("123456"));					
			
			admin_acc.setRoles(adminRoles);
			player1.setRoles(userRoles);
			player2.setRoles(userRoles);
			player3.setRoles(userRoles);
			player4.setRoles(userRoles);
			player6.setRoles(userRoles);
			
			playerRepository.save(admin_acc);
			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);
			playerRepository.save(player6);

			// Games
			Game game1 = gameRepository.save(new Game());
			Game game2 = gameRepository.save(new Game());
			Game game3 = gameRepository.save(new Game());
			
			// GamePlayers
			GamePlayer gp1 = gamePlayerRepository.save(new GamePlayer(game1, player1));
			GamePlayer gp2 = gamePlayerRepository.save(new GamePlayer(game1, player2));
			GamePlayer gp3 = gamePlayerRepository.save(new GamePlayer(game2, player1));
			GamePlayer gp4 = gamePlayerRepository.save(new GamePlayer(game3, admin_acc));
			
			// Ships
			Ship ship1 = shipRepository.save(new Ship(gp1, ShipType.BATTLESHIP, List.of("A1","A2","A3","A4")));
			Ship ship2 = shipRepository.save(new Ship(gp1, ShipType.DESTROYER, List.of("B1","B2","B3")));
			Ship ship3 = shipRepository.save(new Ship(gp1, ShipType.CARRIER, List.of("C1","C2","C3","C4","C5")));
			Ship ship4 = shipRepository.save(new Ship(gp1, ShipType.SUBMARINE, List.of("D1","D2","D3")));
			Ship ship5 = shipRepository.save(new Ship(gp1, ShipType.PATROL_BOAT, List.of("E1","E2")));

			Salvo salvo1 = salvoRepository.save(new Salvo(gp1, List.of("A1","E3","A4")));
			Salvo salvo2 = salvoRepository.save(new Salvo(gp2, List.of("F4","C2","G7")));
			Salvo salvo3 = salvoRepository.save(new Salvo(gp3, List.of("A1","E3","A4")));
			Salvo salvo4 = salvoRepository.save(new Salvo(gp3, List.of("F4","C2","G7")));
			
			// Scores
			Score score1 = scoreRepository.save(new Score(player1, game1, 1, LocalDateTime.now()));
			Score score2 = scoreRepository.save(new Score(player2, game1, 0, LocalDateTime.now()));
		};
	}
}
