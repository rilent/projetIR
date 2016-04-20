package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import utils.Constants;
import utils.TextureFactory;

public class AlienCostaud extends Alien{

	public AlienCostaud(Vector2 position, int colonne) {
		super(TextureFactory.getInstance().getTextureAlien(1), position, (int)  Constants.VALEUR_ALIEN_COSTAUD, colonne);
	}

	@Override
	public TextureRegion getTexture() {
		return TextureFactory.getInstance().getTextureAlien(1);
	}

	
	
}
