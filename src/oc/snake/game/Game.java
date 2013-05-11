package oc.snake.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

abstract public class Game extends ImageView implements Runnable {

	protected GameTimer timer = new GameTimer();
	protected Thread thread = new Thread(this);
	
	public Game(Context context) {
		super(context);
	}
	
	public Game(Context context, AttributeSet atr) {
		super(context, atr);
	}
	
	public void start() {
		onStart();
		timer.start();
		thread.start();
		resume();
	}
	
	public void stop() {
		pause();
		timer.stop();
		onStop();
	}
	
	public void pause() {
		timer.pause();
		onPause();
	}
	
	public void resume() {
		onResume();
		timer.resume();
	}
	
	abstract protected void onStart();
	
	abstract protected void onStop();
	
	abstract protected void onPause();
	
	abstract protected void onResume();

	@Override
	public void run() {
		while (timer.getState() == GameTimerState.Running) {
			handleInput();
			updateState(timer.getElapsedTime());
			postInvalidate();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {}
		}
	}

	abstract public void updateState(long time);
	
	abstract public void handleInput();
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
