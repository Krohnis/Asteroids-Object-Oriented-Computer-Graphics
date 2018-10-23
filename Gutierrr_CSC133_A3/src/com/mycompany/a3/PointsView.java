package com.mycompany.a3;

import java.util.Observer;
import java.util.Observable;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.layouts.FlowLayout;

public class PointsView extends Container implements Observer
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	private Label livesLabel;
	private Label scoreLabel;
	private Label missilesLabel;
	private Label timeLabel;
	private Label soundLabel;
	private Label pauseLabel;
	private boolean isPaused;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~ POINTS VIEW CONSTRUCTOR ~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Constructs PointView and the labels within it, to display all of the player information
	public PointsView()
	{
	//Instantiate all necessary labels
		livesLabel = new Label("Lives: XXXX ");
		scoreLabel = new Label("Score: XXXX ");
		missilesLabel = new Label("Missiles: XXXX ");
		timeLabel = new Label("Time: XXXX ");
		soundLabel = new Label("Sound: XXXX ");
		pauseLabel = new Label("Pause: XXXXXX ");
	//Customizes the labels and makes them prettier
	//Lives Label
		livesLabel.getAllStyles().setBgTransparency(255);
		livesLabel.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 200, 200));
		livesLabel.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Score Label
		scoreLabel.getAllStyles().setBgTransparency(255);
		scoreLabel.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 200, 200));
		scoreLabel.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Missile Label
		missilesLabel.getAllStyles().setBgTransparency(255);
		missilesLabel.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 200, 200));
		missilesLabel.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Time Label
		timeLabel.getAllStyles().setBgTransparency(255);
		timeLabel.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 200, 200));
		timeLabel.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Sound Label
		soundLabel.getAllStyles().setBgTransparency(255);
		soundLabel.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 200, 200));
		soundLabel.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Pause Label
		pauseLabel.getAllStyles().setBgTransparency(255);
		pauseLabel.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 200, 200));
		pauseLabel.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Adds the components to the Container
		this.add(livesLabel);
		this.add(scoreLabel);
		this.add(missilesLabel);
		this.add(timeLabel);
		this.add(soundLabel);
		this.add(pauseLabel);
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ PAUSE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Crude way to get selectability to work
	public void setPause(boolean isPaused)
	{
		this.isPaused = isPaused;
		if (isPaused == true)
		{
			pauseLabel.setText("STATE: Paused");
		}
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Update ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//The workhorse that does the actual changes inside of PointsView. Alters the display to show
//whatever new information has been passed in from gameWorld and the objects
	public void update(Observable o, Object arg) 
	{
		GameWorld gameWorld = (GameWorld)o;
	//Display for LIVES
		int livesCount = gameWorld.getLives();
		livesLabel.setText("Lives: " + Integer.toString(livesCount) + "   ");;
	//Display for SCORE
		int scoreCount = gameWorld.getScore();
		scoreLabel.setText("Score: " + Integer.toString(scoreCount) + "   ");
	//Display for MISSILES
		if (gameWorld.shipFinder() != null)
		{
			int missileCount = gameWorld.getShipMissileCount();
			missilesLabel.setText("Missiles: " + Integer.toString(missileCount) + "   ");;
		}
		else
		{
			missilesLabel.setText("Missiles: " + Integer.toString(0) + "   ");
		}
	//Display for TIME, divided by 100 to give a more reasonable display instead of jumping to 2,600 within a few seconds
		int timeCount = gameWorld.getTime();
		timeLabel.setText("Time: " + Integer.toString(timeCount/100) + "   ");
	//Display for SOUND
		if (gameWorld.getSound() == false)
		{
			soundLabel.setText("Sound: OFF");
		}
		else
		{
			soundLabel.setText("Sound: ON");
		}
	//Display for PAUSE
		if (isPaused == false)
		{
			pauseLabel.setText("STATE: Playing");
		}
		else
		{
			pauseLabel.setText("STATE: Paused");
		}
	}
}