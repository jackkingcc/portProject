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
		System.out.println("��ʼ������...");
		System.out.println("������ۿڵ��豸����Ĭ��Ϊ1...");
		int ou1=processInput(scanner, "�������󣬸ۿ�������Ϊ�����������ٴ�����ۿڵ��豸����Ĭ��Ϊ1������");
		if(ou1!=0) machineCount=ou1;
		
		System.out.println("������ۿڶ��ڵȴ�ж���������������ɵĴ�ֻ���������Ĭ��Ϊû������...");
		int ou2 = processInput(scanner, "�����������ٴ�����ۿڶ��ڵȴ�ж���������������ɵĴ�ֻ���������Ĭ��Ϊû������...");
		if(ou2!=0) size=ou2;
		
		boolean mark1 =false;
		while(!mark1) {
			System.out.println("�������������������ʱ��������Сֵ��Ĭ��Ϊ15����...");
			int ou3 = processInput(scanner, "�����������ٴ������������������ʱ��������Сֵ��Ĭ��Ϊ15����");

			System.out.println("�������������������ʱ���������ֵ��Ĭ��Ϊ145����...");
			int ou4 = processInput(scanner, "�����������ٴ������������������ʱ���������ֵ��Ĭ��Ϊ145����");
			
			if(ou3 >ou4) {
				System.out.println("�������������ʱ��������Сֵ���ܱ����ֵ��");
			}else {
				mark1=true;
				if (ou3 != 0) minArriveTime = ou3;
				if (ou4 != 0) maxArriveTime = ou4;
			}
		}
		
		mark1=false;
		
		while(!mark1) {
		System.out.println("������һ�Ҵ�ֻж����ʱ�����Сֵ��Ĭ��Ϊ45����...");
		int ou5 = processInput(scanner, "�����������ٴ�����һ�Ҵ�ֻж����ʱ�����Сֵ��Ĭ��Ϊ45����");
		
		System.out.println("������һ�Ҵ�ֻж����ʱ������ֵ��Ĭ��Ϊ90����...");
		int ou6 = processInput(scanner, "�����������ٴ�����һ�Ҵ�ֻж����ʱ������ֵ��Ĭ��Ϊ90����");
		
		if(ou5 >ou6) {
			System.out.println("һ�Ҵ�ֻж����ʱ�����Сֵ���ܱ����ֵ��");
		}else {
			mark1=true;
			if(ou5!=0) minUnloadTime=ou5;
			if(ou6!=0) maxUnloadTime=ou6;
		}
		
		}
		
		System.out.println("����ɹ����ۿ��豸��Ϊ"+machineCount);
		if(size ==Integer.MAX_VALUE) System.out.println("�ۿڲ����Ƶȴ�ж���������������ɵĴ�ֻ�������");
		else System.out.println("�ۿڶԵȴ�ж���������������ɵĴ�ֻ�������Ϊ"+size);
		System.out.println("�������������ʱ������"+minArriveTime+"���ӵ�"+maxArriveTime+"����֮��仯");
		System.out.println("һ�Ҵ�ֻж����ʱ����"+minUnloadTime+"���ӵ�"+maxUnloadTime+"����֮��仯");

		while(true) {
			System.out.println("������ģ��Ĵ�������Ĭ��Ϊ100��...");
			int ou7 = processInput(scanner, "�����������ٴ�����˴�ģ��Ĵ�������Ĭ��Ϊ100��..");
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
			
			System.out.println("��"+(count-leftBoat+1)+"�Ҵ��ĵ�����Ϊ"+newBoat.arriveInterval+"����,����Ҫ��ж��ʱ��Ϊ"+newBoat.unloadTime+"����");
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
				System.out.println("��"+(i+1)+"�Ҵ�û���ڸøۿ�ж��");
			else {
				waitTotal+=boat.waitTime;
				waitMax = Math.max(waitMax, boat.waitTime);
				stayTotal+=boat.waitTime+boat.unloadTime;
				stayMax=Math.max(stayMax, boat.waitTime+boat.unloadTime);
				successCount++;
			}
		}
		System.out.println("һ�Ҵ����ڸۿڵ�ƽ��ʱ��Ϊ"+(stayTotal*1.0)/successCount+"����");
		System.out.println("һ�Ҵ����ڸۿڵ��ʱ��"+stayMax+"����");
		System.out.println("һ�Ҵ���ƽ���ȴ�ʱ��"+(waitTotal*1.0)/successCount+"����");
		System.out.println("һ�Ҵ�����ȴ�ʱ��"+waitMax+"����");
		
		for(int i=0;i<port.machines.size();i++) {
			Machine machine = port.machines.get(i);
			int freeInt = machine.freeCount+1;
			int workInt = machine.workCount;
			System.out.println("total free time is "+freeInt+" ; total work time is "+workInt); 
			double res= (freeInt*1.0)/(workInt+freeInt);
			System.out.println("��"+(i+1)+"��ж���豸�Ŀ���ʱ��İٷֱ�Ϊ"+res);
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
