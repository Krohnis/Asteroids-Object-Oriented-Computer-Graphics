package com.mycompany.a3;

import java.util.Observer;
import java.util.Observable;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class MapView extends Container implements Observer
{            //Observer Class to display map, no graphics yet

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	private GameCollection gameCollection;
	private IIterator gameIterator;
	private GameObject gameObject;
	private boolean isPaused;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MAP VIEW ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Constructor for MapView
	public MapView()
	{
		gameCollection = new GameCollection();
		gameIterator = gameCollection.getIterator();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ PAINT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Draws the Objects from the gameWorld and prints them to the mapView
//While Loop invokes the draw method for the object, and has it drawn to the mapView
	public void paint(Graphics g) 
	{
		super.paint(g);
		Point pCmpRelPrnt = new Point(getX(), getY());
				
		gameIterator = gameCollection.getIterator();
		while ( gameIterator.hasNext() ) 
		{
			gameObject = (GameObject)gameIterator.getNext();
			gameObject.draw(g, pCmpRelPrnt);		
		} 

	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ POINTER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Marks objects as selected for redrawing, redrawing is then handled by calling paint
	public void pointerPressed(int x, int y) 
	{
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		Point pPtrRelPrnt = new Point(x, y);
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		IIterator pointerIterator = gameCollection.getIterator();
		while (pointerIterator.hasNext())
		{
			gameObject = (GameObject)pointerIterator.getNext();
			if (gameObject.containsObject(pPtrRelPrnt, pCmpRelPrnt) && isPaused)
			{
				gameObject.setSelected(true);
			}
			else
			{
				gameObject.setSelected(false);
			}
		}
		repaint();		
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ PAUSE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Crude way to get selectability to work
	public void setPause(boolean isPaused)
	{
		this.isPaused = isPaused;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ UPDATE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Fields for all of the declarations, from making gameWorld reference the apparent gameWorld
//to creating gameObject to be evaluated at runtime, gameCollection for what has been stored
//and getIterator to loop through the Objects and find what is requested
//Cast the Observable objects as the GameWorld first to access variables
	public void update(Observable o, Object gameProxy )
	{	
		GameWorld gameWorld = (GameWorld)o;
		gameCollection = gameWorld.getGameCollection();
		gameIterator = gameCollection.getIterator();
		
		System.out.println("\n<NEXT TURN CYCLE, TURN NUMBER: " + gameWorld.getTime() + ">\n");
		while (gameIterator.hasNext())
		{
			System.out.println( gameIterator.getNext());
		}
		this.repaint();
	}
}
