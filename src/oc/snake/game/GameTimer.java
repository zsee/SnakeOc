package oc.snake.game;

public class GameTimer {
	private GameTimerState state = GameTimerState.Stopped;
	private long lastUpdateTime;
	
	public long getElapsedTime() {
		if (state == GameTimerState.Running) {
			long time = System.currentTimeMillis();
			long d = time - lastUpdateTime;
			lastUpdateTime = time;
			return d;
		} else {
			return 0;
		}
	}
	
	public GameTimerState getState() {
		return state;
	}
	
	public void start() {
		state = GameTimerState.Running;
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public void stop() {
		state = GameTimerState.Stopped;
	}
	
	public void pause() {
		state = GameTimerState.Paused;
	}
	
	public void resume() {
		start();
	}
	
}
