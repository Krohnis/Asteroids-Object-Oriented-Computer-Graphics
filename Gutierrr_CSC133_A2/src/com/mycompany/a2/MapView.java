package com.mycompany.a2;
import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;

public class MapView extends Container implements Observer
{
	private GameWorld gameWorld;
	
	//Sets the MapWidth and MapHeight to the values asked for by the assignment
	public MapView()
	{
		setWidth(1024);
		setHeight(768);
	}
	public void update(Observable o, Object arg) 
	{
	//Fields for all of the declarations, from making gameWorld referrence the apparent gameWorld
	//to creating gameObject to be evaluated at runtime, gameCollection for what has been stored
	//and getIterator to loop through the Objects and find what is requested
	//Cast the Observable objects as the GameWorld first to access variables
		GameWorld gameWorld = (GameWorld)o;
		GameCollection gameCollection = gameWorld.getCollection();
		IIterator gameIterator = gameCollection.getIterator();
	//Prints a header to break up each command
		System.out.println("\n<NEXT TURN CYCLE, TURN NUMBER: " + gameWorld.getTime() + ">\n");
		while ( gameIterator.hasNext() )
		{
			System.out.println(gameIterator.getNext());
		}
	}
}