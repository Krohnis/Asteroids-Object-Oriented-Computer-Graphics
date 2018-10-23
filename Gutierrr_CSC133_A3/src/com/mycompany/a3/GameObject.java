package com.mycompany.a3;

import java.util.Vector;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point;

public abstract class GameObject implements IDrawable, ICollider, ISelectable
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	private String type;
	private float localX, localY;
	private int myColor, objectSize, offset, mapWidth, mapHeight;
	private boolean isSelected, remove, points;
	
	private Vector<GameObject> collide;
	
	public GameObject (int mapWidth, int mapHeight)
	{
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		remove = false;
		points = false;
		isSelected = false;
		collide = new Vector<GameObject>();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ VECTOR ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Vector methods used for invoking certain functions when adding manipulating other objects
	public void add(GameObject obj) 
	{                        
		collide.add(obj);
	}	
    public void remove(GameObject obj) 
    {
		collide.remove(obj); 
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ LOCALX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//The set function that sets the X of the object, it gets its integer value from either the initialization of an object,
//or from the move method that will update the object's location
	public void setLocalX(float localX)
	{
		this.localX = localX;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ LOCALY ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//The same as above except it is instead for Y
	public void setLocalY(float localY)
	{
		this.localY = localY;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SIZE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Size assigned to the object, determines the size of the object and its hitbox
    public void setSize(int objectSize) 
    {
    	this.objectSize = objectSize;
    }
    
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ OFFSET ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Sets the offset value for the object, it is calculated by taking 1/4th the size of the Object
//In Moveable it determines the displayed object's size, whether the draw will fall out of bounds
//If the location plus the offset places it outside of the boundaries of the gameWorld, it then rebounds it
//and changs direction
    public void setOffset(int offset) 
    {
		this.offset = offset;
	}
    
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ COLOR ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//setColor, accepts an integer value from the object to act as the color of the object, I haven't entirely figured this one
//out yet, might need to review
 	public void setColor(int myColor)
	{
		this.myColor = myColor;
	}
    
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CONTAINS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Looks to see if the pointer is inside of an object, this is used for object selection
//Set Selected marks if the object has been selected by the user
public boolean containsObject(Point pPtrRelPrnt, Point pCmpRelPrnt) 
	{
		int pointerX = pPtrRelPrnt.getX();
		int pointerY = pPtrRelPrnt.getY();
		int X = pCmpRelPrnt.getX()+ (int)localX;
		int Y = pCmpRelPrnt.getY()+ (int)localY;
		if ((pointerX >= X) && (pointerX <= X + objectSize) && (pointerY >= Y) && (pointerY <= Y + objectSize))
		{
			return true;
		}
		return false;
	}
//Boolean method to set the isSelected boolean, if true, it will allow for the object to use a different draw function
	public void setSelected(boolean selected)
	{
		this.isSelected = selected;
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ COLLISION OPTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Handles marking objects as collided, collision events and handling, gets the radius of both objects
//and then calculates the difference between them
//If the distance is less than the radius of either object, then that means that the object's
//hitboxes overlap, and returns true
//If the distance is greater than the radius, then the objects do not overlap and it returns false
	public boolean collidesWith(ICollider otherObject) 
	{
		float thisCenterX = getLocalX();
		float thisCenterY = getLocalY();
		float otherCenterX = ((GameObject)otherObject).getLocalX();
		float otherCenterY = ((GameObject)otherObject).getLocalY();

		float deltaX = thisCenterX - otherCenterX;
		float deltaY = thisCenterY - otherCenterY;
		float distance = (deltaX * deltaX + deltaY * deltaY);
		
		float thisRadius = (float)getSize() / 2;
		float otherRadius = (float)(((GameObject)otherObject).getSize()) / 2;
		float radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius 
				+ otherRadius*otherRadius);
		
		if(distance <= radiiSqr)
		{
			return true;
		}
		return false;	
	}
//Checks to see what objects are currently overlapping with this object, and returns true or false
//based on whether or not that object exists in the Vector
//If the object does not exist, collide.contains will return false
    public boolean contains(GameObject gameObject) 
    {
    	return collide.contains(gameObject);
    }
//Makes use of the above contains method, if the object has not already been marked as an object within the vector,
//and the object has a collisionEvent then it will add the objects to their respective Vectors
	public void handleCollision(ICollider otherObject) 
	{
		if(!this.contains((GameObject) otherObject))
		{
			this.add((GameObject) otherObject);
			((GameObject)otherObject).add(this);
		}	
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Marks objects for deletion, and marks them as points when the iteration ends, very vague and just defines
//all of the setter flags that are going to be used
//RemoveFlag sets the removal flag for objects that should be removed from the Vector collide, PointsFlag marks which
//objects should give points once deleted, and the setType is used for the console to display the correct name of the Object
	public void setRemoveFlag(boolean remove) 
	{
		this.remove = remove;
	}
	
	public void setPointsFlag(boolean points) 
	{
		this.points = points;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Getters, very straight forward are are just made to return the variables from GameObject that are requested by
//other classes
	public float getLocalX()
	{
		return this.localX;
	}
	public float getLocalY()
	{
		return this.localY;
	}
	public int getSize()
	{
		return this.objectSize;
	}
	public int getColor()
	{
		return this.myColor;
	}
	public boolean getSelected() 
	{
		return this.isSelected;
	}
	public boolean getRemoveFlag() 
	{
		return this.remove;
	}
	public boolean getPointsFlag() 
	{
		return this.points;
	}
	public int getOffset()
	{
		return this.offset;
	}
	public int getMapWidth()
	{
		return mapWidth;
	}
	public int getMapHeight()
	{
		return mapHeight;
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ toString ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//TO STRING, prints all of the variables to the screen when (M) or (T) are used to display information about the gameWorld
	public String toString()
	{
		String myDesc = type + "[Position X/Y: " + this.localX + ", " + this.localY + " ]"
				+ "[Color: [" + ColorUtil.red(myColor) + ", " + ColorUtil.green(myColor) +", " + ColorUtil.blue(myColor) + "] ]";
		return myDesc;
	}
}