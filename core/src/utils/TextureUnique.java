package utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureUnique implements ITexturable
{
	private TextureRegion texture;

	public TextureUnique(TextureRegion texture)
	{
		this.texture = texture;
	}
	
	@Override
	public TextureRegion getTexture()
	{
		return this.texture;
	}

}
