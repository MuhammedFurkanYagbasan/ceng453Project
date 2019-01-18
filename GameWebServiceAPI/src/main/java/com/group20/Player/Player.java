package com.group20.Player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//This class is player class.

@Entity
@Table(name = "Player")
public class Player {
	// p_id Column is id for Players. It is unique for all players.
	// nickname Column is name for user authentication in sign up user. 
	// password Column is password for user authentication in sign up user.
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int p_id; 
	private String nickname;
	private String password;
	
	//Empty Constructor
	public Player() {}
	
	//Basic Constructor
	public Player(int id, String nickname, String password) {
		super();
		this.p_id = id;
		this.nickname = nickname;
		this.password = password;
	}
	//Getter for Player id
	public int getId() {
		return p_id;
	}
	//Setter for Player id 
	public void setId(int id) {
		this.p_id = id;
	}
	//Getter for Player nickname
	public String getNickname() {
		return nickname;
	}
	//Setter for Player nickname
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	//Getter for Player password
	public String getPassword() {
		return password;
	}
	//Setter for Player password
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
