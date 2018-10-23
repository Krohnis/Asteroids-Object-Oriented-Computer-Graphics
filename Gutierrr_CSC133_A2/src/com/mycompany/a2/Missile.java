package com.mycompany.a2;

public class Missile extends MovableObject
{
	private int fuel = 10;
//Initializes missile at the location of ship, and sets it to go in the same direction at a set speed of 120, as well as 
//determine the color of the missile
	public Missile(float localX, float localY, int direction)
	{
		super.setSpeed(120);
		super.setDirection(direction);
		super.setType("MISSILE:\n");
		super.setLocalX(localX);
		super.setLocalY(localY);
		super.setColor(250, 150, 0);
	}
//getFuel is used in gameClock. When there is at least 1 fuel left, it will return true and allow for the normal print and movement to occur,
//but if fuel is 0, or less than 0, it instead returns false, displays that the missile was destroyed and why, and then is removed from
//vectorArray
	public boolean fuelLevels()
	{
		if (this.fuel > 0)
		{
			this.fuel--;
			return true;
		}
		return false;
	}
	public int getFuel()
	{
		return fuel;
	}
//toString, you know the drill
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Fuel: " + this.fuel + " ]";
		return parentDesc + myDesc;
	}
}
