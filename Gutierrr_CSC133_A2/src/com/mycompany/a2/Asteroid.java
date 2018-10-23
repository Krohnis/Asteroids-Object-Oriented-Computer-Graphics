package com.mycompany.a2;

import java.util.Random;

public class Asteroid extends MovableObject
{
	private int size;
	private Random rn = new Random();
//Initializes asteroid objects in the gameWorld, and randoms their attributes to decide where they should be set. Direction is capped by 360, 
//speed by 100, location by the size of the map (can still move out of bounds), color of the asteroid and the size of the asteroid.
	public Asteroid()
	{
		super.setType("ASTEROID:\n");
		super.setDirection(rn.nextInt(360));
		super.setSpeed(rn.nextInt(100));
		super.setLocalX(rn.nextFloat() * 1024);
		super.setLocalY(rn.nextFloat() * 786);
		super.setColor(0, 0 , 0);
		size = rn.nextInt(10) + 1;
	}
	public int getSize()
	{
		return this.size;
	}
//toString that prints the size of the asteroid, and calls super.toString to get the toString from MovableObject
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Size: " + this.size + " ]";
		return parentDesc + myDesc;
	}
}