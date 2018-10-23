package com.mycompany.a3;

import java.util.Observable;

public class GameWorldProxy extends Observable implements IGameWorld
{
//Field to create a type GameWorld
	private GameWorld gameWorldReal;
	
//Functions as an apparent type, use the getMethods from GameWorld as an
//in between for the model and view, to prevent any modification of objects
//by the observers
	public GameWorldProxy(GameWorld gameWorld) 
	{
		gameWorldReal = gameWorld;
	}
//Gets the number of lives
	public int getLives()
	{
		return gameWorldReal.getLives();
	}
//Gets the score
	public int getScore()
	{
		return gameWorldReal.getScore();
	}
//Gets the time
	public int getTime()
	{
		return gameWorldReal.getTime();
	}
//Gets the missileCount
	public int getShipMissileCount() 
	{
		return gameWorldReal.getShipMissileCount();
	}
//Gets the sound from the game
	public boolean getSound() 
	{
		return gameWorldReal.getSound();
	}
//Gets the width of the map
	public int getMapWidth() 
	{
		return gameWorldReal.getMapWidth();
	}
//Gets the height of the map
	public int getMapHeight() 
	{
		return gameWorldReal.getMapHeight();
	}
//Gets the size of the collection from GameWorld
	public int getCollectionSize() 
	{
		return gameWorldReal.getCollectionSize();
	}
//Gets the gameCollection from GameWorld
	public GameCollection getGameCollection() 
	{
		return (GameCollection) gameWorldReal.getGameCollection();
	}
//Gets the paused state of the game
	public boolean getPaused()
	{
		return gameWorldReal.getPaused();
	}
}