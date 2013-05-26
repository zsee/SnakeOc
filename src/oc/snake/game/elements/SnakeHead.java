package oc.snake.game.elements;

import oc.snake.R;
import oc.snake.game.state.PlayState;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class SnakeHead extends SnakeElement {
	private Bitmap bmp = null;
	protected Context context = null;
	
	public SnakeHead() {
		super();
		paint.setAntiAlias(true);
	}
	
	@Override
	public void update(long time, Object state) {
		if (context == null) {
			context = ((PlayState)state).getContext();
		}
		super.update(time, state);
	}
	
	
	@Override
	public void draw(Canvas canvas) {
		if (bmp == null && context != null) {
			bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.snakehead);
		}
		if (this.bmp != null) {
			canvas.save();
			canvas.rotate(direction.getDirectionDegrees(),position.x, position.y);
			int minx = (int)(position.x - SizeX/2);
			int miny = (int)(position.y - SizeY/2);
			canvas.drawBitmap(bmp, minx, miny,paint);
			canvas.restore();
		} else {
			super.draw(canvas);
		}
	}
}
