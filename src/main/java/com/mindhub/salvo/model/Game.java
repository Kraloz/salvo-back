package com.mindhub.salvo.model;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Game {

	// attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;
	
    private LocalDateTime created;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    Set<GamePlayer> gamePlayers;
    
    @OneToMany(mappedBy="game", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    Set<Score> scores;
    
    // constructors
    public Game() {
    	this.created = LocalDateTime.now();
    }
    
    // getters & setters
    public long getId() {
		return this.id;
	}
    
    public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	public Set<GamePlayer> getGamePlayers() {
		return this.gamePlayers;
	}
	
	public void setGameplayers(Set<GamePlayer> gamePlayers) {
		this.gamePlayers = gamePlayers;
	}
	
    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayer.setGame(this);
        gamePlayers.add(gamePlayer);
    }
    

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public void addScore (Score score) {
        score.setGame(this);
        scores.add(score);
    }
    
    private boolean isFull() {
    	if(this.getGamePlayers().size()<2) 
    		return false;
    	else
    		return true;
    }
    
    // behavior
    public Map<String, Object> gameDTO() {
	  Map<String, Object> dto = new LinkedHashMap<String, Object>();
	  dto.put("id", this.getId());
	  dto.put("created", this.getCreated());
	  dto.put("gamePlayers", this.getGamePlayers()
			  .stream()
			  .map(GamePlayer::gamesPlayersDTO));
	  dto.put("full", this.isFull());
	  
	  return dto;
	}
}
