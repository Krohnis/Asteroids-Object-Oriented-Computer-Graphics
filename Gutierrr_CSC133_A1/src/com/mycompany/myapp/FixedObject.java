package com.mycompany.myapp;

import java.util.Random;

public class FixedObject extends GameObject
{
	private int uniqueID;
	private Random rn = new Random();
//Initialization of FixedObject, and reroll of the uniqueID, if it matches another FixedObject
	public void setID()
	{
		uniqueID = rn.nextInt();
	}
//Is used to get the uniqueIDs of FixedObjects that new FixedObjects must be compared to, to ensure that they are
//indeed unique. Gatekeepers, I guess?
	public int getID()
	{
		return this.uniqueID;
	}
//More toStrings, just prints UniqueID and calls the super.toString in GameObject to print information
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Unique ID: " + this.uniqueID + " ]";
		return parentDesc + myDesc;
	}
}
