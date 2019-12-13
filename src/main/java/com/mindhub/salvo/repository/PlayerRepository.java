package com.mindhub.salvo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mindhub.salvo.model.Player;

@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player, Long> {
	Optional<Player> findByNickName(String nickName);
	

	Boolean existsByNickName(String nickName);
	Boolean existsByEmail(String email);
}
