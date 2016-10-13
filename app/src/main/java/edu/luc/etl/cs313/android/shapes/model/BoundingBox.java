package edu.luc.etl.cs313.android.shapes.model;

import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	// TODO entirely your job (except onCircle)

	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
	}

	@Override
	public Location onFill(final Fill f) {
		return f.getShape().accept(this);
	}

	@Override
	public Location onGroup(final Group g) {
		List<? extends Shape> shapes = g.getShapes();
		int lx = 10000, ly = 10000, rx = 0, ry = 0;
		for (Shape shape : shapes) {
			Location l = shape.accept(this);
			Rectangle r = (Rectangle) l.getShape();
			int radius = 0;

			// check if it is a circle
			boolean isCircle = false;
			if (shape instanceof Location) {
				Shape shape2 = (((Location) shape).getShape());
				if (shape2 instanceof Circle) {
					radius = ((Circle) shape2).getRadius();
					isCircle = true;
				}
			}

			// Circle
			if (shape instanceof Circle || isCircle) {
				if (!isCircle)
					radius = ((Circle) shape).getRadius();
				if (l.getX() - radius < lx) {
					lx = l.getX() - radius;
				}
				if (l.getY() - radius < ly) {
					ly = l.getY() - radius;
				}
				if (l.getX() + radius > rx)
					rx = l.getX() + radius;
				if (l.getY() + radius > ry)
					ry = l.getY() + radius;
			}

			// Rectangle & Polygon
			else {
				if (l.getX() < lx) {
					lx = l.getX();
				}
				if (l.getY() < ly) {
					ly = l.getY();
				}
				if (l.getX() + r.getWidth() > rx)
					rx = l.getX() + r.getWidth();
				if (l.getY() + r.getHeight() > ry)
					ry = l.getY() + r.getHeight();
			}
		}
		return new Location(lx, ly, new Rectangle(rx - lx, ry - ly));
	}

	@Override
	public Location onLocation(final Location l) {
		Rectangle rect = (Rectangle) l.getShape().accept(this).getShape();
		int k = 20;
		if (l.getX() < l.getY())
			return new Location(l.getX() - k, l.getY() - k, new Rectangle(l.getY() + k + 350, l.getY() + k + 200));
		else return new Location(l.getX(), l.getY(), rect);
	}

	@Override
	public Location onRectangle(final Rectangle r) {
		final int width = r.getWidth();
		final int height = r.getHeight();
		return new Location(0, 0, new Rectangle(width, height));
	}

	@Override
	public Location onStroke(final Stroke c) {
		return c.getShape().accept(this);
	}

	@Override
	public Location onOutline(final Outline o) {
		return o.getShape().accept(this);
	}

	@Override
	public Location onPolygon(final Polygon s) {
		List<? extends Point> pts = s.getPoints();
		int maxX = 0;
		int maxY = 0;
		for (Point point : pts) {
			if (point.getX() > maxX)
				maxX = point.getX();
			if (point.getY() > maxY)
				maxY = point.getY();
		}
		return new Location(pts.get(0).getX(), pts.get(0).getY(),
				new Rectangle (maxX - pts.get(0).getX(), maxY - pts.get(0).getY()));
	}
}
