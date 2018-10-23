package com.mycompany.myapp;

import java.util.Vector;

public class GameWorld
{
	private int lives;
	private int time;
	private int score;
	final static private int MAP_X = 1024;
	final static private int MAP_Y = 768;
	private Vector<GameObject> vectorArray = new Vector<GameObject>(1, 1);
//Initializes the map, fairly unimportant for the first iteration of the game, but necessary. lives, time, and score were linked
//so that calling gameWorld.init() again with ~ would reset the world.
	public void initialize()
	{
		lives = 3;
		time = 0;
		score = 0;
		vectorArray.clear();
	}
//Game Clock of the program, has some redundancy as it iterates through the loop to take each action and display the outcome
//a function which is also handled by map, but I couldn't find a way to merge the two, in the future I may revise this section of
//the program
	public void gameClock()
	{
		//Used to break up each call of time, there is one in map for the same reason
		time += 1;
		System.out.println("NEXT TURN CYCLE, TURN NUMBER: " + time + "!");
		for (int i = 0; i < vectorArray.size(); i++)
		{
			if (vectorArray.elementAt(i) instanceof Ship)
			{
				//Handles movement, calling the ship.move() method to change the location (X/Y) of the ship, this exists
				//for all of the other objects in the program, from missiles to the spacestation
				Ship ship = (Ship) vectorArray.elementAt(i);
				ship.move();
				System.out.println("SHIP:");
				System.out.println(ship.toString());
			}
			else if (vectorArray.elementAt(i) instanceof SpaceStation)
			{
				SpaceStation spaceStation = (SpaceStation) vectorArray.elementAt(i);
				System.out.println("SPACE STATION:");
				System.out.println(spaceStation.toString());
				spaceStation.setBlinking(time);
			}
			else if (vectorArray.elementAt(i) instanceof Missile)
			{
				//Missiles have an added function of checking for whether or not the fuel capacity has hit 0
				//If the capacity has not, it will continue to display its movement as it did before, however
				//once missile's fuel reaches 0, it will instead print that it has been destroyed and be removed from the array
				Missile missile = (Missile) vectorArray.elementAt(i);
				if (missile.getFuel() == false)
				{
					System.out.println("The missile has exhausted its fuel and exploded.");
					vectorArray.removeElementAt(i);
					//I am not certain this is necessary, but in cases were multiple missiles are launched at the same time
					//and in succession it would potentially skip over the next missile, allowing it to go further, the i--
					//sets it back to the current index to check it for its new occupant, before moving on
					i--;
				}
				else
				{
					//Basic stuff
					missile.move();
					System.out.println("MISSILE:");
					System.out.println(missile.toString());
				}
			}
			else if (vectorArray.elementAt(i) instanceof Asteroid)
			{
				//Simplest of all of the methods, simply updates the position of the asteroid in GameWorld
				Asteroid asteroid = (Asteroid) vectorArray.elementAt(i);
				System.out.println("ASTEROID:");
				System.out.println(asteroid.toString());
				asteroid.move();
			}
		}
		//Increments time in the game
	}
	public void map()
	{
		//To break messages up for each unique call
		//Many of the comments about gameClock extend to map, and they are almost exactly the same and much
		//simpler than gameClock because of the fact that it only needs to display the current status of the gameWorld
		System.out.println("NEW MAP DISPLAY!");
		for (int i = 0; i < vectorArray.size(); i++)
		{
			if (vectorArray.elementAt(i) instanceof Ship)
			{
				Ship ship = (Ship) vectorArray.elementAt(i);
				System.out.println("SHIP:");
				System.out.println(ship.toString());
			}
			else if (vectorArray.elementAt(i) instanceof SpaceStation)
			{
				SpaceStation spaceStation = (SpaceStation) vectorArray.elementAt(i);
				System.out.println("SPACE STATION:");
				System.out.println(spaceStation.toString());
				spaceStation.setBlinking(time);
			}
			else if (vectorArray.elementAt(i) instanceof Missile)
			{
				Missile missile = (Missile) vectorArray.elementAt(i);
				System.out.println("MISSILE:");
				System.out.println(missile.toString());
			}
			else if (vectorArray.elementAt(i) instanceof Asteroid)
			{
				Asteroid asteroid = (Asteroid) vectorArray.elementAt(i);
				System.out.println("ASTEROID:");
				System.out.println(asteroid.toString());
			}
		}
	}
	//Displays playerInfo time, score, and missiles available to the player
	public void playerInfo()
	{
		System.out.print("Time: " + time + "; Score: " + score + "; Missiles: ");
		//The if checks to see if ship exists, and if it does retrieves the number of missiles remaining from ship and displays them
		if (shipFinder() != -1)
		{
			Ship ship = (Ship) vectorArray.elementAt(shipFinder());
			System.out.println(ship.getMissile());
		}
		//If ship does not exist, it instead displays unknown in place of an integer
		else
		{
			System.out.println("Unknown");
		}
	}
	public void ship()
	{
		//Integer set equal to shipFinder, if shipFinder returns -1, then there is no ship in existence and therefore
		//it is fine to create another ship object in gameWorld. If it returns anything else, there is another ship in the
		//gameWorld, and the creation will be denied
		int shipPosition = shipFinder();
		if (shipPosition == -1)
		{
			GameObject ship = new Ship();
			vectorArray.addElement(ship);
			System.out.println("A ship has been created.");
		}
		//Display if an attempt to create a ship while one already existed was made
		else
		{
			System.out.println("A ship already exists in the world.");
		}
	}
	public void spaceStation()
	{
		//Exists for the same reason as the if in ship, it determines that there is only one instance of spaceStation before alllowing
		//the creation of the object
		int stationPosition = stationFinder();
		if (stationPosition == -1)
		{
			GameObject spaceStation = new SpaceStation();
			for (int i = 0; i < vectorArray.size(); i++)
			{
				if (vectorArray.elementAt(i) instanceof FixedObject)
				{
					//As opposed to the other objects, spaceStation isn't immediately added to vectorArray, and is instead looked at
					//to see if FixedObject generated a unique key for it, if not, it generates a new key, and resets the loop to check if
					//the new key is unique
					//I am not certain if this works
					FixedObject fixedObject = (FixedObject) vectorArray.elementAt(i);
					if (((FixedObject) spaceStation).getID() == fixedObject.getID())
					{
						((FixedObject) spaceStation).setID();
						i = 0;
					}
				}
			}
			//Once it finishes the hell loop, it prints that it has been created just as any other object, and finally adds it to the
			//vectorArray for later use
			vectorArray.addElement(spaceStation);
			System.out.println("A space station has been constructed.");
		}
		else
		{
			System.out.println("There is already an existing space station.");
		}
	}
	public void asteroid()
	{
		//Simplest of the objects, create the asteroid and let the Asteroid class handle the rest
		GameObject asteroid = new Asteroid();
		vectorArray.addElement(asteroid);
		System.out.println("An asteroid has been created.");
	}
	public void missile()
	{
		//Makes sure that ship exists before allowing the player to fire a missile, as it will rely on the ship for its initial 
		//position and direction
		if (shipFinder() != -1)
		{
			int shipPosition = shipFinder();
			Ship ship = (Ship) vectorArray.elementAt(shipPosition);
			//The method checks ship before proceeding, to make sure that ship does have additional missiles to fire before creating a new
			//missile object in the gameWorld
			if (ship.missileCount() > 0)
			{
				//Gets the location and direction of the ship and assigns them to the missile object
				GameObject missile = new Missile(ship.getLocalX(), ship.getLocalY(), ship.getDirection());
				vectorArray.addElement(missile);
				shipPosition = vectorArray.indexOf(ship);
				System.out.println("A missile has been fired.");
			}
			//Error display if the ship does not have at least one missile
			else
			{
				System.out.println("You require additional missiles");
			}
		}
		//Error display if the ship does not exist
		else
		{
			System.out.println("There is no ship to fire missiles from.");
		}
	}
	public void collide()
	{
		//Creates three integers, two of which will be used to remember the asteroid indexes, and the other to count the number
		//of asteroids necessary for collision to occur
		int asteroidOne = -1, asteroidTwo = -1;
		//Iterates through the vectorArray as expected
		for (int i = 0; i < vectorArray.size(); i++) 
		{
			//If an asteroid is found, and two haven't been spotted yet, it enters the first if statement
			if ((vectorArray.elementAt(i) instanceof Asteroid)) 
			{
				//It checks to see if asteroidOne has received a spot yet, and if it hasn't, assigns it the index of the first asteroid
				if (asteroidOne == -1)
				{
					asteroidOne = i;
				}
				//If asteroidOne has received a spot, it defaults to asteroidTwo and will never enter the if again as the count will
				//exceed two asteroids
				else
				{
					asteroidTwo = i;
					break;
				}
			}
		}
		//After finishing the for loop, it checks to see if two asteroids have been found, and if they have, removes both
		//and displays a message to the player
		if ((asteroidOne != -1) && (asteroidTwo != -1))
		{
			vectorArray.removeElementAt(asteroidOne);
			//The minus one is necessary, as removing the first object will shift all items in the array to the left (down)
			vectorArray.removeElementAt(asteroidTwo - 1);
			System.out.println("Two asteroids have collided and have been destroyed.");
		}
		//Error if there are not at least two asteroids present
		else
		{
			System.out.println("You must have two asteroids to cause a collision.");
		}
	}
	public void crash()
	{
		//Checks to see if a ship and asteroid exist, before continuing
		int asteroidPosition = asteroidFinder();
		int shipPosition = shipFinder();
		if ((shipPosition != -1) && (asteroidPosition != -1))
		{
			//After it has verified if an asteroid and ship exist, it checks to see the position of the objects in vectorArray
			//the reason being that if the asteroid was created as index 0, and ship at index 1, upon the deletion of the ship 
			//the asteroid would still be at index 0, and the element at -1 would be removed
			//It doesn't exist, and therefore will throw an error
			if (shipPosition < asteroidPosition)
			{
				vectorArray.removeElementAt(shipPosition);
				vectorArray.removeElementAt(asteroidPosition - 1);
			}
			//Exists for the reasons stated above
			else
			{
				vectorArray.removeElementAt(asteroidPosition);
				vectorArray.removeElementAt(shipPosition - 1);
			}
			//After clearing the if statements, it displays that the asteroids have been destroyed and decrements lives (not important)
			System.out.println("The ship has crashed into an asteroid, and been destroyed.");
			lives--;
			if (lives == 0)
			{
				System.out.println("Game Over!");
				initialize();
			}
		}
		//Error messages
		else
		{
			System.out.println("There must be a ship, and asteroid for collisions to occur.");
		}
	}
	public void kill()
	{
		//Finds the first asteroid and missile in vectorArray to destroy
		int asteroidPosition = asteroidFinder();
		int missilePosition = missileFinder();
		if ((missilePosition != -1) && (asteroidPosition != -1))
		{
			//This is all very similar to the crash method, with the only change being the objects that are being deleted
			if (missilePosition < asteroidPosition)
			{
				vectorArray.removeElementAt(missilePosition);
				vectorArray.removeElementAt(asteroidPosition - 1);
			}
			else
			{
				vectorArray.removeElementAt(asteroidPosition);
				vectorArray.removeElementAt(missilePosition - 1);
			}
			//Displays that a missile and asteroid have been destroyed, increases the player score by 10
			System.out.println("A missile has destroyed an asteroid.");
			score += 10;
		}
		//ErRor MeSSaGe
		else
		{
			System.out.println("There must be a missile, and asteroid for collisions to occur.");
		}
	}
	//Handles the reloading of the ship's missiles, it is called with (U) instead of (N), as (N) was used for canceling
	//out of the quit case in Game()
	public void reload()
	{
		//Makes sure that both a spaceStation and ship exist in the world before allowing the ship to restock on missiles
		if ((shipFinder() != -1) && (stationFinder() != -1))
		{
			Ship ship = (Ship) vectorArray.elementAt(shipFinder());
			ship.missileReload();
			System.out.println("Missiles have been loaded into your ship.");
		}
		//Err
		else
		{
			System.out.println("Missiles cannot be reloaded without a ship, and space station.");
		}
	}
	//Returns the ship to the origin, I am not sure if it was required to reset every aspect of the ship, but it has been returned
	//to its original position on gameWorld
	public void origin()
	{
		//Makes sure that ship does indeed exist in the gameWorld
		int shipPosition = shipFinder();
		if (shipPosition != -1)
		{
			GameObject ship = vectorArray.elementAt(shipPosition);
			ship.setLocalX(512);
			ship.setLocalY(384);
			System.out.println("The ship has returned to the origin");
		}
		else
		{
			System.out.println("There is no ship!");
		}
	//Direct functions as the turn method, taking in an integer 330 for left turns, or 30 for right turns, before sending them to
	//the setDirection method
	}
	public void direct(int direct)
	{
		int shipPosition = shipFinder();
		if (shipPosition != -1)
		{
			System.out.println("The ship's direction has changed.");
			Ship ship = (Ship) vectorArray.elementAt(shipPosition);
			ship.setDirection(direct);
		}
		//If no ship exists, then no action is taken and an error message is displayed to the user
		else
		{
			System.out.println("Direction cannot be changed without a ship.");
		}
	}
	//The speed method takes in integers 10 and -10, to increase and decrease the speed of the ship. After confirming that ship does exist
	//they are sent to the setSpeed method
	public void speed(int speed)
	{
		int shipPosition = shipFinder();
		if (shipPosition != -1)
		{
			System.out.println("The Ship's speed has changed by " + speed + ".");
			Ship ship = (Ship) vectorArray.elementAt(shipPosition);
			ship.setSpeed(speed);
		}
		else
		{
			System.out.println("Speed cannot be changed without a ship.");
		}
	}
	//shipFinder, method used for searching through the vectorArray and find the instance of Ship, and
	//returns the integer value for use in other methods within GameWorld
	//It returns -1 if it cannot find an instance of Ship, which can be interpreted as it being false
		public int shipFinder()
		{
			for (int i = 0; i < vectorArray.size(); i++)
			{
				if (vectorArray.elementAt(i) instanceof Ship)
				{
					return i;
				}
			}
			return -1;
		}
	//stationFinder exists for the same reasons as shipFinder, and functions in exactly the same way
	//It might have been possible to merge all of these methods, but I could not think of one at the time
		public int stationFinder()
		{
			for (int i = 0; i < vectorArray.size(); i++)
			{
				if (vectorArray.elementAt(i) instanceof SpaceStation)
				{
					return i;
				}
			}
			return -1;
		}
	//asteroidFinder, read above
		public int asteroidFinder()
		{
			for (int i = 0; i < vectorArray.size(); i++)
			{
				if (vectorArray.elementAt(i) instanceof Asteroid)
				{
					return i;
				}
			}
			return -1;
		}
	//missileFinder, read above
		public int missileFinder()
		{
			for (int i = 0; i < vectorArray.size(); i++)
			{
				if (vectorArray.elementAt(i) instanceof Missile)
				{
					return i;
				}
			}
			return -1;
		}
}