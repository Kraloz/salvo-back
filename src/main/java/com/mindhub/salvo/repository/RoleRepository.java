package com.mindhub.salvo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mindhub.salvo.model.Role;
import com.mindhub.salvo.model.RoleType;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(RoleType name);
}
