
//******************************************************************************
//Copyright (C) 2019 University of Oklahoma Board of Trustees.
//******************************************************************************
//Last modified: Sun Feb 24 18:17:00 2019 by Chris Weaver
//******************************************************************************
//Major Modification History:
//
//20190203 [weaver]:	Original file.
//20190220 [weaver]:	Adapted from swingmvc to fxmvc.
//
//******************************************************************************
//
//******************************************************************************

package edu.ou.cs.hci.assignment.prototypeb;

//import java.lang.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;

import edu.ou.cs.hci.assignment.prototypeb.Controller;

//******************************************************************************



/**
* The <CODE>Pane</CODE> class.
*
* @author  Chris Weaver
* @version %I%, %G%
*/
public class myPane  extends AbstractPane {
	//**********************************************************************
		// Private Class Members
		//**********************************************************************

		private static final String	NAME = "BackSmash";
		private static final String	HINT = "Simple Game for Everyone!";
		private static final Insets PADDING = new Insets(10,10,10,10);

		//**********************************************************************
		// Private Class Members (Data)
		//**********************************************************************

		private static final String	URL =
			"http://ampliation.org/hci/HPSS.mp4";

		//**********************************************************************
		// Private Members
		//**********************************************************************

		// Layout
		private Label					settingsIcon;
		private Label					exitButton;
		private Pane						mainMenu;
		private Pane						gamePane;
		private Pane						settingsPane;
		private StackPane				mainPane;
	
		// Support
		private final char[]		playerKeys = {'a', 'l', 'c', 'n', 'r', 'u', '1', '0'};

		// Handlers
		private final MouseHandler	mouseHandler;

		//**********************************************************************
		// Constructors and Finalizer
		//**********************************************************************

		public myPane(Controller controller)
		{
			super(controller, NAME, HINT);

			mouseHandler = new MouseHandler();
			settingsIcon = new Label();

			setBase(buildPane());
		}

		//**********************************************************************
		// Public Methods (Controller)
		//**********************************************************************

		// The controller calls this method when it adds a view.
		// Set up the nodes in the view with data accessed through the controller.
		public void	initialize()
		{
			settingsIcon.setOnMouseClicked(mouseHandler);
			exitButton.setOnMouseClicked(mouseHandler);

		}

		// The controller calls this method when it removes a view.
		// Unregister event and property listeners for the nodes in the view.
		public void	terminate()
		{
			settingsIcon.setOnMouseClicked(null);
			exitButton.setOnMouseClicked(null);
		}

		// The controller calls this method whenever something changes in the model.
		// Update the nodes in the view to reflect the change.
		public void	update(String key, Object value)
		{
			if (key.equals("gameState")) {
				if((int)value == 0) { //Main menu
					mainMenu.setVisible(true);
					settingsPane.setVisible(false);
				    gamePane.setVisible(false);
				}
				if((int)value == 1) { //Settings
					mainMenu.setVisible(false);
					settingsPane.setVisible(true);
				    gamePane.setVisible(false);
				}
				if((int)value == 2) { //Game menu
					mainMenu.setVisible(false);
					settingsPane.setVisible(false);
				    gamePane.setVisible(true);
				}
			}
		}

		//**********************************************************************
		// Private Methods (Layout)
		//**********************************************************************

		private Pane	buildPane() 
		{
			mainPane = new StackPane();
			mainMenu = buildMainMenu();
			settingsPane = buildSettings();
			
			gamePane = buildGame();
		    mainPane.getChildren().addAll(gamePane, mainMenu, settingsPane);
		    settingsPane.setVisible(false);
		    gamePane.setVisible(false);
			
			return mainPane;
		}
		
		private BorderPane	buildMainMenu()
		{
			BorderPane base = new BorderPane();

			base.setId("media-pane");			// See #media-pane in View.css

			base.setCenter(createScreen());
			base.setTop(createMenu());
			base.setBottom(createPlayers());

			
			return base;
		}
		
		private BorderPane buildGame()
		{
			BorderPane base = new BorderPane();

			base.setId("game-pane");			// See #media-pane in View.css

			base.setBottom(createGamePlayers());

			return base;		}

		private BorderPane	buildSettings()
		{
			BorderPane base = new BorderPane();
			base.setId("settings-pane");

			base.setCenter(createSettings());
			base.setTop(createSettingsTitle());
			return base;
		}

		private Pane 	createSettings()
		{
			GridPane center = new GridPane();
			center.setHgap(10);
	  		center.setVgap(10);
	   		center.setPadding(new Insets(0, 10, 0, 10));

			//Category in column 1, row 1
			Text numPlayer = new Text("Number of Players");
			center.add(numPlayer, 0, 0);

			//Category in column 2, row 1
			Spinner<Integer> numPlayerSelector = new Spinner<Integer>(2, 8, 2);
			center.add(numPlayerSelector, 1, 0);

			//Category in column 1, row 2
			Text pointGoal = new Text("Point Goal");
			center.add(pointGoal, 0, 1);

			//Category in column 2, row 2
			Spinner<Integer> pointGoalSelector = new Spinner<Integer>(1, 999, 100);
			center.add(pointGoalSelector, 1, 1);

			//Category in column 1, row 3
			Text clickCD = new Text("Click Cooldown");
			center.add(clickCD, 0, 2);
			
			//Category in column 2, row 3
			Spinner<Integer> clickCDchooser = new Spinner<Integer>(0, 60, 0);
			center.add(clickCDchooser, 1, 2);

			//Category in column 2, row 4
			Spinner<Integer> finiteClickCount = new Spinner<Integer>(1, 20, 5);
			finiteClickCount.setEditable(false);
			center.add(finiteClickCount, 1, 3);
			
			//Category in column 1, row 4
			CheckBox finiteClicks = new CheckBox("Finite Clicks");
			finiteClicks.setSelected(false);
			finiteClicks.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t){
					if(finiteClickCount.isEditable())
						finiteClickCount.setEditable(false);
					else
						finiteClickCount.setEditable(true);
				}
			});
			center.add(finiteClicks, 0, 3);



			//Category in column 1, row 5
			Text PAD = new Text("Point Accumulation Speed");
			center.add(PAD, 0, 4);

			//Category in column 2, row 5
			Spinner<Double> PADChooser = new Spinner<Double>(1.0, 2.0, 1.3, 0.1);
			center.add(PADChooser, 1, 4);
			
			return center;
			
		}
		
		private Pane	createSettingsTitle()
		{
			GridPane title = new GridPane();
			title.setHgap(10);
	  		title.setVgap(10);
	   		title.setPadding(new Insets(0, 10, 0, 10));

			//Category in row 1 column 2
			Text settingsTitle = new Text("Settings");
			settingsTitle.setTextAlignment(TextAlignment.CENTER);
			title.add(settingsTitle, 1, 0);

			//Category in row 1 column 3
			exitButton = new Label();
			exitButton.setGraphic(createFXIcon("gear.png", 32, 32));
			exitButton.setAlignment(Pos.TOP_RIGHT);
			title.add(exitButton, 2, 0);
			
			return title;
		}
		
		// Create a node with the media view.
		private Pane	createScreen()
		{
			Button start = new Button("Start");
			start.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t){
					controller.set("gameState", 2);
				}
			});
			start.setPrefSize(500, 300);
			HBox center = new HBox();
			center.setAlignment(Pos.CENTER);
			center.getChildren().add(start);
			return center;
		}

		// Create a node with various playback controls.
		private Pane	createMenu()
		{
			settingsIcon.setGraphic(createFXIcon("gear.png", 64, 64));
			HBox top = new HBox();
			top.setAlignment(Pos.CENTER_RIGHT);
			top.getChildren().add(settingsIcon);
			top.setPadding(PADDING);
			return top;

		}

		private Pane	createPlayers()
		{
			HBox bottom = new HBox();
			int playerNum = (int)controller.get("players");
			for(int i = 0; i < playerNum; i++) {
				Label player = new Label();
				player.setGraphic(createFXIcon("key" + playerKeys[i] + ".png", 32, 32));
				player.setText("Player " + (i + 1));
				player.setGraphicTextGap(10);
				player.setContentDisplay(ContentDisplay.TOP);
				player.setPadding(PADDING);
				bottom.getChildren().add(player);
			}
			bottom.setAlignment(Pos.CENTER);
			return bottom;
		}
		
		private Pane	createGamePlayers()
		{
			HBox bottom = new HBox();
			int playerNum = (int)controller.get("players");
			ArrayList<Integer> playerPoints = (ArrayList<Integer>)controller.get("playerPoints");

			for(int i = 0; i < playerNum; i++) {
				VBox box = new VBox();
				Label points = new Label();
				points.setText(playerPoints.get(i).toString());
				Label player = new Label();
				player.setGraphic(createFXIcon("key" + playerKeys[i] + ".png", 32, 32));
				player.setText("Player " + (i + 1));
				player.setGraphicTextGap(10);
				player.setContentDisplay(ContentDisplay.TOP);
				player.setPadding(PADDING);
				box.getChildren().addAll(points, player);
				bottom.getChildren().add(box);
			}
			bottom.setAlignment(Pos.CENTER);
			return bottom;
		}

		//**********************************************************************
		// Inner Classes (Event Handlers)
		//**********************************************************************


		private final class MouseHandler 
			implements EventHandler<MouseEvent>
		{
			public void handle(MouseEvent e){
				Object source = e.getSource();
				if(source == settingsIcon){
					controller.set("gameState", 1);
				}
				if(source == exitButton){
					controller.set("gameState", 0);
				}
			}
		}
}
