package oc.snake.graphics;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class Point extends Drawable {
	protected int size = 5;
	protected int posx = 100;
	protected int posy = 100;

	@Override
	public void draw(Canvas c) {
		int i,j;
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setARGB(255, 255, 0, 0);
		for (i=0;i<size;i++)
			for (j=0;j<size;j++)
				c.drawPoint(posx+i, posy+j, paint);
	}
	
	public void setPos(int x, int y) {
		posx = x;
		posy = y;
	}
	
	public int getX() {
		return posx;
	}
	
	public int getY() {
		return posy;
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

}
