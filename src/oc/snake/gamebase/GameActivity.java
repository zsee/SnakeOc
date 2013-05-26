package oc.snake.gamebase;

import android.app.Activity;

public abstract class GameActivity extends Activity implements Runnable {
	protected GameTimer timer = new GameTimer();
	protected Thread thread = new Thread(this);
	protected GameView activeView = null;
	protected GameContainerView container = null;
	
	
	protected abstract Object getGameState();
	
	protected abstract GameContainerView getContainerView();
	
	protected abstract GameView activeView();
	
	@Override
	public void onResume() {
		super.onResume();
		container = getContainerView();
		timer.resume();
		thread.start();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		timer.pause();
		thread.interrupt();
	}
	
	public void run() {
		boolean i = false;
		while (i || !Thread.interrupted()) {
			synchronized (container.getLock()) {
				Object state = getGameState();
				GameView view = activeView();
				if (view != activeView) {
					if (view != null)
						view.onActivate(state);
					if (activeView != null)
						activeView.onDeactivate(state);
				}
				activeView = view;
				container.setGameState(state);
				container.setGameView(activeView);
				activeView.update(timer.getElapsedTime(), state);
			}
			container.postInvalidate();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				i = true;
			}
		}
	}
}
