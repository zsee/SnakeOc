package oc.snake.game;

import oc.snake.game.elements.MovingWall;
import oc.snake.game.elements.PowerUp;
import oc.snake.game.elements.Wall;
import oc.snake.game.state.GameSituation;
import oc.snake.game.state.MainGameState;
import oc.snake.game.state.PlayState;
import oc.snake.gamebase.Vector2D;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SnakeGameView extends oc.snake.gamebase.GameView {
	
	protected SensorManager sm;
	protected Sensor sensAcc, sensMagn, sensRot;
	protected Context context;
	protected PlayState gameState = null;
	protected GameSituation s = GameSituation.CompletedGame;

	@Override
	public void onActivate(Object gameState) {
		context = ((MainGameState)gameState).activity;
		this.gameState = ((MainGameState)gameState).playState;
		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensAcc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensMagn = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensRot = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		
		sm.registerListener(myListener, sensAcc, SensorManager.SENSOR_DELAY_GAME);
		sm.registerListener(myListener, sensMagn, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onDeactivate(Object gameState) {
		sm.unregisterListener(this.myListener);
	}

	@Override
	public void update(long time, Object state) {
		if (gameState.getSituation() != s) {
			Log.i("Situation changed", gameState.getSituation().toString());
		}
		s = gameState.getSituation();
		switch (s) {
		case Starting:
			try {
				Thread.sleep(800);
			} catch (Exception e) {
			}
			gameState.setSituation(GameSituation.Playing);
			break;
		case Dead:
			gameState.restartLevel();
			gameState.setSituation(GameSituation.Starting);
			try {
				Thread.sleep(800);
			} catch (Exception e) {
			}
			break;
		case CompletedLevel:
			gameState.loadLevel(gameState.getLevelNum() + 1);
			gameState.setSituation(GameSituation.Starting);
			try {
				Thread.sleep(800);
			} catch (Exception e) {
			}
			break;
		case CompletedGame:
			break;
		case GameOver:
			break;
		}
		gameState.getSnake().update(time, gameState);
		gameState.getFood().update(time, gameState);
		for (Wall w : gameState.getWalls()) {
			w.update(time, gameState);
		}
		for (MovingWall w : gameState.getMovingWalls()) {
			w.update(time, gameState);
		}
		for (PowerUp p : gameState.getPowerUps()) {
			p.update(time, gameState);
		}
		gameState.getPortal().update(time, gameState);
	}
	
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawRGB(255, 255, 255);
		//canvas.drawRGB(0, 0, 0);
		gameState.getPortal().draw(canvas);
		for (Wall w : gameState.getWalls()) {
			w.draw(canvas);
		}
		for (MovingWall w : gameState.getMovingWalls()) {
			w.draw(canvas);
		}
		for (PowerUp p : gameState.getPowerUps()) {
			p.draw(canvas);
		}
		gameState.getSnake().draw(canvas);
		gameState.getFood().draw(canvas);
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
	            if (gameState != null && (mOrientation[1] != 0 || mOrientation[2] != 0)) {
	            	Vector2D dir = gameState.getSnake().getDirection();
	            	synchronized(dir) {
	            		if (isTablet(context)) {
	            			dir.set(-mOrientation[2],mOrientation[1]);
	            		} else {
	            			dir.set(mOrientation[1],mOrientation[2]);
	            		}
	            	}
	            	//Log.i("sensor", mOrientation[0] + ", " + mOrientation[1] + ", "+mOrientation[2]);
	            }
	        }
	    }

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
		}
		
	};
}
