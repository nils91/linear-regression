package de.dralle;

import java.util.ArrayList;
import java.util.List;

public class PolynomialFuntion implements IFunction {

	private List<Double> coefficents=new ArrayList<>();
	@Override
	public double getY(double x) {
		double sum=0;
		for (int i = 0; i < coefficents.size(); i++) {
			if(i==0) {
				sum+=coefficents.get(i);
			}else {
				sum+=coefficents.get(i)*Math.pow(x, i);
			}
			
		}
		return sum;
	}
	public void addCoefficent(double coefficent) {
		coefficents.add(new Double(coefficent));
	}
	@Override
	public String toString() {
		String function="f(x)=";
		for (int i = 0; i < coefficents.size(); i++) {
			function+=coefficents.get(i);
			if(i>0) {
				function+="x";
				if(i>1) {
					function+="^"+i;
				}
			}
			if(i<coefficents.size()-1) {
				function+="+";
			}
		}
		return function;
	}
	

}
