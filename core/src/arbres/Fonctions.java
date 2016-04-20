package arbres;

import utils.Constants;
import model.Alien;
import model.Bloc;
import model.GameElement;
import model.Missile;
import model.MissileTank;
import model.World;

public class Fonctions implements ElementNoeud{

	String key;
	String pourAffichage;
	
	public Fonctions(EComparaisonFeatures c) {
		super();
		this.key = c.toString();
		this.pourAffichage = c.getAffichage();
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	public static boolean TankxSupAlienPlusProchex(World w)
	{
		
    	Alien lePlusProche = null;
    	//Alien lePlusProche = getListeAlien().get(0);
    	//double plusPetiteDistance = distanceEntreDeuxGameElement(getTank(), lePlusProche);
    	double plusPetiteDistance = 1000000;
    	double distanceTest = 0;
    	
    	if (!(w.getListeAlien().size()==0)) {
        	for(Alien m : w.getListeAlien())
        	{
        		distanceTest = distanceEntreDeuxGameElement(w.getTank(), m);
        		if (distanceTest< plusPetiteDistance) {
        			plusPetiteDistance = distanceTest;
        			lePlusProche= m;
        		}
        	}  
    	}
    	else // si il n'ya plus d'Alien cad partie terminee
    		return false;
    	
    	// sert a rien surement
    	if (lePlusProche == null)
    		return true;
    	
    	return w.getTank().getPosition().x > lePlusProche.getPosition().x;        
	}
	
	
	
	public static boolean TankxSupProtectionPlusProche(World w)
	{
    	Bloc lePlusProche = null;
    	//Bloc lePlusProche = getListeBloc().get(0);
    	double plusPetiteDistance = 1000000;
    	//double plusPetiteDistance = distanceEntreDeuxGameElement(getTank(), lePlusProche);
    	double distanceTest = 0;
    	
    	if (!(w.getListeBloc().size()==0)) {
        	for(Bloc m : w.getListeBloc())
        	{
        		distanceTest = distanceEntreDeuxGameElement(w.getTank(), m);
        		if (distanceTest< plusPetiteDistance) {
        			plusPetiteDistance = distanceTest;
        			lePlusProche= m;
        		}
        	}  
        	return w.getTank().getPosition().x > lePlusProche.getPosition().x; 
    	}
    	else // si il n'y a plus de bloc
    		return false;
    		    	       	
    }
	
	
	public static boolean TankxSupMissilePlusProche(World w)
	{
    	
    	Missile lePlusProche = null;
    	
    	if (w.getListeMissile().size()==0) // si il n'y a pas de missile
    		return false;
    	/*
    	if (getListeMissile().get(0) instanceof MissileTank)
    		lePlusProche = getListeMissile().get(1);
    	else
    		lePlusProche = getListeMissile().get(0);
    	*/
    	double plusPetiteDistance = 1000000;
    	
    	//double plusPetiteDistance = distanceEntreDeuxGameElement(getTank(), lePlusProche);
    	double distanceTest = 0;
    	
    	if (!(w.getListeMissile().size()==0)) {
        	for(Missile m : w.getListeMissile())
        	{
        		if (!(m instanceof MissileTank)) {
	        		distanceTest = distanceEntreDeuxGameElement(w.getTank(), m);
	        		if (distanceTest< plusPetiteDistance) {
	        			plusPetiteDistance = distanceTest;
	        			lePlusProche= m;
	        		}
        		}
        	}  
    	}
    	
    	//if (plusPetiteDistance == 1000000)
    	//	return null;
    	
    	return w.getTank().getPosition().x > lePlusProche.getPosition().x;        	
    }

	public static double distanceEntreDeuxGameElement(GameElement g1, GameElement g2)
    {
    	return Math.sqrt( Math.pow(g2.getPosition().x - g1.getPosition().x, 2) + Math.pow(g2.getPosition().y - g1.getPosition().y, 2) );
    }
	
	
	public static boolean AlienLePlusBasEstAuDessusDeLaMoitieDuTerrain(World w)
	{
		boolean reponse = true ;
		
		double moitie = (Constants.VIEWPORT_GUI_HEIGHT/2);
		double distanceTest = 0;
		if (!(w.getListeAlien().size()==0)) {
        	for(Alien m : w.getListeAlien())
        	{
        		distanceTest = m.getPosition().x - moitie;
        		if (distanceTest<0) {
        			reponse = false;
        		}
        	}  
    	}
		
		//System.out.println("distance par rapport a la moitie = " + (distanceTest+ moitie) + " - " + moitie + " = " +distanceTest);
		
		return reponse;
	}
	
	

	@Override
	public String toStringMethod() {
		return key;
	}


	@Override
	public String toStringAffichage() {
		return ""+pourAffichage;
	}
	
	
	
}
