package oc.snake.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import oc.snake.exceptions.SnakeHitSelfException;
import oc.snake.game.elements.Food;
import oc.snake.game.elements.MovingWall;
import oc.snake.game.elements.Snake;
import oc.snake.game.elements.Wall;
import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class SnakeGame extends Game {
	
	protected Snake snake;
	protected Vector2D dir = new Vector2D(1,0);
	
	protected List<Wall> walls = new ArrayList<Wall>();
	protected List<MovingWall> movingWalls = new ArrayList<MovingWall>();
	
	protected Food food = new Food();
	
	protected SensorManager sm;
	protected Sensor sensAcc, sensMagn;

	public SnakeGame(Context context, AttributeSet atr) {
		super(context, atr);
		
		walls.add(new Wall(new Rect(0,0,1,800)));
		walls.add(new Wall(new Rect(0,0,1280,1)));
		walls.add(new Wall(new Rect(1279,0,1280,800)));
		walls.add(new Wall(new Rect(0,799,1280,800)));
	
		food.getGeneratedPosition().set(500,500);
		
		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensAcc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensMagn = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		
		snake = new Snake(5);
		snake.setSpeed(100);
	}
	
	public Snake getSnake() {
		return snake;
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
		canvas.drawColor(R.color.black);
		canvas.save();
		canvas.scale((float)canvas.getWidth()/1280, (float)canvas.getHeight()/800);
		canvas.drawRGB(255, 255, 255);
		snake.draw(canvas);
		food.draw(canvas);
		for (Wall w : walls) {
			w.draw(canvas);
		}
		for (MovingWall w : movingWalls) {
			w.draw(canvas);
		}
		canvas.restore();
	}
	
	protected void placeFood() {
		List<Rect> a = new LinkedList<Rect>();
		a.addAll(snake.getBoundingBoxes());
		List<Wall> wl = new LinkedList<Wall>();
		wl.addAll(walls);
		wl.addAll(movingWalls);
		for (Wall w : wl) {
			a.add(w.getBoundingBox());
		}
		boolean ok = false;
		Rect r;
		while (!ok) {
			ok = true;
			food.eat();
			r = food.generatePosition();
			for (Rect x : a) {
				if (Rect.intersects(x, r)) {
					ok = false;
					break;
				}
			}
		}
		food.eat();
	}

	@Override
	public void updateState(long time) {
		// TODO Auto-generated method stub
		List<Rect> sn = snake.getBoundingBoxes();
		Rect snakeHead = snake.getHead().getBoundingBox();
		if (Rect.intersects(snakeHead, food.getBoundingBox())) {
			placeFood();
			snake.grow();
			snake.setSpeed(snake.getSpeed() + 33);
		}
		for (Rect r : sn) {
			for (Wall w : walls) {
				if (Rect.intersects(r, w.getBoundingBox())) {
					this.pause();
				}
			}
			for (MovingWall w : movingWalls) {
				try {
					w.update(time);
				} catch (Exception e) {
					Log.e("MovingWall", "Exception on draw");
				}
				if (Rect.intersects(r, w.getBoundingBox())) {
					this.pause();
				}
			}
		}
		snake.getDirection().set(dir);
		try {
			snake.update(time);
			food.update(time);
		} catch(SnakeHitSelfException e) {
			this.pause();
		} catch(Exception e) {}
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		snake.grow();
		return super.onTouchEvent(event);
	}
	
	protected SensorEventListener myListener = new SensorEventListener() {
		private boolean mLastAccelerometerSet = false; 
		private boolean mLastMagnetometerSet = false;
	    private float[] mLastAccelerometer = new float[3];
	    private float[] mLastMagnetometer = new float[3];
	    private float[] mOrientation = new float[3];
	    private float[] mR = new float[9];
		
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
	            SensorManager.getOrientation(mR, mOrientation);
	            dir.set(-mOrientation[1],-mOrientation[2]);
	        }
	    }

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
		}
		
	};
}
