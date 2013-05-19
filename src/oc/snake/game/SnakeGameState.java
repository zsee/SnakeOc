package oc.snake.game;

import java.util.ArrayList;
import java.util.List;

import oc.snake.game.elements.Food;
import oc.snake.game.elements.MovingWall;
import oc.snake.game.elements.Portal;
import oc.snake.game.elements.Snake;
import oc.snake.game.elements.Wall;
import oc.snake.game.levels.Level;
import oc.snake.game.levels.LevelFactory;
import android.graphics.Rect;

public class SnakeGameState {
	protected Snake snake;
	protected List<Wall> walls = new ArrayList<Wall>();
	protected List<MovingWall> movingWalls = new ArrayList<MovingWall>();
	protected Wall gate;
	protected Portal portal;
	protected Food food;
	protected int lives = 100;
	protected int numfood = 1;
	protected int foodPickedUp = 0;
	protected Vector2D direction;
	
	protected GameSituation situation = GameSituation.Playing;
	
	protected int speedIncrease = 20;
	
	protected int levelNum;
	
	public void setSituation(GameSituation s) {
		situation = s;
	}
	
	public GameSituation getSituation() {
		return situation;
	}
	
	public Portal getPortal() {
		return portal;
	}
	
	public int getSpeedIncrease() {
		return speedIncrease;
	}
	
	public int numFood() {
		return numfood;
	}
	
	public void setNumFood(int n) {
		numfood = n;
	}
	
	public int numFoodPickedUp() {
		return foodPickedUp;
	}
	
	public int getLevelNum() {
		return levelNum;
	}
	
	public void pickUpFood() {
		foodPickedUp++;
		food.eat(this);
		if (foodPickedUp == numfood) {
			//situation = GameSituation.CompletedLevel;
			gate.getPosition().set(-1000,-1000);
			// no new food
			food.getGeneratedPosition().set(-1000,-1000);
			food.getPosition().set(-1000,-1000);
		}
	}
	
	public void hitWall() {
		this.lives -= 1;
		situation = GameSituation.Dead;
		if (lives == 0) {
			situation = GameSituation.GameOver;
		}
	}
	
	public void finishLevel() {
		situation = GameSituation.CompletedLevel;
		if (levelNum == LevelFactory.numLevels()) {
			situation = GameSituation.CompletedGame;
		}
	}
	
	public void setLives(int l) {lives=l;}
	public int getLives() {return lives;}
	
	public void setSnake(Snake s) {snake=s;}
	public Snake getSnake() {return snake;}
	
	public void setFood(Food f) {food=f;}
	public Food getFood() {return food;}
	
	public void setWalls(List<Wall> l) {walls = l;}
	public List<Wall> getWalls() {return walls;}
	
	public void setMovingWalls(List<MovingWall> l) {movingWalls = l;}
	public List<MovingWall> getMovingWalls() {return movingWalls;}
	
	
	public void newGameInit() {
		lives = 3;
		init();
	}
	
	public void init() {
		portal = new Portal();
		walls.clear();
		movingWalls.clear();
		gate = new Wall();
		gate.getPosition().set(1205,725);
		gate.getSize().set(75,75);
		walls.add(gate);
		snake = new Snake(new Vector2D(100,100), 6);
		snake.setSpeed(100);
		snake.getDirection().set(1,0);
		numfood = 1;
		foodPickedUp = 0;
		speedIncrease = 20;
		// screen margins
		walls.add(new Wall(new Rect(0,0,1,800)));
		walls.add(new Wall(new Rect(0,0,1280,1)));
		walls.add(new Wall(new Rect(1279,0,1280,800)));
		walls.add(new Wall(new Rect(0,799,1280,800)));
	}
	
	public void loadLevel(int num) {
		init();
		levelNum = num;
		Level level = LevelFactory.getLevel(num);
		level.applyTo(this);
		afterLoad();
	}
	
	public void restartLevel() {
		loadLevel(levelNum);
	}
	
	protected void afterLoad() {
		food = new Food();
		food.reposition(this);
	}
}
