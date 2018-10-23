package com.mycompany.myapp;

import java.util.Random;

public class SpaceStation extends FixedObject
{
	private Random rn = new Random();
//Determines if the spaceStation is blinking (active) and what those intervals are
	private boolean isBlinking = true;
	private int blinkRate = rn.nextInt(10) + 1;
//Initilization of spaceStation, unlike MovableObject it only has its location and its color, there isn't much to say
	public SpaceStation()
	{
		super.setID();
		super.setLocalX(rn.nextFloat() * 1024);
		super.setLocalY(rn.nextFloat() * 786);
		super.setColor(60);
	}
//Is called in gameClock, it is sent the current gameWorld time and checks to see if the modulus of time over blinkRate is 0. If the
//result is 0, then isBlinking is set equal to its opposite (true turns false, false turns true), otherwise nothing happens
	public void setBlinking(int time)
	{
		if ((time % blinkRate) == 0)
		{
			isBlinking = (!isBlinking);
		}
	}
//Another toString
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Blink Rate: " + this.blinkRate + " ][Blinking: " + this.isBlinking + " ]";
		return parentDesc + myDesc;
	}
}
