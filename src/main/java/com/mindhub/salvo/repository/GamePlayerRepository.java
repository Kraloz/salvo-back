package com.mindhub.salvo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.mindhub.salvo.model.GamePlayer;

@RepositoryRestResource
public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long> {

	// select * from GAME_PLAYER gp where gp.GAME_ID = gameId AND gp.PLAYER_ID = playerId
	@Query(value = "select * from GAME_PLAYER gp where gp.GAME_ID = :gameId AND gp.PLAYER_ID = :playerId", nativeQuery = true)
	Optional<GamePlayer> findByGameIdAndPlayerId(@Param("gameId") Long gameId, @Param("playerId") Long playerId);
}
