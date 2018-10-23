package com.mycompany.a2;

public class Ship extends MovableObject implements ISteerable
{
	private int missile;
//Initialization of ship, sets its speed, direction, location, color, and number of missiles
	public Ship()
	{
		setSpeed(0);
		setDirection(0);
		super.setType("SHIP:\n");
		super.setLocalX(512);
		super.setLocalY(384);
		super.setColor(255, 0, 0);
		this.missile = 10;
	}
//Handles setting speed of the ship, after initialization it will either send 10 or -10. This will not be adjusted
	public void setSpeed(int speed)
	{
		super.setSpeed(speed);
	}
//Handles setting the direction of the ship, after initialization it will turn 30 degrees to the left or the right
//to function as the direction (North, South, East, West)
	public void setDirection(int direct)
	{
		super.setDirection(direct);
	}
//missileCount is used in fire to decrement the number of missiles available to the ship/user, it checks to see if there are missiles
//and if there are it returns that value, if there are not, it returns -1 to function as the false statement and stop the creation
//of another missileObject
	public int missileCount()
	{
		if (this.missile <= 0)
		{
			return -1;
		}
		else
		{
			this.missile =  this.missile - 1;
			return this.missile;
		}
	}
//Simply gets the current number of missiles and returns them, used by playerInfo to avoid decrementing the number of missiles by using
//missileCount
	public int getMissile()
	{
		return this.missile;
	}
	public void missileReload()
	{
		this.missile = 10;
	}
//toString for Ship, displays missile and calls the super.toString to get the toString of MovableObject
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Missile Count: " + this.missile + " ]";
		return parentDesc + myDesc;
	}
}