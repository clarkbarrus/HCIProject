private Pane	buildSettings()
	{
		base = new BorderPane();
		base.setId(“settings-pane”);

		base.setCenter(createSettings());
		base.setTop(createSettingsTitle());
	}

	private Pane 	createSettings()
	{
		GridPane center = new GridPane();
		center.setHgap(10);
  		center.setVgap(10);
   		center.setPadding(new Insets(0, 10, 0, 10));

		//Category in column 1, row 1
		Text numPlayer = new Text(“Number of Players”);
		center.add(numPlayers, 0, 0);

		//Category in column 2, row 1
		Spinner numPlayerSelector = new Spinner(2, 8, 2);
		center.add(numPlayerSelector, 1, 0);

		//Category in column 1, row 2
		Text pointGoal = new Text(“Point Goal”);
		center.add(pointGoal, 0, 1);

		//Category in column 2, row 2
		Spinner pointGoalSelector = new Spinner(1, 999, 100);
		center.add(pointGoalSelector, 1, 1);

		//Category in column 1, row 3
		Text clickCD = new Text(“Click Cooldown”);
		center.add(clickCD, 0, 2);
		
		//Category in column 2, row 3
		Spinner clickCDchooser = new Spinner(0, 60, 0);
		center.add(clickCDChooser, 1, 2);

		//Category in column 1, row 4
		CheckBox finiteClicks = new CheckBox(“Finite Clicks”);
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

		//Category in column 2, row 4
		Spinner finiteClickCount = new Spinner(1, 20, 5);
		finiteClickCount.setEditable(false);
		center.add(finiteClickCount, 1, 3);

		//Category in column 1, row 5
		Text PAD = new Text(“Point Accumulation Speed”);
		center.add(PAD, 0, 4);

		//Category in column 2, row 5
		Spinner PADChooser = new Spinner(1.0, 2.0, 1.3);
		center.add(PADChooser, 1, 4);
		
		return center;
		
	}
