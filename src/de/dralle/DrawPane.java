package de.dralle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

public class DrawPane extends JPanel {
	private Point coordinateSystemOriginOnScreen;
	private Point coordinateSystemInternalOrigin;
	private List<Point> pointList;

	public List<Point> getPointList() {
		return pointList;
	}

	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}

	public Point getCoordinateSystemInternalOrigin() {
		return coordinateSystemInternalOrigin;
	}

	public void setCoordinateSystemInternalOrigin(Point coordinateSystemInternalOrigin) {
		this.coordinateSystemInternalOrigin = coordinateSystemInternalOrigin;
	}

	public Dimension getCoordinateSystemInternalPosSize() {
		return coordinateSystemInternalPosSize;
	}

	public void setCoordinateSystemInternalPosSize(Dimension coordinateSystemInternalPosSize) {
		this.coordinateSystemInternalPosSize = coordinateSystemInternalPosSize;
	}

	public int getxScale() {
		return xScale;
	}

	public void setxScale(int xScale) {
		this.xScale = xScale;
	}

	public int getyScale() {
		return yScale;
	}

	public void setyScale(int yScale) {
		this.yScale = yScale;
	}

	private Dimension coordinateSystemInternalPosSize;
	private int xScale, yScale;

	public Point getCoordinateSystemOriginOnScreen() {
		return coordinateSystemOriginOnScreen;
	}

	public void setCoordinateSystemOriginOnScreen(Point coordinateSystemOriginOnScreen) {
		this.coordinateSystemOriginOnScreen = coordinateSystemOriginOnScreen;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		drawCoordinateSystem(g);
	}

	private void drawCoordinateSystem(Graphics g) {
		if (coordinateSystemOriginOnScreen != null) {
			Dimension drawArea = getSize();
			g.setColor(Color.BLACK);
			// draw lines
			g.drawLine(coordinateSystemOriginOnScreen.x, 0, coordinateSystemOriginOnScreen.x, drawArea.height);
			g.drawLine(0, coordinateSystemOriginOnScreen.y, drawArea.width, coordinateSystemOriginOnScreen.y);

			// draw origin
			g.fillOval(coordinateSystemOriginOnScreen.x - 2, coordinateSystemOriginOnScreen.y - 2, 4, 4);
			// scale
			// x
			for (int i = coordinateSystemInternalOrigin.x
					+ xScale; i < coordinateSystemInternalPosSize.width; i += xScale) {
				g.drawLine(mapXAxis(i), coordinateSystemOriginOnScreen.y - 2, mapXAxis(i),
						coordinateSystemOriginOnScreen.y + 2);
			}
			// y
			for (int i = coordinateSystemInternalOrigin.y
					+ yScale; i < coordinateSystemInternalPosSize.height; i += yScale) {
				g.drawLine(coordinateSystemOriginOnScreen.x - 2, mapYAxis(i), coordinateSystemOriginOnScreen.x + 2,
						mapYAxis(i));
			}

			// x neg
			int currentInternalPos = coordinateSystemInternalOrigin.x;
			int currentPosOnScreen = coordinateSystemOriginOnScreen.x;
			while (currentPosOnScreen > 0) {
				currentInternalPos -= xScale;
				currentPosOnScreen = mapXAxis(currentInternalPos);
				g.drawLine(currentPosOnScreen, coordinateSystemOriginOnScreen.y - 2, currentPosOnScreen,
						coordinateSystemOriginOnScreen.y + 2);
			}
			// y neg
			currentInternalPos = coordinateSystemInternalOrigin.y;
			currentPosOnScreen = coordinateSystemOriginOnScreen.y;
			while (currentPosOnScreen < drawArea.height) {
				currentInternalPos -= yScale;
				currentPosOnScreen = mapYAxis(currentInternalPos);
				g.drawLine(coordinateSystemOriginOnScreen.x - 2, currentPosOnScreen,
						coordinateSystemOriginOnScreen.x + 2, currentPosOnScreen);
			}

			// points
			for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
				Point point = (Point) iterator.next();
				g.drawLine(mapXAxis(point.x) - 2, mapYAxis(point.y) - 2, mapXAxis(point.x) + 2, mapYAxis(point.y) + 2);
				g.drawLine(mapXAxis(point.x) - 2, mapYAxis(point.y) + 2, mapXAxis(point.x) + 2, mapYAxis(point.y) - 2);
			}

			double s0 = getS0();
			double s1 = getS1();

			// function
			SimpleLinearRegresFunction alrf = new SimpleLinearRegresFunction();
			alrf.setOffset(s0);
			alrf.setRate(s1);

			// line
			g.drawLine(mapXAxis(0), mapYAxis((int) alrf.getY(0)), mapXAxis(coordinateSystemInternalPosSize.width),
					mapYAxis((int) alrf.getY(coordinateSystemInternalPosSize.width)));
			// line neg
			g.drawLine(mapXAxis(0), mapYAxis((int) alrf.getY(0)), mapXAxis(-coordinateSystemInternalPosSize.width),
					mapYAxis((int) alrf.getY(-coordinateSystemInternalPosSize.width)));
			// residuals
			g.setColor(Color.RED);
			for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
				Point point = (Point) iterator.next();
				double yCalculated = alrf.getY(point.x);
				g.drawLine(mapXAxis(point.x), mapYAxis(point.y), mapXAxis(point.x), mapYAxis(yCalculated));
			}
			// draw numbers
			g.setColor(Color.BLUE);
			g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 11));
			// origin

			g.drawString(coordinateSystemInternalOrigin.x + ", " + coordinateSystemInternalOrigin.y,
					mapXAxis(coordinateSystemInternalOrigin.x) - 30, mapYAxis(coordinateSystemInternalOrigin.y) + 13);
			// x axis
			for (int i = coordinateSystemInternalOrigin.x
					+ xScale; i < coordinateSystemInternalPosSize.width; i += xScale) {
				g.drawString(i + "", mapXAxis(i) - 13, mapYAxis(coordinateSystemInternalOrigin.y) + 13);
			}
			// y axis
			for (int i = coordinateSystemInternalOrigin.y
					+ yScale; i < coordinateSystemInternalPosSize.height; i += yScale) {
				g.drawString(i + "", mapXAxis(coordinateSystemInternalOrigin.x) - 15, mapYAxis(i) + 13);
			}
			// x axis neg
			currentInternalPos = coordinateSystemInternalOrigin.x;
			currentPosOnScreen = coordinateSystemOriginOnScreen.x;
			while (currentPosOnScreen > 0) {
				currentInternalPos -= xScale;
				currentPosOnScreen = mapXAxis(currentInternalPos);
				g.drawString(currentInternalPos + "", currentPosOnScreen - 13,
						mapYAxis(coordinateSystemInternalOrigin.y) + 13);
			}
			// y axis neg
			currentInternalPos = coordinateSystemInternalOrigin.y;
			currentPosOnScreen = coordinateSystemOriginOnScreen.y;
			while (currentPosOnScreen < drawArea.height) {
				currentInternalPos -= yScale;
				currentPosOnScreen = mapYAxis(currentInternalPos);
				g.drawString(currentInternalPos + "", mapXAxis(coordinateSystemInternalOrigin.x) - 15,
						currentPosOnScreen + 13);
			}
		}

	}

	private double map(double v, int max, int targetMax) {
		double tmp = v / max;
		return tmp * targetMax;
	}

	private int mapXAxis(double v) {
		return coordinateSystemOriginOnScreen.x + (int) map(v, coordinateSystemInternalPosSize.width,
				getSize().width - coordinateSystemOriginOnScreen.x);
	}

	private int mapYAxis(double v) {
		return coordinateSystemOriginOnScreen.y
				- (int) map(v, coordinateSystemInternalPosSize.height, coordinateSystemOriginOnScreen.y);
	}


	private double getXAverage() {
		double sum = 0;
		for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			sum += point.x;
		}
		return sum / pointList.size();
	}

	private double getYAverage() {
		double sum = 0;
		for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			sum += point.y;
		}
		return sum / pointList.size();
	}

	private double getS1() {
		return getSumAllPointAvgDistance() / getSumAllPointsXAvgDistSquared();
	}

	private double getS0() {
		return getYAverage() - getS1() * getXAverage();
	}

	private double getSumAllPointAvgDistance() {
		double sum = 0;
		for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			sum += (point.x - getXAverage()) * (point.y - getYAverage());
		}
		return sum;
	}

	private double getSumAllPointsXAvgDistSquared() {
		double sum = 0;
		for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			sum += (point.x - getXAverage()) * (point.x - getXAverage());
		}
		return sum;
	}

}
