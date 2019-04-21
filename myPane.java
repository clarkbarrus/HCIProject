//******************************************************************************
// Copyright (C) 2019 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Sun Feb 24 18:17:00 2019 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20190203 [weaver]:	Original file.
// 20190220 [weaver]:	Adapted from swingmvc to fxmvc.
//
//******************************************************************************
//
//******************************************************************************

package edu.ou.cs.hci.assignment.prototypeb.pane;

//import java.lang.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.control.*;
import edu.ou.cs.hci.assignment.prototypeb.Controller;

//******************************************************************************

/**
 * The <CODE>MediaPane</CODE> class.
 *
 * @author  Chris Weaver 
 * @version %I%, %G%
 */
public final class MediaPane extends AbstractPane
{
	//**********************************************************************
	// Private Class Members
	//**********************************************************************

	private static final String	NAME = “BackSmash”;
	private static final String	HINT = “Simple Game for Everyone!”;

	//**********************************************************************
	// Private Class Members (Data)
	//**********************************************************************

	private static final String	URL =
		"http://ampliation.org/hci/HPSS.mp4";

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// Layout
	private Button					playButton;
	private Button					stopButton;

	private Label					settingsIcon;
	// Support
	private final char[]		playerKeys = [‘a’, ‘l’, ‘c’, ’n’, ‘r’, ‘u’, ‘1’, ‘0’];

	// Handlers
	private final MouseHandler	mouseHandler;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public MediaPane(Controller controller)
	{
		super(controller, NAME, HINT);

		mouseHandler = new MouseHandler();

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
	}

	// The controller calls this method when it removes a view.
	// Unregister event and property listeners for the nodes in the view.
	public void	terminate()
	{
		settingsIcon.setOnMouseClicked(null);
	}

	// The controller calls this method whenever something changes in the model.
	// Update the nodes in the view to reflect the change.
	public void	update(String key, Object value)
	{
	}

	//**********************************************************************
	// Private Methods (Layout)
	//**********************************************************************

	private Pane	buildPane()
	{
		base = new BorderPane();

		base.setId("media-pane");			// See #media-pane in View.css

		base.setCenter(createScreen());
		base.setTop(createMenu());
		base.setBottom(createPlayers());
		
		return base;
	}

	// Create a node with the media view.
	private Pane	createScreen()
	{
		Button start = new Button("Start");
		start.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t){
				System.out.println("START!");
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
		settingsIcon.setGraphic(createFXIcon(“gear.png”, 128, 128));
		HBox top = new HBox();
		top.setAlignment(Pos.RIGHT);
		top.getChildren().add(settingsIcon);
		return top;

	}
	
	private Pane	createPlayers()
	{
		HBox bottom = new HBox();
		int playerNum = controller.get(“players”);
		for(i = 0; i < playerNum; i++) {
			Label player = new Label();
			player.setGraphic(createFXIcon(“key” + playerKeys[i] + “.png”, 64, 64));
			player.setText(“Player” + (i + 1));
			bottom.getChildren().add(player);
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
			
			}
		}
	}

}

//******************************************************************************
