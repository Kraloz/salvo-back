package com.mindhub.salvo.model;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.Set;

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
    private Set<String> shots = new HashSet<>();
    
    private int turn;
    
    // constructors
    public Salvo () { }

    public Salvo (GamePlayer gamePlayer, Set<String> shots) {
        this.gamePlayer = gamePlayer;
    	this.shots = shots;
    }
    
    public Salvo (Set<String> shots) {
        this.shots = shots;
    }

    public Salvo (int turn, Set<String> shots) {
        this.turn = turn;
        this.shots = shots;
    }

    
    // getters & setters

	public long getId() {
		return id;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getTurn() {
		return turn;
	}
	
	public GamePlayer getGamePlayer() {
		return gamePlayer;
	}

	public void setGamePlayer(GamePlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
	}

	public Set<String> getShots() {
		return shots;
	}

	public void setShots(Set<String> shots) {
		this.shots = shots;
	}
	
   public void addShots (List<String> shots) {
        this.shots.addAll(shots);
    }

    // behavior
    public Map<String, Object> salvoesDTO (){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", this.getTurn());
        dto.put("player", this.getGamePlayer().getPlayer().getId());
        dto.put("locations", this.getShots());        
        
        return dto;
    }
}
