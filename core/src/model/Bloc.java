package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import utils.TextureFactory;

public class Bloc extends GameElement{

	boolean detruit = false;
	
	public Bloc(Vector2 position) {
		super(TextureFactory.getInstance().getTextureAlien(3), position); //penser a changer
		
	}



	@Override
	public TextureRegion getTexture() {
		return TextureFactory.getInstance().getTextureAlien(3);
	}



	public boolean isDetruit() {
		return detruit;
	}



	public void setDetruit(boolean detruit) {
		this.detruit = detruit;
	}

	
	
}
