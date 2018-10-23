package com.mycompany.a2;
import java.util.Observable;
import java.util.Observer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;

public class PointsView extends Container implements Observer
{
	private Label livesLabel;
	private Label scoreLabel;
	private Label missilesLabel;
	private Label timeLabel;
	private Label soundLabel;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~ Points View Constructor ~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Constructs PointView and the labels within it, to display all of the player information
	public PointsView()
	{
	//Instantiate all necessary labels
		livesLabel = new Label("Lives: XXXX ");
		scoreLabel = new Label("Score: XXXX ");
		missilesLabel = new Label("Missiles: XXXX ");
		timeLabel = new Label("Time: XXXX ");
		soundLabel = new Label("Sound: XXXX ");
	//Customizes the labels and makes them prettier
		livesLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		scoreLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		missilesLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		timeLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		soundLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
	//Adds the components to the Container
		this.add(livesLabel);
		this.add(scoreLabel);
		this.add(missilesLabel);
		this.add(timeLabel);
		this.add(soundLabel);
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
			int missileCount = gameWorld.getMissile();
			missilesLabel.setText("Missiles: " + Integer.toString(missileCount) + "   ");;
		}
		else
		{
			missilesLabel.setText("Missiles: " + Integer.toString(0) + "   ");
		}
	//Display for TIME
		int timeCount = gameWorld.getTime();
		timeLabel.setText("Time: " + Integer.toString(timeCount) + "   ");
	//Display for SOUND
		if (gameWorld.getSound() == false)
		{
			soundLabel.setText("Sound: ON");
		}
		else
		{
			soundLabel.setText("Sound: OFF");
		}
	}
}