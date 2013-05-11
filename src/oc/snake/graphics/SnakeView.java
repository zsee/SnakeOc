package oc.snake.graphics;

import oc.snake.game.Vector2D;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SnakeView extends ImageView {
	protected xPoint p = new xPoint();
	protected Snake e = new Snake();
	
	public SnakeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public SnakeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SnakeView(Context context) {
		super(context);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		Vector2D target = new Vector2D(event.getX(), event.getY());
		e.getHead().follow(target);
		e.getDirection().set(e.getHead().getDirection());
		e.update(10000);
		
		this.invalidate();
		return true;
	}
	
	public void setDirection(int dx, int dy) {
		int x = p.getX();
		int y = p.getY();
		p.setPos(x+dx,y+dy);
		e.getDirection().set(dx, dy);
		e.update(5000);
		invalidate();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		p.draw(canvas);
		e.draw(canvas);
	}
}

