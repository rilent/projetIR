package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Alien extends GameElement{

	int valeurPoint;
	boolean peutTirer;
	boolean actif;
	int colonne;
	
	public Alien(TextureRegion texture, Vector2 position, int valeurPoint, int colonne) {
		super(texture, position);
		this.valeurPoint = valeurPoint;
		this.actif = true;
		this.colonne = colonne;
	}

	public int getValeurPoint() { 
		return valeurPoint;
	}



	

	public int getColonne() {
		return colonne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	public void setValeurPoint(int valeurPoint) {
		this.valeurPoint = valeurPoint;
	}

	


	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	@Override
	public abstract TextureRegion getTexture();

}
