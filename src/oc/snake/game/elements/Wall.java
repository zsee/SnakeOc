package oc.snake.game.elements;

import java.util.ArrayList;
import java.util.List;

import oc.snake.game.Collidable;
import oc.snake.game.Vector2D;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Wall extends Drawable implements Collidable
{
	protected Vector2D size = new Vector2D(20,20);
	protected Vector2D position = new Vector2D();
	protected Paint paint;
	
	public Wall(Rect r) {
		this();
		position.x = r.left;
		position.y = r.top;
		size.x = r.width();
		size.y = r.height();
	}
	
	public Wall() {
		paint = new Paint();
		paint.setARGB(128, 255, 0, 0);
	}
	
	public Paint getPaint() {
		return paint;
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public Vector2D getSize() {
		return size;
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(getBoundingBox(),paint);
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
	public Rect getBoundingBox() {
		return new Rect(
				(int)position.x,
				(int)position.y,
				(int)position.x+(int)size.x,
				(int)position.y+(int)size.y
				);
	}

	@Override
	public List<Rect> getBoundingBoxes() {
		List<Rect> l = new ArrayList<Rect>();
		l.add(getBoundingBox());
		return l;
	}

}
