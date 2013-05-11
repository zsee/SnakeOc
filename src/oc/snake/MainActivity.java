package oc.snake;

import oc.snake.game.SnakeGame;
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
	private SnakeGame view;
	protected SensorManager sm;
	protected Sensor o;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		o = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		view = (SnakeGame) findViewById(R.id.VIEW1);
		view.start();
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
		//view.pause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//view.resume();
	}
}
