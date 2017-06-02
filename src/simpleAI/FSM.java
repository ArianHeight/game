package simpleAI;

import java.util.ArrayList;
import java.util.List;

/*
 * finite state machine used for ai
 */
public abstract class FSM 
{
	protected List<state> m_availibleStates;
	protected state m_currentState; //current state
	protected List<String> m_activeEffects;
	
	public FSM(List<state> in)
	{
		//cstr
		this.m_availibleStates = in;
	}
	
	public FSM()
	{
		//default cstr
		this.m_availibleStates = new ArrayList<state>();
		this.m_activeEffects = new ArrayList<String>();
	}
	
	//returns index of added state, intended for classes other than ones ineriting from this
	public int addState(state in)
	{
		this.m_availibleStates.add(in);
		return this.m_availibleStates.lastIndexOf(in);
	}
	
	//returns whether or not removal was successful
	public boolean removeState(state in)
	{
		return this.m_availibleStates.remove(in);
	}
	
	//returns current state
	public state getState()
	{
		return this.m_currentState;
	}
	
	public List<String> getFX()
	{
		return this.m_activeEffects;
	}
	
	//automatic difference, state changing method
	public void runStateUpdate(List<String> activeConditions)
	{
		for (state s : this.m_availibleStates)
		{
			s.checkPrecon(activeConditions);
		}
		
		//checks according to priority
		this.m_activeEffects.clear(); //clears fx buffer
		for (int i = this.m_availibleStates.size() - 1; i >= 0; i--)
		{
			// if higher priority state can be pushed, push it
			if (this.m_availibleStates.get(i).getPushState())
			{
				this.m_currentState = this.m_availibleStates.get(i); //sets current state
				this.m_activeEffects.addAll(this.m_availibleStates.get(i).getFX()); //fills fx buffer
				
				return; //early return
			}
		}
	}
}