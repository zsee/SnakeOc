package oc.snake.game.elements;

import oc.snake.R;
import oc.snake.game.SnakeGameState;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Ghost extends PowerUp {

	public Ghost() {
		super();
		effectDuration = 10000;
	}
	
	@Override
	public Bitmap getBitmap() {
		if (context == null) {
			return null;
		} else {
			return BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost);
		}
	}

	@Override
	public void onEat(long time, SnakeGameState state) {
		int i = 0;
		for (Wall w : state.getWalls()) {
			// for non default walls
			// default + surround + gate
			if (i > 4) {
				w.setSnakeHitDetection(false);
			}
			i++;
		}
		for (Wall w : state.getMovingWalls()) {
			w.setSnakeHitDetection(false);
		}
	}

	@Override
	public void updateActive(long time, SnakeGameState state) {
	}

	@Override
	public void onEffectTerminated(long time, SnakeGameState state) {
		for (Wall w : state.getWalls()) {
			w.setSnakeHitDetection(true);
		}
		for (Wall w : state.getMovingWalls()) {
			w.setSnakeHitDetection(true);
		}
	}

}
