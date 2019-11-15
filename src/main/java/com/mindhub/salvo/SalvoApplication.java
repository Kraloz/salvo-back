package com.mindhub.salvo;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.mindhub.salvo.model.*;
import com.mindhub.salvo.model.Ship.ShipType;
import com.mindhub.salvo.repository.*;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@SuppressWarnings("unused")
	@Bean
	public CommandLineRunner initData(
			PlayerRepository playerRepository,
			GameRepository gameRepository,
			GamePlayerRepository gamePlayerRepository,
			ShipRepository shipRepository,
			SalvoRepository salvoRepository,
			ScoreRepository scoreRepository) 
	{
		return (args) -> {
			Player player1 = playerRepository.save(new Player("awa@nyc.gov", "awa", passwordEncoder().encode("123")));
			Player player2 = playerRepository.save(new Player("ewe@nyc.gov", "ewe", passwordEncoder().encode("123")));
			Player player3 = playerRepository.save(new Player("iwi@nyc.gov", "iwi", passwordEncoder().encode("123")));
			Player player4 = playerRepository.save(new Player("owo@nyc.gov", "owo", passwordEncoder().encode("123")));
			Player player6 = playerRepository.save(new Player("uwu@nyc.gov", "uwu", passwordEncoder().encode("123")));
			
			Player admin = playerRepository.save(new Player("adm@adm.com", "admin", passwordEncoder().encode("admin")));
			
			Game game1 = gameRepository.save(new Game());
			Game game2 = gameRepository.save(new Game());
			
			GamePlayer gp1 = gamePlayerRepository.save(new GamePlayer(game1, player1));
			GamePlayer gp2 = gamePlayerRepository.save(new GamePlayer(game1, player2));
			GamePlayer gp3 = gamePlayerRepository.save(new GamePlayer(game2, player1));
			
			Ship ship1 = shipRepository.save(new Ship(gp1, ShipType.BATTLESHIP, List.of("A1","A2","A3","A4")));
			Ship ship2 = shipRepository.save(new Ship(gp1, ShipType.DESTROYER, List.of("B1","B2","B3")));
			Ship ship3 = shipRepository.save(new Ship(gp1, ShipType.CARRIER, List.of("C1","C2","C3","C4","C5")));
			Ship ship4 = shipRepository.save(new Ship(gp1, ShipType.SUBMARINE, List.of("D1","D2","D3")));
			Ship ship5 = shipRepository.save(new Ship(gp1, ShipType.PATROL_BOAT, List.of("E1","E2")));
			
			Ship ship6 = shipRepository.save(new Ship(gp2, ShipType.BATTLESHIP, List.of("I1","I2","I3","I4")));
			Ship ship7 = shipRepository.save(new Ship(gp2, ShipType.SUBMARINE, List.of("J1","J2","J3")));
			
			Ship ship8 = shipRepository.save(new Ship(gp3, ShipType.BATTLESHIP, List.of("I1","I2","I3","I4")));
			Ship ship9 = shipRepository.save(new Ship(gp3, ShipType.SUBMARINE, List.of("J1","J2","J3")));
			
			Salvo salvo1 = salvoRepository.save(new Salvo(gp1, List.of("A1","E3","A4")));
			Salvo salvo2 = salvoRepository.save(new Salvo(gp2, List.of("F4","C2","G7")));
			Salvo salvo3 = salvoRepository.save(new Salvo(gp3, List.of("A1","E3","A4")));
			Salvo salvo4 = salvoRepository.save(new Salvo(gp3, List.of("F4","C2","G7")));
			
			Score score1 = scoreRepository.save(new Score(player1, game1, 1, LocalDateTime.now()));
			Score score2 = scoreRepository.save(new Score(player2, game1, 0, LocalDateTime.now()));
		};
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName -> {
            Player player = playerRepository.findByNickName(inputName);
            if (player != null) {
                if(player.getNickName().equals("admin")){
                    System.out.println("RESPONSE -> Loged and getting data");
                    return new User(player.getNickName(), player.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                }else {
                    System.out.println("RESPONSE -> Loged and getting data");
                    return new User(player.getNickName(), player.getPassword(),
                            AuthorityUtils.createAuthorityList("USER"));
                }
            } else {
                throw new UsernameNotFoundException("RESPONSE -> Usuario desconocido : " + inputName);
            }
        }).passwordEncoder(passwordEncoder);
    }
}


@EnableWebSecurity
@Configuration
class WebSecurityRoutes extends WebSecurityConfigurerAdapter {

	  @Override
	    protected void configure(HttpSecurity http) throws Exception {

	        http
	            .authorizeRequests()
	                .antMatchers("/api/games", "/api/players", "/api/leaderboard").permitAll()
	                .antMatchers("/rest/**").hasAuthority("ADMIN")
	                .antMatchers("/api/**").hasAnyAuthority("ADMIN", "USER")
	                .antMatchers("/web/game.html").hasAnyAuthority("ADMIN", "USER")
	                .and()
	            .formLogin()
	                .usernameParameter("name")
	                .passwordParameter("pwd")
	                .loginPage("/api/login")
	                .permitAll()
	                .and()
	            .logout()
	                .permitAll();

	        http.logout().logoutUrl("/api/logout");

	        // turn off checking for CSRF tokens
	        http.csrf().disable();

	        // if user is not authenticated, just send an authentication failure response
	        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

	        // if login is successful, just clear the flags asking for authentication
	        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

	        // if login fails, just send an authentication failure response
	        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

	        // if logout is successful, just send a success response
	        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	    }

	    private void clearAuthenticationAttributes(HttpServletRequest request) {
	        HttpSession session = request.getSession(false);
	        if (session != null) {
	            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	        }
	    }
}
