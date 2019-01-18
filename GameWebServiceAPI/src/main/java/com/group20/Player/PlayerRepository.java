package com.group20.Player;

import org.springframework.data.repository.CrudRepository;

import com.group20.Player.Player;

//This class is PlayerRepository which connect database and set or get Player Entities. 

public interface PlayerRepository extends CrudRepository<Player, Integer> {
	
	public Player findById(int id);

	public Player findByNickname(String nickname);

}
