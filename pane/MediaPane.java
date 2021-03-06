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

	private static final String	NAME = "Media";
	private static final String	HINT = "Basic Player for Movie Clips";

	//**********************************************************************
	// Private Class Members (Data)
	//**********************************************************************

	private static final String	URL =
		"http://ampliation.org/hci/HPSS.mp4";

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// Layout
	private BorderPane				base;
	private MediaView				mediaView;
	private Button					playButton;
	private Button					stopButton;

	// Support
	private MediaPlayer			mediaPlayer;

	// Handlers
	private final ActionHandler	actionHandler;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public MediaPane(Controller controller)
	{
		super(controller, NAME, HINT);

		actionHandler = new ActionHandler();

		setBase(buildPane());
	}

	//**********************************************************************
	// Public Methods (Controller)
	//**********************************************************************

	// The controller calls this method when it adds a view.
	// Set up the nodes in the view with data accessed through the controller.
	public void	initialize()
	{
		// Keep the media view the same width as the entire scene
		mediaView.fitWidthProperty().bind(mediaView.getScene().widthProperty());

		Media		media = new Media(URL);

		media.setOnError(new Runnable() {
				public void run() {
					System.out.println(media.getError());
				}
			});

		mediaPlayer = new MediaPlayer(media);

		mediaPlayer.setAutoPlay(false);

		mediaPlayer.setOnError(new Runnable() {
				public void run() {
					System.out.println(mediaPlayer.getError());
				}
			});

		mediaView.setMediaPlayer(mediaPlayer);
	}

	// The controller calls this method when it removes a view.
	// Unregister event and property listeners for the nodes in the view.
	public void	terminate()
	{
		mediaView.setMediaPlayer(null);
		mediaPlayer.dispose();

		playButton.setOnAction(null);
		stopButton.setOnAction(null);
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
		MenuBar menuBar = new MenuBar();
		
		Menu menuFile = new Menu("File");
		
		final ToggleGroup groupEffect = new ToggleGroup();
		
		RadioMenuItem optionOne = new RadioMenuItem("Option One");
        optionOne.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                controller.set("menuIndex", 0);
            }
        });        
		optionOne.setSelected(true);
		optionOne.setToggleGroup(groupEffect);
		
		RadioMenuItem optionTwo = new RadioMenuItem("Option One");
        optionTwo.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                controller.set("menuIndex", 1);
            }
        });        
		optionTwo.setSelected(false);
		optionTwo.setToggleGroup(groupEffect);
		
		RadioMenuItem optionThree = new RadioMenuItem("Option One");
        optionThree.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                controller.set("menuIndex", 2);
            }
        });        
		optionThree.setSelected(false);
		optionThree.setToggleGroup(groupEffect);
		
		
		CheckMenuItem checkItem = new CheckMenuItem("Check Item");
        checkItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                if((boolean)controller.get("checkItem"))
					controller.set("checkItem", false);
				else
					controller.set("checkItem", true);
            }
        });        
		checkItem.setSelected(true);
		
		MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
				System.exit(0);
            }
        });        
 
        menuFile.getItems().addAll(optionOne, optionTwo, optionThree, new SeparatorMenuItem(), checkItem, exit);
		
		Menu menuWindow = new Menu("Window");
		Menu menuHelp = new Menu("Help");
		
		menuBar.getMenus().addAll(menuFile, menuWindow, menuHelp);
		
		VBox topMenu = new VBox();
		topMenu.getChildren().add(menuBar);
		
		return topMenu;
	}

	//**********************************************************************
	// Inner Classes (Event Handlers)
	//**********************************************************************

	private final class ActionHandler
		implements EventHandler<ActionEvent>
	{
		public void	handle(ActionEvent e)
		{
			Object	source = e.getSource();

			if (source == playButton)
				mediaPlayer.play();
			else if (e.getSource() == stopButton)
				mediaPlayer.stop();
		}
	}

	private final class MediaErrorHandler
		implements EventHandler<MediaErrorEvent>
	{
		public void	handle(MediaErrorEvent e)
		{
			System.err.println(e);
		}
	}
}

//******************************************************************************
