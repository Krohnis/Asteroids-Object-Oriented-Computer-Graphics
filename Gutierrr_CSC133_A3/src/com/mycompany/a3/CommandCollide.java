package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandCollide extends Command
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Fields ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Global class fields for the Command class, creates type GameWorld that will be assigned to the
//existing gameWorld that is passed into it from Game
	private GameWorld gameWorld;
				
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructor ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Makes the type GameWorld within the Command the same as the actual GameWorld
	public CommandCollide(GameWorld gameWorld)
	{
		super("Exterminate");
		this.gameWorld = gameWorld;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Method Invoker ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Is called when the associated button is pressed, and calls the related method inside of gameWorld
//to execute its function
	public void actionPerformed(ActionEvent e)
	{
		gameWorld.twoAsteriodsCollided();
	}
}