package oc.snake.game.levels;

import oc.snake.game.SnakeGameState;

public interface Level {
	public void applyTo(SnakeGameState s);
}
