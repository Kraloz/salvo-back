package com.mindhub.salvo.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Player {

	// attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long id;
	private String email;
	
    @Column(name="nickName", unique=true)
    private String nickName;
    
    @OneToMany(mappedBy="player", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    Set<GamePlayer> gamePlayers;

    // constructors
    public Player() { }

    public Player(String email, String nickName) {
    	this.email = email;
        this.nickName = nickName;
    }

    // getters & setters
 	public long getId() {
 		return this.id;
 	}
 	
    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public String getEmail() {
    	return this.email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayer.setPlayer(this);
        gamePlayers.add(gamePlayer);
    }
    
    public Map<String, Object> playerDTO() {
		Map<String, Object> dto = new LinkedHashMap<String, Object>();
		dto.put("id", this.getId());
		dto.put("email", this.getEmail());
		dto.put("nickName", this.getNickName());
    	
    	return dto;
    }
}
