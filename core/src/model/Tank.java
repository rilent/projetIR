package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Tank extends GameElement{

	private Vector2 vitesse;

	
	public Tank(TextureRegion texture, Vector2 position, Vector2 vitesse)
	{
		super(texture, position);
		this.vitesse = vitesse;
	}	
	
	public Vector2 getVitesse() {
		return vitesse;
	}

	public void setVitesse(Vector2 vitesse) {
		this.vitesse = vitesse;
	}
	
	
	
}
