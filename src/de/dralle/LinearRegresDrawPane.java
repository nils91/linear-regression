package de.dralle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class LinearRegresDrawPane extends JPanel {
	private Point coordinateSystemOriginOnScreen;
	private Point2D coordinateSystemInternalOrigin = new Point2DDouble(0, 0);
	private double functionDefinitionMin;
	private double functionDefinitionMax;
	private double functionSampleRate = 1;
	private boolean drawFunction = true;
	private int k=1;

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public boolean isDrawFunction() {
		return drawFunction;
	}

	public void setDrawFunction(boolean drawFunction) {
		this.drawFunction = drawFunction;
	}

	public boolean isDrawResiduals() {
		return drawResiduals;
	}

	public void setDrawResiduals(boolean drawResiduals) {
		this.drawResiduals = drawResiduals;
	}

	private boolean drawResiduals = true;

	public double getFunctionDefinitionMin() {
		return functionDefinitionMin;
	}

	public void setFunctionDefinitionMin(double functionDefinitionMin) {
		this.functionDefinitionMin = functionDefinitionMin;
	}

	public double getFunctionDefinitionMax() {
		return functionDefinitionMax;
	}

	public void setFunctionDefinitionMax(double functionefinitionMax) {
		this.functionDefinitionMax = functionefinitionMax;
	}

	public double getFunctionSampleRate() {
		return functionSampleRate;
	}

	public void setFunctionSampleRate(double functionSampleRate) {
		this.functionSampleRate = functionSampleRate;
	}

	private List<Point2D> pointList;

	public List<Point2D> getPointList() {
		return pointList;
	}

	public void setPointList(List<Point2D> pointList2) {
		this.pointList = pointList2;
	}

	public Point2D getCoordinateSystemInternalOrigin() {
		return coordinateSystemInternalOrigin;
	}

	public void setCoordinateSystemInternalOrigin(Point2D point) {
		this.coordinateSystemInternalOrigin = point;
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
			//do matrix stuff
			RealVector y=new ArrayRealVector(new double[pointList.size()]);
			for (int i = 0; i < pointList.size(); i++) {
				y.setEntry(i,pointList.get(i).getY());
			}
			RealMatrix xMatrix=new Array2DRowRealMatrix(pointList.size(), k+1);
			for (int i = 0; i < k+1; i++) {
				for (int j = 0; j < pointList.size(); j++) {					
					xMatrix.setEntry(j, i, Math.pow(pointList.get(j).getX(), i));			
					
				}
			}
			RealMatrix xMatrixMultipliedWithItselfTransposed = xMatrix.transpose().multiply(xMatrix);
			DecompositionSolver solver=new LUDecomposition(xMatrixMultipliedWithItselfTransposed).getSolver();
			RealMatrix inverse = solver.getInverse();
			RealMatrix inverseWithXMatrixTransposed = inverse.multiply(xMatrix.transpose());
			RealVector weights = inverseWithXMatrixTransposed.operate(y);
			
			Dimension drawArea = getSize();
			g.setColor(Color.BLACK);
			// draw lines
			g.drawLine(coordinateSystemOriginOnScreen.x, 0, coordinateSystemOriginOnScreen.x, drawArea.height);
			g.drawLine(0, coordinateSystemOriginOnScreen.y, drawArea.width, coordinateSystemOriginOnScreen.y);

			// draw origin
			g.fillOval(coordinateSystemOriginOnScreen.x - 2, coordinateSystemOriginOnScreen.y - 2, 4, 4);
			// scale
			// x
			for (double i = coordinateSystemInternalOrigin.getX()
					+ xScale; i < coordinateSystemInternalPosSize.width; i += xScale) {
				g.drawLine(mapXAxis(i), coordinateSystemOriginOnScreen.y - 2, mapXAxis(i),
						coordinateSystemOriginOnScreen.y + 2);
			}
			// y
			for (double i = coordinateSystemInternalOrigin.getY()
					+ yScale; i < coordinateSystemInternalPosSize.height; i += yScale) {
				g.drawLine(coordinateSystemOriginOnScreen.x - 2, mapYAxis(i), coordinateSystemOriginOnScreen.x + 2,
						mapYAxis(i));
			}

			// x neg
			double currentInternalPos = coordinateSystemInternalOrigin.getX();
			int currentPosOnScreen = coordinateSystemOriginOnScreen.x;
			while (currentPosOnScreen > 0) {
				currentInternalPos -= xScale;
				currentPosOnScreen = mapXAxis(currentInternalPos);
				g.drawLine(currentPosOnScreen, coordinateSystemOriginOnScreen.y - 2, currentPosOnScreen,
						coordinateSystemOriginOnScreen.y + 2);
			}
			// y neg
			currentInternalPos = coordinateSystemInternalOrigin.getY();
			currentPosOnScreen = coordinateSystemOriginOnScreen.y;
			while (currentPosOnScreen < drawArea.height) {
				currentInternalPos -= yScale;
				currentPosOnScreen = mapYAxis(currentInternalPos);
				g.drawLine(coordinateSystemOriginOnScreen.x - 2, currentPosOnScreen,
						coordinateSystemOriginOnScreen.x + 2, currentPosOnScreen);
			}

			// points
			for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
				Point2D point = (Point2D) iterator.next();
				g.drawLine(mapXAxis(point.getX()) - 2, mapYAxis(point.getY()) - 2, mapXAxis(point.getX()) + 2,
						mapYAxis(point.getY()) + 2);
				g.drawLine(mapXAxis(point.getX()) - 2, mapYAxis(point.getY()) + 2, mapXAxis(point.getX()) + 2,
						mapYAxis(point.getY()) - 2);
			}

			double s0 = getS0();
			double s1 = getS1();

			// function
			SimpleLinearRegresFunction alrf = new SimpleLinearRegresFunction();
			alrf.setOffset(s0);
			alrf.setRate(s1);

			// line
			if (drawFunction) {
				List<Point2D> functionSamplePoints = new ArrayList<>();
				for (double x = functionDefinitionMin; x <= functionDefinitionMax; x += functionSampleRate) {
					functionSamplePoints.add(new Point2DDouble(x, alrf.getY(x)));
				}
				for (int i = 0; i < functionSamplePoints.size() - 1; i++) {
					g.drawLine(mapXAxis(functionSamplePoints.get(i).getX()),
							mapYAxis(functionSamplePoints.get(i).getY()),
							mapXAxis(functionSamplePoints.get(i + 1).getX()),
							mapYAxis(functionSamplePoints.get(i + 1).getY()));
				}
			}
			if (drawResiduals) {
				// residuals
				g.setColor(Color.RED);
				for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
					Point2D point = (Point2D) iterator.next();
					double yCalculated = alrf.getY(point.getX());
					g.drawLine(mapXAxis(point.getX()), mapYAxis(point.getY()), mapXAxis(point.getX()),
							mapYAxis(yCalculated));
				}
			}
			// draw numbers
			g.setColor(Color.BLUE);
			g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 11));
			// origin

			g.drawString(coordinateSystemInternalOrigin.getX() + ", " + coordinateSystemInternalOrigin.getY(),
					mapXAxis(coordinateSystemInternalOrigin.getX()) - 30,
					mapYAxis(coordinateSystemInternalOrigin.getY()) + 13);
			// x axis
			for (double i = coordinateSystemInternalOrigin.getX()
					+ xScale; i < coordinateSystemInternalPosSize.width; i += xScale) {
				g.drawString(i + "", mapXAxis(i) - 13, mapYAxis(coordinateSystemInternalOrigin.getY()) + 13);
			}
			// y axis
			for (double i = coordinateSystemInternalOrigin.getY()
					+ yScale; i < coordinateSystemInternalPosSize.height; i += yScale) {
				g.drawString(i + "", mapXAxis(coordinateSystemInternalOrigin.getX()) - 15, mapYAxis(i) + 13);
			}
			// x axis neg
			currentInternalPos = coordinateSystemInternalOrigin.getX();
			currentPosOnScreen = coordinateSystemOriginOnScreen.x;
			while (currentPosOnScreen > 0) {
				currentInternalPos -= xScale;
				currentPosOnScreen = mapXAxis(currentInternalPos);
				g.drawString(currentInternalPos + "", currentPosOnScreen - 13,
						mapYAxis(coordinateSystemInternalOrigin.getY()) + 13);
			}
			// y axis neg
			currentInternalPos = coordinateSystemInternalOrigin.getY();
			currentPosOnScreen = coordinateSystemOriginOnScreen.y;
			while (currentPosOnScreen < drawArea.height) {
				currentInternalPos -= yScale;
				currentPosOnScreen = mapYAxis(currentInternalPos);
				g.drawString(currentInternalPos + "", mapXAxis(coordinateSystemInternalOrigin.getX()) - 15,
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
			Point2D point = (Point2D) iterator.next();
			sum += point.getX();
		}
		return sum / pointList.size();
	}

	private double getYAverage() {
		double sum = 0;
		for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
			Point2D point = (Point2D) iterator.next();
			sum += point.getY();
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
			Point2D point = (Point2D) iterator.next();
			sum += (point.getX() - getXAverage()) * (point.getY() - getYAverage());
		}
		return sum;
	}

	private double getSumAllPointsXAvgDistSquared() {
		double sum = 0;
		for (Iterator iterator = pointList.iterator(); iterator.hasNext();) {
			Point2D point = (Point2D) iterator.next();
			sum += Math.pow(point.getX() - getXAverage(), 2);
		}
		return sum;
	}

}
