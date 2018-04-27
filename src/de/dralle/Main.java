package de.dralle;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Main {
	static Dimension defaultD=new Dimension(800, 600);
	static Point coordinateSystemOriginOnScreen=new Point(400, 300);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f=new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(defaultD);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation(screen.width/2-defaultD.width/2, screen.height/2-defaultD.height/2);
		f.setVisible(true);
		//
		List<Point> pointList=new ArrayList();
		
//		pointList.add(new Point(0, 1));
//		pointList.add(new Point(1, 1));
//		pointList.add(new Point(2, 4));
//		pointList.add(new Point(3, 8));
//		pointList.add(new Point(4, 16));
//		pointList.add(new Point(5, 32));
//		pointList.add(new Point(6, 64));
//		pointList.add(new Point(7, 128));
//		pointList.add(new Point(8, 256));
		
		pointList.add(new Point(-5, -8));
		pointList.add(new Point(-4, -4));
		pointList.add(new Point(-3, -6));
		pointList.add(new Point(-2, -2));
		pointList.add(new Point(-1, -3));
		pointList.add(new Point(0, 2));
		pointList.add(new Point(1, 4));
		pointList.add(new Point(2, 1));
		pointList.add(new Point(3, 3));
		pointList.add(new Point(4, 6));
		pointList.add(new Point(5, 6));
		
		
		DrawPane drawPane = new DrawPane();
		drawPane.setCoordinateSystemOriginOnScreen(coordinateSystemOriginOnScreen);
		drawPane.setCoordinateSystemInternalOrigin(new Point(0, 0));
		drawPane.setCoordinateSystemInternalPosSize(new Dimension(6, 8));
		drawPane.setxScale(2);
		drawPane.setyScale(2);
		drawPane.setPointList(pointList);
		f.add(drawPane);
	}
}
