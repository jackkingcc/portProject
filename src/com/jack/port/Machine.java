package com.jack.port;

public class Machine {

	private boolean isWorking= false;
	private Boat workingBoat = null;
	private int workingTime=0;
	
	public int workCount=0;
	public int freeCount=0;
	
	public boolean workOneMin() {
		if(!isWorking || workingBoat==null) {
			freeCount++;
			setDefault();
			return isWorking;
		}
		workingTime ++;
		workCount++;
		if(workingTime >= workingBoat.getUnloadTime()) {
			setDefault();
		}
		return isWorking;
	}
	
	private void setDefault() {
		workingTime=0;
		workingBoat=null;
		isWorking=false;
	}
	
	public boolean isWorking() {
		return isWorking;
	}
	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}
	public Boat getWorkingBoat() {
		return workingBoat;
	}
	public void setWorkingBoat(Boat workingBoat) {
		this.workingBoat = workingBoat;
		isWorking=true;
		workingTime=0;
	}
	
	
}
