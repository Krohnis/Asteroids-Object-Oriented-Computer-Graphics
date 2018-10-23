package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandRefuel extends Command
{
	private GameWorld gameWorld;
	public CommandRefuel( GameWorld gameWorld )
	{
		super("Refuel");
		this.gameWorld = gameWorld;
	}
	@Override
	public void actionPerformed( ActionEvent e ){
		gameWorld.refuelMissile();
		System.out.println("<MISSILE REFUELED>");
	}	
}
