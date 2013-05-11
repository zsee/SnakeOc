package oc.snake.game;

import java.util.List;

import android.graphics.Rect;

public interface Collidable {
	public List<Rect> getAreas();
	
	public void onCollision(Collidable o);
}
