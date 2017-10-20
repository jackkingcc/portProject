package com.jack.port;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class PortMain {
	
	private static int size=Integer.MAX_VALUE;
	private static int machineCount=1;
	private static int minArriveTime = 15;
	private static int maxArriveTime = 145;
	private static int minUnloadTime = 45;
	private static int maxUnloadTime = 90;
	private static int count=100;
	private static List<Boat> boats = new ArrayList<>();
	private static final Logger logger = Logger.getLogger(PortMain.class);

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("初始化参数...");
		System.out.println("请输入港口的设备数，默认为1...");
		int ou1=processInput(scanner, "输入有误，港口数必须为正整数。请再次输入港口的设备数，默认为1。。。");
		if(ou1!=0) machineCount=ou1;
		
		System.out.println("请输入港口对于等待卸货的区域所能容纳的船只最大数量，默认为没有限制...");
		int ou2 = processInput(scanner, "输入有误，请再次输入港口对于等待卸货的区域所能容纳的船只最大数量，默认为没有限制...");
		if(ou2!=0) size=ou2;
		
		boolean mark1 =false;
		while(!mark1) {
			System.out.println("请输入相邻两船到达的时间间隔的最小值，默认为15分钟...");
			int ou3 = processInput(scanner, "输入有误，请再次输入相邻两船到达的时间间隔的最小值，默认为15分钟");

			System.out.println("请输入相邻两船到达的时间间隔的最大值，默认为145分钟...");
			int ou4 = processInput(scanner, "输入有误，请再次输入相邻两船到达的时间间隔的最大值，默认为145分钟");
			
			if(ou3 >ou4) {
				System.out.println("相邻两船到达的时间间隔的最小值不能比最大值大");
			}else {
				mark1=true;
				if (ou3 != 0) minArriveTime = ou3;
				if (ou4 != 0) maxArriveTime = ou4;
			}
		}
		
		mark1=false;
		
		while(!mark1) {
		System.out.println("请输入一艘船只卸货的时间的最小值，默认为45分钟...");
		int ou5 = processInput(scanner, "输入有误，请再次输入一艘船只卸货的时间的最小值，默认为45分钟");
		
		System.out.println("请输入一艘船只卸货的时间的最大值，默认为90分钟...");
		int ou6 = processInput(scanner, "输入有误，请再次输入一艘船只卸货的时间的最大值，默认为90分钟");
		
		if(ou5 >ou6) {
			System.out.println("一艘船只卸货的时间的最小值不能比最大值大");
		}else {
			mark1=true;
			if(ou5!=0) minUnloadTime=ou5;
			if(ou6!=0) maxUnloadTime=ou6;
		}
		
		}
		
		System.out.println("输入成功，港口设备数为"+machineCount);
		if(size ==Integer.MAX_VALUE) System.out.println("港口不限制等待卸货的区域所能容纳的船只最大数量");
		else System.out.println("港口对等待卸货的区域所能容纳的船只最大数量为"+size);
		System.out.println("相邻两船到达的时间间隔在"+minArriveTime+"分钟到"+maxArriveTime+"分钟之间变化");
		System.out.println("一艘船只卸货的时间在"+minUnloadTime+"分钟到"+maxUnloadTime+"分钟之间变化");

		while(true) {
			System.out.println("请输入模拟的船数量，默认为100艘...");
			int ou7 = processInput(scanner, "输入有误，请再次输入此次模拟的船数量，默认为100艘..");
			if(ou7!=0) count=ou7;
			System.out.println();
			process(count);
		}
		
	}

	public static void process(int count) {
	    	
	    	boats.clear();
	    	
	    	logger.info("Start to run Application with "+count+" ships");
		Port port = new Port(machineCount, size);
		int leftBoat=count;
		int totalTime=1;
		
		while(leftBoat>0) {
			int time=1;
			
			Boat newBoat = new Boat();
			newBoat.setArriveInterval(Units.randomEquidistribution(minArriveTime, maxArriveTime));
			newBoat.setUnloadTime(Units.randomEquidistribution(minUnloadTime, maxUnloadTime));
			
			System.out.println("第"+(count-leftBoat+1)+"艘船的到达间隔为"+newBoat.arriveInterval+"分钟,所需要的卸货时间为"+newBoat.unloadTime+"分钟");
			boats.add(newBoat);
			
			boolean doesArrive =false;
			while(!doesArrive) {
				if(newBoat.getArriveInterval()<=time) {
					doesArrive=true;
					if(port.enchase(newBoat)==false)
						newBoat.setSucess(false);
				}
				time++;
				totalTime++;
				port.workOneMin();
			}
			leftBoat--;
		}
		
		while(port.isWork()){
		    port.workOneMin();
		}
		
		
		int waitTotal=0;
		int successCount=0;
		int waitMax=0;
		int stayTotal=0;
		int stayMax=0;
		
		for(int i=0;i<count;i++) {
			Boat boat = boats.get(i);
			if(!boat.isSucess())
				System.out.println("第"+(i+1)+"艘船没有在该港口卸货");
			else {
				waitTotal+=boat.waitTime;
				waitMax = Math.max(waitMax, boat.waitTime);
				stayTotal+=boat.waitTime+boat.unloadTime;
				stayMax=Math.max(stayMax, boat.waitTime+boat.unloadTime);
				successCount++;
			}
		}
		System.out.println("一艘船待在港口的平均时间为"+(stayTotal*1.0)/successCount+"分钟");
		System.out.println("一艘船待在港口的最长时间"+stayMax+"分钟");
		System.out.println("一艘船的平均等待时间"+(waitTotal*1.0)/successCount+"分钟");
		System.out.println("一艘船的最长等待时间"+waitMax+"分钟");
		
		for(int i=0;i<port.machines.size();i++) {
			Machine machine = port.machines.get(i);
			int freeInt = machine.freeCount+1;
			int workInt = machine.workCount;
			System.out.println("total free time is "+freeInt+" ; total work time is "+workInt); 
			double res= (freeInt*1.0)/(workInt+freeInt);
			System.out.println("第"+(i+1)+"个卸货设备的空闲时间的百分比为"+res);
		}
		
		System.out.println("****************************");
		System.out.println();
			
	}
	


	public static int processInput(Scanner scanner,String s1) {
		boolean mark = false;
		int res=0;
		while (!mark) {
			String in1 = scanner.nextLine();
			if (in1.length() > 0) {
				if (!isNumeric(in1) || Integer.valueOf(in1)<=0) {
					System.out.println(s1);
				} else {
					res = Integer.valueOf(in1);
					mark = true;
				}
			} else {
				mark = true;
			}
		}
		System.out.println();
		return res;
	}

	public static boolean isNumeric(String str) {
        Matcher mer = Pattern.compile("^[0-9]+$").matcher(str);  
        return mer.find();  
	}
}
