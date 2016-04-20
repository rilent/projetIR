package com.mygdx.spaceinvaders;

import java.util.ArrayList;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import screens.GameScreen;
import utils.Individu;

public class MyGdxspaceinvaders extends Game {
	
	private static final String TAG = MyGdxspaceinvaders.class.getName();
	
	@Override
	public void create () {

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		
		//start game at menu screen
		
		
		setScreen(new GameScreen(this,0,new ArrayList<Individu>()));
		
	}

}
