package com.mycompany.a3;

import java.util.Observable;

public class GameWorld extends Observable implements IGameWorld 
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GAMEWORLD FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Creates the global variables for sound, lives, time, and score, all of which
//should be visible to the player during runtime
	
	private int lives, playerScore, gameTime, elapseTime, asteroidNumber, mapWidth, mapHeight;
	private boolean sound = true;
	private boolean isPaused;
	
	private GameCollection gameCollection;
	private GameObject gameObject;
	private IIterator gameIterator;
	
	private BGSound BGM = new BGSound("CLEVER DRIVER.mp3");
	private Sound fireMissile = new Sound("FIRE.mp3"); 
	private Sound destroyed = new Sound("EXPLODE.mp3");
	private Sound gameOver = new Sound("GAMEOVER.mp3");
	
	public GameWorld() 
	{
		gameCollection = new GameCollection();	
		this.initialize();
	}
		
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INITIALIZATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public void initialize() 
	{				
		lives = 3;
		playerScore = 0;
		gameTime = 0;
		isPaused = false;
		elapseTime = 20;
		BGM.volume(25);
		BGM.run();
		updateWorld();
		gameCollection.clear();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MAP INFORMATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public void setMapWidth(int width) 
	{
		mapWidth = width;
	}
	public void setMapHeight(int height) 
	{
		mapHeight = height;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SOUND ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//SetSound function, when invoked it sets sound to whatever the opposite of it currently is
//then invokes the method soundPlay() or soundPause() based on what boolean state sound is
	public void setSound ()
	{
		this.sound = !(sound);
		if (sound == true)
		{
			soundPlay();
		}
		else
		{
			soundPause();
		}
		System.out.println("<SOUND options have been changed>");
		updateWorld();
	}
	
//Uses soundPlay() and soundPause() as opposed to using BGM.play() and BGM.pause() in setSound, so that it can be invoked
//by CommandPause() to disable and re-enable sound while the game is paused
	
	public void soundPlay()
	{
		BGM.play();
	}
	public void soundPause()
	{
		BGM.pause();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ PAUSE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Pause function of the game, stops sound or enables selection whatever it was suppose to do
	public void setPause()
	{
		isPaused = !isPaused;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GAMECLOCK ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Game Clock of the program, has some redundancy as it iterates through the loop to take each action 
//and display the outcome a function which is also handled by map, but I couldn't find a way to merge the two, 
//in the future I may revise this section of the program
	public void gameClock()
	{
		gameTime += 1;
		gameIterator = gameCollection.getIterator();
		while(gameIterator.hasNext())
		{
			gameObject = (GameObject)gameIterator.getNext();
			if (gameObject instanceof MoveableObject)
			{
				((MoveableObject)gameObject).move(elapseTime);
			}
			if (gameObject instanceof Missile)
			{
				if (((Missile)gameObject).getFuel() <= 0)
				{
					gameCollection.remove(gameObject);
					gameIterator.stepBack();
				}
			}
			else if (gameObject instanceof SpaceStation)
			{
				((SpaceStation)gameObject).setBlinking(gameTime);
			}
		}
		collisionEvent();
		updateWorld();
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ COLLISION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//This method handles collision events, when objects overlap with other objects
//It selects one object, and compares its position to all other objects in the Vector array,
//if it comes across any object that overlaps, it deletes both
	public void collisionEvent()
	{
		gameIterator = gameCollection.getIterator();
		ICollider gameObject, otherObject;		
		while(gameIterator.hasNext()) 
		{
			gameObject = (ICollider) gameIterator.getNext();	
			IIterator otherIterator = gameCollection.getIterator();
						
			while(otherIterator.hasNext()) 
			{
				otherObject = (ICollider)otherIterator.getNext();
				if(otherObject != gameObject)
				{   
					if(gameObject.collidesWith(otherObject))
					{
						if(!((GameObject)gameObject).contains((GameObject)otherObject))
						{
							gameObject.handleCollision(otherObject);
						
						//Largely just looks to see what the colliding objects are, and marks all of them
						//with their respective flags
						
							if(gameObject instanceof Asteroid && otherObject instanceof Asteroid)
						    {			    	
						    	((GameObject)gameObject).setRemoveFlag(true);
						    	((GameObject)otherObject).setRemoveFlag(true);
								if (sound == true) 
								{
									destroyed.play();
								}
						    }					   																	   
							else if(gameObject instanceof Ship && otherObject instanceof Asteroid || 
									otherObject instanceof Ship && gameObject instanceof Asteroid)
							{					    	
								((GameObject)gameObject).setRemoveFlag(true);
								((GameObject)otherObject).setRemoveFlag(true);
								if (sound == true) 
								{
									destroyed.play();
								}
							}
							
						//In the future it might be necessary to place another If/Else to differentiate between 
						//whether otherObject or gameObject was the asteroid to be marked for points, but for now
						//it is unnecessary as either way the player is only awarded 100 points and both objects
						//are marked for deletion
							
							else if(gameObject instanceof Missile && otherObject instanceof Asteroid || 
									otherObject instanceof Missile && gameObject instanceof Asteroid)
							{		    	
								((GameObject)gameObject).setRemoveFlag(true);
								((GameObject)otherObject).setRemoveFlag(true);
								((GameObject)otherObject).setPointsFlag(true);
								if (sound == true) 
								{
									destroyed.play();
								}
							}
							
						//Has to differentiate whether or not the gameObject or otherObject is a ship
						//If treated like missile/asteroid, where otherObject is assumed to be ship, it will result in
						//errors as it will try to invoke setMissileCount() in SpaceStation, a method that does not exist
						//in that particular class
							
							else if(gameObject instanceof Ship && otherObject instanceof SpaceStation || 
									otherObject instanceof Ship && gameObject instanceof SpaceStation)
							{		 
								if (gameObject instanceof Ship)
								{
									((Ship)gameObject).setMissileCount();
								}
								else
								{
									((Ship)otherObject).setMissileCount();
								}
							}
						}
					}
					else if(((GameObject)gameObject).contains((GameObject)otherObject))
					{
						((GameObject)gameObject).remove((GameObject)otherObject);
						((GameObject)otherObject).remove((GameObject)gameObject);
					}
				}	
			}
		}
	
	//The upper portion of collisionEvent handles marking objects for deletion, only setting flags and playing
	//the related sound if the event occurs
	//This second portion goes through again, and deletes all of the marked objects, awards points when the marked object
	//was also marked as a points event, and if the object is a ship on its last life, it pauses the music and plays the
	//gameOver sound
		gameIterator = gameCollection.getIterator();
		while(gameIterator.hasNext()) {
			gameObject = (ICollider) gameIterator.getNext();
			
			if( ((GameObject)gameObject).getPointsFlag())
			{
				playerScore += 100;
			}
			if( ((GameObject)gameObject).getRemoveFlag())
			{   
				if(gameObject instanceof Ship)
				{
					lives = lives - 1;
					if (lives <= 0)
	        		{
	        			gameOver.play();
	        			BGM.pause();
	        			try
	        			{
	        				Thread.sleep(3000);
	        			}
	        			catch(InterruptedException ex)
	        			{
	        				ex.printStackTrace();
	        			}
	        			initialize();
	        		}
				}
			//It removes the gameObject from the gameCollection, and moves back one step in the array, this is done because
			//all objects ahead of the deleted object will move back by one index
			//Prevents nullPointers, and skipping over Objects
				gameCollection.remove(gameObject);
				gameIterator.stepBack();
			}
		}			
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ASTEROID ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Simplest of the objects, create the asteroid and let the Asteroid class handle the rest
	public void addAsteroid()
	{
		GameObject asteroid = new Asteroid(mapWidth, mapHeight);
		gameCollection.add(asteroid);
		System.out.println("<ASTEROID has been created>");
		updateWorld();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SHIP ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles Ship creation and its addition into the gameCollection
//ShipFinder() is called and iterates through the gameCollection, returning any
//Object instances of class Ship
//If it cannot find any instances of type Ship, it instead returns a null Object
//indicating that there is no existing GameObject of that type
	
	public void addSpaceShip()
	{
		if (shipFinder() == null)
		{
			GameObject spaceShip = new Ship(mapWidth, mapHeight);
			gameCollection.add(spaceShip);
			System.out.println("<SHIP has been created>");
		}
		//Display if an attempt to create a ship while one already existed was made
		else
		{
			System.out.println("<SHIP already exists>");
		}
		updateWorld();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SPACE STATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Exists for the same reason as the if in ship, it determines that there is only one instance of spaceStation before alllowing
//the creation of the object
	public void addSpaceStation()
	{
		GameObject spaceStation = new SpaceStation(mapWidth, mapHeight);
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
		updateWorld();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MISSILE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles the creation of Missiles in GameWorld, uses the information stored inside of Ship
//to determine direction, and starting location
//Makes sure that ship exists before allowing the player to fire a missile,
//as it will rely on the ship for its initial position and direction
	public void fireShipMissile()
	{
		if (shipFinder() != null)
		{
			Ship ship = (Ship)shipFinder();
			if (ship.getMissileCount() > 0)
			{
				//Gets the location and direction of the ship and assigns them to the missile object
				ship.fireMissile();
				GameObject missile = new Missile(ship.getLocalX(), ship.getLocalY(), ship.getDirection(), mapWidth, mapHeight);
				gameCollection.add(missile);
				if (sound == true)
				{
					fireMissile.play();
				}
				System.out.println("<A MISSILE has been fired from the SHIP>");
			}
			else
			{
				System.out.println("<SHIP does not have any MISSILES ready>");
			}
		}
		else
		{
			System.out.println("<SHIP is not present, no MISSILES can be fired>");
		}
		updateWorld();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SHIP DIRECTION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Direct functions as the turn method, taking in an integer 330 for left turns
//or 30 for right turns, before sending them to the setDirection method
	public void direction(int direction)
	{
		if (shipFinder() != null)
		{
			System.out.println("<SHIP direction has changed by " + direction + ">");
			Ship ship = (Ship) shipFinder();
			ship.setDirection(direction);
		}
		else
		{
			System.out.println("<SHIP is not present, direction cannot be changed>");
		}
		updateWorld();
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SHIP SPEED ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//The speed method takes in integers 10 and -10, to increase and decrease the speed 
//of the ship. After confirming that ship does exist they are sent to the setSpeed method
	public void speed(int speed)
	{
		if (shipFinder() != null)
		{
			System.out.println("<SHIP speed has changed by " + speed + ">");
			Ship spaceShip = (Ship) shipFinder();
			spaceShip.setSpeed(speed);
		}
		else
		{
			System.out.println("<SHIP is not present, speed cannot be changed>");
		}
		updateWorld();
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
			ship.setLocalX(mapWidth/2);
			ship.setLocalY(mapHeight/2);
			System.out.println("<SHIP has been placed at Origin>");
		}
		else
		{
			System.out.println("<SHIP is not present, coordinates cannot be changed>");
		}
		updateWorld();
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
			ship.setMissileCount();
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
		updateWorld();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ KILL ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles the removal of Asteroids and Missiles when the kill command is invoked
	public void killedAsteroid()
	{
		//Finds the first asteroid and missile in vectorArray to destroy
		if (asteroidFinder() != null && missileFinder() != null)
		{
			gameCollection.remove(asteroidFinder());
			gameCollection.remove(missileFinder());
			if (sound == true) 
			{
				destroyed.play();
			}
			this.playerScore += 100;
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
		updateWorld();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CRASH ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles the case of the destruction of the Ship and Asteroid in a crash
	public void shipCrashed()
	{
	//Confirms that an instance of Ship and an Asteroid exist before removing both Objects
	//If the finder returns a null Object, that means that no Object of that type exists
		if (shipFinder() != null && asteroidFinder() != null)
		{
			gameCollection.remove(shipFinder());
			gameCollection.remove(asteroidFinder());
			if (sound == true) 
			{
				destroyed.play();
			}
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
			BGM.pause();
			gameOver.play();
			try
			{
				Thread.sleep(3000);
			}
			catch(InterruptedException ex)
			{
				ex.printStackTrace();
			}
			initialize();
		}
		updateWorld();
	}

	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ COLLIDE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles the extermination of two Asteroids, when they have collided into each other
	public void twoAsteriodsCollided()
	{
	//Looks at asteroidNumber to see if two or more Asteroids exist in gameCollection before allowing
	//deletion of any Objects
		if (asteroidNumber() >= 2)
		{
			gameCollection.remove(asteroidFinder());
			gameCollection.remove(asteroidFinder());
			if (sound == true) 
			{
				destroyed.play();
			}
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
		updateWorld();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ REFUEL ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Lets you refuel missiles that are selected while the game is paused
	public void refuelMissile() 
	{
		IIterator theIterator = gameCollection.getIterator();
		while(theIterator.hasNext())
		{
			GameObject gameObject = (GameObject) theIterator.getNext();
			if(gameObject instanceof Missile)
			{
				if(gameObject.getSelected())
				{
					((Missile) gameObject).setFuel();
				}
			}
		}
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
			gameObject = (GameObject)gameIterator.getNext();
			if (gameObject instanceof Ship)
			{
				return gameObject;
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
			gameObject = (GameObject)gameIterator.getNext();
			if (gameObject instanceof SpaceStation)
			{
				return gameObject;
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
			gameObject = (GameObject)gameIterator.getNext();
			if (gameObject instanceof Asteroid)
			{
				return gameObject;
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
			gameObject = (GameObject)gameIterator.getNext();
			if (gameObject instanceof Missile)
			{
				return gameObject;
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
			gameObject = (GameObject)gameIterator.getNext();
			if (gameObject instanceof Asteroid)
			{
				asteroidNumber += 1;
			}
		}
		return asteroidNumber;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Get Methods for all of the values that need to be pushed to Proxy, and the Observers
	public int getLives() 
	{
		return lives;
	}
	public int getScore() 
	{
		return playerScore;
	}
	public int getTime() 
	{
		return gameTime;
	}
	public int getElapseTime()
	{
		return elapseTime;
	}
	public boolean getSound() 
	{		
		return sound;
	}
	public int getShipMissileCount() 
	{
		gameObject = (Ship)shipFinder();
		return ((Ship) gameObject).getMissileCount();
	}
	public int getCollectionSize() 
	{
		return gameCollection.getSize();
	}
	public GameCollection getGameCollection() 
	{
		return gameCollection;
	}
	public int getMapWidth()
	{
		return mapWidth;
	}	
	public int getMapHeight()
	{
		return mapHeight;
	}
	public boolean getPaused()
	{
		return this.isPaused;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Update ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Updates Observers, passes GameWorld into Proxy before passing it to Observers to get data from
	public void updateWorld() 
	{
		this.setChanged();
		GameWorldProxy gameProxy = new GameWorldProxy(this);
		notifyObservers(gameProxy);
	}
}
