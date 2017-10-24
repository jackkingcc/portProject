package com.jack.port;

public class Boat {

	public int arriveInterval; //mins
	public int unloadTime;
	public int waitTime=0;
	private int finishTime;
	private int startTime;
	private int arriveTime;
	private int idelTime;//time that port is idle before unloading this boat  
	private int waitNumber; // the number of waiting boats when this boat arrives
	
	public int getWaitNumber()
	{
	    return waitNumber;
	}
	public void setWaitNumber(int waitNumber)
	{
	    this.waitNumber = waitNumber;
	}
	public int getFinishTime()
	{
	    return finishTime;
	}
	public void setFinishTime(int finishTime)
	{
	    this.finishTime = finishTime;
	}
	public int getStartTime()
	{
	    return startTime;
	}
	public void setStartTime(int startTime)
	{
	    this.startTime = startTime;
	}
	public int getArriveTime()
	{
	    return arriveTime;
	}
	public void setArriveTime(int arriveTime)
	{
	    this.arriveTime = arriveTime;
	}
	public int getIdelTime()
	{
	    return idelTime;
	}
	public void setIdelTime(int idelTime)
	{
	    this.idelTime = idelTime;
	}
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
