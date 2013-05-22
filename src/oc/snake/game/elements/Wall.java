package oc.snake.game.elements;

import java.util.ArrayList;
import java.util.List;

import oc.snake.R;
import oc.snake.game.Collidable;
import oc.snake.game.SnakeGameState;
import oc.snake.game.Updateable;
import oc.snake.game.Vector2D;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Wall extends Drawable
	implements
		Collidable,
		Updateable
{
	protected static Bitmap horizontalBrick = null;
	protected static Bitmap verticalBrick = null;
	protected static Context context = null;
	
	protected Vector2D size = new Vector2D(20,20);
	protected Vector2D position = new Vector2D();
	protected Paint paint;
	protected boolean snakeHitDetect = true;

	public Wall(Rect r) {
		this();
		position.x = r.left;
		position.y = r.top;
		size.x = r.width();
		size.y = r.height();
	}
	
	public Wall() {
		paint = new Paint();
		paint.setARGB(255, 255, 0, 0);
		paint.setAlpha(255);
	}
	
	public void setSnakeHitDetection(boolean b) {
		snakeHitDetect = b;
	}
	
	public boolean getSnakeHitDetection() {
		return snakeHitDetect;
	}
	
	
	public Paint getPaint() {
		return paint;
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public Vector2D getSize() {
		return size;
	}
	
	protected void drawTiles(Canvas canvas) {
		Bitmap bmp = size.x < size.y ? verticalBrick : horizontalBrick;
		int h = bmp.getHeight();
		int w = bmp.getWidth();
		int ynum = (int)size.y / h;
		int xnum = (int)size.x / w;
		for (int i=0; i<xnum; i++) {
			for (int j=0; j<ynum; j++) {
				canvas.drawBitmap(bmp, position.x + i*w, position.y + j*h, paint);
			}
		}
		int xremaining = (int)size.x & w;
		int yremaining = (int)size.y % h;
		Rect src = new Rect(0,0,w,h);
		Rect dest = new Rect();
		// remaining x;
		int x = (int) (position.x + xnum*w);
		int y = (int) position.y;
		src.set(0,0,xremaining,h);
		for (int i=0; i<ynum; i++) {
			dest.set(x, y, x+xremaining, y+h );
			canvas.drawBitmap(bmp, src, dest, paint);
			y += h;
		}
		// remaining y
		x = (int) (position.x);
		y = (int) (position.y + ynum * h);
		src.set(0,0,w,yremaining);
		for (int i=0; i<xnum; i++) {
			dest.set(x, y, x+w, y+yremaining);
			canvas.drawBitmap(bmp, src, dest, paint);
			x += w;
		}
		// right bottom corner
		x = (int) position.x + xnum*w;
		y = (int) position.y + ynum*h;
		src.set(0,0,xremaining, yremaining);
		dest.set(x,y,x+xremaining,y+yremaining);
		canvas.drawBitmap(bmp,src,dest,paint);
	}
	
	@Override
	public void draw(Canvas canvas) {
		if (horizontalBrick == null || verticalBrick == null) {
			canvas.drawRect(getBoundingBox(),paint);
		} else {
			//canvas.drawRect(getBoundingBox(),paint);
			drawTiles(canvas);			
		}
	}

	@Override
	public int getOpacity() {
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
	}

	@Override
	public Rect getBoundingBox() {
		return new Rect(
				(int)position.x,
				(int)position.y,
				(int)position.x+(int)size.x,
				(int)position.y+(int)size.y
				);
	}

	@Override
	public List<Rect> getBoundingBoxes() {
		List<Rect> l = new ArrayList<Rect>();
		l.add(getBoundingBox());
		return l;
	}

	@Override
	public void update(long elapsedTtime, Object gameState) throws Exception {
		SnakeGameState state = (SnakeGameState) gameState;
		if (context == null) {
			context = state.getContext();
			horizontalBrick = BitmapFactory.decodeResource(context.getResources(), R.drawable.wallh);
			verticalBrick = BitmapFactory.decodeResource(context.getResources(), R.drawable.wallv);
		}
		Rect pos = getBoundingBox();
		if (snakeHitDetect) {
			for (SnakeElement e : state.getSnake().getElements()) {
				if (Rect.intersects(pos, e.getBoundingBox())) {
					state.hitWall();
					break; // only hit the wall once
				}
			}
		}
	}

}
