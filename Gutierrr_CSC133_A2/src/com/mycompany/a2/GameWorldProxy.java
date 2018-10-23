package com.mycompany.a2;
import java.util.Observable;

public class GameWorldProxy extends Observable implements IGameWorld
{
//Field to create a type GameWorld
	private GameWorld gameWorldReal;
	
//Functions as an apparent type, use the getMethods from GameWorld as an
//inbetween for the model and view, to prevent any modification of objects
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
	public int getMissile()
	{
		return gameWorldReal.getMissile();
	}
	
//Gets the Iterator from GameWorld
	public IIterator getIterator()
	{
		return (IIterator) gameWorldReal.getIterator();
	}
	
//Gets the gameCollection from GameWorld
	public GameCollection getCollection()
	{
		return (GameCollection) gameWorldReal.getCollection();
	}
}