package oc.snake.game;

import oc.snake.graphics.Snake;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;

public class SnakeGame extends Game {
	
	Snake snake = new Snake();
	Vector2D dir = new Vector2D(1,0);
	
	protected SensorManager sm;
	protected Sensor o;

	public SnakeGame(Context context, AttributeSet atr) {
		super(context, atr);
		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		o = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		snake.setSpeed(100);
		snake.getHeadPosition().set(100,100);
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
		sm.registerListener(myListener, o, SensorManager.SENSOR_DELAY_GAME);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		snake.draw(canvas);
	}

	@Override
	public void updateState(long time) {
		// TODO Auto-generated method stub
		snake.getDirection().set(dir);
		snake.update(time);
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}
	
	protected SensorEventListener myListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			float dx = (int) - event.values[1];
			float dy = (int)   event.values[2];
			if (dx != 0 || dy != 0) {
				dir.set(dx,dy);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
		}
		
	};
}
