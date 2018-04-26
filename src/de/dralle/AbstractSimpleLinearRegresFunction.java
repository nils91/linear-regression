package de.dralle;

public class AbstractSimpleLinearRegresFunction implements SimpleLinearRegresFunction{
	private double rate;
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getFactor() {
		return factor;
	}
	public void setFactor(double factor) {
		this.factor = factor;
	}
	private double factor;
	@Override
	public double getY(double x) {
		return rate-factor*x;
	}
	
}
