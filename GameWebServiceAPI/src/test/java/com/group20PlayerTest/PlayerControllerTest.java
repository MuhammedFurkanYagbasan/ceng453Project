package com.group20PlayerTest;

import com.group20.WebSeveiceApiApplication;
import com.group20.Player.Player;
import com.group20.Player.PlayerController;


import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

@ActiveProfiles("testPlayerController")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebSeveiceApiApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerControllerTest {
	private PlayerController mock = org.mockito.Mockito.mock(PlayerController.class);
	
	@Autowired
    private PlayerController playerController;
	
	private Player newPlayer = new Player(0,"test","test");
	
	private Player newPlayer2 = new Player(0,"test2","test2");
	
	/*
	 * Test Case: Testing controlling can add player to database.
	 * 
	 */
	@Test
    public void AplayerAdd(){
        Mockito.when(mock.addPlayer(newPlayer)).thenReturn(newPlayer);
        Player responseNewPlayer = playerController.addPlayer(newPlayer);
        assertEquals(newPlayer,responseNewPlayer);
    }
	
	/*
	 * Test Case: Testing controlling cannot access not exist player id.
	 * 
	 */
	@Test
    public void BnotExistingPlayerId() {
        Mockito.when(mock.getPlayer(-1)).thenReturn(null);
        Player player2 = playerController.getPlayer(-1);
        Assert.assertEquals(player2,null);
    }
	
	/*
	 * Test Case: Testing controlling can update player.
	 * 
	 */
	@Test
    public void CcanUpdatePlayer() {
        Mockito.when(mock.updatePlayer(newPlayer2,newPlayer2.getId())).thenReturn(newPlayer2);
        Player player2 = playerController.updatePlayer(newPlayer2,newPlayer2.getId());
        Assert.assertEquals(player2.getNickname(),newPlayer2.getNickname());
        Assert.assertEquals(player2.getPassword(),newPlayer2.getPassword());
    }
	
	/*
	 * Test Case: Testing controlling cannot authenticate true password exist player.
	 * 
	 */
	@Test
    public void DauthenticateTruePassword() {
		Mockito.when(mock.authenticatePlayer(newPlayer2)).thenReturn(true);
        boolean acceses = playerController.authenticatePlayer(newPlayer2);
        Assert.assertEquals(true,acceses);
    }
	
	/*
	 * Test Case: Testing controlling cannot authenticate wrong password exist player or notexist player .
	 * 
	 */
	@Test
    public void EauthenticateWrongPassword() {
		Player a = new Player(0,"dontexist","dontexist");
        Mockito.when(mock.authenticatePlayer(a)).thenReturn(false);
        boolean acceses = playerController.authenticatePlayer(a);
        Assert.assertEquals(false,acceses);
    }
	
	/*
	 * Test Case: Testing controlling can access exist given player.
	 * 
	 */
	
	@Test
    public void FcheckExistPlayer() {
		Mockito.when(mock.checkPlayer(newPlayer2.getNickname())).thenReturn(true);
        boolean acceses = playerController.checkPlayer(newPlayer2.getNickname());
        Assert.assertEquals(true,acceses);
    }
	
	/*
	 * Test Case: Testing controlling cannot access  given player.
	 * 
	 */
	@Test
    public void GcheckNotExistPlayer() {
        Mockito.when(mock.checkPlayer("dontexist")).thenReturn(false);
        boolean acceses = playerController.checkPlayer("dontexist");
        Assert.assertEquals(false,acceses);
    }
	
	/*
	 * Test Case: Testing controlling can delete  given player.
	 * 
	 */
	@Test
    public void HcanDeletePlayer() {
		List<Player> players = playerController.getAllPlayers();
		Player a = players.get(0);
        Mockito.when(mock.deletePlayer(a.getId())).thenReturn(a);
        Player player = playerController.deletePlayer(a.getId());
        Assert.assertEquals(a.getNickname(),player.getNickname());
    }
	
	/*
	 * Test Case: Testing controlling can access allplayer in database.
	 * 
	 */
	@Test
    public void IallUsers() {
		List<Player> players = new ArrayList<>();
		players.add(newPlayer2);
		Mockito.when(mock.getAllPlayers()).thenReturn(players);
		List<Player> players2 = playerController.getAllPlayers();
        Assert.assertEquals(players.size(),players2.size());
    }

	/*
	 * Test Case: Deleting non-existing Player from database
	 * 
	 */
	@Test
    public void JtryDeleteNonExistPlayer(){
          doThrow(new EmptyResultDataAccessException(1)).when(mock).deletePlayer(newPlayer.getId());
    }
	
}
