package screens;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.spaceinvaders.MyGdxspaceinvaders;

import arbres.BinaryTree;
import arbres.EComparaisonFeatures;
import arbres.ElementNoeud;
import arbres.ElementTernaire;
import arbres.Fonctions;
import arbres.Node;
import arbres.TreePrinter;
import utils.Constants;
import utils.Individu;
import utils.EActionTank;

public class EndingScreen extends AbstractGameScreen{
		
		MyGdxspaceinvaders gam;
		Individu leMeilleurDeLaGenEnCours = null; //le meilleur de la population en cours
		private SpriteBatch sBatch;
		private BitmapFont font;
		Skin skin;
	    Stage stage;
	    private boolean destruction = false;
	    
		public EndingScreen (MyGdxspaceinvaders game) {
			
			super(game);
			gam = game;
			sBatch = new SpriteBatch();
			font = new BitmapFont();
			font.setColor(Color.WHITE);
            stage=new Stage();
            
            Gdx.input.setInputProcessor(stage);
            
                       
		            
            
            //trouve le meilleur de la gÃ©nÃ©ration actuelle
            trouveLeMeilleur();
            
            
            skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));
            Table table = new Table();
            table.setSize(800,800);
            
            final TextButton revoir = new TextButton("Revoir le meileur", skin);
            table.add(revoir).width(200).height(50);
            table.row();
            
            final TextButton prochainepop = new TextButton("Lancer la population suivante", skin);
            table.add(prochainepop).width(200).height(50);
            
            stage.addActor(table);
            
            final TextButton revoirafond = new TextButton("Revoir le meilleur, a fond", skin);
            table.add(revoirafond).width(200).height(50);
            table.row();
            
            if(!gam.isEnModeRalenti() && !gam.isPremiereGeneration()) //si on ne vient pas du mode ralenti
            {
            	gam.setCalculNbIterationPopulation(gam.getCalculNbIterationPopulation() + 1); //alors on tient les comptes dans les gÃ©nÃ©rations
            }
            
            if(!gam.isEnModeRalenti()) //si on ne vient pas du mode ralenti
            {
            	gam.meilleurDuTournoi(); //on modifie la gï¿½nï¿½ration pour la prochaine itï¿½ration
            }
            
            //on est sure d avoir fait au moins une generation si on arrive ici
            gam.setPremiereGeneration(false);
            
            
            
            int sommeAnciennePop = 0;
            for(int i = 0; i <gam.getPopulation().size(); i++)
            	sommeAnciennePop = sommeAnciennePop + gam.getPopulation().get(i).getScore();
            
            int moyenneAnciennePop = sommeAnciennePop / Constants.NB_INDIVIDU_PAR_GEN;
            System.out.println("population numero "+gam.getCalculNbIterationPopulation() + " : "+ moyenneAnciennePop);

            
            gam.getMoyenneScore().add(moyenneAnciennePop);
            
            if(gam.getCalculNbIterationPopulation() > 4)
            {
	            int valpop = gam.getMoyenneScore().get(gam.getMoyenneScore().size()-1);
	            int valpopmoins1 = gam.getMoyenneScore().get(gam.getMoyenneScore().size()-2);
	            int valpopmoins2 = gam.getMoyenneScore().get(gam.getMoyenneScore().size()-3);
	            
	            if(valpop < valpopmoins1)
	            {
	            	if(valpopmoins1 < valpopmoins2)
	            	{
	            		if (gam.getNbGenDepuisDerniereDstruction()>5) {
	            	   		destruction = true;
	            	   		gam.setNbGenDepuisDerniereDstruction(0);
	            		}
	            	   	else {
		            		gam.augmenteNbGenDepuisDerniereDestruction();
	            	   	}
	            	}
	            }
            }
            
            
            //quand on a fait les 100 generations
            if(!(gam.getCalculNbIterationPopulation() < 100))
            {
            	int scoreMax = -1;
            	int scoreMin = 999999;
            	for (int i = 0; i < gam.getMoyenneScore().size(); i++) {
					System.out.println("score de la gen"+i+ " est : " +gam.getMoyenneScore().get(i));
					//moi
					if (gam.getMoyenneScore().get(i)>scoreMax) {
						scoreMax=gam.getMoyenneScore().get(i);
					}
					if(gam.getMoyenneScore().get(i)< scoreMin) {
						scoreMin = gam.getMoyenneScore().get(i);
					}
            	}
            	for (int i = 0; i < gam.getHistoriqueDesDestruction().size(); i++) {
					System.out.println("i" + gam.getHistoriqueDesDestruction().get(i));
				
            	}
            	
            	System.out.println("Pire score moyen : "+scoreMin);
            	System.out.println("Meilleur score moyen : "+scoreMax);          	
            	System.out.println("Nombre de destructions : " + gam.getHistoriqueDesDestruction().size());
            	System.out.println("Score du meilleur individu : "+leMeilleurDeLaGenEnCours.getScore());
            	//affichage meilleur arbre
            	//leMeilleurDeLaGenEnCours.getTree().printTree(new OutputStreamWriter(System.out));
            	
            	
            }

            
           
            revoir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                revoir.addAction(Actions.fadeOut(0.7f));
                System.out.println("ET CEST LE RALENTI DU MEILLEUR!!");
				Gdx.graphics.setVSync(true);
				gam.setEnModeRalenti(true);
				//TreePrinter.print(leMeilleurDeLaGenEnCours.getTree());
				System.out.println("Le score du meilleur est " +leMeilleurDeLaGenEnCours.getScore());
				
				gam.setIndividuQuonRevoie(leMeilleurDeLaGenEnCours.getTree().copyNode());
				gam.setScreen(new GameScreenPourRepetition(gam));
                }
           });
            
            
            revoirafond.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                revoir.addAction(Actions.fadeOut(0.7f));
                System.out.println("ET CEST LE RALENTI A FOND DU MEILLEUR!!");
				Gdx.graphics.setVSync(false);
				gam.setEnModeRalenti(true);
				//TreePrinter.print(leMeilleurDeLaGenEnCours.getTree());
				System.out.println("Le score du meilleur est " +leMeilleurDeLaGenEnCours.getScore());
				
				gam.setIndividuQuonRevoie(leMeilleurDeLaGenEnCours.getTree().copyNode());
				gam.setScreen(new GameScreenPourRepetition(gam));
                }
           });
            
            
            prochainepop.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                prochainepop.addAction(Actions.fadeOut(0.7f));
                System.out.println("POPULATION SUIVANTE!!");
                gam.setEnModeRalenti(false); //on vire le mode ralenti
                gam.setCalculNbIndividu(0); //on reset le compteur des individus
                Gdx.graphics.setVSync(false);
                creationNouvelPop();     
                
                gam.setPopulation((ArrayList<Individu>) gam.getProchainePopulation().clone());
				
                gam.getProchainePopulation().clear();
                
                gam.setScreen(new GameScreenPourRepetition(gam));
                }
           });
            
		}	

		
		
		
		public void destructionPopulation() {
            System.out.println("DESTRUCTION DE MALADE");
            //garder le meilleur
            gam.getProchainePopulation().add(leMeilleurDeLaGenEnCours);
            if(Constants.NB_INDIVIDU_PAR_GEN == 50)
            {
                    //on rajoute 49 nouveaux arbres generes aleatoirement
                    int nombreAleatoire  = -1;
                    for (int i = 0; i < 50; i++) {
                            nombreAleatoire = BinaryTree.foncRandom(EComparaisonFeatures.values().length);                                                
                            Node nouveau_node = new Node(new Fonctions(EComparaisonFeatures.values()[nombreAleatoire]));
                            BinaryTree b1 = new BinaryTree(nouveau_node);
                            
                            b1.ajoutAleatoireNoeud(1, nouveau_node, Constants.HAUTEUR_MAX_ARBRE);
                            gam.getProchainePopulation().add(new Individu(-1, nouveau_node));
                    }
            }
            
    }
		
		
		
		public void creationNouvelPop()
		{
			//on mets le meilleur en premier
			gam.getProchainePopulation().add(leMeilleurDeLaGenEnCours);
			if(Constants.NB_INDIVIDU_PAR_GEN == 50)
			{
				//on fait 11 mutation
				for (int i = 0; i < 11; i++) {
					Node gagnant_tournoi = tournoi(3).getTree().copyNode();
					mutation_vraie(gagnant_tournoi);
					gam.getProchainePopulation().add(new Individu(-1,gagnant_tournoi));
				}
					//on fait 38 croisements
				for(int i = 0; i < 19; i++)
				{
					Node copy_gagnant_tournoi1 = tournoi(3).getTree().copyNode();
					Node copy_gagnant_tournoi2 = tournoi(3).getTree().copyNode();
					
					
					croisement(copy_gagnant_tournoi1, copy_gagnant_tournoi2);
					gam.getProchainePopulation().add(new Individu(-1,copy_gagnant_tournoi1));
					gam.getProchainePopulation().add(new Individu(-1,copy_gagnant_tournoi2));
					
				}
			}
			
		}

		@Override
		public void render(float deltaTime) {

			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glClearColor(0, 0, 0, 0);
			
			
			
            if(gam.getCalculNbIterationPopulation() < 100)
            {
            	
            	
            	
            	
	            System.out.println("POPULATION SUIVANTE!!");
	            gam.setCalculNbIndividu(0); //on reset le compteur des individus
	            Gdx.graphics.setVSync(false);
	            if(destruction)
	            {
	            	destructionPopulation();
	            	gam.getHistoriqueDesDestruction().add(gam.getCalculNbIterationPopulation());
	            }
	            else
	            {
		            creationNouvelPop();     
	            }
	            destruction = false;
	            gam.setPopulation((ArrayList<Individu>) gam.getProchainePopulation().clone());
				
	            gam.getProchainePopulation().clear();
	            
	            gam.setScreen(new GameScreenPourRepetition(gam));
            }
            else
            {
            	System.out.println("on a fini");
            }
            
			
			
			
			
			
			sBatch.begin();
			
		    font.draw(sBatch,  "Nous en sommes Ã  la gÃ©nÃ©ration numÃ©ro : " + gam.getCalculNbIterationPopulation(), 200,650);
		    font.draw(sBatch,  "Le meilleur score de cette gÃ©nÃ©ration est : " + leMeilleurDeLaGenEnCours.getScore(), 200,600);
			font.draw(sBatch,  "Les scores obtenus sont : ", 300, 500);
			for (int i = 0; i < gam.getPopulation().size(); i++) {
				if(gam.getPopulation().get(i).getScore() < 100)
					font.draw(sBatch,""+i+" : "+String.valueOf(0),50,800-15*i);
					
				if(gam.getPopulation().get(i).getScore() >100)
				font.draw(sBatch,""+i+" : "+String.valueOf(gam.getPopulation().get(i).getScore()).subSequence(0,String.valueOf(gam.getPopulation().get(i).getScore()).length()-2),50,800-15*i);
			}
			sBatch.end();
			

		      // let the stage act and draw
		      stage.act(deltaTime);
		      stage.draw();
			
			
		}
		
		public void mutation_vraie(Node n_a_muter) {
			
			 Node noeudEnCours = n_a_muter;
	           
          try {
				noeudEnCours.printTree(new OutputStreamWriter(System.out));
			} catch (IOException e) {
				e.printStackTrace();
			}
          
          //on se place la ou on va rajouter une mutation
          int cpt_ou_est_on  = 1;
          int droiteOuGauche = 1;	// 1 pour droite, 0  pour gauche           
          int continuer = 1;		// 1 si oui , 0 sinon
          boolean noeudEstUneAction = false;
          
          System.out.println("place a la mutation :");
          
          //pb : je dois connaitre la hauteur max de l'arbre a muter si je veux m'arreter a temps
          //pb quand noeud = feuille
          
          while(continuer==1)  {
          	cpt_ou_est_on++;
          	
          	droiteOuGauche = (int)(Math.random() * ((1 - 0) + 1));		            	
          	if (droiteOuGauche==1) {
          		noeudEnCours = noeudEnCours.getRight();
          		System.out.println("on va a droite");
          	}
          	else {
          		noeudEnCours = noeudEnCours.getLeft();
          		System.out.println("on va a gauche");
          	}       	
          	
          	if (!(noeudEnCours.getKey() instanceof Fonctions ) ){
          		continuer=0;
          		noeudEstUneAction=true;
          	}
          	else        
          		continuer = (int)(Math.random() * ((1 - 0) + 1)); 
          	
          	System.out.println("on s'arrete ou pas ? "+continuer);
          	try {
					noeudEnCours.printTree(new OutputStreamWriter(System.out));
				} catch (IOException e) {
					e.printStackTrace();
				}
          	
          }
          //System.out.println("hauteur : "+cpt_ou_est_on);

          // si noeud est une feuille --> marche mais peut donner le meme machin // a changer
       	
   			//int hauteur_max_de_l_extension = Constants.HAUTEUR_MAX_ARBRE- cpt_ou_est_on;
			int nombreAleatoire  = BinaryTree.foncRandom(EComparaisonFeatures.values().length);	
			
			while (noeudEnCours.getKey().equals(EComparaisonFeatures.values()[nombreAleatoire]))
			{
				nombreAleatoire = BinaryTree.foncRandom(EComparaisonFeatures.values().length);
			}
			noeudEnCours.setKey(new Fonctions(EComparaisonFeatures.values()[nombreAleatoire]));
			BinaryTree b1 = new BinaryTree(noeudEnCours);
			
			b1.ajoutAleatoireNoeud(1, noeudEnCours, Constants.HAUTEUR_MAX_ARBRE);
       			 
       		            
           System.out.println("Noeud modifi� :");
           try {
				noeudEnCours.printTree(new OutputStreamWriter(System.out));
			} catch (IOException e) {
				e.printStackTrace();
			}
           
           System.out.println("Nouveau noeud mut� :");
           try {
				n_a_muter.printTree(new OutputStreamWriter(System.out));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		public void mutation(Node n_a_muter) {
			
			 Node noeudEnCours = n_a_muter;
	           
	            try {
					noeudEnCours.printTree(new OutputStreamWriter(System.out));
				} catch (IOException e) {
					e.printStackTrace();
				}
	            
	            //on se place la ou on va rajouter une mutation
	            int cpt_ou_est_on  = 1;
	            int droiteOuGauche = 1;	// 1 pour droite, 0  pour gauche           
	            int continuer = 1;		// 1 si oui , 0 sinon
	            boolean noeudEstUneAction = false;
	            
	            System.out.println("place a la mutation :");
	            
	            //pb : je dois connaitre la hauteur max de l'arbre a muter si je veux m'arreter a temps
	            //pb quand noeud = feuille
	            
	            while(continuer==1)  {
	            	cpt_ou_est_on++;
	            	
	            	droiteOuGauche = (int)(Math.random() * ((1 - 0) + 1));		            	
	            	if (droiteOuGauche==1) {
	            		noeudEnCours = noeudEnCours.getRight();
	            		System.out.println("on va a droite");
	            	}
	            	else {
	            		noeudEnCours = noeudEnCours.getLeft();
	            		System.out.println("on va a gauche");
	            	}       	
	            	
	            	if (!(noeudEnCours.getKey() instanceof Fonctions ) ){
	            		continuer=0;
	            		noeudEstUneAction=true;
	            	}
	            	else        
	            		continuer = (int)(Math.random() * ((1 - 0) + 1)); 
	            	
	            	System.out.println("on s'arrete ou pas ? "+continuer);
	            	try {
						noeudEnCours.printTree(new OutputStreamWriter(System.out));
					} catch (IOException e) {
						e.printStackTrace();
					}
	            	
	            }
	            System.out.println("hauteur : "+cpt_ou_est_on);

	            // si noeud est une feuille --> marche mais peut donner le meme machin // a changer
         	if (noeudEstUneAction) {
         		
         		// si noeud est une action, 2 possibilitï¿½s
         		// soit c'est une action ET on est a la hauter max         		
         		if (cpt_ou_est_on>=Constants.HAUTEUR_MAX_ARBRE) {
         		 	System.out.println("Le noeud est une action");
		            	int nombreAleatoire = BinaryTree.foncRandom(EActionTank.values().length);
		            	while (noeudEnCours.getKey().equals(EActionTank.values()[nombreAleatoire])) {
		            		nombreAleatoire = BinaryTree.foncRandom(EActionTank.values().length);
		            	}
		            	noeudEnCours.setKey(new ElementTernaire(EActionTank.values()[nombreAleatoire]));
         		}
         		else {
         			// soit on peut changer cette feuille en un arbre 
         			// attention il faut respecter la taille max Constants.HAUTEUR_MAX_ARBRE
         			
         			int hauteur_max_de_l_extension = Constants.HAUTEUR_MAX_ARBRE- cpt_ou_est_on;
         			int nombreAleatoire  = BinaryTree.foncRandom(EComparaisonFeatures.values().length);	
         			
         			while (noeudEnCours.getKey().equals(EComparaisonFeatures.values()[nombreAleatoire]))
         			{
         				nombreAleatoire = BinaryTree.foncRandom(EComparaisonFeatures.values().length);
         			}
         			noeudEnCours.setKey(new Fonctions(EComparaisonFeatures.values()[nombreAleatoire]));
         			BinaryTree b1 = new BinaryTree(noeudEnCours);
         			
         			b1.ajoutAleatoireNoeud(1, noeudEnCours, hauteur_max_de_l_extension);
         			 
         			
         		}
		            	
		            }
         	else {	// soit c'est une comparaison, un test
         		// on change la key 
         		int nombreAleatoire2 = BinaryTree.foncRandom(EComparaisonFeatures.values().length);	  
         		while (noeudEnCours.getKey().equals(EComparaisonFeatures.values()[nombreAleatoire2]))
     			{
     				nombreAleatoire2 = BinaryTree.foncRandom(EComparaisonFeatures.values().length);
     			}
         			
         		noeudEnCours.setKey(new Fonctions(EComparaisonFeatures.values()[nombreAleatoire2]));
         		
         	}
	            		            
	            System.out.println("Noeud modifiï¿½ :");
	            try {
					noeudEnCours.printTree(new OutputStreamWriter(System.out));
				} catch (IOException e) {
					e.printStackTrace();
				}
	            
	            System.out.println("Nouveau noeud mutï¿½ :");
	            try {
					n_a_muter.printTree(new OutputStreamWriter(System.out));
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
		
		public Individu tournoi(int nombre_de_node_choisi_au_hasard) {
			ArrayList<Individu> pop = new ArrayList<Individu>();
			for (int i = 0; i < gam.getPopulation().size(); i++) {
				pop.add(gam.getPopulation().get(i));
			}

			ArrayList<Individu> participants_tournoi = new ArrayList<Individu>();
			Individu gagnant = null ;
			int nombreAleatoire = -1;
			//
			//System.out.println("population totale");
			for (int i = 0; i < pop.size(); i++) {
				//System.out.println(pop.get(i).getTree()+" - "+pop.get(i).getScore());
			}
			//
			for (int i = 0; i < nombre_de_node_choisi_au_hasard; i++) {
				nombreAleatoire = (int)(Math.random() * (((pop.size()-1) - 0) + 1));
				//System.out.println("nb aleatoire"+ nombreAleatoire);
				participants_tournoi.add(pop.get(nombreAleatoire));
				pop.remove(nombreAleatoire);
			}
			//
			//System.out.println("participants tournoi("+nombre_de_node_choisi_au_hasard+")");
			for (int i = 0; i < participants_tournoi.size(); i++) {
				System.out.println(participants_tournoi.get(i).getTree()+" - "+participants_tournoi.get(i).getScore());
			}
			//
			
			gagnant = new Individu(participants_tournoi.get(0).getScore(), pop.get(0).getTree());
			
			for (int i = 1; i < participants_tournoi.size(); i++) {
		    	if(participants_tournoi.get(i).getScore() > gagnant.getScore())
		    	{
		    		gagnant = participants_tournoi.get(i);
		    	}				
			}
			//System.out.println("gagnant : " + gagnant.getTree());
			//System.out.println("son score : " + gagnant.getScore());
			
			return gagnant;
			
			
		}
		
		
		public void croisement(Node n1, Node n2){
			//System.out.println("debut croisement");
			Node noeudEnCours1 = n1;
			int droiteOuGauche = 0;
            int continuer = 1;
            int cpt_ou_est_on2 = 1;
            //System.out.println("parcours noeud 1");
            while(continuer==1)  {
            	cpt_ou_est_on2++;            	
            	droiteOuGauche = (int)(Math.random() * ((1 - 0) + 1));		            	
            	if (droiteOuGauche==1) {
            		noeudEnCours1 = noeudEnCours1.getRight();
            		//System.out.println("on va a droite");
            	}
            	else {
            		noeudEnCours1 = noeudEnCours1.getLeft();
            		//System.out.println("on va a gauche");
            	}       	
            	
            	if (!(noeudEnCours1.getKey() instanceof Fonctions ))
            		continuer=0;
            	else        
            		continuer = (int)(Math.random() * ((1 - 0) + 1));
            	            	
            	//System.out.println("on s'arrete ou pas ? "+continuer);
            	/*
            	try {
					noeudEnCours1.printTree(new OutputStreamWriter(System.out));
				} catch (IOException e) {
					e.printStackTrace();
				}
            	*/
            	
            }
            
            //System.out.println("parcours noeud 2");
            Node noeudEnCours2 = n2;
            continuer = 1;
            cpt_ou_est_on2=1;
            while(continuer==1)  {
            	cpt_ou_est_on2++;            	
            	droiteOuGauche = (int)(Math.random() * ((1 - 0) + 1));		            	
            	if (droiteOuGauche==1) {
            		noeudEnCours2 = noeudEnCours2.getRight();
            		//System.out.println("on va a droite");
            	}
            	else {
            		noeudEnCours2 = noeudEnCours2.getLeft();
            		//System.out.println("on va a gauche");
            	}       	
            	
            	if (!(noeudEnCours2.getKey() instanceof Fonctions ))
            		continuer=0;
            	else        
            		continuer = (int)(Math.random() * ((1 - 0) + 1));
            	            	
            	//System.out.println("on s'arrete ou pas ? "+continuer);
            	/*
            	try {
					noeudEnCours2.printTree(new OutputStreamWriter(System.out));
				} catch (IOException e) {
					e.printStackTrace();
				}
            	 */           	
            	
            }
            System.out.println("avant croisement");
            try {
            	n1.printTree(new OutputStreamWriter(System.out));
            	n2.printTree(new OutputStreamWriter(System.out));
				//noeudEnCours1.printTree(new OutputStreamWriter(System.out));
				//noeudEnCours2.printTree(new OutputStreamWriter(System.out));
			} catch (IOException e) {
				e.printStackTrace();
			}
            /*
            Node noeud_pour_croisement = noeudEnCours1;
        	noeudEnCours1= noeudEnCours2;
        	noeudEnCours2=noeud_pour_croisement;
        	*/
            
            //Node noeud_pour_croisement = noeudEnCours1;
            ElementNoeud element_noeud_noeudEnCours1 = noeudEnCours1.getKey();
            Node left_noeudEnCours1 = noeudEnCours1.getLeft();
            Node right_noeudEnCours1 = noeudEnCours1.getRight();
            noeudEnCours1.setKey(noeudEnCours2.getKey());
            noeudEnCours1.setLeft(noeudEnCours2.getLeft());
            noeudEnCours1.setRight(noeudEnCours2.getRight());
            noeudEnCours2.setKey(element_noeud_noeudEnCours1);
            noeudEnCours2.setLeft(left_noeudEnCours1);
            noeudEnCours2.setRight(right_noeudEnCours1);
            
            System.out.println("apres croisement");
            try {
            	n1.printTree(new OutputStreamWriter(System.out));
            	n2.printTree(new OutputStreamWriter(System.out));
				//noeudEnCours1.printTree(new OutputStreamWriter(System.out));
				//noeudEnCours2.printTree(new OutputStreamWriter(System.out));
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            
		}
		
		
		public void trouveLeMeilleur()
		{
		    //on trouve le meilleur individu
			leMeilleurDeLaGenEnCours = new Individu(gam.getPopulation().get(0).getScore(), gam.getPopulation().get(0).getTree().copyNode());
		    for (int i = 0; i < gam.getPopulation().size(); i++) {
		    	if(gam.getPopulation().get(i).getScore() > leMeilleurDeLaGenEnCours.getScore())
		    	{
		    		leMeilleurDeLaGenEnCours = new Individu(gam.getPopulation().get(i).getScore(), gam.getPopulation().get(i).getTree().copyNode());
		    	}
				
			}
		    
		    //System.out.println("je suis dans la fonction trouve le meilleur \n");
		    TreePrinter.print(leMeilleurDeLaGenEnCours.getTree());
		}


		public Individu getLeMeilleurDeLaGenEnCours() {
			return leMeilleurDeLaGenEnCours;
		}


		public void setLeMeilleurDeLaGenEnCours(Individu leMeilleurDeLaGenEnCours) {
			this.leMeilleurDeLaGenEnCours = leMeilleurDeLaGenEnCours;
		}


		@Override
		public void resize(int width, int height) {
			// TODO Auto-generated method stub
			
		}
		

		@Override
		public void show() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void hide() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		
	}

























