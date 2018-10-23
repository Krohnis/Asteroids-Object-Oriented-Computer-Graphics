package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;


public class SpaceStation extends FixedObject 
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	private Random random = new Random();
	private boolean isBlinking;
	private int blinkRate;
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INITIALIZATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public SpaceStation(int mapWidth, int mapHeight)
	{
		super(mapWidth, mapHeight);
		super.setType("SPACE STATION");
		super.setLocalX(random.nextFloat() * mapWidth);
		super.setLocalY(random.nextFloat() * mapHeight);
		super.setSize(40);
		super.setID();
		
		isBlinking = true;
		blinkRate = (100 + random.nextInt(100));
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ BLINKER OPTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public void setBlinking(int time)
	{
		if ((time % blinkRate) == 0)
		{
			isBlinking = !(isBlinking);
		}
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public boolean getBlinking()
	{
		return this.isBlinking;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DRAW ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Draw method used to define how a gameObject should be drawn to the mapView
//Uses the built-in graphics function to draw objects to the screen
//For spaceStation it uses the size, and location to draw the object
//When it is blinking, it will draw a different shape with a different color for better indication
public void draw(Graphics g, Point pCmpRelPrnt) {
	g.setColor(this.getColor());
	int localX = pCmpRelPrnt.getX() + (int)getLocalX();// shape location relative
	int localY = pCmpRelPrnt.getY() + (int)getLocalY();// to parent’s origin
	int objectSize = getSize();
	
	if(isBlinking == true) {
		g.drawArc(localX, localY, objectSize, objectSize, 0, 360);
		super.setColor(ColorUtil.BLACK);
	}
	else {
		g.drawRect(localX, localY, objectSize, objectSize);
		super.setColor(ColorUtil.CYAN);
	}	
}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ toString ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Another toString
	public String toString()
	{
		String parentDesc = super.toString();
		String myDesc = "[Blink Rate: " + this.blinkRate + " ][Blinking: " + this.isBlinking + " ]";
		return parentDesc + myDesc;
	}
}