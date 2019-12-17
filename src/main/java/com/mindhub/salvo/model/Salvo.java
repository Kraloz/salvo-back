package com.mindhub.salvo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Salvo (Set<String> shots, int turn) {
    	this.shots = shots;
        this.turn = turn;
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
    public Map<String, Object> salvoesDTO () {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", this.getTurn());
        dto.put("player", this.getGamePlayer().getPlayer().getId());
        dto.put("locations", this.getShots());    
        dto.put("hits", this.getHits());
        dto.put("sinks", this.getSinks());
        
        return dto;
    }

    
    private Optional<GamePlayer> getOpponentGamePlayer() {
        return this.getGamePlayer().getGame().getGamePlayers().stream().filter(gp -> gp.getId() != this.gamePlayer.getId()).findFirst();
    }

    private List<String> getHits () {
        return shots.stream()
                .filter(shot -> getOpponentGamePlayer().get().getShips().stream().anyMatch(ship -> ship.getCells().contains(shot)))
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getSinks() {
        List<String> allShots = new ArrayList<>();
         this.gamePlayer.getSalvoes().stream()
                 .filter(salvo -> salvo.getTurn() <= this.getTurn())
                 .forEach(salvo -> allShots.addAll(salvo.getShots()));
         List<Map<String, Object>> allSinks = new ArrayList<>();

         if (getOpponentGamePlayer().isPresent()) {
             allSinks = getOpponentGamePlayer().get().getShips().stream()
                     .filter(ship -> allShots.containsAll(ship.getCells()))
                     .map(Ship::shipDTO)
                     .collect(Collectors.toList());
         }
         
//	     Collections.sort(allSinks, (sink1, sink2) -> {
//	    	 Integer turn1 = (Integer) sink1.get("turn");
//	    	 Integer turn2 = (Integer) sink2.get("turn2");
//	    	    return turn1.compareTo(turn2);
//	    	});
         
         return allSinks;
    }    
}
