package com.jack.port;

public class Boat {

	public int arriveInterval; //mins
	public int unloadTime;
	public int waitTime=0;
	private boolean sucess=true;

	
	public boolean isSucess() {
		return sucess;
	}
	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}
	public int getArriveInterval() {
		return arriveInterval;
	}
	public void setArriveInterval(int arriveInterval) {
		this.arriveInterval = arriveInterval;
	}
	public int getUnloadTime() {
		return unloadTime;
	}
	public void setUnloadTime(int unloadTime) {
		this.unloadTime = unloadTime;
	}
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
}
