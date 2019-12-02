package com.mindhub.salvo.model;

import java.util.HashSet;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Player {

	// attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    private long id;
	
	@NotBlank
    @Column(name="nickName", unique=true)
    private String nickName;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();
    
    @OneToMany(mappedBy="player", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    Set<Score> scores;

    // constructors
    public Player() {}

    public Player(String email, String nickName, String password) {
    	this.email = email;
        this.nickName = nickName;
        this.password = password;
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
    
    public String getPassword() {
    	return this.password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }

    public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
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
    
    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }
    
    public void addScore (Score score) {
        score.setPlayer(this);
        scores.add(score);
    }
    
    public Score getGameScore (Game game) {
    	return scores.stream()
			.filter(score -> score.getGame().getId() == game.getId())
			.findAny()
			.orElse(null);
	}
    
    public double getScoresSum() {
        if (this.getScores() == null )
        	return 0;
        
    	double total = this.getScores()
        		.stream()
        		.mapToDouble(Score::getScore)
        		.sum();
    	
    	return total;
    }
    
    public Map<String, 	Object> playerDTO() {
		Map<String, Object> dto = new LinkedHashMap<String, Object>();
		dto.put("id", this.getId());
		dto.put("email", this.getEmail());
		dto.put("nickName", this.getNickName());
        
    	dto.put("points", this.getScoresSum());
    	return dto;
    }
    
    public Map<String, Object> scoresDTO() {
    	// calc W/L/D
    	int wins = 0, losses = 0, draws = 0;
        for (GamePlayer gp : this.getGamePlayers()) {
        	Score sc = this.getGameScore(gp.getGame());
        	if (sc != null) {
	        	float score = gp.getScore().getScore();
	        	wins  +=  (score == 1)   ? 1 : 0;
	        	losses +=  (score == 0)   ? 1 : 0; 
	        	draws  += (score == 0.5)  ? 1 : 0;
        	}
        }
        // populate dto
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
    	dto.put("nickName", this.getNickName());
    	dto.put("total", this.getScoresSum());
        dto.put("wins", wins);
        dto.put("draws", draws);
        dto.put("losses", losses);

    	return dto;
    }
}
