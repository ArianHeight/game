package simpleAI;


/*
 * simple ai that chases target in straight line
 */
public class HeavyChaser extends FSM
{
	public HeavyChaser()
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
		
		state attacked = new state("ATTACKING");
		attacked.addPrecon("PLAYER_SWIPE_RANGE");
		attacked.addFX("FLAG_DMG_PLAYER");
		
		state attackedO = new state("STILL_ATTACKING");
		attackedO.addPrecon("PLAYER_ATKTIMER_ACTIVE");
		attackedO.addFX("FLAG_DMG_PLAYER");
		
		state stunned = new state("STUNNED");
		stunned.addPrecon("HIT");
		stunned.addFX("STUN_LOCK");
		
		state specialAtk = new state("SPECIAL_ATTACK");
		specialAtk.addPrecon("PLAYER_SUCK_RANGE");
		specialAtk.addPrecon("NO_VOID_ACTIVE");
		specialAtk.addFX("FLAG_SUCK_ATTACK");
		
		state specialAtkO = new state("SPECIAL_ATTACKING");
		specialAtkO.addPrecon("SPECIAL_ACTIVATED");
		specialAtkO.addFX("FLAG_SUCK_ATTACK");
		
		state dead = new state("DEADED");
		dead.addPrecon("NO_HEALTH");
		dead.addFX("DELETE_ENTITY");
		dead.addFX("ADD_COIN");

		//adding states priority is higher the larger the index
		this.m_availibleStates.add(idle); //idle state 0
		this.m_availibleStates.add(chase); //chasing player around 1
		this.m_availibleStates.add(attacked); //2
		this.m_availibleStates.add(attackedO); //3
		this.m_availibleStates.add(stunned); //4
		this.m_availibleStates.add(specialAtk); //5
		this.m_availibleStates.add(specialAtkO); //6
		this.m_availibleStates.add(dead); //7
		
		this.m_currentState = this.m_availibleStates.get(0); //defaults to idle state
	}

}