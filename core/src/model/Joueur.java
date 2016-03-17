package model;

public abstract class Joueur {

	Tank t;

	
	
	public Joueur(Tank t) {
		super();
		this.t = t;
	}

	public Tank getT() {
		return t;
	}

	public void setT(Tank t) {
		this.t = t;
	}
	
	
	
}
