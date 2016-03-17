package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import utils.TextureFactory;

public class TankAI extends Tank{
	
	public static final String TAG = TankAI.class.getName();


	public TankAI(Vector2 position, Vector2 vitesse) {
		super(TextureFactory.getInstance().getTextureAlien(2), position, vitesse);
	}



	@Override
	public TextureRegion getTexture() {
		return TextureFactory.getInstance().getTextureAlien(2);
	}

}
