package oc.snake.gamebase;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class GameContainerView extends ImageView
{
	protected Object lock = new Object();
	protected GameView view = null;
	protected int screenX = 0;
	protected int screenY = 0;
	protected Object gameState = null;
	
	public GameView getGameView() {
		return view;
	}
	
	public void setGameView(GameView view) {
		this.view = view;
	}
	
	public void setGameState(Object o) {
		gameState = o;
	}
	
	public Object getGameState() {
		return gameState;
	}

	public Object getLock() {
		return lock;
	}
	
	public GameContainerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		screenX = canvas.getWidth();
		screenY = canvas.getHeight();
		synchronized (lock) {
			super.onDraw(canvas);
			canvas.save();
			canvas.scale((float)screenX/1280, (float)screenY/800);
			if (view != null) {
				view.draw(canvas);
			}
			canvas.restore();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) (event.getX()/(float)screenX * 1280);
		int y = (int) (event.getY()/(float)screenY * 800);
		synchronized (lock) {
			if (view != null) {
				view.onClick(x, y, gameState);
			}
		}
		return super.onTouchEvent(event);
	}
}
