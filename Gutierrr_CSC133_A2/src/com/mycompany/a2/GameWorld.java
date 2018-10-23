package com.mycompany.a2;
import java.util.Observable;
import java.util.Vector;

public class GameWorld extends Observable implements IGameWorld
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GameWorld Fields ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Creates the global variables for sound, lives, time, and score, all of which
//should be visible to the player during runtime
	private int lives;
	private int time;
	private int score;
	private int asteroidNumber;
	private boolean soundOn = false;
//Sets the width and height of the map to 1024 by 768
	final static private int MAP_X = 1024;
	final static private int MAP_Y = 768;
//
	private GameCollection gameCollection = new GameCollection();
	private IIterator gameIterator;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Initialization ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public void initialize()
	{
		lives = 3;
		time = 0;
		score = 0;
		gameCollection.clear();
		update();
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Sound ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public void setSound() 
	{
		this.soundOn = !(soundOn);
		System.out.println("<SOUND options have been changed>");
		update();
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GameClock ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Game Clock of the program, has some redundancy as it iterates through the loop to take each action 
//and display the outcome a function which is also handled by map, but I couldn't find a way to merge the two, 
//in the future I may revise this section of the program
	public void gameClock()
	{
		time += 1;
		gameIterator = gameCollection.getIterator();
		while (gameIterator.hasNext() != false)
		{
			GameObject gameObject = (GameObject)gameIterator.getNext();
			if (gameObject instanceof Ship)
			{
				((Ship) gameObject).move();
			}
			else if (gameObject instanceof SpaceStation)
			{
				//Checks to see if the SpaceStation is still blinking at that point in time, and changes its
				//state if it is meant to swap
				((SpaceStation)gameObject).setBlinking(time);
			}
			else if (gameObject instanceof Missile)
			{
				//Missiles have an added function of checking for whether or not the fuel capacity has hit 0
				//If the capacity has not, it will continue to display its movement as it did before, however
				//once missile's fuel reaches 0, it will instead print that it has been destroyed and be removed
				if (((Missile) gameObject).fuelLevels() != false)
				{
					((Missile) gameObject).move();
				}
			//I am not certain this is necessary, but in cases were multiple missiles are launched at the same time
			//and in succession it would potentially skip over the next missile, allowing it to go further, the stepBack()
			//sets it back to the current index to check it for its new occupant, before moving on
				else
				{
					gameCollection.remove(gameObject);
					System.out.println("<MISSILE has exhausted its fuel>");
					gameIterator.stepBack();
				}
			}
			else if (gameObject instanceof Asteroid)
			{
				((Asteroid) gameObject).move();
			}
		}
		update();
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Ship ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles Ship creation and its addition into the gameCollection
	public void ship()
	{
		//ShipFinder() is called and iterates through the gameCollection, returning any
		//Object instances of class Ship
		//If it cannot find any instances of type Ship, it instead returns a null Object
		//indicating that there is no existing GameObject of that type
		if (shipFinder() == null)
		{
			GameObject ship = new Ship();
			gameCollection.add(ship);
			System.out.println("<SHIP has been created>");
		}
		//Display if an attempt to create a ship while one already existed was made
		else
		{
			System.out.println("<SHIP already exists>");
		}
		update();
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Space Station ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Exists for the same reason as the if in ship, it determines that there is only one instance of spaceStation before alllowing
//the creation of the object
	public void spaceStation()
	{
		if (stationFinder() == null)
		{
			GameObject spaceStation = new SpaceStation();
			gameIterator = gameCollection.getIterator();
			while (gameIterator.hasNext() != false)
			{
				GameObject gameObject = (GameObject)gameIterator.getNext();
				if (gameObject instanceof SpaceStation)
				{
					if (((SpaceStation)spaceStation).getID() == ((SpaceStation) gameObject).getID())
					{
						//As opposed to the other objects, spaceStation isn't immediately added to vectorArray, and is instead looked at
						//to see if FixedObject generated a unique key for it, if not, it generates a new key, and resets the loop to check if
						//the new key is unique
						((FixedObject)spaceStation).setID();
						gameIterator = gameCollection.getIterator();
					}
				}
			}
			//Once it finishes the hell loop, it prints that it has been created just as any other object
			//and finally adds it to the gameCollection for later use
			gameCollection.add(spaceStation);
			System.out.println("<SPACE STATION has been created>");
		}
		else
		{
			System.out.println("<SPACE STATION already exists>");
		}
		update();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ASTEROID ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public void asteroid()
	{
		//Simplest of the objects, create the asteroid and let the Asteroid class handle the rest
		GameObject asteroid = new Asteroid();
		gameCollection.add(asteroid);
		System.out.println("<ASTEROID has been created>");
		update();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MISSILE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles the creation of Missiles in GameWorld, uses the information stored inside of Ship
//to determine direction, and starting location
	public void missile()
	{
	//Makes sure that ship exists before allowing the player to fire a missile,
	//as it will rely on the ship for its initial position and direction
		if (shipFinder() != null)
		{
			Ship ship = (Ship)shipFinder();
			if (ship.missileCount() > 0)
			{
			//Gets the location and direction of the ship and assigns them to the missile object
				GameObject missile = new Missile(ship.getLocalX(), ship.getLocalY(), ship.getDirection());
				gameCollection.add(missile);
				System.out.println("<A MISSILE has been fired from the SHIP>");
			}
		//Error display if the ship does not have at least one missile
			else
			{
				System.out.println("<SHIP does not have any MISSILES ready>");
			}
		}
	//Error display if the ship does not exist
		else
		{
			System.out.println("<SHIP is not present, no MISSILES can be fired>");
		}
		update();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Collide ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles the extermination of two Asteroids, when they have collided into each other
	public void collide()
	{
	//Looks at asteroidNumber to see if two or more Asteroids exist in gameCollection before allowing
	//deletion of any Objects
		if (asteroidNumber() >= 2)
		{
			gameCollection.remove(asteroidFinder());
			gameCollection.remove(asteroidFinder());
			System.out.println("<ASTEROIDS have collided and been destroyed>");
		}
	//Errors for when the criteria for collision is not met
		else if (asteroidNumber() == 1)
		{
			System.out.println("<ASTEROID only has ONE instance>");
		}
		else
		{
			System.out.println("<ASTEROIDS must be present to be destroyed>");
		}
		update();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Crash ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles the case of the destruction of the Ship and Asteroid in a crash
	public void crash()
	{
	//Confirms that an instance of Ship and an Asteroid exist before removing both Objects
	//If the finder returns a null Object, that means that no Object of that type exists
		if (shipFinder() != null && asteroidFinder() != null)
		{
			gameCollection.remove(shipFinder());
			gameCollection.remove(asteroidFinder());
			lives -= 1;
			System.out.println("<SHIP AND ASTEROID have crashed and been destroyed>");
		}
	//Error Messages
		else if (asteroidFinder() == null)
		{
			System.out.println("<ASTEROID is not present, and cannot be destroyed>");
		}
		else if (shipFinder() == null)
		{
			System.out.println("<SHIP is not present, and cannot be destroyed>");
		}
		else
		{
			System.out.println("<SHIP and ASTEROID are not present, and cannot be destroyed>");
		}
		if (lives <= 0)
		{
			System.out.println("\n<GAME OVER>\n");
			initialize();
		}
		update();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Kill ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles the removal of Asteroids and Missiles when the kill command is invoked
	public void kill()
	{
	//Finds the first asteroid and missile in vectorArray to destroy
		if (asteroidFinder() != null && missileFinder() != null)
		{
			gameCollection.remove(asteroidFinder());
			gameCollection.remove(missileFinder());
			this.score += 10;
			System.out.println("<MISSILE has struck an ASTEROID, both have been destroyed>");
		}
	//Error Messages
		else if (asteroidFinder() == null)
		{
			System.out.println("<ASTEROID is not present, and cannot be destroyed>");
		}
		else if (missileFinder() == null)
		{
			System.out.println("<MISSILE is not present, and cannot be destroyed>");
		}
		else
		{
			System.out.println("<ASTEROID and MISSILE are not present, and cannot be destroyed>");
		}
		update();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Reload ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles the reloading of the ship's missiles, it is called with (U) instead of (N), as 
//(N) was used for canceling out of the quit case in Game()
	public void reload()
	{
	//Makes sure that both a spaceStation and ship exist in the world before allowing the ship to restock on missiles
		if ((shipFinder() != null) && (stationFinder() != null))
		{
			Ship ship = (Ship) shipFinder();
			ship.missileReload();
			System.out.println("<SHIP has replenished its MISSILES>");
		}
		//Error
		else if (shipFinder() == null)
		{
			System.out.println("<MISSLES cannot be replenished without SHIP present>");
		}
		else if (stationFinder() == null)
		{
			System.out.println("<MISSLES cannot be replenished without STATION present>");
		}
		else
		{
			System.out.println("<MISSLES cannot be replenished without SHIP and STATION present>");
		}
		update();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Origin ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Returns the ship to the origin, I am not sure if it was required to reset every aspect of 
//the ship, but it has been returned to its original position on gameWorld
	public void origin()
	{
		//Makes sure that ship does indeed exist in the gameWorld
		if (shipFinder() != null)
		{
			Ship ship = (Ship) shipFinder();
			ship.setLocalX(512);
			ship.setLocalY(384);
			System.out.println("<SHIP has been placed at Origin>");
		}
		else
		{
			System.out.println("<SHIP is not present, coordinates cannot be changed>");
		}
		update();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Ship Direction ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Direct functions as the turn method, taking in an integer 330 for left turns
//or 30 for right turns, before sending them to the setDirection method
	public void direct(int direct)
	{
		if (shipFinder() != null)
		{
			System.out.println("<SHIP speed has changed by " + direct + ">");
			Ship ship = (Ship) shipFinder();
			ship.setDirection(direct);
		}
		//If no ship exists, then no action is taken and an error message is displayed to the user
		else
		{
			System.out.println("<SHIP is not present, direction cannot be changed>");
		}
		update();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Ship Speed ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//The speed method takes in integers 10 and -10, to increase and decrease the speed 
//of the ship. After confirming that ship does exist they are sent to the setSpeed method
	public void speed(int speed)
	{
		if (shipFinder() != null)
		{
			System.out.println("<SHIP speed has changed by " + speed + ">");
			Ship ship = (Ship) shipFinder();
			ship.setSpeed(speed);
		}
		else
		{
			System.out.println("<SHIP is not present, speed cannot be changed>");
		}
		update();
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Ship Finder ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//ShipFinder() is used to search through the gameCollection and find instances of type Ship, and return
//the found GameObject back to the calling method
//If it returns a null Object, then that means there is no existing type of that GameObject
	public Object shipFinder()
	{
		gameIterator = gameCollection.getIterator();
		while (gameIterator.hasNext() != false)
		{
			GameObject ship = (GameObject)gameIterator.getNext();
			if (ship instanceof Ship)
			{
				return ship;
			}
		}
		return null;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Station Finder ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//stationFinder exists for the same reasons as shipFinder, and functions in exactly the same way
//It might have been possible to merge all of these methods, but I could not think of one at the time
	public Object stationFinder()
	{
		gameIterator = gameCollection.getIterator();
		while (gameIterator.hasNext() != false)
		{
			GameObject spaceStation = (GameObject)gameIterator.getNext();
			if (spaceStation instanceof SpaceStation)
			{
				return spaceStation;
			}
		}
		return null;
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Asteroid Finder ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//asteroidFinder, read above
	public Object asteroidFinder()
	{
		gameIterator = gameCollection.getIterator();
		while (gameIterator.hasNext() != false)
		{
			GameObject asteroid = (GameObject)gameIterator.getNext();
			if (asteroid instanceof Asteroid)
			{
				return asteroid;
			}
		}
		return null;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Missile Finder ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//missileFinder, read above
	public Object missileFinder()
	{
		gameIterator = gameCollection.getIterator();
		while (gameIterator.hasNext() != false)
		{
			GameObject missile = (GameObject)gameIterator.getNext();
			if (missile instanceof Missile)
			{
				return missile;
			}
		}
		return null;
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Asteroid Number ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Searches through the gameCollection and returns all instances of type Asteroid
	public int asteroidNumber()
	{
		asteroidNumber = 0;
		gameIterator = gameCollection.getIterator();
		while (gameIterator.hasNext() != false)
		{
			GameObject asteroid = (GameObject)gameIterator.getNext();
			if (asteroid instanceof Asteroid)
			{
				asteroidNumber += 1;
			}
		}
		return asteroidNumber;
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Get Methods for all of the values that need to be pushed to Proxy, and the Observers
	public int getLives()
	{
		return lives;
	}
	public int getScore()
	{
		return score;
	}
	public int getTime()
	{
		return time;
	}
	public boolean getSound()
	{
		return soundOn;
	}
	public int getMissile()
	{
		return ((GameWorld) shipFinder()).getMissile();
	}
	public IIterator getIterator()
	{
		return gameIterator;
	}
	public GameCollection getCollection()
	{
		return gameCollection;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Update ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Updates Observers, passes GameWorld into Proxy before passing it to Observers to get data from
	public void update()
	{
		this.setChanged();
		GameWorldProxy gameProxy = new GameWorldProxy(this);
		this.notifyObservers(gameProxy);
	}
}