package com.mycompany.a3;

import java.util.Random;

public abstract class FixedObject extends GameObject
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	private int uniqueID;
	private Random random;
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INITIALIZATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Initialization of FixedObject, and re-roll of the uniqueID, if it matches another FixedObject
	public FixedObject(int MapWidth, int mapHeight)
	{
		super(MapWidth, mapHeight);
		random = new Random();
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SET UNIQUE ID ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Sets the ID to a new random value on call, it is not necessarily unique until after going through
//an approval process in GameWorld
	public void setID()
	{
		uniqueID = random.nextInt();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public int getID()
	{
		return this.uniqueID;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ toString ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//More toStrings, just prints UniqueID and calls the super.toString in GameObject to print information
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Unique ID: " + this.uniqueID + " ]";
		return parentDesc + myDesc;
	}
}