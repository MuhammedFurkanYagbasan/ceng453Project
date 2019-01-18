package com.group20PlayerTest;

import com.group20.Player.PlayerController;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("testPlayerController")
@Configuration
public class PlayerControllerTestConfiguration {

	@Bean
    @Primary
    public PlayerController playerController(){
        return Mockito.mock(PlayerController.class);
    }
}
