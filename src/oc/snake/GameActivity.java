package oc.snake;

import oc.snake.game.state.MainGameState;
import oc.snake.gamebase.GameContainerView;
import oc.snake.gamebase.GameView;
import android.os.Bundle;

public class GameActivity extends oc.snake.gamebase.GameActivity {
	private MainGameState gameState = new MainGameState();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gameState.activity = this;
		gameState.playState.setContext(this);
		gameState.timer = this.timer;
	}

	@Override
	protected Object getGameState() {
		return gameState;
	}

	@Override
	protected GameContainerView getContainerView() {
		return (GameContainerView) findViewById(R.id.VIEW1);
	}

	@Override
	protected GameView activeView() {
		return gameState.getActiveView();
	}
}
