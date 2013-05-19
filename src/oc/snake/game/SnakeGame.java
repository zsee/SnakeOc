package oc.snake.game;

import oc.snake.game.elements.MovingWall;
import oc.snake.game.elements.Wall;
import oc.snake.game.exceptions.SnakeHitSelfException;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class SnakeGame extends Game {
	
	protected SensorManager sm;
	protected Sensor sensAcc, sensMagn;
	protected SnakeGameState gameState;

	public SnakeGame(Context context, AttributeSet atr) {
		super(context, atr);
		gameState = new SnakeGameState();
		gameState.newGameInit();
		gameState.loadLevel(1);
		
		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensAcc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensMagn = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	}

	@Override
	protected void onStart() {}

	@Override
	protected void onStop() {}

	@Override
	protected void onPause() {
		sm.unregisterListener(this.myListener);
	}

	@Override
	protected void onResume() {
		sm.registerListener(myListener, sensAcc, SensorManager.SENSOR_DELAY_GAME);
		sm.registerListener(myListener, sensMagn, SensorManager.SENSOR_DELAY_GAME);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.scale((float)canvas.getWidth()/1280, (float)canvas.getHeight()/800);
		canvas.drawRGB(255, 255, 255);
		gameState.getSnake().draw(canvas);
		gameState.getFood().draw(canvas);
		gameState.getPortal().draw(canvas);
		for (Wall w : gameState.getWalls()) {
			w.draw(canvas);
		}
		for (MovingWall w : gameState.getMovingWalls()) {
			w.draw(canvas);
		}
		canvas.restore();
	}

	GameSituation s = GameSituation.CompletedGame;
	
	@Override
	public void updateState(long time) {
		if (gameState.getSituation() != s ) {
			Log.i("Situation changed", gameState.getSituation().toString());
		}
		switch (gameState.getSituation()) {
			case Dead:
				this.pause();
				gameState.restartLevel();
				gameState.setSituation(GameSituation.Playing);
				try {
					Thread.sleep(500);
				} catch (Exception e) {}
				this.resume();
				break;
			case CompletedLevel:
				this.pause();
				gameState.loadLevel(gameState.getLevelNum()+1);
				gameState.setSituation(GameSituation.Playing);
				this.resume();
				break;
			case CompletedGame:
				this.pause();
				break;
			case GameOver:
				this.pause();
				break;
		}
		s = gameState.getSituation();
		try {
			gameState.getSnake().update(time, gameState);
			for (Wall w : gameState.getWalls()) {
				w.update(time, gameState);
			}
			for (MovingWall w : gameState.getMovingWalls()) {
				w.update(time, gameState);
			}
			gameState.getFood().update(time, gameState);
			gameState.getPortal().update(time, gameState);
		} catch(SnakeHitSelfException e) {
		} catch(Exception e) {}
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gameState.getSnake().grow();
		return super.onTouchEvent(event);
	}
	
	protected SensorEventListener myListener = new SensorEventListener() {
		private boolean mLastAccelerometerSet = false; 
		private boolean mLastMagnetometerSet = false;
	    private float[] mLastAccelerometer = new float[3];
	    private float[] mLastMagnetometer = new float[3];
	    private float[] mOrientation = new float[3];
	    private float[] mR = new float[9];
	    private float[] mRotatedR = new float[9];
		
		@Override
		public void onSensorChanged(SensorEvent event) {
	        if (event.sensor == sensAcc) {
	            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
	            mLastAccelerometerSet = true;
	        } else if (event.sensor == sensMagn) {
	            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
	            mLastMagnetometerSet = true;
	        }
	        if (mLastAccelerometerSet && mLastMagnetometerSet) {
	            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
	            SensorManager.remapCoordinateSystem(mR, SensorManager.AXIS_MINUS_X, SensorManager.AXIS_MINUS_Y, mRotatedR);
	            SensorManager.getOrientation(mRotatedR, mOrientation);
	            if (mOrientation[1] != 0 || mOrientation[2] != 0) {
	            	gameState.getSnake().getDirection().set(mOrientation[1],mOrientation[2]);
	            	//Log.i("sensor", mOrientation[1] + ", "+mOrientation[2]);
	            }
	        }
	    }

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
		}
		
	};
}
