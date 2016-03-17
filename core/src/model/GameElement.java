package model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameElement {

	private TextureRegion texture;
	private Vector2 position;
	private Rectangle hitbox;
	
	

	public GameElement(TextureRegion TextureRegion, Vector2 position) {
		super();
		this.texture = TextureRegion;
		this.position = position;
		Rectangle rec = new Rectangle();
		rec.setPosition(position);
		rec.setWidth(this.getTexture().getRegionWidth());
		rec.setHeight(this.getTexture().getRegionHeight());
		this.hitbox = rec;
	}
	public TextureRegion getTexture() {
		return texture;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
		hitbox.setPosition(position);
	}
	public float getWidth()
	{
		return texture.getRegionWidth();
	}
	public float getHeight()
	{
		return texture.getRegionHeight();
	}
	public Rectangle gethitBox()
	{
		return hitbox;
	}
	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}
}
