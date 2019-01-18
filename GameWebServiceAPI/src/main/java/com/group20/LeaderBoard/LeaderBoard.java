package com.group20.LeaderBoard;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

//This class is LeaderBoard class. It basically hold Scores.

@Entity
@Table(name = "LeaderBoard")
public class LeaderBoard {
	//l_id Column is id for LeaderBoard entries. It is unique for all entries.
	//date Column hold date for entries. Pattern is "Year-Month-Day Hour:Minutes:Second" ex:"2018-10-11 12:00:00" or "2017-01-10 22:21:20"
	//score Column hold players points for a single game. 
	//p_id Column hold player id and it is foreign key which refer to player table. 
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int l_id;  
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private String date;
	private int score;
	
	private int p_id;
	
	//Empty Constructor
	public LeaderBoard(){}
	
	//Basic Constructor
	public LeaderBoard(int l_id, int p_id, int score , String date){
		super();
		this.l_id = l_id;
		this.p_id = p_id;
		this.score = score;
		this.date = date;
	}
	
	//Getter for entries id
	public Integer getL_id() {
		return l_id;
	}
	
	//Setter for entries id
	public void setL_id(Integer l_id) {
		this.l_id = l_id;
	}
	
	//Getter for date
	public String getDate() {
		return date;
	}
	
	//Setter for date
	public void setDate(String date) {
		this.date = date;
	}
	
	//Getter for score
	public Integer getScore() {
		return score;
	}
	
	//Setter for score
	public void setScore(Integer score) {
		this.score = score;
	}
	
	//Getter for player id
	public int getP_id() {
		return p_id;
	}
	
	//Setter for player id
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	
	
	
	
	
}
