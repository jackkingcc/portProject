package com.jack.port;

import java.util.Random;

import org.apache.log4j.Logger;





public class Units {

	private static Random random = new Random();
	private final static Logger logger = Logger.getLogger(Units.class);
	
	public static int randomEquidistribution(int from, int to) {
		double i=random.nextDouble();
		int res= (int) (i*(to-from)+from);
		logger.info("random from "+from+" to "+to+" : result is "+ res);
		return res;
	}
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++)
		System.out.println(Units.randomEquidistribution(15,145));
	}
	
}
