package oc.snake.graphics;

import java.util.ArrayList;
import java.util.List;

import oc.snake.game.Updateable;
import oc.snake.game.Vector2D;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class Snake extends Drawable implements Updateable {
	protected List<SnakeElement> elements;
	protected Vector2D direction = new Vector2D();
	protected Vector2D target = new Vector2D();
	
	public float getSpeed() {
		return getHead().getSpeed();
	}
	
	public void setSpeed(float s) {
		getHead().setSpeed(s);
	}
	
	public Vector2D getDirection() {
		return direction;
	}
	
	public SnakeElement getHead() {
		return elements.get(elements.size()-1);
	}
	
	public Vector2D getHeadPosition() {
		return elements.get(elements.size()-1).getPosition();
	}
	
	public Snake() {
		elements = new ArrayList<SnakeElement>();
		int i;
		SnakeElement e = new SnakeElement();
		SnakeElement n;
		e.setBeforePoint(new Vector2D(10,10));
		elements.add(e);
		for (i=0; i<300; i++) {
			n = new SnakeElement();
			n.setBeforePoint(e.getPosition());
			elements.add(n);
			e = n;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		for (SnakeElement e : elements) {
			e.draw(canvas);
		}
	}
	
	@Override
	public void update(long elapsedTime) {
		SnakeElement b,c;
		direction.normalize();
		int i=elements.size() - 1;
		b = elements.get(i--);
		b.getDirection().setRotationVector(direction);
		b.update(elapsedTime);
		for (; i>=0; i--) {
			c = elements.get(i);
			c.follow(b.getPosition());
			c.setBeforePoint(b.getPosition());
			//c.update(elapsedTime);
			b = c;
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

}
