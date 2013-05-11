package oc.snake.game;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * A 2D vector that can be used for directions, speeds etc.
 * @author Zsee
 *
 */
public class Vector2D extends PointF {
	
	public Vector2D(PointF p1, PointF p2) {
		super( p2.x-p1.x, p2.y-p1.y );
	}
	
	public Vector2D(Point p1, Point p2) {
		super( p2.x-p1.x, p2.y-p1.y );
	}
	
	public Vector2D() {
		super();
	}
	
	public Vector2D(float x, float y) {
		super(x,y);
	}
	
	public Vector2D(PointF p) {
		super(p.x, p.y);
	}
	
	public Vector2D(Point p) {
		super(p.x, p.y);
	}
	
	/**
	 * Get the modulus/length of the vector
	 */
	public float getModulus() {
		return (float) Math.sqrt( x*x + y*y );
	}
	
	/**
	 * Change the vectors modulus (length) but keep the direction
	 * @param m
	 */
	public void setModulus(float m) {
		normalize();
		x *= m;
		y *= m;
	}
	
	/**
	 * Set the rotation vector, but keep the length
	 * @param v Should be normalized
	 */
	public void setRotationVector(Vector2D v) {
		float m = getModulus();
		x = v.x * m;
		y = v.y * m;
	}
	
	/**
	 * Normalize the vector
	 */
	public void normalize() {
		float len = this.getModulus();
		x = x/len;
		y = y/len;
	}
	
	/**
	 * Get the same vector's normalized form (direction)
	 * @return
	 */
	public Vector2D toNormalizedVector() {
		Vector2D n = new Vector2D(this);
		n.normalize();
		return n;
	}
	
	/**
	 * Calculate the scalar product with another vector
	 * @param v 
	 * @return
	 */
	public float scalarProduct(Vector2D v) {
		return this.x*v.x + this.y*v.y;
	}
	
	/**
	 * Get the rotation angle in radians
	 * interval:[-PI, PI]
	 * @return radians
	 */
	public float getDirectionRadians() {
		if (x== 0 && y==0) {
			return 0;
		}
		int v = 0;
		if (x<0) {
			if (y>0) {
				v = 1;
			} else {
				v = -1;
			}
		}
		return (float) (Math.atan(y/x) + (v * Math.PI));
	}
	
	/**
	 * Get the rotation angle in degrees
	 * @return degrees
	 */
	public float getDirectionDegrees() {
		return (float) (180 * getDirectionRadians() / Math.PI);
	}
	
	/**
	 * Get the direction vector (the normalized form)
	 * @return The normalized form of this vector
	 */
	public Vector2D getDirectionVector() {
		return toNormalizedVector();
	}
	
	public Point toPoint() {
		return new Point((int)this.x, (int)this.y);
	}
	
	public void invert() {
		x = -x;
		y = -y;
	}
}
