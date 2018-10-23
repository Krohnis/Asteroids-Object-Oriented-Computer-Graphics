package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Ship extends MoveableObject implements ISteerable
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	private int missileCount;
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INITIALIZATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Initialization of ship, sets its speed, direction, location, color, and number of missiles
	public Ship(int mapWidth, int mapHeight)
	{
		super(mapWidth, mapHeight);
		super.setType("SHIP");
		super.setLocalX(mapWidth/2);
		super.setLocalY(mapHeight/2);
		super.setSpeed(0);
		super.setDirection(0);
		super.setSize(60);
		super.setOffset(getSize()/4);
		super.setColor(ColorUtil.MAGENTA);
		setMissileCount();
		
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MISSILES OPTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Resets the number of missiles the player has to 10, removes a missile whenever the player
//fires one
	public void fireMissile()
	{
		if (missileCount > 0)
		{
			this.missileCount -= 1;
		}
	}
//Resets the missileCount to 10, used when the ship is first spawned to give the ship it's initial supply
//and again whenever the ship collides with a spaceStation or the resupply method is invokved
	public void setMissileCount()
	{
		this.missileCount = 10;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Returns the number of missiles the player has
	public int getMissileCount()
	{
		return this.missileCount;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DRAW ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Draw method used to define how a gameObject should be drawn to the mapView
//Uses the built-in graphics function to draw objects to the screen
//For ship it uses the position +- values to set the boundaries of the triangle
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		g.setColor(this.getColor());
		int localX = pCmpRelPrnt.getX() + (int)getLocalX();
		int localY = pCmpRelPrnt.getY() + (int)getLocalY();
		g.fillTriangle(localX - 30, localY - 30, localX + 30, localY - 30, localX, localY + 30);
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ toString ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//toString for Ship, displays missile and calls the super.toString to get the toString of MovableObject
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Missile Count: " + this.missileCount + " ]";
		return parentDesc + myDesc;
	}
}