package utils;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureFactory 
{
	private HashMap<Class<?>, ITexturable> textures;
	private HashMap<Integer,ITexturable> textureAlien;
	private static TextureFactory instance = null;
	private HashMap<Integer,ITexturable> textureTank;
	private TextureAtlas atlas;
	
	private TextureFactory()
	{
		atlas = new TextureAtlas(Gdx.files.internal("sprites/spaceInvaders.atlas"));
		textures = new HashMap<Class<?>, ITexturable>();
		textureAlien = new HashMap<Integer, ITexturable>();
		//textures.put(Fond.class, new TextureUnique(new Texture(Gdx.files.internal("data/fond.png"))));
	    
//		textureLettres.put(0, new TextureUnique(atlas.findRegion("momba")));
//		textureLettres.put(1, new TextureUnique(atlas.findRegion("momba")));
//	    textureLettres.put(2, new TextureUnique(atlas.findRegion("plante")));
//	    textureLettres.put(3, new TextureUnique(atlas.findRegion("plante")));
//	    textureLettres.put(4, new TextureUnique(atlas.findRegion("fleur")));
//	    textureLettres.put(5, new TextureUnique(atlas.findRegion("fleur")));
//	    textureLettres.put(6, new TextureUnique(atlas.findRegion("calamar")));
//	    textureLettres.put(7, new TextureUnique(atlas.findRegion("calamar")));
//	    textureLettres.put(8, new TextureUnique(atlas.findRegion("oeufhaut")));
//	    textureLettres.put(9, new TextureUnique(atlas.findRegion("oeufbas")));
		
		//avirerplustard
		textureAlien.put(0, new TextureUnique(atlas.findRegion("alienmoyen32")));
		textureAlien.put(1, new TextureUnique(atlas.findRegion("aliencostaud32")));
		//textureAlien.put(2, new TextureUnique(atlas.findRegion("calamar")));
		
		
		textureAlien.put(2, new TextureUnique(atlas.findRegion("tank32")));
		textureAlien.put(3, new TextureUnique(atlas.findRegion("bloc232")));
		textureAlien.put(4, new TextureUnique(atlas.findRegion("laserrouge")));
		textureAlien.put(5, new TextureUnique(atlas.findRegion("laservert")));
	    
	    
	}
	
	public static TextureFactory getInstance()
	{
		if (instance == null)
		{
		      instance = new TextureFactory();
		}
		return instance;
	}
	
	public ITexturable getITexturable(Class <?> c)
	{
		return textures.get(c);
	}
	
//	public TextureRegion getTexture (Class <?> c)
//	  {
//	    return textures.get(c).getTexture ();
//	  }
	
	public TextureRegion getTextureAlien (int numeroLettre)
	  {
	    return textureAlien.get(numeroLettre).getTexture();
	  }
	public TextureRegion getTextureTank (int num)
	  {
	    return textureTank.get(num).getTexture();
	  }
}
