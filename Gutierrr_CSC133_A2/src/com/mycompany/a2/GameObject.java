package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;

public abstract class GameObject extends GameWorld
{
	private String type;
	private float localX, localY;
	private int myColor;
//The set function that sets the X of the object, it gets its integer value from either the initialization of an object,
//or from the move method that will update the object's location
	public void setLocalX(float localX)
	{
		this.localX = localX;
	}
//The same as above except it is instead for Y
	public void setLocalY(float localY)
	{
		this.localY = localY;
	}
//setColor, accepts an integer value from the object to act as the color of the object, I haven't entirely figured this one
//out yet, might need to review
	public void setColor(int color1, int color2, int color3)
	{
		myColor = ColorUtil.rgb(color1, color2, color3);
	}
//This getLocal is used by Missile to obtain the current location of the ship, and place the missiles in the same position
	public float getLocalX()
	{
		return this.localX;
	}
//Again, same except for Y
	public float getLocalY()
	{
		return this.localY;
	}
//Returns the color of the Object
	public int getColor()
	{
		return myColor;
	}
//Gets the Type of Object
	public void setType(String t)
	{
		this.type = t;
	}
	public String getType()
	{
			return this.type;
	}
//TO STRING, prints all of the variables to the screen when (M) or (T) are used to display information about the gameWorld
	public String toString()
	{
		String myDesc = getType() + "[Position X/Y: " + this.localX + ", " + this.localY + " ]"
				+ "[Color: [" + ColorUtil.red(myColor) + ", " + ColorUtil.green(myColor) +", " + ColorUtil.blue(myColor) + "] ]";
		return myDesc;
	}
}