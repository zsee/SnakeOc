package oc.snake.game.state;

import java.util.HashMap;

import oc.snake.game.MenuView;
import oc.snake.game.SnakeGameView;
import oc.snake.gamebase.GameTimer;
import oc.snake.gamebase.GameView;
import android.app.Activity;

public class MainGameState {
	public Activity activity;
	public PlayState playState = new PlayState();
	public HashMap<String, GameView> views = new HashMap<String, GameView>();
	public String activeView = "menu";
	public GameTimer timer;
	
	public GameView getActiveView() {
		return views.get(activeView);
	}
	
	public MainGameState() {
		views.put("menu", new MenuView());
		views.put("game", new SnakeGameView());
		playState.init();
		playState.newGameInit();
		playState.loadLevel(0);
	}
}
