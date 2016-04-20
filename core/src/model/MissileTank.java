package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import utils.TextureFactory;

public class MissileTank extends Missile{

	public MissileTank(Vector2 position) {
		super(TextureFactory.getInstance().getTextureAlien(5), position, "Haut");
	}

	@Override
	public TextureRegion getTexture() {
		return TextureFactory.getInstance().getTextureAlien(5);
	}


	
	
}
