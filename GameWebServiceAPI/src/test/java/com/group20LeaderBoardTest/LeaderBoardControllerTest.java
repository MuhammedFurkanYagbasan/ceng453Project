package com.group20LeaderBoardTest;

import com.group20.WebSeveiceApiApplication;
import com.group20.Player.Player;
import com.group20.LeaderBoard.LeaderBoardController;
import com.group20.LeaderBoard.LeaderBoard;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("testLeaderBoardController")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebSeveiceApiApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LeaderBoardControllerTest {

	private LeaderBoardController mock = org.mockito.Mockito.mock(LeaderBoardController.class);

	@Autowired
	private LeaderBoardController leaderBoardController;


	private String date = "2018-12-13 02:10:10";
	private Player newPlayer = new Player(84, "test", "test");

	/*
	 * Test Case: Testing controlling can add player given leaderboard.
	 */
	@Test
	public void AaddPlayerToLeaderBoard() {
		LeaderBoard entry = new LeaderBoard(0, newPlayer.getId(), 500, date);
		Mockito.when(mock.addPlayerToLeaderBoard(entry)).thenReturn(entry);
		LeaderBoard entry2 = leaderBoardController.addPlayerToLeaderBoard(entry);
		Assert.assertEquals(entry.getP_id(),entry2.getP_id());
		Assert.assertEquals(entry.getDate(), entry2.getDate());
		Assert.assertEquals(entry.getScore(), entry2.getScore());

	}

	/*
	 * Test Case: Testing controlling can get weekly leaderboard.
	 */
	@Test
	public void BgetWeekLeaderBoard() {
		List<LeaderBoard> board = new ArrayList<>();
		LeaderBoard entry = new LeaderBoard(1, newPlayer.getId(), 500, date);
		board.add(entry);
		Mockito.when(mock.getWeekLeaderBoard()).thenReturn(board);
		List<LeaderBoard> board2 = leaderBoardController.getWeekLeaderBoard();
		Assert.assertEquals(board.get(0).getP_id(), board2.get(0).getP_id());
		Assert.assertEquals(board.get(0).getDate(), board2.get(0).getDate());
	}


	/*
	 * Test Case: Testing controlling can get all times leaderboard.
	 */
	@Test
	public void CgetAllLeaderBoard() {
		List<LeaderBoard> board = new ArrayList<>();
		LeaderBoard entry = new LeaderBoard(1, newPlayer.getId(), 500, date);
		board.add(entry);
		Mockito.when(mock.getAllLeaderBoard()).thenReturn(board);
		List<LeaderBoard> board2 = leaderBoardController.getAllLeaderBoard();	
		Assert.assertEquals(board.size(), board2.size());
		Assert.assertEquals(board.get(0).getP_id(), board2.get(0).getP_id());
		Assert.assertEquals(board.get(0).getDate(), board2.get(0).getDate());
	}

}
