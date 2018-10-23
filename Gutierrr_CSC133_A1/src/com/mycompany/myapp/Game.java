package com.mycompany.myapp;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import java.lang.String;

public class Game extends Form
{
	private GameWorld gameWorld;
	//Used when quitting out of the game, important for that reason and that reason alone
	private boolean followingFlag = false;
	//Initialization of gameWorld, calls play() to start things off
	public Game()
	{
		gameWorld = new GameWorld();
		gameWorld.initialize();
		play();
	}
	@SuppressWarnings("rawtypes")
	private void play()
	{
		Label myLabel = new Label("Enter a Command:");
		this.addComponent(myLabel);
		final TextField myTextField = new TextField();
		this.addComponent(myTextField);
		this.show();
		myTextField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				String stringCommand = myTextField.getText().toString();
				myTextField.clear();
				//These switch statements are used to determine what actions should be taken in gameWorld, everything from
				//spawning in additional asteroids, firing missiles, spawning the ship itself, as well as less fun things like
				//quitting the game and taxes
				//If (Q) is set, and (Y) is not used as the next input, followingFlag is set to false again, and you must
				//press (Q) before you are again allowed to decide if you want to quit or keep playing.
				switch (stringCommand.charAt(0))
				{
				//SPAWN ASTEROID
					case 'a':
						gameWorld.asteroid();
						followingFlag = false;
						break;
				//SPAWN SPACESTATION
					case 'b':
						gameWorld.spaceStation();
						followingFlag = false;
						break;
				//SPAWN SHIP
					case 's':
						gameWorld.ship();
						followingFlag = false;
						break;
				//INCREASE SPEED
					case 'i':
						gameWorld.speed(10);
						followingFlag = false;
						break;
				//DECREASE SPEED
					case 'd':
						gameWorld.speed(-10);
						followingFlag = false;
						break;
					case 'l':
				//TURN LEFT, 330 DEGREES
						gameWorld.direct(330);
						followingFlag = false;
						break;
				//TURN RIGHT, 30 DEGREES
					case 'r':
						gameWorld.direct(30);
						followingFlag = false;
						break;
				//FIRE MISSILE
					case 'f':
						gameWorld.missile();
						followingFlag = false;
						break;
				//BACK TO ORIGIN
					case 'j':
						gameWorld.origin();
						followingFlag = false;
						break;
				//RELOAD MISSILES
					case 'u':
						gameWorld.reload();
						followingFlag = false;
						break;
				//KILL
					case 'k':
						gameWorld.kill();
						followingFlag = false;
						break;
				//CRASH
					case 'c':
						gameWorld.crash();
						followingFlag = false;
						break;
				//COLLIDE
					case 'x':
						gameWorld.collide();
						followingFlag = false;
						break;
				//NEXT TURN
					case 't':
						gameWorld.gameClock();
						followingFlag = false;
						break;
				//PLAYER INFO
					case 'p':
						gameWorld.playerInfo();
						followingFlag = false;
						break;
				//PRINT MAP
					case 'm':
						gameWorld.map();
						followingFlag = false;
						break;
				//QUIT SESSION
					case 'q':
							followingFlag = true;
							System.out.println("Please enter yes (y), or no (n) to continue.");
						break;
				//CONFIRM QUIT
					case 'y':
						if (followingFlag == true)
						{
							System.out.println("You have quit the game.");
							System.exit(0);
						}
						else
						{
							System.out.println("You must submit a request to end the game, before accepting it!");
						}
						break;
				//CANCEL QUIT
					case 'n':
						if (followingFlag == true)
						{
							followingFlag = false;
						}
						else
						{
							System.out.println("You must submit a request to end the game, before rejecting it!");
						}
						break;
					case '~':
						gameWorld.initialize();
					default:
						break;
				}
			}
		}
		);
	}
}
