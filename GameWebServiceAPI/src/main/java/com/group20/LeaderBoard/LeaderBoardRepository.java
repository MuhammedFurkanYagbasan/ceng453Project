package com.group20.LeaderBoard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

//This class is LeaderBoardRepository which connect database and set or get LeaderBoard Entities. 


public interface LeaderBoardRepository extends CrudRepository<LeaderBoard,Integer> {
	
	//In basic Repository, there is no function to get highest scores in descending order
	//, so we write a query to get entities.
	@Query(nativeQuery = true, value="SELECT min(L.l_id) as l_id, L.p_id, sum(L.score) as score,max(date) as date  FROM LeaderBoard as L  Group by L.p_id Order by score DESC")
	List<LeaderBoard> getLeaderBoardAll();
	
	//In basic Repository, there is no function to get highest score for this week in descending order 
	//, so we write a query to get entities.
	@Query(nativeQuery = true, value="SELECT min(L.l_id) as l_id,L.p_id, sum(L.score) as score,max(date) as date FROM LeaderBoard as L WHERE L.date >= DATE_ADD(CURDATE(),INTERVAL -7 DAY) Group by L.p_id Order by sum(L.score) DESC")
	List<LeaderBoard> getLeaderBoardWeekly();

}
