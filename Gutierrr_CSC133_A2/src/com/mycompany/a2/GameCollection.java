package com.mycompany.a2;
import java.util.Vector;

public class GameCollection implements ICollection
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Fields ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Vector of type GameObject, keeps a list of all of the GameObjects currently present in
//GameWorld and allows some of them to be manipulated
	private Vector<GameObject> gameCollection;
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GameCollection ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Constructor of the GameCollection, creates a new gameCollection Vector of type GameObject
	public GameCollection()
	{
		gameCollection = new Vector<GameObject>();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Add GameObject ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Takes in the passed GameObject and adds the Object to the Vector
	public void add(GameObject newObject)
	{
		gameCollection.addElement(newObject);
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Remove GameObject ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Takes in the passed GameObject and removes the Object from the Vector
	public void remove(Object oldObject)
	{
		gameCollection.remove(oldObject);
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Clear GameObjects ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Clears the Vector of all GameObjects, only used on player's third death
	public void clear()
	{
		gameCollection.clear();
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Get Iterator  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Returns type IIterator when called, returns a new GameCollectionIterator for use
	public IIterator getIterator()
	{
		return new GameCollectionIterator();
	}
	private class GameCollectionIterator implements IIterator
	{
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Fields ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Field for the currentIndex of the Iterator, meant to indicate what Object in the Vector is
	//being pointed to during iteration
		private int currentIndex;
		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructor ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Starts the currentIndex at -1, so that once getNext is called the Iterator will first look
	//at index 0 of the Vector
		public GameCollectionIterator ()
		{
			currentIndex = -1;
		}
		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Has Next ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Looks at the Vector to see if there is another Object, and, if there is, returns True
		public boolean hasNext()
		{
			if (currentIndex == gameCollection.size() - 1 || gameCollection.size() <= 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Get Next ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Increments the currentIndex by 1, and returns the GameObject at that Vector index
		public Object getNext()
		{
			currentIndex += 1;
			return gameCollection.elementAt(currentIndex);
		}
		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Step Back ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Moves back one step in the Vector
		public void stepBack()
		{
			currentIndex -= 1;
		}
	}
}