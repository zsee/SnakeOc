package oc.snake.gamebase;

import android.graphics.Canvas;

public abstract class ConditionalGameView extends GameView {
	
	protected GameView activeView = null;
	
	protected abstract GameView activeView(Object gameState);
	

	@Override
	public void onActivate(Object gameState) {
	}

	@Override
	public void onDeactivate(Object gameState) {
	}

	@Override
	public void update(long elapsedTtime, Object gameState) {
		activeView = activeView(gameState);
		if (activeView != null) {
			activeView.update(elapsedTtime, gameState);
		}
	}

	@Override
	public void draw(Canvas canvas) {
		if (activeView != null) {
			activeView.draw(canvas);
		}
	}

	@Override
	public void onClick(int x, int y, Object gameState) {
		if (activeView != null) {
			activeView.onClick(x, y, gameState);
		}
	}
}
