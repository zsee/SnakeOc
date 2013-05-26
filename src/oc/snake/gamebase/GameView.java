package oc.snake.gamebase;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

public abstract class GameView extends Drawable
	implements
		Updateable,
		Clickable
{

	abstract public void onActivate(Object gameState);
	
	abstract public void onDeactivate(Object gameState);
	
	
	@Override
	abstract public void update(long elapsedTtime, Object gameState);

	@Override
	abstract public void draw(Canvas canvas);
	
	public void onClick(int x, int y, Object gameState) {}

	@Override
	public int getOpacity() {
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
	}

	@Override
	public void setColorFilter(ColorFilter colorfilter) {
	}

}
