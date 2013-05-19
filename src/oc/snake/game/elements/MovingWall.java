package oc.snake.game.elements;

import android.graphics.Rect;
import oc.snake.game.Updateable;
import oc.snake.game.Vector2D;

public class MovingWall extends Wall implements Updateable {
	
	protected Vector2D speed = new Vector2D(0,0);
	protected Rect movementContainer = new Rect();
	
	public MovingWall() {
		super();
	}
	
	public MovingWall(Rect r) {
		super(r);
	}
	
	public Vector2D getSpeed() {
		return speed;
	}
	
	public Rect getContainerBox() {
		return movementContainer;
	}
	
	protected void move(float dx, float dy) {
		position.x += dx;
		position.y += dy;
	}

	@Override
	public void update(long elapsedTime, Object gameState) throws Exception {
		super.update(elapsedTime, gameState);
		float d = (float) (elapsedTime / 1000.0);
		float dx = speed.x * d;
		float dy = speed.y * d;
		move(dx,dy);
		Rect pos = getBoundingBox();
		if (!movementContainer.contains(pos)) {
			speed.invert();
			dx = dy = 0;
			if (pos.left < movementContainer.left) {
				dx = movementContainer.left - pos.left + 1;
			} else if (movementContainer.right < pos.right){
				dx = movementContainer.right - pos.right - 1;
			}
			if (pos.top < movementContainer.top) {
				dy = movementContainer.top - pos.top + 1;
			} else if (movementContainer.bottom < pos.bottom) {
				dy = movementContainer.bottom - pos.bottom - 1;
			}
			move(dx,dy);
		}
	}

}
