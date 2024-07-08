package me.tobiasliese;

import org.junit.jupiter.api.Test;


public class TestSystemD {

	@Test
	void testSystemd() {
		SystemD systemD = new SystemD();
		systemD.getUnits();
		System.out.println(systemD);
	}
}
