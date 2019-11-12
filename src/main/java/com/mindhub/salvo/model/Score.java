package com.mindhub.salvo.model;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Score {

	// attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long id;
	
	private float score = 0;
	
	private LocalDateTime finishDate;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "player_id")
	private Player player;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "game_id")
	private Game game;
	
	// constructors
	public Score() {}
	
	public Score(Player player, Game game, float score, LocalDateTime finishDate) {
		this.player = player;
		this.game = game;
		this.score = score;
		this.finishDate = finishDate;
	}
	
	// getters & setters	
	public Long getId() {
		return id;
	}
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public float getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public LocalDateTime getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(LocalDateTime finishDate) {
		this.finishDate = finishDate;
	}	
	
	// behavior
	public Map<String, Object> scoreDTO() {
		Map<String, Object> dto = new LinkedHashMap<>();
		dto.put("score", this.score);
		
		return dto;
	}

}
