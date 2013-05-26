package oc.snake.game.levels;

import oc.snake.game.state.PlayState;

public interface Level {
	public void applyTo(PlayState s);
}
