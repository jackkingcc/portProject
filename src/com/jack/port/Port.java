package com.jack.port;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class Port {

	private int size;
	private int machineCount;
	public List<Boat> boats;
	public List<Machine> machines;


	private final static Logger logger = Logger.getLogger(Units.class); 

	public Port(int machineCount, int size) {
		this.size = size;
		this.machineCount = machineCount;

		boats = new ArrayList<Boat>();
		machines = new ArrayList<Machine>(machineCount);
		for (int i = 0; i < machineCount; i++) {
			machines.add(new Machine());
		}
	}

	//²åÈë¶ÓÎ²
	public boolean enchase(Boat boat) {
		if (boats.size() == size) {
			logger.info("Port are full, this boat will not be handled");
			return false;
		}

		boats.add(boat);
		return true;

	}
	

	public void workOneMin() {
		for (int i = 0; i < machineCount; i++) {
			Machine curMachine = machines.get(i) ;
			if (!boats.isEmpty() && !curMachine.isWorking()) {
				curMachine.setWorkingBoat(boats.get(0));
				boats.remove(0);
				logger.info("One boat starting to unload...");
			}else if(i==machineCount-1 && !boats.isEmpty() && curMachine.isWorking()){  //add wait time when it can't be put into last  machine
			    for(Boat boat:boats){
				boat.setWaitTime(boat.getWaitTime()+1);
			    }
			}
			logger.info("Start to work on Machine " + i+", its working status is "+curMachine.isWorking()+". Current there are "+boats.size()+ " waiting boats");
			curMachine.workOneMin();
		}

	}
	
	public boolean isWork(){
	    boolean res=false;
	    for(Machine machine:machines){
		if(machine.isWorking()) return true;
	    }
	    if(!boats.isEmpty()) res=true;
	    return res;
	}

}
