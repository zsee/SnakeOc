package oc.snake.game.levels;

import oc.snake.game.state.PlayState;

public class Level1 implements Level {

	@Override
	public void applyTo(PlayState s) {
		s.setNumFood(3);
	}

}
