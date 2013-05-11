package oc.snake;

import oc.snake.graphics.SnakeView;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private SnakeView view;
	protected SensorManager sm;
	protected Sensor o;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		o = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		view = (SnakeView) findViewById(R.id.VIEW1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		sm.unregisterListener(this.myListener);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		sm.registerListener(myListener, o, SensorManager.SENSOR_DELAY_GAME);
	}

	protected SensorEventListener myListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			TextView tv1 = (TextView) findViewById(oc.snake.R.id.tv1);
			TextView tv2 = (TextView) findViewById(oc.snake.R.id.tv2);
			TextView tv3 = (TextView) findViewById(oc.snake.R.id.tv3);
			tv1.setText(Float.toString(event.values[0]));
			tv2.setText(Float.toString(event.values[1]));
			tv3.setText(Float.toString(event.values[2]));
			int dx = (int) - event.values[1];
			int dy = (int)   event.values[2];
			if (Math.abs(dx) <= 1) dx = 0;
			if (Math.abs(dy) <= 1) dy = 0;
			if (dx!=0 || dy!=0) {
				view.setDirection(dx, dy);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
		}
		
	};
}
