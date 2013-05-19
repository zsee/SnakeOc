package oc.snake.game.levels;

import oc.snake.game.SnakeGameState;
import oc.snake.game.elements.MovingWall;
import oc.snake.game.elements.Wall;
import android.graphics.Rect;

public class Level2 implements Level {
	protected Rect[] walls = {
			new Rect(150,150,	155,800),
			new Rect(150,0,		155,50),
			new Rect(1000,0,	1015,400),
			new Rect(300,300,	400,400)
	};
	
	protected void addMovingWalls(SnakeGameState s) {
		s.setNumFood(2);
		s.getSnake().setSpeed(150);
		s.getSnake().grow();
		s.getSnake().grow();
		s.getSnake().grow();
		s.getSnake().grow();
		MovingWall w = new MovingWall();
		w.getSize().set(50,50);
		Rect r = w.getContainerBox();
		r.bottom = 600;
		r.top = 200;
		r.left = 200;
		r.right = 600;
		w.getSpeed().set(50,50);
		s.getMovingWalls().add(w);
	}
	
	public void applyTo(SnakeGameState s) {
		for (Rect r : walls) {
			Wall w = new Wall(r);
			s.getWalls().add(w);
		}
		addMovingWalls(s);
	}
}
