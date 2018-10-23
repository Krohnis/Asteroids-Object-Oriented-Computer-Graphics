package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandPause extends Command 
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	private Game game;
	private GameWorld gameWorld;
	private boolean isPaused;
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CONSTRUCTOR ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Makes the type GameWorld within the Command the same as the actual GameWorld
	public CommandPause(Game game, GameWorld gameWorld){
		super( "Pause" );
		this.game = game;
		this.gameWorld = gameWorld;
		isPaused = false;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ METHOD INVOKER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Displays a dialog box to allow the user to confirm whether or not they wish to exit, if the value
//entered is (Y), it will exit the game, if the value entered is (N), the game will close the dialog and continue
//There is only one method to override the action performed
	@Override
	public void actionPerformed( ActionEvent e )
	{
		isPaused = !isPaused;
		if ( isPaused == true )
		{
			gameWorld.setPause();
			gameWorld.soundPause();
			game.pause();
		}
		else
		{
			gameWorld.setPause();
			gameWorld.soundPlay();
			game.resume();
		}
	}
}
