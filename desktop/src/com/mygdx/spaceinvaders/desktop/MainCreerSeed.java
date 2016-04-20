package com.mygdx.spaceinvaders.desktop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import utils.Constants;

public class MainCreerSeed {

	public static void main (String[] arg) {
		
		String directionDepart = "";

        int droiteOugauche = (int)(Math.random() * ((1 - 0) + 1));
		
        System.out.println("num ) " +droiteOugauche);
        if (droiteOugauche==0)
			directionDepart = "droite";
		else
			directionDepart="gauche";
        
        int nombredeColonneAlien = (int) (Constants.MINIMUM_COLONNE_ALIEN + (int)(Math.random() * ((Constants.MAXIMUM_COLONNE_ALIEN - Constants.MINIMUM_COLONNE_ALIEN) + 1)));

	    
	    int nombredeLigneAlien = (int) (Constants.MINIMUM_LIGNE_ALIEN + (int)(Math.random() * ((Constants.MAXIMUM_LIGNE_ALIEN - Constants.MINIMUM_LIGNE_ALIEN) + 1)));
	    
	    //generationAlien(nombredeLigneAlien,nombredeColonneAlien);
	    
	    int nombreDeCube = (int) (3 + (int)(Math.random() * ((4 - 3) + 1))); // 3 ou paquets de blocs
      
        ArrayList<Integer> ordonnancementDeQuiTire = new ArrayList<Integer>();
		int nbMaxDeColonnes = 7;
        for(int i = 0; i<100; i++)
        {
            Random rand = new Random();
            int nombreAleatoire = rand.nextInt(nbMaxDeColonnes - 1 + 1) + 1;
            ordonnancementDeQuiTire.add(nombreAleatoire);
        }
	
    	File f = new File("seed_parties.txt");
        System.out.println("Chemin absolu du fichier : " + f.getAbsolutePath());

        String caracteristiquesPartie = "" + directionDepart + "-" 
                                                                        + nombredeLigneAlien + "-"
                                                                        + nombredeColonneAlien + "-"
                                                                        + nombreDeCube ; 
        
        for (int i = 0; i < ordonnancementDeQuiTire.size(); i++) {
        	caracteristiquesPartie += "-" + ordonnancementDeQuiTire.get(i);
		}
        
        try
        {
            FileWriter fw = new FileWriter (f, true);
                  
                fw.write (caracteristiquesPartie);
                fw.write ("\r\n");
                     
            fw.close();
        }
        catch (IOException exception)
        {
            System.out.println ("Erreur lors de la lecture "+ exception.getMessage());
        }
           
            
    }
		
		
	
	
}
