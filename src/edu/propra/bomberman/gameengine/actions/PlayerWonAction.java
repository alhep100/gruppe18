package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Player;

public class PlayerWonAction extends ActionObject {

	public PlayerWonAction(Player actor) {
		this.actor=actor;
		this.time=System.currentTimeMillis();
	}
	@Override
	public void action() {
		System.out.println(((Player)actor).getName()+" has won the game");;
		SGameEngine.get().endGame();

	}

}
