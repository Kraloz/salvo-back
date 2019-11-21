package com.mindhub.salvo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mindhub.salvo.model.Player;
import com.mindhub.salvo.repository.PlayerRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Player player = playerRepository.findByNickName(username);
		
		if (player == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(player.getNickName(), player.getPassword(),
				new ArrayList<>());
	}
	
	public Player save(Player player) {
		Player p = new Player();
		p.setNickName(player.getNickName());
		p.setPassword(bcryptEncoder.encode(player.getPassword()));
		return playerRepository.save(p);
	}	
}
