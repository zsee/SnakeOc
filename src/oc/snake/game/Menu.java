package oc.snake.game;

import oc.snake.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class Menu extends Drawable
	implements
		Updateable
{
	protected enum State {
		Initital,
		Help,
		Menu,
		Starting,
		Over
	}
	
	protected Paint paint = new Paint();
	protected Bitmap background = null;
	protected int totalTime = 0;
	protected float appearTime = 2000;
	protected State state = State.Initital;
	protected SnakeGameState gameState = null;
	protected boolean visible = true;
	protected Context context = null;


	@Override
	public void draw(Canvas c) {
		if (background != null && visible) {
			c.drawBitmap(background, 0, 0, paint);
		}
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
		// TODO Auto-generated method stub

	}
	
	protected void updateState() {
		if (state == State.Initital) {
			if (totalTime > appearTime) {
				paint.setAlpha(255);
			} else {
				paint.setAlpha((int) ((totalTime/appearTime)*255));
			}
		}
		if (state == State.Starting) {
			if (totalTime > appearTime) {
				visible = false;
				//
				gameState.init();
				gameState.loadLevel(1);
				gameState.setSituation(GameSituation.Playing);
			} else {
				paint.setAlpha((int) ((1-totalTime/appearTime)*255) );
			}
		}
	}

	@Override
	public void update(long elapsedTtime, Object gameState) throws Exception {
		totalTime += elapsedTtime;
		updateState();
		if (background == null) {
			background = BitmapFactory.decodeResource(((SnakeGameState)gameState).getContext().getResources(), R.drawable.background);
		}
		if (this.gameState == null) {
			this.gameState = (SnakeGameState)gameState;
			this.context = this.gameState.getContext();
		}
	}

	public void onTouch(float x, float y) {
		if (gameState != null && state!=State.Starting) {
			state = State.Starting;
			totalTime = 0;
		}
	}
}
