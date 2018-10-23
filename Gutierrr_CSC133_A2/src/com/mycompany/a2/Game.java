package com.mycompany.a2;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import java.lang.String;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import java.lang.String;

public class Game extends Form
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Game Fields ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//Fields for creating variables of Objects type GameWorld, MapView, PointsViews
	private GameWorld gameWorld;
	private MapView mapView;
	private PointsView pointsView;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Initialization of Game ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public Game()
	{
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Initialization ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
		gameWorld = new GameWorld();
		mapView = new MapView();
		pointsView = new PointsView();
		this.setLayout(new BorderLayout());
		gameWorld.addObserver(mapView);
		gameWorld.addObserver(pointsView);
		gameWorld.initialize();
		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ North Container ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Contains the display for information from the number of player lives, to missile count, score, and time
		Container northContainer = new Container();
		northContainer.setLayout(new FlowLayout());
		northContainer = FlowLayout.encloseCenter(pointsView);
		this.add(BorderLayout.NORTH, northContainer);
		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Center Container ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//For MapView's display
		Container centerContainer = new Container();
		centerContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(0, 0, 0)));
		centerContainer.add(mapView);
		this.add(BorderLayout.CENTER, centerContainer);

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GameWorld Buttons ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//This block creates all of the buttons that will be shown in the GUI, it does not manage their style
	//however as that is handled in the block beneath this
	//Adds an Asteroid to the GameWorld
		Button addAsteroid = new Button("Add Asteroid");
	//Adds a Space Station to the GameWorld
		Button addStation = new Button("Add Station");
	//Adds a Ship to the GameWorld
		Button addShip = new Button("Add Ship");
	//Increases speed of the Ship
		Button faster = new Button("Increase");
	//Decreases speed of the Ship
		Button slower = new Button("Decrease");
	//Turns the Ship left
		Button left = new Button("Left");
	//Turns the Ship right
		Button right = new Button("Right");
	//Fires a Missile from the Ship
		Button fire = new Button("Add Fire");
	//Warps the Ship to the starting position in GameWorld
		Button warp = new Button("Origin Warp");
	//Reloads the missiles in the Ship, setting the value back to 10
		Button addMissiles = new Button("Add Missiles");
	//Kills an Asteroid and Missile if both objects are present in the Vector
		Button kill = new Button("Kill");
	//Kills an Asteroid and the Ship if both objects are present in the Vector
		Button crash = new Button("Crash");
	//Kills two Asteroids if two unique Asteroids are present in the Vector
		Button collide = new Button("Collide");
	//Increments Time in GameWorld by 1, allows movement
		Button time = new Button("Increment Time");
	//Ends the Game
		Button quit = new Button("Quit");
	//Resets the game with a new version
		Button newGame = new Button("New");
	//Saves the game, but currently does nothing
		Button saveGame = new Button("Save");
	//Readme with student information, and program information
		Button readme = new Button("About");
	//Sound Checkbox
		CheckBox soundCheck = new CheckBox("Turn Sound ON / OFF");
		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Button Appearance ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Makes the addAsteroid button prettier
		addAsteroid.getAllStyles().setBgTransparency(255);
		addAsteroid.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		addAsteroid.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the addStation button prettier
		addStation.getAllStyles().setBgTransparency(255);
		addStation.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		addStation.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the addShip button prettier
		addShip.getAllStyles().setBgTransparency(255);
		addShip.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		addShip.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the faster button prettier
		faster.getAllStyles().setBgTransparency(255);
		faster.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		faster.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the slower button prettier
		slower.getAllStyles().setBgTransparency(255);
		slower.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		slower.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the left button prettier
		left.getAllStyles().setBgTransparency(255);
		left.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		left.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the right button prettier
		right.getAllStyles().setBgTransparency(255);
		right.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		right.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the fire button prettier
		fire.getAllStyles().setBgTransparency(255);
		fire.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		fire.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the warpShip button prettier
		warp.getAllStyles().setBgTransparency(255);
		warp.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		warp.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the addAsteroid button prettier
		addMissiles.getAllStyles().setBgTransparency(255);
		addMissiles.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		addMissiles.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the addStation button prettier
		kill.getAllStyles().setBgTransparency(255);
		kill.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		kill.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the addShip button prettier
		crash.getAllStyles().setBgTransparency(255);
		crash.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		crash.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the warpShip button prettier
		collide.getAllStyles().setBgTransparency(255);
		collide.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		collide.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the addShip button prettier
		time.getAllStyles().setBgTransparency(255);
		time.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		time.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the warpShip button prettier
		quit.getAllStyles().setBgTransparency(255);
		quit.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		quit.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the warpShip button prettier
		newGame.getAllStyles().setBgTransparency(255);
		newGame.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		newGame.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the addShip button prettier
		saveGame.getAllStyles().setBgTransparency(255);
		saveGame.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		saveGame.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Makes the warpShip button prettier
		readme.getAllStyles().setBgTransparency(255);
		readme.getUnselectedStyle().setBgColor(ColorUtil.rgb(100, 100, 100));
		readme.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	//Change soundCheck's appearance
		soundCheck.getAllStyles().setBgTransparency(255);
		soundCheck.getAllStyles().setBgColor(ColorUtil.rgb(200, 200, 200));
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Command ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Fields that exist to allow the use of the command classes, and invoke methods within GameWorld
	//Handles everything from adding objects, removing objects, and manipulation of objects
		CommandAddAsteroid myAsteroid = new CommandAddAsteroid(gameWorld);
		CommandAddStation myStation = new CommandAddStation(gameWorld);
		CommandAddShip myShip = new CommandAddShip(gameWorld);
		CommandFireMissile myFire = new CommandFireMissile(gameWorld);
		CommandWarp myWarp = new CommandWarp(gameWorld);
		CommandAddMissiles myMissiles = new CommandAddMissiles(gameWorld);
		CommandKill myKill = new CommandKill(gameWorld);
		CommandCrash myCrash = new CommandCrash(gameWorld);
		CommandCollide myCollide = new CommandCollide(gameWorld);
		CommandTime myTime = new CommandTime(gameWorld);
		CommandQuit myQuit = new CommandQuit(gameWorld);
		CommandNew myNew = new CommandNew(gameWorld);
		CommandSave mySave = new CommandSave(gameWorld);
		CommandAbout myRead = new CommandAbout(gameWorld);
		CommandIncreaseSpeed myIncrease = new CommandIncreaseSpeed(gameWorld);
		CommandDecreaseSpeed myDecrease = new CommandDecreaseSpeed(gameWorld);
		CommandTurnLeft myLeft = new CommandTurnLeft(gameWorld);
		CommandTurnRight myRight = new CommandTurnRight(gameWorld);
		CommandSoundCheck mySound = new CommandSoundCheck(gameWorld);
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Button to Command ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Sets each of the buttons to a command that was created in the previous block
	//Without this, the buttons will not perform any action when pressed. Binds button and action together
		addAsteroid.setCommand(myAsteroid);
		addStation.setCommand(myStation);
		addShip.setCommand(myShip);
		faster.setCommand(myIncrease);
		slower.setCommand(myDecrease);
		left.setCommand(myLeft);
		right.setCommand(myRight);
		fire.setCommand(myFire);
		warp.setCommand(myWarp);
		addMissiles.setCommand(myMissiles);
		kill.setCommand(myKill);
		crash.setCommand(myCrash);
		collide.setCommand(myCollide);
		time.setCommand(myTime);
		quit.setCommand(myQuit);
		newGame.setCommand(myNew);
		saveGame.setCommand(mySave);
		readme.setCommand(myRead);
		soundCheck.setCommand(mySound);
		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ KeyListener ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//KeyListeners that enable methods to be invoked through the press of the key as opposed to pressing the button
	//Each listener has been assigned to the appropriate key excluding reload, which has been bound to (U) as (N) was
	//used in the previous assignment for canceling a game exit
		addKeyListener('a', myAsteroid);
		addKeyListener('b', myStation);
		addKeyListener('s', myShip);
		addKeyListener('f', myFire);
		addKeyListener('j', myWarp);
		addKeyListener('u', myMissiles);
		addKeyListener('k', myKill);
		addKeyListener('c', myCrash);
		addKeyListener('x', myCollide);
		addKeyListener('t', myTime);
		addKeyListener('q', myQuit);
		addKeyListener('i', myIncrease);
		addKeyListener('d', myDecrease);
		addKeyListener('l', myLeft);
		addKeyListener('r', myRight);
		addKeyListener('q', myQuit);
		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Temporary West Container ~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Continues from the previous West Container, adds all of the newly created buttons to the container
	//so that they can be viewed and pressed by the user
		Container tempCWest = new Container();
		tempCWest.setLayout(new BoxLayout (BoxLayout.Y_AXIS));
	//Places the buttons within another container, before that container is added to the Form
		tempCWest.add(addAsteroid);
		tempCWest.add(addStation);
		tempCWest.add(addShip);
		tempCWest.add(faster);
		tempCWest.add(slower);
		tempCWest.add(left);
		tempCWest.add(right);
		tempCWest.add(fire);
		tempCWest.add(warp);
		tempCWest.add(addMissiles);
		tempCWest.add(kill);
		tempCWest.add(crash);
		tempCWest.add(collide);
		tempCWest.add(time);
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ West Container ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Container that holds all of the buttons used to create, remove and modify GameObjects in GameWorld
		Container westContainer = new Container();
		westContainer.setLayout(new FlowLayout());
		westContainer = FlowLayout.encloseCenterMiddle(tempCWest);
		westContainer.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(0, 0, 0)));
		this.add(BorderLayout.WEST, westContainer);
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Title Bar ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	//Creates a toolbar to act as the title bar for the GUI
		Toolbar myToolBar = new Toolbar();
		setToolbar(myToolBar);
		myToolBar.setTitle("ASTEROIDS");
		myToolBar.setTitleCentered(true);

	//Add soundCheck to the side menu
		soundCheck.setText("Mute Sound");
		//tempCWest.add(quit);
		myToolBar.addComponentToSideMenu(soundCheck);
		myToolBar.addComponentToSideMenu(newGame);
		myToolBar.addComponentToSideMenu(saveGame);
		myToolBar.addComponentToSideMenu(quit);
		myToolBar.addComponentToSideMenu(readme);
		
		this.show();
	}
}
