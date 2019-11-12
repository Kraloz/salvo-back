package com.mindhub.salvo.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class GamePlayer {
		
	// attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "game_id")
	private Game game;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "player_id")
	private Player player;
	
    private LocalDateTime created;
    
    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Ship> ships = new HashSet<>();
    
    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Salvo> salvoes = new HashSet<>();    
    
	// constructors
	public GamePlayer() {}
	
	public GamePlayer(Game game, Player player) {
		this.game = game;
		this.player = player;
		this.created = LocalDateTime.now();
	}

	// getters & setters
	public long getId() {
		return this.id;
	}
	
	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public LocalDateTime getCreated() {
		return this.created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	public Set<Ship> getShips() {
		return ships;
	}
	
	public void addShip(Ship ship) {
		ship.setGamePlayer(this);
		this.ships.add(ship);
	}
	
	public void addShips(Set<Ship> ships) {
		ships.stream().forEach(ship -> this.addShip(ship));
	}
	
	public Set<Salvo> getSalvoes() {
		return salvoes;
	}
	
    public void addSalvo(Salvo salvo) {
        salvo.setGamePlayer(this);
        this.salvoes.add(salvo);
    }

    public void addSalvoes(Set<Salvo> salvoes){
        salvoes.stream().forEach(salvo -> this.addSalvo(salvo));
    }
    
    public Score getScore () {
		return this.player.getGameScore(this.game);
    }
	
	// DTOs
    public Map<String, Object> gamePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.game.getId());
        dto.put("created", this.game.getCreated());
        dto.put("gamePlayers" , this.game.getGamePlayers()
				.stream()
				.map(GamePlayer::gamesPlayersDTO));
        dto.put("ships", this.getShips()
        		.stream()
        		.map(Ship::shipDTO));
        dto.put("salvoes", this.game.getGamePlayers()
    			.stream()
    			.flatMap(gamePlayer -> gamePlayer.getSalvoes()
    					.stream()
    					.map(Salvo::salvoesDTO)));
     
        // seguramente esto esté mal o sobre
        if (this.getScore() != null)
            dto.put("score", this.getScore().getScore());
        else
            dto.put("score", this.getScore());
        return dto;
    }
    
    public Map<String, Object> gamesPlayersDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("player", this.player.playerDTO());
        Score score = this.player.getGameScore(this.game);
        if (score != null)
            dto.put("score", score.getScore());
        else
            dto.put("score", null);
        
        return dto;
    }
}
