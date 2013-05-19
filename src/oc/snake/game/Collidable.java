package oc.snake.game;

import java.util.List;

import android.graphics.Rect;

public interface Collidable {
	
	public Rect getBoundingBox();
	
	public List<Rect> getBoundingBoxes();
	
}
