package com.mindhub.salvo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mindhub.salvo.model.Player;


@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player, Long> {
	Player findByNickName(String nickName);
	Player findByEmail(String email);
}
