package oc.snake.game.elements;

import java.util.List;

import oc.snake.R;
import oc.snake.game.Collidable;
import oc.snake.game.state.PlayState;
import oc.snake.gamebase.Updateable;
import oc.snake.gamebase.Vector2D;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Portal extends Drawable
	implements
		Collidable,
		Updateable
{
	protected int radius = 30;
	protected Vector2D position = new Vector2D(1242, 762);
	protected Paint paint = new Paint();
	protected Bitmap bmp = null;
	
	public Portal() {
		//paint.setARGB(255, 0, 0, 180);
		paint.setAntiAlias(true);
	}
	
	
	public Vector2D getPosition() {
		return position;
	}
	
	public Paint getPaint() {
		return paint;
	}

	@Override
	public Rect getBoundingBox() {
		return new Rect(
					(int)(position.x - radius),
					(int)(position.y - radius),
					(int)(position.x + radius),
					(int)(position.y + radius)
				);
	}

	@Override
	public List<Rect> getBoundingBoxes() {
		return null;
	}

	@Override
	public void draw(Canvas c) {
		if (bmp != null) {
			c.drawBitmap(bmp, position.x - radius, position.y - radius, paint);
		}
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub

	}


	@Override
	public void update(long elapsedTtime, Object gameState) {
		Snake snake = ((PlayState)gameState).getSnake();
		if (bmp == null) {
			bmp = BitmapFactory.decodeResource(((PlayState)gameState).getContext().getResources(), R.drawable.portal);
		}
		Rect r = snake.getHead().getBoundingBox();
		if (Rect.intersects(r, getBoundingBox()) ) {
			((PlayState)gameState).finishLevel();
		}
	}

}
