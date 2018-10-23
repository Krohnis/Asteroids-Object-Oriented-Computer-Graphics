package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Missile extends MoveableObject
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	private int fuel;
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INITIALIZATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Initializes missile at the location of ship, and sets it to go in the same direction at a set speed of 120, as well as 
//determine the color of the missile
	public Missile(float localX, float localY, int direction, int mapWidth, int mapHeight)
	{
		super(mapWidth, mapHeight);
		super.setType("Missile");
		super.setLocalX(localX);
		super.setLocalY(localY);
		super.setSpeed(1200);
		super.setDirection(direction);
		super.setSize(30);
		super.setOffset(getSize()/4);
		super.setColor(ColorUtil.BLUE);
		setFuel();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FUEL OPTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//getFuel is used in gameClock. When there is at least 1 fuel left, it will return true and allow for the normal print and movement to occur,
//but if fuel is 0, or less than 0, it instead returns false, displays that the missile was destroyed and why, and then is removed from
//vectorArray
	public void useFuel()
	{
		this.fuel -= 1;
	}
//Used initially to set the fuel of the ship, and is invoked by the refuel command is used
	public void setFuel()
	{
		this.fuel = 500;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public int getFuel()
	{
		return this.fuel;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DRAW ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Draw method used to define how a gameObject should be drawn to the mapView
//Uses the built-in graphics function to draw objects to the screen
//For missile it uses the size, and location to draw the object
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		int localX = pCmpRelPrnt.getX() + (int)getLocalX();
		int localY = pCmpRelPrnt.getY() + (int)getLocalY();
		int objectSize = getSize();
		
		if(getSelected())
		{
			g.drawArc(localX, localY, objectSize, objectSize, 0, 360);
		}
		else
		{
			g.fillArc(localX, localY, objectSize, objectSize, 0, 360);
		}
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ toString ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//toString, you know the drill
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Fuel: " + this.fuel + " ]";
		return parentDesc + myDesc;
	}
}