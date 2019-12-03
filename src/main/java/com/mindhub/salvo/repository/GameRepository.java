package com.mindhub.salvo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mindhub.salvo.model.Game;
import com.mindhub.salvo.model.Player;

@RepositoryRestResource
public interface GameRepository extends JpaRepository<Game, Long> {
	Optional<Game> findById(Long id);
}
