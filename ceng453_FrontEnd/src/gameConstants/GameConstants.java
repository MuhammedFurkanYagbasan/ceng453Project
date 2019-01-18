package gameConstants;

import javafx.scene.paint.Color;

/**
 * Game Constants
 */
public class GameConstants {

    public static final int BULLET_DIRECTION_UP = -1;
    public static final int BULLET_DIRECTION_DOWN = 1;

    public static final int MOVEMENT_DIRECTION_RIGHT = 1;
    public static final int MOVEMENT_DIRECTION_LEFT = 2;
    public static final int MOVEMENT_DIRECTION_DOWN = 3;
    public static final int MOVEMENT_DIRECTION_UP = 4;

    /**
     * ENEMY TYPES
     */
    public static final int ENEMY_TYPE_1 = 1;
    public static final int ENEMY_TYPE_1_HEALTH_ = 1;
    public static final Color ENEMY_TYPE_1_COLOR = Color.RED;
    public static final double ENEMY_TYPE_1_WIDTH = 20;
    public static final double ENEMY_TYPE_1_HEIGHT = 40;
    public static final double ENEMY_TYPE_1_BULLET_MOVE_SPEED = 1;
    public static final double ENEMY_TYPE_1_MOVEMENT_SPEED = 0.02;

    public static final int ENEMY_TYPE_2 = 2;
    public static final int ENEMY_TYPE_2_HEALTH_ = 2;
    public static final Color ENEMY_TYPE_2_COLOR = Color.ORANGE;
    public static final double ENEMY_TYPE_2_WIDTH = 20;
    public static final double ENEMY_TYPE_2_HEIGHT = 40;
    public static final double ENEMY_TYPE_2_BULLET_MOVE_SPEED = 1;
    public static final double ENEMY_TYPE_2_MOVEMENT_SPEED = 0.02;

    public static final int ENEMY_TYPE_3 = 3;
    public static final int ENEMY_TYPE_3_HEALTH_ = 20;
    public static final Color ENEMY_TYPE_3_COLOR = Color.GREEN;
    public static final double ENEMY_TYPE_3_WIDTH = 50;
    public static final double ENEMY_TYPE_3_HEIGHT = 100;
    public static final double ENEMY_TYPE_3_BULLET_MOVE_SPEED = 1;
    public static final double ENEMY_TYPE_3_MOVEMENT_SPEED = 0.02;

    /**
     * PLAYER TYPES
     */
    public static final int PLAYER_TYPE_1 = 1;
    public static final int PLAYER_TYPE_1_HEALTH = 100;
    public static final Color PLAYER_TYPE_1_COLOR = Color.BLUE;
    public static final double PLAYER_TYPE_1_WIDTH = 20;
    public static final double PLAYER_TYPE_1_HEIGHT = 40;
    public static final double PLAYER_TYPE_1_BULLET_SPEED = 0.5;

    public static final int PLAYER_TYPE_2 = 2;
    public static final int PLAYER_TYPE_2_HEALTH = 200;
    public static final Color PLAYER_TYPE_2_COLOR = Color.BLUE;
    public static final double PLAYER_TYPE_2_WIDTH = 20;
    public static final double PLAYER_TYPE_2_HEIGHT = 40;
    public static final double PLAYER_TYPE_2_BULLET_SPEED = 0.5;

    public static final int PLAYER_TYPE_3 = 3;
    public static final int PLAYER_TYPE_3_HEALTH = 200;
    public static final Color PLAYER_TYPE_3_COLOR = Color.BLUE;
    public static final double PLAYER_TYPE_3_WIDTH = 20;
    public static final double PLAYER_TYPE_3_HEIGHT = 40;
    public static final double PLAYER_TYPE_3_BULLET_SPEED = 0.5;

    /**
     * BULLET TYPES
     */
    public static final int BULLET_TYPE_1 = 1;
    public static final int BULLET_TYPE_1_RADIUS = 5;
    public static final int BULLET_TYPE_1_DAMAGE = 2;
    public static final Color BULLET_TYPE_1_COLOR = Color.BLUE;
    public static final double BULLET_TYPE_1_BULLETSPEED = 0.001;

    public static final int BULLET_TYPE_2 = 2;
    public static final int BULLET_TYPE_2_RADIUS = 5;
    public static final int BULLET_TYPE_2_DAMAGE = 1;
    public static final Color BULLET_TYPE_2_COLOR = Color.RED;
    public static final double BULLET_TYPE_2_BULLETSPEED = 0.001;

    public static final int BULLET_TYPE_3 = 3;
    public static final int BULLET_TYPE_3_RADIUS = 5;
    public static final int BULLET_TYPE_3_DAMAGE = 2;
    public static final Color BULLET_TYPE_3_COLOR = Color.ORANGE;
    public static final double BULLET_TYPE_3_BULLETSPEED = 0.001;

    public static final int BULLET_TYPE_4 = 4;
    public static final int BULLET_TYPE_4_RADIUS = 15;
    public static final int BULLET_TYPE_4_DAMAGE = 8;
    public static final Color BULLET_TYPE_4_COLOR = Color.GREEN;
    public static final double BULLET_TYPE_4_BULLETSPEED = 0.001;

    /**
     * MULTIPLAYER LEVEL RESULT STATUS
     */
    public static final int RESULT_WON = 1;
    public static final int RESULT_DEFEATED = 2;
    public static final int RESULT_DRAW = 3;

    /**
     * LEVELS
     */
    public static final int LEVEL_1_WAVE_1_ENEMY_NUM = 8;
    public static final int LEVEL_1_WAVE_2_ENEMY_NUM = 4;
    public static final int LEVEL_2_WAVE_1_ENEMY_NUM = 10;
    public static final int LEVEL_3_WAVE_1_ENEMY_NUM = 1;

    /**
     * SCORE INCREASE
     */
    public static final int SCORE_TYPE_1 = 1;
    public static final int SCORE_TYPE_2 = 2;
    public static final int SCORE_TYPE_3 = 10;

}
