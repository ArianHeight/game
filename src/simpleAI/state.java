package simpleAI;

import java.util.List;
import java.util.ArrayList;

/*
 * state used in finit state machine
 */
public class state 
{
	private String stateName;
	private boolean pushState = false; //pushes both state and effects onto stack
	private List<String> preconditions;
	private List<String> effects;
	
	public state(String name)
	{
		this.stateName = name;
		
		//initializes lists
		this.preconditions = new ArrayList<String>();
		this.effects = new ArrayList<String>();
	}
	
	//all capital letters please, for simplicity's sake
	public void addPrecon(String condition)
	{
		this.preconditions.add(condition);
	}
	
	public void addFX(String condition)
	{
		this.effects.add(condition);
	}
	
	//assumes state has preconditions
	public void checkPrecon(List<String> activeConditions)
	{
		for (String condition : this.preconditions)
		{
			if (!activeConditions.contains(condition)) //active condition checks out against precon, push state to front
			{
				this.pushState = false;
				return; //early return
			}
		}
		this.pushState = true; //condition does not check out, state is no longer being pushed
	}
	
	public List<String> getFX()
	{
		return this.effects;
	}
	
	public boolean getPushState()
	{
		return this.pushState;
	}
	
	public String getName()
	{
		return this.stateName;
	}
}