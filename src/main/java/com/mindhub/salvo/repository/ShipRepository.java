package com.mindhub.salvo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mindhub.salvo.model.Ship;

@RepositoryRestResource
public interface ShipRepository extends JpaRepository<Ship, Long> {
}