package oc.snake.game.elements;

import oc.snake.R;
import oc.snake.game.state.PlayState;
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
	public void onEat(long time, PlayState state) {
		int i = 0;
		for (Wall w : state.getWalls()) {
			// for non default walls
			// default + surround + gate
			if (i > 4) {
				w.setSnakeHitDetection(false);
				w.getPaint().setAlpha(120);
			}
			i++;
		}
		for (Wall w : state.getMovingWalls()) {
			w.setSnakeHitDetection(false);
			w.getPaint().setAlpha(120);
		}
	}

	@Override
	public void updateActive(long time, PlayState state) {
	}

	@Override
	public void onEffectTerminated(long time, PlayState state) {
		for (Wall w : state.getWalls()) {
			w.setSnakeHitDetection(true);
			w.getPaint().setAlpha(255);
		}
		for (Wall w : state.getMovingWalls()) {
			w.setSnakeHitDetection(true);
			w.getPaint().setAlpha(255);
		}
	}

}
