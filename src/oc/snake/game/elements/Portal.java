package oc.snake.game.elements;

import java.util.List;

import oc.snake.game.Collidable;
import oc.snake.game.SnakeGameState;
import oc.snake.game.Updateable;
import oc.snake.game.Vector2D;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Portal extends Drawable
	implements
		Collidable,
		Updateable
{
	protected int radius = 30;
	protected Vector2D position = new Vector2D(1242, 762);
	protected Paint paint = new Paint();
	
	public Portal() {
		paint.setARGB(255, 0, 0, 180);
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
		c.drawCircle(position.x, position.y, radius, paint);
		//c.drawRect(getBoundingBox(), paint);
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
	public void update(long elapsedTtime, Object gameState) throws Exception {
		Snake snake = ((SnakeGameState)gameState).getSnake();
		Rect r = snake.getHead().getBoundingBox();
		if (Rect.intersects(r, getBoundingBox()) ) {
			((SnakeGameState)gameState).finishLevel();
		}
	}

}
