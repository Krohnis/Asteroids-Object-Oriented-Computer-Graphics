package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;


public class Asteroid extends MoveableObject
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Used for random number generation
	private Random random = new Random();
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INITIALIZATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Initializes asteroid objects in the gameWorld, and random their attributes to decide where they should be set. Direction is capped by 360, 
//speed by 100, location by the size of the map (can still move out of bounds), color of the asteroid and the size of the asteroid.
	public Asteroid(int mapWidth, int mapHeight)
	{
		super(mapWidth, mapHeight);
		super.setType("Asteroid");
		super.setLocalX(random.nextFloat() * mapWidth);
		super.setLocalY(random.nextFloat() * mapHeight);
		super.setSpeed(random.nextInt(700) + 200);
		super.setDirection(random.nextInt(360));
		super.setSize(random.nextInt(60) + 40);
		super.setOffset(getSize()/4);
		setColor(ColorUtil.BLACK);
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DRAW ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Draw method used to define how a gameObject should be drawn to the mapView
//Uses the built-in graphics function to draw objects to the screen
//For asteroid it uses the size, and location to draw the object
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		g.setColor(this.getColor());
		int localX = pCmpRelPrnt.getX() + (int)getLocalX();// shape location relative
		int localY = pCmpRelPrnt.getY() + (int)getLocalY();// to parent’s origin
		int objectSize = super.getSize();

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
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Size: " + super.getSize() + " ]";
		return parentDesc + myDesc;
	}
}

