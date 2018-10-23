package com.mycompany.a3;

import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

//Taken mostly from the sample code and demo program, uses the built-in functionality to allow for audio files
//to be selected and played through other built-in methods such as play() and pause()
public class BGSound implements Runnable
{
	private Media m;

	public BGSound(String fileName)
	{
		try
		{
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/"+fileName);
			m = MediaManager.createMedia(is, "audio/wav", this);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void pause(){ m.pause();}
	public void play(){ m.play();}
		
	public void volume(int vol)
	{ 
		m.setVolume(vol);
	}
	
	public void run() 
	{
		m.setTime(0);
		m.play();
	}
}
