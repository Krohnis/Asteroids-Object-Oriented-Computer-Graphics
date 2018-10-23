package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;

public class CommandQuit extends Command
{					
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructor ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Makes the type GameWorld within the Command the same as the actual GameWorld
	public CommandQuit()
	{
		super("Quit");
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Method Invoker ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Displays a dialog box to allow the user to confirm whether or not they wish to exit, if the value
//entered is (Y), it will exit the game, if the value entered is (N), the game will close the dialog and continue
	public void actionPerformed(ActionEvent e)
	{
		if (Dialog.show("Are you sure you want to quit?", "Select YES or CANCEL", "CANCEL", "YES"))
		{
			System.out.println("\n<CANCELING EXIT>\n");
		}
		else 
		{
			System.out.println("\n<CLOSING GAME>");
			Display.getInstance().exitApplication();
		}
	}
}