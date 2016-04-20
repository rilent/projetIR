package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

import utils.Constants;

import com.badlogic.gdx.math.Vector2;

import arbres.BinaryTree;
import arbres.Node;
import arbres.TreePrinter;

public class World {

        
        private int scorePartie;
        //private WorldListener listener;
        ArrayList<Alien> listeAlien; //sauvegarde ds le seed
        ArrayList<MissileAlien> listeMissileAlien;
        ArrayList<Missile> listeMissile;
        ArrayList<Bloc> listeBloc;
        ArrayList<Integer> seed;
        ArrayList<GameElement> listeGameElement;
        int nombreDeColonnes;
        int nombredeLignes;
        int nombredeCubes;
        Tank tank;
        int scoreTemps;
        boolean partieTermine;
        int cpt_render = 0; // compter le nombre de fois qu'un render est appel�
        boolean AI; //sauvegarde seed
        String directionDepartAlien; //sauvegarde dans le seed
        String directionAlienCourante;
        String directionAlienSauvegarde;
        private ArrayList<Integer> ordonnancementDeQuiTire;
        private float delaiSwitchColonnes = 2;
        private boolean premierPassage = false; //pour g�nerer le seed la premiere fois si c'est AI
    	BinaryTree tree = null; // Tree of the current game, reminder : any games got a different tree
        
        private boolean peutTirer = true;
        
        
        public World(ArrayList<Integer> seed) {
                this.seed = seed;
                //restauration(seed);
        }

        public World(Joueur j, Node b) //si b est null, c'est a dire que l'on en est à la première population
        {	
    		//génération de l'arbre
        	if(b == null)
        	{
        		tree = BinaryTree.generationTreeAleatoire();
        	}
        	else
        	{
        		tree = new BinaryTree(b);
        	}
    		//affichage dans les deux modes de l'arbre :
    		TreePrinter.print(tree.root);
    		
    		try {
    			tree.root.printTree(new OutputStreamWriter(System.out));
    		} catch (IOException e) {
    			e.printStackTrace();
    		}

        	
            tank = j.getT();
            listeBloc = new ArrayList<Bloc>();
            listeMissile = new ArrayList<Missile>();

            
            /*
            listeMissileAlien = new ArrayList<MissileAlien>();
            listeAlien = new ArrayList<Alien>();
            */
            
            listeGameElement = new ArrayList<GameElement>();
            ordonnancementDeQuiTire = new ArrayList<Integer>();
            
        	if(j instanceof JoueurHumain || (j instanceof JoueurAI && premierPassage))
        	{
        		initAleatoire(j.getT());
        		// mettre premierPassage a false car on a maintenant un seed pour les premiers world
        		//premierPassage = false;
        		//World w1 = new World(new JoueurAI(new TankJoueur(new Vector2(Constants.POSITION_DEPART_TANK_X,Constants.POSITION_DEPART_TANK_Y), new Vector2(1,0))));				
        		//w1.initAI(recupererLastSeed());
        	}
        	else
        	{        		
        		//direction...
        		//generationAlien(...)
        		//generationBloc(...)
        		//String recup = recupererSeed(numeroSeed)
        		//ordonnancementDeQuiTire.add(1erevaleur)
        		
        		this.initAI(recupererLastSeed()); 
        		//System.out.println(recupererLastSeed());
        		//System.out.println(recupererLastSeed());
        		
        		/*
        		ordonnancementDe100Colonnes(this.getNombreDeColonnes());
        		generationAlien(this.nombredeLignes,this.nombreDeColonnes);
        		generationBloc(this.nombredeCubes);
    	        listeGameElement.addAll(listeMissile);
        		*/
        		
        	}
        	listeGameElement.add(tank);
        	scorePartie = 0;
        }        
        
        private void initAI(String ligneSeed) {
			//exemple : droite-4-7-4-tous-les-chiffres-ordonnancement	
        	String delimitation = "[-]";
        	String[] infosLigneSeed = ligneSeed.split(delimitation);
        	// direction_depart-nb_ligne-nb_colonnes-nb_cube
        	
        	/*
        	System.out.println(infosLigneSeed[0]);
        	System.out.println(infosLigneSeed[1]);
        	System.out.println(infosLigneSeed[2]);
        	System.out.println(infosLigneSeed[3]);
        	*/
        	
        	listeMissileAlien = new ArrayList<MissileAlien>();
            listeAlien = new ArrayList<Alien>();
        	
        	this.directionAlienCourante=infosLigneSeed[0];
        	this.directionAlienSauvegarde=infosLigneSeed[0];
        	this.directionDepartAlien=infosLigneSeed[0];	
        	
        	this.nombredeLignes= Integer.parseInt(infosLigneSeed[1]);
        	this.nombreDeColonnes= Integer.parseInt(infosLigneSeed[2]);
        	this.nombredeCubes= Integer.parseInt(infosLigneSeed[3]);
        	
        	partieTermine = false;
            if(tank instanceof TankAI)
            {
                    AI = true;
            }
            if(tank instanceof TankJoueur)
            {
                    AI = false;
            }
        		
        	for (int i = 4; i < infosLigneSeed.length; i++) {
            	this.ordonnancementDeQuiTire.add(Integer.parseInt(infosLigneSeed[i]));
            	//System.out.println(infosLigneSeed[i]);
        	}
        	
        	generationAlien(this.nombredeLignes,this.nombreDeColonnes);
        	generationBloc(this.nombredeCubes);
        	listeGameElement.addAll(listeMissile);
			
		}

		public void initAleatoire(Tank t)
        {
            partieTermine = false;
            if(t instanceof TankAI)
            {
                    AI = true;
            }
            if(t instanceof TankJoueur)
            {
                    AI = false;
            }
            
            String directionDepart = "";
            
            //aleatoire entre 1 et 0 pour determiner si gauche ou droite
            Random rand = new Random();
            int randomNum = rand.nextInt((1 - 0) + 1) + 0;            
            if (randomNum==0)
                    directionDepart = "gauche";
            else if (randomNum==1)
                    directionDepart = "droite";        
        
        
        	directionDepartAlien = directionDepart;
            directionAlienCourante = directionDepartAlien;
            directionAlienSauvegarde = directionAlienCourante;
            
            //Vector2 positionTank = new Vector2();
            //positionTank.set(Constants.POSITION_DEPART_TANK_X, Constants.POSITION_DEPART_TANK_Y);
            
            
            listeMissileAlien = new ArrayList<MissileAlien>();
            listeAlien = new ArrayList<Alien>();
            
            int nombredeColonneAlien = (int) (Constants.MINIMUM_COLONNE_ALIEN + 
                            (int)(Math.random() * ((Constants.MAXIMUM_COLONNE_ALIEN - Constants.MINIMUM_COLONNE_ALIEN) + 1)));
            
            nombreDeColonnes = nombredeColonneAlien;
            int nombredeLigneAlien = (int) (Constants.MINIMUM_LIGNE_ALIEN + 
            (int)(Math.random() * ((Constants.MAXIMUM_LIGNE_ALIEN - Constants.MINIMUM_LIGNE_ALIEN) + 1)));
            
            nombredeLignes = nombredeLigneAlien;
            
            generationAlien(nombredeLigneAlien,nombredeColonneAlien);
            
            
            int nbCube = (int) (3 + 
                            (int)(Math.random() * ((4 - 3) + 1))); // 3 ou paquets de blocs
                    
            nombredeCubes= nbCube;
            generationBloc(nbCube);          
                
	        listeGameElement.addAll(listeMissile);
	        ordonnancementDe100Colonnes(nombredeColonneAlien);
	        
	        sauvegarde(directionDepart,nombredeLigneAlien, nombredeColonneAlien, nbCube, this.ordonnancementDeQuiTire);  
	        
	       // String seeddd = recupererSeed(1);
	       // System.out.println(" voila ton seed : " + seeddd);
	        
        }
        
        public void sauvegarde(String directionDepart, int nombredeLigneAlien, int nombredeColonneAlien, int nombreDeCube, ArrayList<Integer> ordonnancement ) {
            
        	File f = new File("seed_parties.txt");
            System.out.println("Chemin absolu du fichier : " + f.getAbsolutePath());

            String caracteristiquesPartie = "" + directionDepart + "-" 
                                                                            + nombredeLigneAlien + "-"
                                                                            + nombredeColonneAlien + "-"
                                                                            + nombreDeCube ; 
            
            for (int i = 0; i < ordonnancement.size(); i++) {
            	caracteristiquesPartie += "-" + ordonnancement.get(i);
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
        
        public String recupererSeed(int numeroSeed) {
                String sortie = null;
                int i = 1;
                try {
                        File file = new File("seed_parties.txt");
                        FileReader fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        StringBuffer stringBuffer = new StringBuffer();
                        String line;
                        while (((line = bufferedReader.readLine()) != null) && (i<=numeroSeed)) {
                                stringBuffer.append(line);
                                stringBuffer.append("\n");
                                i++;
                                sortie = line;
                        }
                        //sortie = line;
                        fileReader.close();
                       // System.out.println("Contents of file:");
                       // System.out.println(stringBuffer.toString());
                } catch (IOException e) {
                        e.printStackTrace();
                }
                
                return sortie;
        }
        
        public String recupererLastSeed() {
            String sortie = null;
            int i = 0;
            try {
                    File file = new File("seed_parties.txt");
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while (((line = bufferedReader.readLine()) != null)) {
                            stringBuffer.append(line);
                            stringBuffer.append("\n");
                            i++;
                            sortie = line;
                    }
                    //sortie = line;
                    fileReader.close();
                   // System.out.println("Contents of file:");
                   // System.out.println(stringBuffer.toString());
            } catch (IOException e) {
                    e.printStackTrace();
            }
            
            System.out.println("la derniere ligne est la ligne : " +i);
            return sortie;
        }
       
        //sauvegarde si jamais mais normalement tout est dans fonctions maintenant
        /*
        
        public double distanceEntreDeuxGameElement(GameElement g1, GameElement g2)
        {
        	return Math.sqrt( Math.pow(g2.getPosition().x - g1.getPosition().x, 2) + Math.pow(g2.getPosition().y - g1.getPosition().y, 2) );
        }
        
        public Alien AlienLePlusProcheDuTank() {
        	Alien lePlusProche = null;
        	//Alien lePlusProche = getListeAlien().get(0);
        	//double plusPetiteDistance = distanceEntreDeuxGameElement(getTank(), lePlusProche);
        	double plusPetiteDistance = 1000000;
        	double distanceTest = 0;
        	
        	if (!(getListeAlien().size()==0)) {
	        	for(Alien m : getListeAlien())
	        	{
	        		distanceTest = distanceEntreDeuxGameElement(getTank(), m);
	        		if (distanceTest< plusPetiteDistance) {
	        			plusPetiteDistance = distanceTest;
	        			lePlusProche= m;
	        		}
	        	}  
        	}
        	return lePlusProche;        	
        }
        
        public Bloc BlocLePlusProcheDuTank() {
        	Bloc lePlusProche = null;
        	//Bloc lePlusProche = getListeBloc().get(0);
        	double plusPetiteDistance = 1000000;
        	//double plusPetiteDistance = distanceEntreDeuxGameElement(getTank(), lePlusProche);
        	double distanceTest = 0;
        	
        	if (!(getListeBloc().size()==0)) {
	        	for(Bloc m : getListeBloc())
	        	{
	        		distanceTest = distanceEntreDeuxGameElement(getTank(), m);
	        		if (distanceTest< plusPetiteDistance) {
	        			plusPetiteDistance = distanceTest;
	        			lePlusProche= m;
	        		}
	        	}  
        	}
        	return lePlusProche;        	
        }
        
        public Missile MissileLePlusProcheDuTank() {
        	
        	Missile lePlusProche = null;
        	
        	if (getListeMissile().size()==0)
        		return null;
        	
        	//if (getListeMissile().get(0) instanceof MissileTank)
        	//	lePlusProche = getListeMissile().get(1);
        	//else
        	//	lePlusProche = getListeMissile().get(0);
        	
        	double plusPetiteDistance = 1000000;
        	
        	//double plusPetiteDistance = distanceEntreDeuxGameElement(getTank(), lePlusProche);
        	double distanceTest = 0;
        	
        	if (!(getListeMissile().size()==0)) {
	        	for(Missile m : getListeMissile())
	        	{
	        		if (!(m instanceof MissileTank)) {
		        		distanceTest = distanceEntreDeuxGameElement(getTank(), m);
		        		if (distanceTest< plusPetiteDistance) {
		        			plusPetiteDistance = distanceTest;
		        			lePlusProche= m;
		        		}
	        		}
	        	}  
        	}
        	
        	//if (plusPetiteDistance == 1000000)
        	//	return null;
        	
        	return lePlusProche;        	
        }
        
        */
        
        public void generationAlien(int l, int c)
        {    
                //minimum colonne = 4, max = 7
                //l = 5;
                int pos = 0;
                int departLigne = 730;
                /*
                if(c == 3)
                {
                    for(int j = 0; j < c; j++)
                    {
                        if(j==0) 
                            pos = 144;
                        if(j==1) 
                            pos = 224;
                        if(j==2) 
                            pos = 304;
                        for (int i = 0; i < l; i++) 
                        {
                            if(i%2 == 0)
                            listeAlien.add(new AlienCostaud(new Vector2(pos,departLigne - 50*i)));
                            
                            if(i%2 == 1)
                            listeAlien.add(new AlienMoyen(new Vector2(pos,departLigne - 50*i)));
                        }
                    }

                }
                */
                
                if(c == 4)
                {
                    for(int j = 0; j < c; j++)
                    {
                        if(j==0) 
                            pos = 264;
                        if(j==1) 
                            pos = 344;
                        if(j==2) 
                            pos = 424;
                        if(j==3) 
                            pos = 504;
                        for (int i = 0; i < l; i++) 
                        {
                            if(i%2 == 0)
                            listeAlien.add(new AlienCostaud(new Vector2(pos,departLigne - 50*i),j+1));
                            
                            if(i%2 == 1)
                            listeAlien.add(new AlienMoyen(new Vector2(pos,departLigne - 50*i),j+1));
                        }
                    }
                }
                
                if(c == 5)
                {
                    for(int j = 0; j < c; j++)
                    {
                        if(j==0) 
                            pos = 224;
                        if(j==1) 
                            pos = 304;
                        if(j==2) 
                            pos = 384;
                        if(j==3) 
                            pos = 464;
                        if(j==4) 
                            pos = 544;    
                
                        for (int i = 0; i < l; i++) 
                        {
                            if(i%2 == 0)
                            listeAlien.add(new AlienCostaud(new Vector2(pos,departLigne - 50*i),j+1));
                            
                            if(i%2 == 1)
                            listeAlien.add(new AlienMoyen(new Vector2(pos,departLigne - 50*i),j+1));
                        }
                    }
                }
                
                if(c == 6)
                {
                    for(int j = 0; j < c; j++)
                    {
                        if(j==0) 
                            pos = 184;
                        if(j==1) 
                            pos = 264;
                        if(j==2) 
                            pos = 344;
                        if(j==3) 
                            pos = 424;
                        if(j==4) 
                            pos = 504; 
                        if(j==5)
                                pos = 584;
                
                        for (int i = 0; i < l; i++) 
                        {
                            if(i%2 == 0)
                            listeAlien.add(new AlienCostaud(new Vector2(pos,departLigne - 50*i),j+1));
                            
                            if(i%2 == 1)
                            listeAlien.add(new AlienMoyen(new Vector2(pos,departLigne - 50*i),j+1));
                        }
                    }
                }
                
                if(c == 7)
                {
                    for(int j = 0; j < c; j++)
                    {
                        if(j==0) 
                            pos = 144;
                        if(j==1) 
                            pos = 224;
                        if(j==2) 
                            pos = 304;
                        if(j==3) 
                            pos = 384;
                        if(j==4) 
                            pos = 464;   
                        if(j==5)
                                pos = 544;
                        if(j==6)
                                pos =  624;
                
                        for (int i = 0; i < l; i++) 
                        {
                            if(i%2 == 0)
                            listeAlien.add(new AlienCostaud(new Vector2(pos,departLigne - 50*i),j+1));
                            
                            if(i%2 == 1)
                            listeAlien.add(new AlienMoyen(new Vector2(pos,departLigne - 50*i),j+1));
                        }
                    }
                }
                
                listeGameElement.addAll(listeAlien);
                
        }
        
        
        
        public void generationBloc(int nombre)
        {

                if(nombre%2 == 1) //3blocs
                {
                        //bloc du milieu
                        Bloc b1 = new Bloc(new Vector2(Constants.VIEWPORT_GUI_WIDTH / 2 - Constants.TAILLE_BLOC
                        ,Constants.HAUTEUR_BLOC));
                        listeBloc.add(b1);
                        completationBloc(b1);
                        
                        //bloc � gauche
                        Bloc b2 = new Bloc(new Vector2(Constants.VIEWPORT_GUI_WIDTH / 4 - Constants.TAILLE_BLOC,Constants.HAUTEUR_BLOC));
                        listeBloc.add(b2);
                        completationBloc(b2);
                                        
                        //bloc a droite
                        Bloc b3 = new Bloc(new Vector2(Constants.VIEWPORT_GUI_WIDTH *3 / 4 - Constants.TAILLE_BLOC,Constants.HAUTEUR_BLOC));
                        listeBloc.add(b3);
                        completationBloc(b3);                
                                        

                        
                }
                
                
                if(nombre%2 == 0) // 4 blocs
                {
                        
                        //bloc a gauche gauche
                        Bloc b1 = new Bloc(new Vector2(Constants.VIEWPORT_GUI_WIDTH / 5 - Constants.TAILLE_BLOC
                        ,Constants.HAUTEUR_BLOC));
                        listeBloc.add(b1);
                        completationBloc(b1);
                        
                        //bloc � gauche
                        Bloc b2 = new Bloc(new Vector2(Constants.VIEWPORT_GUI_WIDTH *2 / 5 - Constants.TAILLE_BLOC,Constants.HAUTEUR_BLOC));
                        listeBloc.add(b2);
                        completationBloc(b2);
                                        
                        //bloc a droite
                        Bloc b3 = new Bloc(new Vector2(Constants.VIEWPORT_GUI_WIDTH *3 / 5 - Constants.TAILLE_BLOC,Constants.HAUTEUR_BLOC));
                        listeBloc.add(b3);
                        completationBloc(b3);                
                        
                        //bloc a droite droite
                        Bloc b4 = new Bloc(new Vector2(Constants.VIEWPORT_GUI_WIDTH *4 / 5 - Constants.TAILLE_BLOC,Constants.HAUTEUR_BLOC));
                        listeBloc.add(b4);
                        completationBloc(b4);                
                        
                        
                        
                }
                
        listeGameElement.addAll(listeBloc);
        }
        
        
        public void ordonnancementDe100Colonnes(int nbDeColonnes)
        {
                int colonne = 0;
                for(int i = 0; i<100; i++)
                {
                    //colonne = (int) (Math.random() * (nombreDeColonnes-1) + 1);
                    //ordonnancementDeQuiTire.add(colonne);
                    Random rand = new Random();
                    int nombreAleatoire = rand.nextInt(nbDeColonnes - 1 + 1) + 1;
                    ordonnancementDeQuiTire.add(nombreAleatoire);
                }
                
        }
        
        
        
        //permets de rajouter les 3 blocs autour d'un bloc cr�e 
        //(en dessous, � droite de lui, en diagonale � droite)
        public void completationBloc(Bloc b) 
        {
                listeBloc.add(new Bloc(new Vector2(b.gethitBox().x + 32,b.gethitBox().y))); // le bloc � droite de lui
                listeBloc.add(new Bloc(new Vector2(b.gethitBox().x,b.gethitBox().y - 32))); //le bloc en dessous de lui
                listeBloc.add(new Bloc(new Vector2(b.gethitBox().x + 32,b.gethitBox().y - 32))); //le bloc en diagonale � droite
        }
        

        public String getDirectionAlienCourante() {
                return directionAlienCourante;
        }

        public void setDirectionAlienCourante(String directionAlien) {
                this.directionAlienCourante = directionAlien;
        }
        
        

        
        
        public ArrayList<Integer> getOrdonnancementDeQuiTire() {
                return ordonnancementDeQuiTire;
        }

        public void setOrdonnancementDeQuiTire(ArrayList<Integer> ordonnancementDeQuiTire) {
                this.ordonnancementDeQuiTire = ordonnancementDeQuiTire;
        }

        public String getDirectionAlienSauvegarde() {
                return directionAlienSauvegarde;
        }

        public void setDirectionAlienSauvegarde(String directionAlienSauvegarde) {
                this.directionAlienSauvegarde = directionAlienSauvegarde;
        }

        public Tank getTank() {
                return tank;
        }

        public void setTank(Tank tank) {
                this.tank = tank;
        }

        public ArrayList<Alien> getListeAlien() {
                return listeAlien;
        }

        public void setListeAlien(ArrayList<Alien> listeAlien) {
                this.listeAlien = listeAlien;
        }

        public ArrayList<Integer> getSeed() {
                return seed;
        }

        public void setSeed(ArrayList<Integer> seed) {
                this.seed = seed;
        }

        public boolean isPartieTermine() {
                return partieTermine;
        }

        public void setPartieTermine(boolean partieTermine) {
                this.partieTermine = partieTermine;
        }


        public ArrayList<Bloc> getListeBloc() {
                return listeBloc;
        }

        public void setListeBloc(ArrayList<Bloc> listeBloc) {
                this.listeBloc = listeBloc;
        }

        public ArrayList<Missile> getListeMissile() {
                return listeMissile;
        }

        public void setListeMissile(ArrayList<Missile> listeMissile) {
                this.listeMissile = listeMissile;
        }

        public ArrayList<GameElement> getListeGameElement() {
                return listeGameElement;
        }

        public void setListeGameElement(ArrayList<GameElement> listeGameElement) {
                this.listeGameElement = listeGameElement;
        }

        public boolean isPeutTirer() {
                return peutTirer;
        }

        public void setPeutTirer(boolean peutTirer) {
                this.peutTirer = peutTirer;
        }

        public String getDirectionDepartAlien() {
                return directionDepartAlien;
        }

        public void setDirectionDepartAlien(String directionDepartAlien) {
                this.directionDepartAlien = directionDepartAlien;
        }

        public int getNombreDeColonnes() {
                return nombreDeColonnes;
        }

        public void setNombreDeColonnes(int nombreDeColonnes) {
                this.nombreDeColonnes = nombreDeColonnes;
        }

        public int getScorePartie() {
                return scorePartie;
        }

        public void setScorePartie(int scorePartie) {
                this.scorePartie = scorePartie;
        }

		public int getCpt_render() {
			return cpt_render;
		}

		public void setCpt_render(int cpt_render) {
			this.cpt_render = cpt_render;
		}

		public BinaryTree getTree() {
			return tree;
		}

		public void setTree(BinaryTree tree) {
			this.tree = tree;
		}

        
        
        
        
}

