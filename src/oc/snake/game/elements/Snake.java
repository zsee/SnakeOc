package oc.snake.game.elements;

import java.util.ArrayList;
import java.util.List;

import oc.snake.game.Collidable;
import oc.snake.game.state.PlayState;
import oc.snake.gamebase.Updateable;
import oc.snake.gamebase.Vector2D;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Snake extends Drawable
	implements
		Updateable,
		Collidable
{
	protected List<SnakeElement> elements;
	protected Vector2D direction = new Vector2D(1,0);
	
	
	public Snake(Vector2D position, int size) {
		elements = new ArrayList<SnakeElement>(size);
		int i;
		SnakeElement n;
		for (i=0; i<size; i++) {
			if (i==size-1) {
				n = new SnakeHead();
			} else {
				n = new SnakeElement();
			}
			n.setBeforePoint(new Vector2D(position.x-size+i*30, position.y));
			elements.add(n);
		}		
		try {
			update(0, this);
		} catch (Exception ex) {
			Log.i("Snake","Error on init:" + ex.getMessage());
		}
	}
	
	public Snake(Vector2D position) {
		this(position, 5);
	}
	
	public Snake(int size) {
		this(new Vector2D(100,100), size);
	}
	
	public Snake() {
		this(5);
	}

	
	public List<SnakeElement> getElements() {
		return elements;
	}
	
	public void grow(int num) {
			SnakeElement head = this.getHead();
			SnakeElement n = elements.get(elements.size()-2);
			Vector2D d = new Vector2D( n.getPosition(), head.getPosition() );
			elements.remove(elements.size()-1);
			int i = 0;
			while (i != num) {
				SnakeElement nw = new SnakeElement();
				nw.getPosition().set(n.getPosition().x + d.x/2, n.getPosition().y + num * d.y/2);
				nw.follow(head.getPosition());
				head.getPosition().set(head.getPosition().x + d.x/2, head.getPosition().y + d.y/2);
				elements.add(nw);
				//elements.add(head);
				i++;
			}
			elements.add(head);
	}
	
	public void grow() {
		grow(1);
	}
	
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

	@Override
	public void draw(Canvas canvas) {
		for (SnakeElement e : elements) {
			e.draw(canvas);
		}
	}
	
	@Override
	public void update(long elapsedTime, Object gameState) {
		SnakeElement b,c;
		Rect headbox;
		direction.normalize();
		int i=elements.size() - 1;
		b = elements.get(i--);
		b.getDirection().setRotationVector(direction);
		//b.getDirection().normalize();
		b.update(elapsedTime, gameState);
		headbox = b.getBoundingBox();
		for (; i>=0; i--) {
			c = elements.get(i);
			c.follow(b.getPosition());
			c.setBeforePoint(b.getPosition());
			//c.update(elapsedTime);
			if (Rect.intersects(headbox,c.getBoundingBox()) && elapsedTime != 0) {
				((PlayState)gameState).hitWall();
			}
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

	@Override
	public Rect getBoundingBox() {
		return getHead().getBoundingBox();
	}

	@Override
	public List<Rect> getBoundingBoxes() {
		List<Rect> l = new ArrayList<Rect>();
		for(SnakeElement e : elements) {
			l.add(e.getBoundingBox());
		}
		return l;
	}

}
