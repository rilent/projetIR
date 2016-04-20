package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import utils.Constants;
import utils.TextureFactory;

public class AlienRouge extends Alien{

	public AlienRouge(Vector2 position, int colonne) {
		super(TextureFactory.getInstance().getTextureAlien(2), position, (int) Constants.VALEUR_ALIEN_ROUGE, colonne);
	}

	@Override
	public TextureRegion getTexture() {
		return TextureFactory.getInstance().getTextureAlien(2);
	}

	
	
}
