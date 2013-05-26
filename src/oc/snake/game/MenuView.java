package oc.snake.game;

import oc.snake.R;
import oc.snake.game.state.MainGameState;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class MenuView extends oc.snake.gamebase.GameView
{
	protected Paint paint = new Paint();
	protected Bitmap background = null;
	protected boolean log = false;
	
	protected int fadeTime = 5000;
	protected int totalTime = 0;
	
	@Override
	public void onActivate(Object gameState) {
		Log.i("menu","Deactivate");
	}
	
	@Override
	public void onDeactivate(Object gameState) {
		Log.i("menu","Deactivate :)");
	}
	
	@Override
	public void update(long elapsedTtime, Object gameState) {
		MainGameState state = (MainGameState) gameState;
		if (background == null) {
			background = BitmapFactory.decodeResource(state.activity.getResources(), R.drawable.background);
		}
		if (totalTime < fadeTime) {
			paint.setAlpha((int)((float)totalTime / fadeTime * 255));
		}
		totalTime += elapsedTtime;
	}
	@Override
	public void draw(Canvas canvas) {
		if (background != null) {
			canvas.drawBitmap(background, 0, 0, paint);
		}
	}

	@Override
	public void onClick(int x, int y, Object gameState) {
		MainGameState state = (MainGameState) gameState;
		state.activeView = "game";
	}
}
