package com.mygdx.spaceinvaders;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.GameScreen;

public class MyGdxspaceinvaders extends Game {
	
	private static final String TAG = MyGdxspaceinvaders.class.getName();
	
	@Override
	public void create () {

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		
		//start game at menu screen
		setScreen(new GameScreen(this));
	}

}
