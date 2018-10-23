package com.mycompany.myapp;

public abstract class MovableObject extends GameObject implements IMovableObject
{
	//MAX/MIN_SPEED, there is no real reason for this as I could've easily just used variables but this seemed much more
	//convenient at the time 
	private int direct, speed;
	private final static int MAX_SPEED = 100;
	private final static int MIN_SPEED = 0;
//Takes the direction given to it by the initialization of an object or from the ISteerable interface to change the direction
//of the object. The only object that changes its original direction is ship
	public void setDirection(int direct)
	{
		//Modulus is used to keep direct within the 0-360 range, as the numbers would be too large without it after extended peroids
		this.direct = (this.direct + direct) % 360;
	}
//Takes the speed given to it by the initialization of an object or from ISteerable, again, the only object that changes speed is ship
	public void setSpeed(int speed)
	{
		//An if statement that checks if the maximum or minimum speed allowed by the game has been exceeded, during initialization 
		//the number can exceed the cap of 0-100, as it only checks if this.speed is greater than the MAX, something that is 0 at initialization
		//E.g. missile can go 120 units as opposed to the max of 100, same for lower end
		if (((this.speed < MAX_SPEED) || (speed < 0)) && ((this.speed > MIN_SPEED) || (speed > 0)))
		{
			this.speed = this.speed + speed;
		}
	}
//The move method that allows for updating the world, it takes the localX and localY provided by gameObject, and the speed and direction
//of the variables in MovableObject to set the new location. Direction uses Math.cos/sin to determine what direction the object is heading in
//before multiplying by the speed of the object. This gives deltaLocal(X/Y), that is then added to the original before being sent back
//to gameObject
	public void move()
	{
		float localX = (float) (super.getLocalX() + (this.speed * Math.sin(Math.toRadians(this.direct))));
		float localY = (float) (super.getLocalY() + (this.speed * Math.cos(Math.toRadians(this.direct))));
		super.setLocalX(localX);
		super.setLocalY(localY);
	}
//getDirection was necessary for getting the direction of the ship so that the missiles could match it, there is no real reason for getSpeed
//originally there was getSpeed, but I realized I had no reason to keep it around and deleted it
	public int getDirection()
	{
		return this.direct;
	}
//toString for the variables in MovableObject, calls super.toString to get information from GameObject
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Speed: " + this.speed + " ][Direction: " + this.direct + " ]";
		return parentDesc + myDesc;
	}
}
