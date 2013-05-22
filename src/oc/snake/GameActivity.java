package oc.snake;

import oc.snake.game.SnakeGameView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class GameActivity extends Activity {
	private SnakeGameView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        //                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		view = (SnakeGameView) findViewById(R.id.VIEW1);
		int level = getIntent().getIntExtra("LEVEL", 1);
		view.setLevel(level);
		view.start();
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
