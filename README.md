# The Game

The game  is a simple two-dimensional shooter game. Player controls an auto-shooting spaceship by moving it freely (with mouse) in two dimensions to shoot aliens. The game includes four levels. First three levels will be single player levels and the final level will be a multiplayer level. In single player levels, player will destroy all the aliens alone. In the multiplayer level, two players will destroy the same set of aliens and the goal will be to destroy more aliens than other player.The player that hits an alien last time before it is destroyed is given the full credit of destroying that alien. The game ends after the fourth level.

## Levels And Aliens

  - There are four levels.
  - First level includes two waves of aliens. First wave includes red aliens. Second wave includes orange and red aliens.
  - Second level includes one wave of aliens which are orange.
  - Third level includes one type of alien which is final green boss.
  - Fourth level (multiplayer level) includes one wave of aliens which is green ones.  If the other player is dead, you gain twenty point.
  - Red aliens have one health point and one firepower. If you kill a red alien, you get one point.
  - Orange aliens have two health points and two firepowers. If you kill a orange alien, you get two points.
  - Green aliens have twenty health points and ten firepowers. If you kill a green alien, you get ten  points.
 

## How to play?
First of all, player must be registered to the game and login with nickname and password. With the new game button, four level adventure is starting . You are captain of a spaceship and the fate of the universe is in your hands. You have very good shooters behind the laser canons. To shoot the aliens, you must direct your ship. After completing first three levels, you wait for backup. When the back up arrives, you start to walk another challenging road. If you can strike more aliens then your backup, a feast is waiting for you in your planet.

## Features
 You can change difficulties, levels or aliens in below code and you can create another type of allien in Enemy object. 
To change single player levels, You can edit function below.
```java
public static void enemySlotEmpty() {
	slotNum++;
	switch (slotNum){
		case 1: // First level first wave
			generateEnemies(8,1); //  8 red aliens
			break;
		case 2: // First level second wave
			generateEnemies(4, 1); //  4 red aliens
			generateEnemies(4, 2); //  4 orange aliens
	  		break;
	  	case 3: // Second level first wave
			GamePage.updateLevelLabel(++level);
			generateEnemies(10,2); //  10 orange aliens
			break;
  		case 4: // Third level first wave
			GamePage.updateLevelLabel(++level);
			generateEnemies(1,3); // 1 green aliens
			break;
	  	case 5: // waiting other player
			GamePage.updateLevelLabel(++level);
			MultiplayerWaitingPage.show(); 
			NetworkClient.connectToServer();
	  		break;
		default:
	  		ResultPage.show("Score: " + player.getScore());
	 }
}
```

To change multiplayer level, You can edit function below.
```java
public static void enemySlotEmpty() {
	slotNum++;
	switch (slotNum){
		case 1: // First level first wave
			generateEnemies(3,3); //  3 green aliens
			break;
	 }
}
```


## Authors

* **Muhammed Furkan Yağbasan**  - [e2099505](http://144.122.71.144:8083/e2099505)
* **Barış Yerci**  - [e2099513](http://144.122.71.144:8083/e2099513)


## Tech

The Game uses a number of open source projects to work properly:

* [JavaFX] - awesome framework
* [MariaDB]- database
* [Spring Boot]- web service 
* [Apache Tomcat]- server 

[JavaFX]:<http://javafx.com>
[MariaDB]:<https://mariadb.org/>
[Spring Boot]:<http://spring.io/projects/spring-boot>
[Apache Tomcat]:<http://tomcat.apache.org/># The Game

