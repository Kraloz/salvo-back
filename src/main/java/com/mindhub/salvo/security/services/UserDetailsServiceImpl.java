package com.mindhub.salvo.security.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mindhub.salvo.model.Player;
import com.mindhub.salvo.repository.PlayerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	PlayerRepository playerRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Player user = playerRepository.findByNickName(username);
//				usar esto cuando findByNickname devuelva Optional
//				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

}
