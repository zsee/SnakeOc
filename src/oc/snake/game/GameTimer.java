package oc.snake.game;

public class GameTimer {
	private long startTime = 0;
	private GameTimerState state = GameTimerState.Stopped;
	private long pauseTime;
	
	public long getElapsedTime() {
		if (state == GameTimerState.Running) {
			return System.currentTimeMillis() - startTime;
		} else {
			return 0;
		}
	}
	
	public GameTimerState getState() {
		return state;
	}
	
	public void start() {
		state = GameTimerState.Running;
		startTime = System.currentTimeMillis();
	}
	
	public void stop() {
		state = GameTimerState.Stopped;
	}
	
	public void pause() {
		state = GameTimerState.Paused;
		pauseTime = getElapsedTime();
	}
	
	public void resume() {
		state = GameTimerState.Running;
		startTime = System.currentTimeMillis() - pauseTime;
	}
	
}
