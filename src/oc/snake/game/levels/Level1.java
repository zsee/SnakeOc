package oc.snake.game.levels;

import oc.snake.game.SnakeGameState;

public class Level1 implements Level {

	@Override
	public void applyTo(SnakeGameState s) {
		s.setNumFood(3);
	}

}
