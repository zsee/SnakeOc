package oc.snake.game.elements;

import oc.snake.game.state.PlayState;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Mouse - adds one life
 * @author Zsee
 */
public class Mouse extends PowerUp {

	public Mouse() {
		super();
		this.effectDuration = Integer.MAX_VALUE;
	}
	
	
	@Override
	public void onEat(long time, PlayState state) {
		int l = state.getLives();
		state.setLives(l+1);
	}

	@Override
	public void updateActive(long time, PlayState state) {
	}

	@Override
	public void onEffectTerminated(long time, PlayState state) {
	}

	@Override
	public Bitmap getBitmap() {
		if (context == null) {
			return null;
		} else {
			return BitmapFactory.decodeResource(context.getResources(), oc.snake.R.drawable.mouse);
		}
	}
	
}