package oc.snake.game.elements;

import java.util.LinkedList;
import java.util.List;

import oc.snake.game.Collidable;
import oc.snake.gamebase.Updateable;
import oc.snake.gamebase.Vector2D;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;


public class SnakeElement extends Drawable
	implements
		Collidable,
		Updateable
{
	protected static int SizeX = 25;
	protected static int SizeY = 12;
	protected static int Distance = 10;
	
	protected Vector2D position = new Vector2D(200,200);
	protected Paint paint = new Paint();
	protected Vector2D direction = new Vector2D(1,0);
	protected float speed = 1;
	
	public Rect getBoundingBox() {
		int ms = Math.min(SizeX, Math.min(SizeY, Distance)) / 3;
		int mx = (int) this.position.x - ms;
		int my = (int) this.position.y - ms;
		int Mx = (int) this.position.x + ms;
		int My = (int) this.position.y + ms;
		return new Rect( mx, my, Mx, My );
	}
	
	public SnakeElement() {
		paint.setARGB(255,0,120,0);
		//direction.set(1, 1);
	}
	
	public Vector2D getDirection() {
		return direction;
	}
	
	public void setSpeed(float s) {
		speed = s;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public void setBeforePoint(Vector2D p) {
		Vector2D dir = direction.toNormalizedVector();
		dir.setModulus(Distance);
		dir.invert();
		synchronized(p) {
			position.set(p.x+dir.x, p.y+dir.y);
		}
	}

	public void follow(Vector2D p) {
		Vector2D dir;
		synchronized(p) {
			dir = new Vector2D(position, p);
		}
		dir.normalize();
		direction.setRotationVector(dir);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		canvas.rotate(direction.getDirectionDegrees(),position.x, position.y);
		int minx = (int)(position.x - SizeX/2);
		int miny = (int)(position.y - SizeY/2);
		int maxx = (int)(position.x + SizeX/2);
		int maxy = (int)(position.y + SizeY/2);
		RectF r = new RectF(minx, miny, maxx, maxy);
		canvas.drawOval(r, paint);
		canvas.restore();
	}

	@Override
	public int getOpacity() {return PixelFormat.UNKNOWN;}

	@Override
	public void setAlpha(int arg0) {}

	@Override
	public void setColorFilter(ColorFilter arg0) {}

	/**
	 * Update the part
	 */
	@Override
	public void update(long elapsedTime, Object gameState) {
		position.x += direction.x * speed * elapsedTime/1000.0;
		position.y += direction.y * speed * elapsedTime/1000.0;
	}

	@Override
	public List<Rect> getBoundingBoxes() {
		List<Rect> l = new LinkedList<Rect>();
		l.add(getBoundingBox());
		return l;
	}
}