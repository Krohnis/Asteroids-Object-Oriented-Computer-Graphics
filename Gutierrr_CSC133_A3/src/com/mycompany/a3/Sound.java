package com.mycompany.a3;

import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

//Taken mostly from the sample code and demo program, uses the built-in functionality to allow for audio files
//to be selected and played through other built-in methods such as play() and pause()
public class Sound 
{
	private Media m;
	public Sound(String fileName) 
	{
		try
		{
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/"+fileName);
			m = MediaManager.createMedia(is, "audio/wav");
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void play() 
	{
		m.setTime(0); 
		m.play();
	}
}
