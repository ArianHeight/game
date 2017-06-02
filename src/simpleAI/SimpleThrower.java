package simpleAI;

import java.util.ArrayList;
import java.util.List;

/*
 * simple ai that chases target in straight line
 */
public class SimpleThrower extends FSM
{
	public SimpleThrower()
	{
		this.setBeginningState();
	}
	
	private void setBeginningState()
	{
		//creates states
		state idle = new state("IDLE");
		idle.addPrecon("PLAYER_OUT_RANGE");
		idle.addFX("NONE");
		
		state chase = new state("CHASE");
		chase.addPrecon("PLAYER_IN_RANGE");
		chase.addFX("STRAIGHT_LINE_MOVE");
		chase.addFX("THROW");
		
		state run = new state("RUN");
		run.addPrecon("PLAYER_TOO_CLOSE");
		run.addFX("BACK_UP");
		chase.addFX("THROW");
		
		state dodge = new state("DODGE");
		dodge.addPrecon("PROJECTILLE_IN_RANGE");
		dodge.addFX("DODGE");
		
		state stunned = new state("STUNNED");
		stunned.addPrecon("HIT");
		stunned.addFX("STUN_LOCK");
		
		state attacked = new state("ATTACKING");
		attacked.addPrecon("PLAYER_SWIPE_RANGE");
		attacked.addFX("THROW");
		
		state dead = new state("DEADED");
		dead.addPrecon("NO_HEALTH");
		dead.addFX("DELETE_ENTITY");
		dead.addFX("ADD_COIN");

		//adding states priority is higher the larger the index
		this.m_availibleStates.add(idle); //idle state 0
		this.m_availibleStates.add(chase); //chasing player around 1
		this.m_availibleStates.add(run); //running from player 2
		this.m_availibleStates.add(dodge); //dodging projectilles 3
		this.m_availibleStates.add(stunned); //stun state 4
		this.m_availibleStates.add(attacked); //5
		this.m_availibleStates.add(dead); //6
		
		this.m_currentState = this.m_availibleStates.get(0); //defaults to idle state
	}

}