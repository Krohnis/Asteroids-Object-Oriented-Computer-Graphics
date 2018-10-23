package com.mycompany.a3;

public interface IGameWorld 
{
	public int getScore();
	public int getLives();
	public int getShipMissileCount();
	public int getTime();
	public int getMapWidth();
	public int getMapHeight();
	public int getCollectionSize();
	public boolean getSound();
	public boolean getPaused();
	public GameCollection getGameCollection();
}
