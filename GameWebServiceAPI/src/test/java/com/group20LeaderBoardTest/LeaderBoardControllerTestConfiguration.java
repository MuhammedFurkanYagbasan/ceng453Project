package com.group20LeaderBoardTest;

import com.group20.LeaderBoard.LeaderBoardController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("testLeaderBoardController")
@Configuration
public class LeaderBoardControllerTestConfiguration {
	@Bean
    @Primary
    public LeaderBoardController scoreBoardCont(){
        return Mockito.mock(LeaderBoardController.class);

	}
}
