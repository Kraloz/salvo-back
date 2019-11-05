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
public class Salvo {

	// attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_player_id")
    private GamePlayer gamePlayer;
	
    @ElementCollection
    @Column(name="salvo_locations")
    private List<String> shots = new ArrayList<>();

    // constructors
    public Salvo () { }

    public Salvo (GamePlayer gamePlayer, List<String> shots) {
        this.gamePlayer = gamePlayer;
    	this.shots = shots;
    }
    
    public Salvo (List<String> shots) {
        this.shots = shots;
    }

    // getters & setters
	public GamePlayer getGamePlayer() {
		return gamePlayer;
	}

	public void setGamePlayer(GamePlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
	}

	public List<String> getShots() {
		return shots;
	}

	public void setShots(List<String> shots) {
		this.shots = shots;
	}

	public long getId() {
		return id;
	}

    // behavior
    public Map<String, Object> salvoesDTO (){
        Map<String, Object> dto = new LinkedHashMap<>();        
        dto.put("player", this.getGamePlayer().getPlayer().getId());
        dto.put("locations", this.getShots());
        
        return dto;
    }
}
