package oc.snake.game.elements;

import java.util.ArrayList;
import java.util.LinkedList;
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

public class Food extends Drawable implements Collidable, Updateable {
	protected Paint paint = new Paint();
	protected Vector2D position = new Vector2D(-100,-100);
	protected Vector2D generatedPosition = new Vector2D(-100,-100);
	protected int appearTime = 8000;
	protected int eatTime = appearTime + 1;
	protected int radius = 10;

	public Food() {
		paint.setARGB(255, 0, 255, 0);
	}
	
	public void setRadius(int r) {
		radius = r;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setAppearTime(int t) {
		appearTime = t;
	}
	
	public int getAppearTime() {
		return appearTime;
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public Vector2D getGeneratedPosition() {
		return generatedPosition;
	}
	
	public void eat(SnakeGameState s) {
		eatTime = 0;
		position.set(-100,-100);
		Snake snake = s.getSnake();
		snake.grow();
		snake.setSpeed(snake.getSpeed() + s.getSpeedIncrease());
		reposition(s);
	}
	
	public void reposition(SnakeGameState s) {
		// calculate new position for food, but do not place it on top of the walls or the snake
		Snake snake = s.getSnake();
		List<Rect> a = new LinkedList<Rect>();
		a.addAll(snake.getBoundingBoxes());
		List<Wall> wl = new LinkedList<Wall>();
		wl.addAll(s.getWalls());
		//wl.addAll(s.getMovingWalls());
		for (Wall w : wl) {
			a.add(w.getBoundingBox());
		}
		boolean ok = false;
		Rect r;
		while (!ok) {
			ok = true;
			r = generatePosition();
			for (Rect x : a) {
				if (Rect.intersects(x, r)) {
					ok = false;
					break;
				}
			}
		}
	}
	
	@Override
	public void update(long elapsedTtime, Object gameState) throws Exception {
		if (eatTime > appearTime) {
			position.set(generatedPosition);
			SnakeGameState state = (SnakeGameState) gameState;
			Rect snakeHead = state.getSnake().getHead().getBoundingBox();
			if (Rect.intersects(snakeHead, getBoundingBox())) {
				state.pickUpFood();
			}
		} else {
			eatTime += elapsedTtime;
		}
	}
	
	protected Rect generatePosition() {
		generatedPosition.x = (float) (Math.random() * 1280);
		generatedPosition.y = (float) (Math.random() * 800);
		return new Rect(
				(int)generatedPosition.x-radius,
				(int)generatedPosition.y-radius, 
				(int)generatedPosition.x+radius, 
				(int)generatedPosition.y+radius
			);
	}
	
	@Override
	public Rect getBoundingBox() {
		return new Rect(
				(int)position.x-radius,
				(int)position.y-radius, 
				(int)position.x+radius, 
				(int)position.y+radius
			);
	}

	@Override
	public List<Rect> getBoundingBoxes() {
		List<Rect> l = new ArrayList<Rect>(1);
		l.add(getBoundingBox());
		return null;
	}

	@Override
	public void draw(Canvas c) {
		c.drawCircle(position.x, position.y, radius, paint);
	}

	@Override
	public int getOpacity() {return 0;}

	@Override
	public void setAlpha(int arg0) {}

	@Override
	public void setColorFilter(ColorFilter arg0) {}

}
