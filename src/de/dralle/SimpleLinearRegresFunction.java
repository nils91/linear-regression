package de.dralle;

public class SimpleLinearRegresFunction implements IFunction{
	private double offset;
	public double getOffset() {
		return offset;
	}
	public void setOffset(double offset) {
		this.offset = offset;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	private double rate;
	@Override
	public double getY(double x) {
		return offset+rate*x;
	}
	@Override
	public String toString() {
		return "f(x)="+offset+"+"+rate+"x";
	}
	
	
}
