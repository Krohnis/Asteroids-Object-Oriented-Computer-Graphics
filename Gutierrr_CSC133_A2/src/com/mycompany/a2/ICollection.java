package com.mycompany.a2;

public interface ICollection 
{
	public void add(GameObject newObject);
	public void remove(Object newObject);
	public void clear();
	public IIterator getIterator();
}