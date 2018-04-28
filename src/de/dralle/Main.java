package de.dralle;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Main {
	static Dimension defaultD=new Dimension(800, 600);
	static Point coordinateSystemOriginOnScreen;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f=new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(defaultD);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation(screen.width/2-defaultD.width/2, screen.height/2-defaultD.height/2);
		f.setVisible(true);
		
		LinearRegresDrawPane drawPane = new LinearRegresDrawPane();
		//
		List<Point2D> pointList=new ArrayList();
		
//		pointList.add(new Point2DDouble(0, 1));
//		pointList.add(new Point2DDouble(1, 1));
//		pointList.add(new Point2DDouble(2, 4));
//		pointList.add(new Point2DDouble(3, 8));
//		pointList.add(new Point2DDouble(4, 16));
//		pointList.add(new Point2DDouble(5, 32));
//		pointList.add(new Point2DDouble(6, 64));
//		pointList.add(new Point2DDouble(7, 128));
//		pointList.add(new Point2DDouble(8, 256));
//		coordinateSystemOriginOnScreen=new Point(50,350);
//		drawPane.setCoordinateSystemInternalOrigin(new Point2DDouble(0, 0));
//		drawPane.setCoordinateSystemInternalPosSize(new Dimension(10, 300));
//		drawPane.setxScale(1);
//		drawPane.setyScale(25);
//		drawPane.setFunctionDefinitionMin(-1);
//		drawPane.setFunctionefinitionMax(9);
//		drawPane.setFunctionSampleRate(1);
		
		pointList.add(new Point2DDouble(155,51));
		pointList.add(new Point2DDouble(162, 55));
		pointList.add(new Point2DDouble(175,60));
		pointList.add(new Point2DDouble(181, 78));
		pointList.add(new Point2DDouble(187, 84));
		pointList.add(new Point2DDouble(190, 90));
		pointList.add(new Point2DDouble(195, 95));
		pointList.add(new Point2DDouble(199, 100));
		coordinateSystemOriginOnScreen=new Point(50,550);
		drawPane.setDrawFunction(false);
		drawPane.setDrawResiduals(false);
		drawPane.setCoordinateSystemInternalOrigin(new Point2DDouble(0, 0));
		drawPane.setCoordinateSystemInternalPosSize(new Dimension(220, 110));
		drawPane.setxScale(10);
		drawPane.setyScale(10);
		drawPane.setFunctionDefinitionMin(140);
		drawPane.setFunctionDefinitionMax(210);
		drawPane.setFunctionSampleRate(1);
		
//		pointList.add(new Point2DDouble(-5, -8));
//		pointList.add(new Point2DDouble(-4, -4));
//		pointList.add(new Point2DDouble(-3, -6));
//		pointList.add(new Point2DDouble(-2, -2));
//		pointList.add(new Point2DDouble(-1, -3));
//		pointList.add(new Point2DDouble(0, 2));
//		pointList.add(new Point2DDouble(1, 4));
//		pointList.add(new Point2DDouble(2, 1));
//		pointList.add(new Point2DDouble(3, 3));
//		pointList.add(new Point2DDouble(4, 6));
//		pointList.add(new Point2DDouble(5, 6));
//		coordinateSystemOriginOnScreen=new Point(400,300);
//		drawPane.setCoordinateSystemInternalOrigin(new Point2DDouble(0, 0));
//		drawPane.setCoordinateSystemInternalPosSize(new Dimension(6, 10));
//		drawPane.setxScale(2);
//		drawPane.setyScale(2);
//		drawPane.setFunctionDefinitionMin(-5);
//		drawPane.setFunctionefinitionMax(5);
//		drawPane.setFunctionSampleRate(1);
		
		
		drawPane.setDoubleBuffered(true);
		drawPane.setCoordinateSystemOriginOnScreen(coordinateSystemOriginOnScreen);		
		drawPane.setPointList(pointList);
		f.add(drawPane);
	}
}
