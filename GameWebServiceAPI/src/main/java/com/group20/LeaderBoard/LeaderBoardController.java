package com.group20.LeaderBoard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class LeaderBoardController {
	
	//This private variable connect database and webservice.
	@Autowired
	private LeaderBoardRepository leaderBoardRepository;
	
	//This function get highest scores in descending order with /leaderboardalltimes url.
	@RequestMapping("/leaderboardalltimes")
	public List<LeaderBoard> getAllLeaderBoard() {
		return  leaderBoardRepository.getLeaderBoardAll();
	}
	
	//This function get highest scores for weekly in descending order with /leaderboardweekly url.
	@RequestMapping("/leaderboardweekly")
	public List<LeaderBoard> getWeekLeaderBoard() {
		return leaderBoardRepository.getLeaderBoardWeekly();
	}
	
	
	//This function insert players score and id to leaderboard
	@RequestMapping(method=RequestMethod.POST, value="/leaderboard")
	public LeaderBoard addPlayerToLeaderBoard(@RequestBody LeaderBoard leaderBoardEntry) {
		leaderBoardRepository.save(leaderBoardEntry);
		return leaderBoardEntry;
	}
	
}
