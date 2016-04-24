package utils;

public class Constants {

	// visible game world is 5 meters wide
		public static final float VIEWPORT_WIDTH = 5.0f;
		
		//visible game world is 5 meters tall
		public static final float VIEWPORT_HEIGHT = 5.0f;
		
		//GUI Width
		public static final float VIEWPORT_GUI_HEIGHT = 800.0f;
		
		//GUI Height
		//public static final float VIEWPORT_GUI_WIDTH = 480.0f;
		public static final float VIEWPORT_GUI_WIDTH = 800.0f;
		
		//TankVitesse
		public static final float VITESSE_TANK = 5.0f;
	
		//Location of description file for texture atlas
		public static final String TEXTURE_ATLAS_OBJECTS = "images/canyonbunny.atlas";
		
		//Valeur alien moyen
		public static final float VALEUR_ALIEN_MOYEN = 1000.0f;
		
		//Valeur alien costaud
		public static final float VALEUR_ALIEN_COSTAUD = 2000.0f;
		
		//valeur alien petit
		public static final float VALEUR_ALIEN_ROUGE= 5000.0f;
		
		//nombre de colonne maximal pour la gï¿½nï¿½ration d'alien
		public static final float MAXIMUM_COLONNE_ALIEN = 7.0f;
		
		//nombre de ligne maximal pour l a gï¿½nï¿½ration d'alien
		public static final float MAXIMUM_LIGNE_ALIEN = 5.0f;
		
		//nombre de colonne minimum pour la gï¿½nï¿½ration d'alien
		public static final float MINIMUM_COLONNE_ALIEN = 4.0f;
		
		//nombre de ligne minimum pour la gï¿½nï¿½ration d'alien
		public static final float MINIMUM_LIGNE_ALIEN = 3.0f;
		
		//taille texture alien
		public static final float TAILLE_ALIEN = 32.0f;
		
		//taille texture tank
		public static final float TAILLE_TANK = 32.0f;
		
		//taille texture bloc
		public static final float TAILLE_BLOC = 32.0f;
		
		//taille texture bloc
		public static final float HAUTEUR_BLOC = 125.0f;

		public static final float DESCENTE_AVANT_STABILISATION = 32.0f;
	
		// position de depart tank x
		public static final float POSITION_DEPART_TANK_X = VIEWPORT_GUI_WIDTH / 2 - TAILLE_TANK / 2;
	
		// position depart tank y
		public static final float POSITION_DEPART_TANK_Y = 16.0f;
	
		// vitesse Missile alien
		public static final float VIT_MISSILE_ALIEN = 3.0f;
	
		// vitesse Missile tank
		public static final float VIT_MISSILE_TANK = 8.0f;
	
		//taille d'un missile
		public static final float TAILLE_MISSILE = 24.0f;
		
		//taille d'un missile
		public static final float LARGEUR_MISSILE = 8.0f;
		
		//durï¿½e entre chaque vague de missile
		public static final float DELAI_MISSILE = 24.0f;
		
		//hauteur minimum d'un arbre
		public static final int HAUTEUR_MIN_ARBRE = 2;
		
		//hauteur maximum d'un arbre
		public static final int HAUTEUR_MAX_ARBRE = 5; // faire + 1 pour avoir al vraie hauteur avec le noeud root
		
		//nombre de render max (pour affichage d'un nouveau missile)
		public static final int MAX_RENDER = 40;
		
		//nombre d'individu que l'on prends pour chaque itération du tournoi
		public static final int N_TOURNOI = 3;
}
