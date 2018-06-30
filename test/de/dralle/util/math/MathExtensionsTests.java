package de.dralle.util.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MathExtensionsTests {

	@Test
	void logToBaseTest() {
		double expected = Math.log10(2);
		double got = MathExtensions.logToBase(2, 10);
		double delta = Math.abs(expected-got);
		assertTrue(delta < 0.1);
	}
	@Test
	void logToBase2Test() {
		double expected = MathExtensions.logToBase(2, 2);
		double got = MathExtensions.log2(2);
		assertEquals(expected, got);
	}
	@Test
	void maxIntTest() {		
		assertEquals(10, MathExtensions.max(new int[] {1,5,4,3,7,2,8,7,10,4,-5}));
	}
	@Test
	void maxDoubleTest() {		
		assertEquals(13.37, MathExtensions.max(new Double[] {1.0,5.6,4.334,3.79,.7,13.37,8.22,7.83,10.1,4.8,-5.0}).doubleValue());
	}
	@Test
	void minIntTest() {		
		assertEquals(-5, MathExtensions.max(new int[] {1,5,4,3,7,2,8,7,10,4,-5}));
	}
	@Test
	void minDoubleTest() {		
		assertEquals(-5.0, MathExtensions.max(new Double[] {1.0,5.6,4.334,3.79,.7,13.37,8.22,7.83,10.1,4.8,-5.0}).doubleValue());
	}
}
