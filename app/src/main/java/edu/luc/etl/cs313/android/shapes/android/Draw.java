package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import java.util.List;

import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

	// TODO entirely your job (except onCircle)


	private final Canvas canvas;

	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
		this.canvas = canvas;
		this.paint = paint;
		paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStroke(final Stroke c) {
		paint.setColor(c.getColor());
		c.getShape().accept(this);
		paint.setColor(Color.BLACK);
		return null;
	}

	@Override
	public Void onFill(final Fill f) {
		paint.setStyle(Style.FILL_AND_STROKE);
		f.getShape().accept(this);
		paint.setStyle(Style.STROKE);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {
		for (Shape shape : g.getShapes()) {
			shape.accept(this);
		}
		return null;
	}

	@Override
	public Void onLocation(final Location l) {
		canvas.translate(l.getX(), l.getY());
		l.getShape().accept(this);
		canvas.translate(-l.getX(), -l.getY());
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		float lx = 0, ly = 0;
		canvas.drawRect(lx, ly, lx + r.getWidth(), ly + r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {
		paint.setStyle(Style.STROKE);
		o.getShape().accept(this);
		paint.setStyle(Style.FILL_AND_STROKE);
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {
		List<? extends Point> points = s.getPoints();
		final float[] pts = new float[points.size()*4 + 4];
		int i = 0;
		for (Point point : points) {
			pts[i++] = point.getX();
			pts[i++] = point.getY();
			if (i > 2) {
				pts[i++] = point.getX();
				pts[i++] = point.getY();
			}
		}
		pts[i++] = pts[0];
		pts[i] = pts[1];
		canvas.drawLines(pts, paint);
		return null;
	}
}
//acidently deleted repo trying to undo
//completed TODO, still not passing all tests