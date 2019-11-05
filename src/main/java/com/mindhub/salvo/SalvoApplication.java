package com.mindhub.salvo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
			PlayerRepository playerRepository,
			GameRepository gameRepository,
			GamePlayerRepository gamePlayerRepository,
			ShipRepository shipRepository,
			SalvoRepository salvoRepository) {
		return (args) -> {
			Player player1 = playerRepository.save(new Player("awa@nyc.gov", "awa"));
			Player player2 = playerRepository.save(new Player("ewe@nyc.gov", "ewe"));
			Player player3 = playerRepository.save(new Player("iwi@nyc.gov", "iwi"));
			Player player4 = playerRepository.save(new Player("owo@nyc.gov", "owo"));
			Player player6 = playerRepository.save(new Player("uwu@nyc.gov", "uwu"));
			
			Game game1 = gameRepository.save(new Game());
			Game game2 = gameRepository.save(new Game());
			
			GamePlayer gp1 = gamePlayerRepository.save(new GamePlayer(game1, player1));
			GamePlayer gp2 = gamePlayerRepository.save(new GamePlayer(game1, player2));
			GamePlayer gp3 = gamePlayerRepository.save(new GamePlayer(game2, player1));
			
			Ship ship1 = shipRepository.save(new Ship(gp1, ShipType.BATTLESHIP, List.of("A1","A2","A3","A4")));
			Ship ship2 = shipRepository.save(new Ship(gp1, ShipType.DESTROYER, List.of("B1","B2","B3")));
			Ship ship3 = shipRepository.save(new Ship(gp1, ShipType.CARRIER, List.of("C1","C2","C3","C4","C5")));
			Ship ship4 = shipRepository.save(new Ship(gp1, ShipType.SUBMARINE, List.of("D1","D2","D3")));
			Ship ship5 = shipRepository.save(new Ship(gp1, ShipType.PATROL_BOAT, List.of("E1","E2")));
			
			Ship ship6 = shipRepository.save(new Ship(gp2, ShipType.BATTLESHIP, List.of("I1","I2","I3","I4")));
			Ship ship7 = shipRepository.save(new Ship(gp2, ShipType.SUBMARINE, List.of("J1","J2","J3")));
			
			Ship ship8 = shipRepository.save(new Ship(gp3, ShipType.BATTLESHIP, List.of("I1","I2","I3","I4")));
			Ship ship9 = shipRepository.save(new Ship(gp3, ShipType.SUBMARINE, List.of("J1","J2","J3")));
			
			Salvo salvo1 = salvoRepository.save(new Salvo(gp1, List.of("A1","E3","A4")));
			Salvo salvo2 = salvoRepository.save(new Salvo(gp2, List.of("F4","C2","G7")));
			Salvo salvo3 = salvoRepository.save(new Salvo(gp3, List.of("A1","E3","A4")));
			Salvo salvo4 = salvoRepository.save(new Salvo(gp3, List.of("F4","C2","G7")));
		};
	}
}
