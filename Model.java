//******************************************************************************
// Copyright (C) 2019 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Mon Feb 25 13:36:49 2019 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20190203 [weaver]:	Original file.
// 20190220 [weaver]:	Adapted from swingmvc to fxmvc.
//
//******************************************************************************
//
//******************************************************************************

package edu.ou.cs.hci.assignment.prototypeb;

import java.util.ArrayList;
import java.util.Arrays;
//import java.lang.*;
import java.util.HashMap;
import javafx.application.Platform;

//******************************************************************************

/**
 * The <CODE>Model</CODE> class.
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Model
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// Master of the program, manager of the data, mediator of all updates
	private final Controller				controller;

	// Easy, extensible way to store multiple simple, independent parameters
	private final HashMap<String, Object>	properties;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public Model(Controller controller)
	{
		this.controller = controller;

		properties = new HashMap<String, Object>();

		// Game Parameters
		properties.put("players",		2);
		properties.put("gameState",		0);
		properties.put("playerPoints",	new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0)));

		
		// Parameters accessed and/or modified by GalleryPane controls
		properties.put("lions",		Boolean.FALSE);
		properties.put("tigers",		Boolean.TRUE);
		properties.put("bears",		Boolean.FALSE);
		properties.put("surprised",	Boolean.FALSE);
		properties.put("flavor",		"chocolate");
		properties.put("decimal",		5.0);
		properties.put("integer",		5);
		properties.put("comment",		"I am buggy. Fix me!");
		properties.put("string",		"123abc");
		properties.put("item",			"Naptime,bed.png");
		properties.put("tool",			"Book,bool.png");

		// Parameters accessed and/or modified by Image/Float/CyclePanes
		properties.put("itemIndex",	0);
		properties.put("menuIndex",	0);
		properties.put("checkItem",	true);
	}

	//**********************************************************************
	// Public Methods (Controller)
	//**********************************************************************

	public Object	getValue(String key)
	{
		return properties.get(key);
	}

	public void	setValue(String key, Object value)
	{
		if (properties.containsKey(key) &&
			properties.get(key).equals(value))
		{
			System.out.println("  model: value not changed");
			return;
		}

		Platform.runLater(new Updater(key, value));
	}

	public void	trigger(String name)
	{
		System.out.println("  model: (not!) calculating function: " + name);
	}

	//**********************************************************************
	// Inner Classes
	//**********************************************************************

	private class Updater
		implements Runnable
	{
		private final String	key;
		private final Object	value;

		public Updater(String key, Object value)
		{
			this.key = key;
			this.value = value;
		}

		public void	run()
		{
			properties.put(key, value);
			controller.update(key, value);
		}
	}
}

//******************************************************************************
