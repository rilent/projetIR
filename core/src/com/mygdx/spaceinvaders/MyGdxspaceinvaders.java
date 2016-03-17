package com.mygdx.spaceinvaders;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.GameScreen;

public class MyGdxspaceinvaders extends Game {
	
	private static final String TAG = MyGdxspaceinvaders.class.getName();
	
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		/*batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		*/
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		
		//start game at menu screen
		setScreen(new GameScreen(this));
	}

}
