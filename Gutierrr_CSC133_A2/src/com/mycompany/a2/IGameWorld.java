package com.mycompany.a2;

public interface IGameWorld 
{
	public int getLives();
	public int getScore();
	public int getTime();
	public int getMissile();
	public IIterator getIterator();
	public GameCollection getCollection();
}