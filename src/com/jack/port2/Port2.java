package com.jack.port2;

import java.util.ArrayList;
import java.util.List;

import com.jack.port.Boat;
import com.jack.port.Units;

public class Port2
{

    public static void main(String[] args)
    {
	// TODO Auto-generated method stub

    }
    
    /**
     * 
     * @param n total number of boats
     * @param m limit number of boat waiting in the same time
     */
    public void process(int n, int m)
    {

	List<Boat> boats = new ArrayList<>();
	
	Boat boat = new Boat();
	boat.setArriveInterval(Units.randomEquidistribution(15, 145));
	boat.setUnloadTime(Units.randomEquidistribution(45, 90));
	boat.setArriveTime(boat.getArriveInterval());
	boat.setIdelTime(boat.getArriveInterval());
	boat.setStartTime(boat.getArriveInterval());
	boat.setFinishTime(boat.getStartTime()+boat.getUnloadTime());
	boat.setWaitNumber(0);
	boat.setSucess(true);
	boats.add(boat);
	
	for (int i = 1; i < n; i++)
	{
	    Boat newBoat = new Boat();
	    newBoat.setArriveInterval(Units.randomEquidistribution(15, 145));
	    newBoat.setUnloadTime(Units.randomEquidistribution(45, 90));
	    
	    Boat previousReaBoat=returnPrivBoat(boats, i,1);

	    
	    int waitNumber =0;
	    for(int j=0;j<=previousReaBoat.getWaitNumber();j++){
		if(returnPrivBoat(boats, i,j+1).getFinishTime() <=newBoat.getArriveTime()){
		    waitNumber=j;
		    break;
		}
		waitNumber=j;
	    }
	    newBoat.setWaitNumber(waitNumber);
	    
	    if(waitNumber>m){
		continue;
	    }
	}
    }
    
    private Boat returnPrivBoat(List<Boat> boats, int t, int m)
    {
	 Boat boat = null;
	for (int i = 0; i < m; i++)
	{
	    do
	    {
		boat = boats.get(--t);
	    }
	    while (!boat.isSucess());
	}
	return boat;
    }

}
