package oc.snake.game.elements;

import oc.snake.game.state.PlayState;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

abstract public class PowerUp extends Food {
	
	protected int appearAfterFood = 0;
	protected boolean shown = false;
	protected boolean eaten = false;
	protected boolean terminated = false;
	protected Context context = null;
	protected Bitmap bmp = null;
	protected int effectDuration = 5000;
	
	public PowerUp() {
		super();
		paint.setAntiAlias(true);
	}
	
	public void setEffectDuration(int d) {
		effectDuration = d;
	}
	
	public int getEffectDuration() {
		return effectDuration;
	}
	
	public void setAppearTime(int time) {
		appearTime = (int)(Math.random() * time);
	}
	
	public int getAppearTime() {
		return appearTime;
	}
	
	public int getAppearFoodNumber() {
		return appearAfterFood;
	}
	
	public void setAppearFoodNumber(int a) {
		appearAfterFood = a;
	}
	
	@Override
	public Rect getBoundingBox() {
		if (bmp == null) {
			return new Rect(
					(int)position.x - 12,
					(int)position.y - 12,
					(int)position.x + 12,
					(int)position.y + 12
					);
		} else {
			return new Rect(
					(int)position.x - bmp.getWidth()/2,
					(int)position.y - bmp.getHeight()/2,
					(int)position.x + bmp.getWidth()/2,
					(int)position.y + bmp.getHeight()/2
					);
		}
	}
	
	abstract public Bitmap getBitmap();
	
	abstract public void onEat(long time, PlayState state);
	
	abstract public void updateActive(long time, PlayState state);
	
	abstract public void onEffectTerminated(long time, PlayState state);
	
	public PowerUpState getPowerUpState() {
		if (!shown && !eaten) {
			return PowerUpState.Invisible;
		}
		if (shown && !eaten) {
			return PowerUpState.Visible;
		}
		if (shown && eaten && !terminated) {
			return PowerUpState.Active;
		} else {
			return PowerUpState.Termintated;
		}
	}
	
	@Override
	public void update(long time, Object state) {
		PlayState gameState = (PlayState) state;
		if (context == null) {
			context = gameState.getContext();
		}
		if (!shown && !eaten && gameState.numFoodPickedUp() >= appearAfterFood) {
			reposition(gameState);
			this.eatTime = 0;
			shown = true;
		}
		if (shown && !eaten) {
			this.eatTime += time;
			if (eatTime > appearTime) {
				position.set(generatedPosition);
			}
			Rect headp = gameState.getSnake().getHead().getBoundingBox();
			if (Rect.intersects(headp, getBoundingBox())) {
				onEat(time, gameState);
				position.set(-100,-100);
				generatedPosition.set(position);
				eatTime = 0;
				eaten = true;
			}
		}
		if (shown && eaten && !terminated) {
			if (eatTime < effectDuration) {
				updateActive(time, gameState);
				eatTime += time;
			} else {
				terminated = true;
				onEffectTerminated(time, gameState);
			}
		}
	}
	
	@Override
	public void draw(Canvas c) {
		if (this.getPowerUpState() != PowerUpState.Termintated) {
			if (context != null && bmp == null) {
				bmp = getBitmap();
			}
			if (bmp != null) {
				c.drawBitmap(bmp,position.x - bmp.getWidth()/2, position.y - bmp.getHeight()/2, paint);
			}
		}
	}
	
}
