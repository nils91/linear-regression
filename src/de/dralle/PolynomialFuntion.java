package de.dralle;

import java.util.ArrayList;
import java.util.List;

public class PolynomialFuntion implements IFunction {

	private List<Double> coefficents=new ArrayList<>();
	@Override
	public double getY(double x) {
		double sum=0;
		for (int i = 0; i < coefficents.size(); i++) {
			sum+=coefficents.get(i)*Math.pow(x, i);
		}
		return sum;
	}
	public void addCoefficent(double coefficent) {
		coefficents.add(new Double(coefficent));
	}

}
