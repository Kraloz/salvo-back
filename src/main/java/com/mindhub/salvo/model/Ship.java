package com.mindhub.salvo.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Ship {
	public enum ShipType {
		BATTLESHIP,
		DESTROYER,
		CARRIER,
		SUBMARINE,	    
		PATROL_BOAT
	}

	// attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "gameplayer_id")
	private GamePlayer gamePlayer;
	
	@ElementCollection
	@Column(name="shipLocation")
	private List<String> cells = new ArrayList<>();

	private ShipType type;
	
	// contructors
	public Ship() {}
	
	public Ship(GamePlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
	}
	
	public Ship(ShipType type, List<String> cells) {
		this.type = type;
		this.cells = cells;
	}
	
	public Ship(GamePlayer gamePlayer, ShipType type , List<String> cells) {
		this.gamePlayer = gamePlayer;
		this.type = type;
		this.cells = cells;
	}

	// getters & setters
	public long getId() {
		return id;
	}
	
	public GamePlayer getGamePlayer() {
		return gamePlayer;
	}

	public void setGamePlayer(GamePlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
	}

	public List<String> getCells() {
		return cells;
	}

	public void setCells(List<String> cells) {
		this.cells = cells;
	}
	
	public ShipType getType() {
		return type;
	}
	
	public void setType(ShipType type) {
		this.type = type;
	}
	
	public Map<String, Object> shipDTO() {
		Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("type", this.getType());
        dto.put("locations", this.getCells());
        return dto;
	}
}
