package com.group20.Player;

//This class is Player control class.If restweb servise send request for adding , getting,updating, deleting or authenticating user, 
//this class handles requests with private playerRepository class.

import java.util.ArrayList;
import java.util.List;

import com.group20.Player.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PlayerController {
	//This private variable connect database and webservice.
	@Autowired
	private PlayerRepository playerRepository;
	
	//This function get all players.Findall function get all entries but it returns iterable object, 
	// so we create player list and add all players
	@RequestMapping("/players")
	public List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<>();
		playerRepository.findAll().forEach(players::add);
		return players;
	}
	
	//This function get player which id is same.findone function is build in function in Repository.
	@RequestMapping("/players/{id}")
	public Player getPlayer(@PathVariable int id) {
		return playerRepository.findById(id);
	}
	
	//This function add player to database.
	@RequestMapping(method=RequestMethod.POST, value="/addplayer")
	public Player addPlayer(@RequestBody Player player) {
		Password password= new Password();
		String hashpassword = password.hash(player.getPassword());
		player.setPassword(hashpassword);
		playerRepository.save(player);
		return player;
	}
	
	//This function update player. In this case, update and add function look like same 
	//but Repository class update if this player is exist.
	@RequestMapping(method=RequestMethod.PUT, value="/updateplayer/{id}")
	public Player updatePlayer(@RequestBody Player player, @PathVariable int id) {
		player.setId(id);
		Password password= new Password();
		String hashpassword = password.hash(player.getPassword());
		player.setPassword(hashpassword);
		playerRepository.save(player);
		return player;
	}
	
	//This function delete player from database.
	@RequestMapping(method=RequestMethod.DELETE, value="/deleteplayer/{id}")
	public Player deletePlayer(@PathVariable int id) {
		Player p = playerRepository.findById(id);
		playerRepository.deleteById(id);
		return p;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/checkplayer")
	public boolean checkPlayer(@RequestBody String nickname) {
		List<Player> players = new ArrayList<>();
		playerRepository.findAll().forEach(players::add);
		int pnum=players.size();
		for(int i=0;i<pnum;i++) {
			if(players.get(i).getNickname().equals(nickname)){
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/getPlayerId/{nickname}")
	public int getPlayerIdFromNickname(@PathVariable String nickname) {
		List<Player> players = new ArrayList<>();
		playerRepository.findAll().forEach(players::add);
		int pnum=players.size();
		for(int i=0;i<pnum;i++) {
			if(players.get(i).getNickname().equals(nickname)){
				return players.get(i).getId();
			}
		}
		return -1;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/canLogIn")
	public boolean authenticatePlayer(@RequestBody Player player) {
		Player p = playerRepository.findByNickname(player.getNickname());
		Password password= new Password();
		if( p!= null && password.authenticate(player.getPassword() , p.getPassword()) ){
			return true;	
		}
		return false;
	}
	
}
