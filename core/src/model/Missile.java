package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Missile extends GameElement{
	
	private String direction;
	boolean dansEcran;
	
	public Missile(TextureRegion texture, Vector2 position, String direction) {
		super(texture, position);
		this.direction = direction;
		this.dansEcran = true;
	}	

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}	
	
	public boolean isDansEcran() {
		return dansEcran;
	}

	public void setDansEcran(boolean dansEcran) {
		this.dansEcran = dansEcran;
	}

	@Override
	public abstract TextureRegion getTexture();

	
	
	
}
