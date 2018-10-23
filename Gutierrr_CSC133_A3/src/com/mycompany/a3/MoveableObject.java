package com.mycompany.a3;

import java.util.Random;
import java.lang.Math;

public abstract class MoveableObject extends GameObject implements IMoveable
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	private int direction, speed;
	private final static int MAX_SPEED = 1000;
	private final static int MIN_SPEED = 0;
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INITIALIZATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public MoveableObject(int mapWidth, int mapHeight) 
	{
		super(mapWidth, mapHeight);
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DIRECTION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Takes the direction given to it by the initialization of an object or from the ISteerable interface to change 
//the direction of the object. The only object that changes its original direction is ship
	public void setDirection(int direction)
	{
		this.direction = (this.direction + direction) % 360;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SPEED ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Takes the speed given to it by the initialization of an object or from ISteerable, 
//again, the only object that changes speed is ship
//An if statement that checks if the maximum or minimum speed allowed by the game has been exceeded, during initialization 
//the number can exceed the cap of 0-100, as it only checks if this.speed is greater than the MAX, something that is 0 at initialization
//E.g. missile can go 120 units as opposed to the max of 100, same for lower end
	public void setSpeed(int speed)
	{
		if (((this.speed < MAX_SPEED) || (speed < 0)) && ((this.speed > MIN_SPEED) || (speed > 0)))
		{
			this.speed = (this.speed + speed);
		}
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MOVE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//The move method that allows for updating the world, it takes the localX and localY provided by gameObject, and the speed and direction
//of the variables in MovableObject to set the new location. Direction uses Math.cos/sin to determine what direction the object is heading in
//before multiplying by the speed of the object. This gives deltaLocal(X/Y), that is then added to the original before being sent back
//to gameObject
//All of the If/Else statements work to check if the new location ever falls out of the boundaries of gameWorld, if they do
//it alters the direction of the object, and does a redo of the change location method
	public void move(int elapseTime)
	{
		int distance = speed * elapseTime / 1000;
		double theta = Math.toRadians(90 - direction);
		float deltaX = (float)(Math.cos(theta) * distance);		
		float deltaY = (float)(Math.sin(theta) * distance);
	    float localX = getLocalX();
	    float localY = getLocalY();		
		int objectSize = getSize();
		int objectOffset = getOffset();
		
		setLocalX(localX + deltaX);
		setLocalY(localY + deltaY);
				
		if (localX - objectSize / 2 <= 0)
		{
			this.direction = -direction;
			setLocalX(localX + objectOffset);
		}
		else if(localX + objectSize / 2 >= getMapWidth())
		{
			this.direction = -direction;
			setLocalX(localX - objectOffset);
		}
		else if(localY - objectSize / 2 <= 0)
		{
			this.direction = 180 - direction;
			setLocalY(localY + objectOffset);
		}
		else if(localY + objectSize / 2 >= getMapHeight())
		{
			this.direction = 180 - direction;
			setLocalY(localY - objectOffset);
		}	
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//getDirection was necessary for getting the direction of the ship so that the missiles could match it, there is no real reason for getSpeed
//originally there was getSpeed, but I realized I had no reason to keep it around and deleted it
	public int getDirection()
	{
		return direction;
	}
	public int getSpeed()
	{
		return speed;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ toString ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//toString for the variables in MovableObject, calls super.toString to get information from GameObject
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Speed: " + this.speed + " ][Direction: " + this.direction + " ]";
		return parentDesc + myDesc;
	}
}
