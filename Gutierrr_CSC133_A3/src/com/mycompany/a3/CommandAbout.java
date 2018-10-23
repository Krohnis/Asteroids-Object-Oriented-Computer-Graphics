package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class CommandAbout extends Command
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Fields ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Global class fields for the Command class, creates type GameWorld that will be assigned to the
//existing gameWorld that is passed into it from Game
					
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructor ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Makes the type GameWorld within the Command the same as the actual GameWorld
	public CommandAbout()
	{
		super("About");
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Method Invoker ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Is called when the associated button is pressed, and calls the related method inside of gameWorld
//to execute its function
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("\n<README>\n");
		Dialog.show("README", "Ricky Gutierrez \nCSc133 \nAssignment#3 \n\n-(U) still handles reloading\n-(.) handles pausing"
				+ "\n-The only change to the program is for muting, as the current mute in submission number 1 calls method soundPause() for both\r\n" + 
				"of the cases, pausing and unpausing, instead of soundPause() and soundPlay(). That is the only major change."
				+ "-Change fixed an issue where muting sound once, muted sound forever, only applied to original's mute, not to its pause, that worked\r\n" + 
				"fine\r\n" + 
				"-Sorry to ask, but could you please go by the November 24th date. Sorry again.", "Ok", "Close");
	}
}