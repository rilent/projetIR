package com.mygdx.spaceinvaders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.spaceinvaders.MyGdxspaceinvaders;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 800;	//800
		config.width = 800;		//480
		
		new LwjglApplication(new MyGdxspaceinvaders(), config);
	}
}
