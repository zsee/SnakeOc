package oc.snake.game.elements;

import oc.snake.R;
import oc.snake.game.SnakeGameState;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Iceblock - freeze moving walls
 * @author Zsee
 *
 */
public class Iceblock extends PowerUp {
	
	public Iceblock() {
		super();
		this.effectDuration = Integer.MAX_VALUE;
	}

	@Override
	public Bitmap getBitmap() {
		if (context ==null) {
			return null;
		} else {
			return BitmapFactory.decodeResource(context.getResources(), R.drawable.iceblock);
		}
	}

	@Override
	public void onEat(long time, SnakeGameState state) {
		for (MovingWall w : state.getMovingWalls()) {
			w.getContainerBox().set(w.getBoundingBox());
		}
	}

	@Override
	public void updateActive(long time, SnakeGameState state) {
	}

	@Override
	public void onEffectTerminated(long time, SnakeGameState state) {
	}
	
}
