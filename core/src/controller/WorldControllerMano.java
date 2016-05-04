package controller;

// nouveau commentaire BaptBerto

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import model.Alien;
import model.Bloc;
import model.GameElement;
import model.Missile;
import model.MissileAlien;
import model.MissileTank;
import model.Tank;
import model.World;
import utils.Constants;
import utils.EActionTank;
import arbres.Fonctions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.spaceinvaders.MyGdxspaceinvaders;

public class WorldControllerMano extends InputAdapter{

	
	private static final String TAG = WorldController.class.getName();
    private MyGdxspaceinvaders game;
    public int lives;
    private float vitesseAlien; //vitesse des aliens, augmentent avec le temps
    private World world;
    private float sauvegardeDescente;
    private long vitesseDuNiveau = 100000;         //100000
    private long gestionVitesseChute; //gerer vitesse chute
    private float vitesseTank; //vitesse du tank
    private ArrayList<Integer> indiceDesColonnesQuiTire;
    public int premierpassage = 0;
    public float gestionDelai;
    long lastMissile;
    int compteurOrdonnancement;
    private ArrayList<Integer> colonneDejaDetruite;
    
    public WorldControllerMano (World world) {
            this.world = world;
            init();
    }
    
    
    private void init()
    {
            Gdx.input.setInputProcessor(this);
            //vitesseAlien =0.5f;
            vitesseAlien =1f;
            sauvegardeDescente = Constants.DESCENTE_AVANT_STABILISATION;
            lives = 1;
            lastMissile = TimeUtils.nanoTime();
            gestionVitesseChute = TimeUtils.nanoTime();
            indiceDesColonnesQuiTire = new ArrayList<Integer>();
            compteurOrdonnancement = 0;
            
            colonneDejaDetruite = new ArrayList<Integer>();
            //indiceDesColonnesQuiTire.add(0);
            //indiceDesColonnesQuiTire.add(1);
            
            
        	/*indiceDesColonnesQuiTire.add(0, world.getOrdonnancementDeQuiTire().get(this.compteurOrdonnancement));
        	indiceDesColonnesQuiTire.add(1, world.getOrdonnancementDeQuiTire().get(this.compteurOrdonnancement+1));
            
        	compteurOrdonnancement = 2;*/
    		vitesseTank = 5.0f;
            //initLevel();
    }
    
    
    private void backToMenu()
    {
            //switch to menu screen
            //game.setScreen(new MenuScreen(game));
    }
    
public boolean isGameOver()
{
        return lives <= 0;
}
    
    
    public void update(float deltaTime) {
    	
    	boolean haha = false;
        haha = Fonctions.AlienLePlusBasEstAuDessusDeLaMoitieDuTerrain(world);
        
	    	//pour faire render par render
	    	//Scanner reader = new Scanner(System.in);  // Reading from System.in
	    	//System.out.println("Enter a number: ");
	    	//int n = reader.nextInt();
	    	
	    	EActionTank action = EActionTank.Nothing; // obligé de initialisé
	    	try {
				action = world.getTree().decisionTank(world);
				System.out.print("score: " + world.getScorePartie() + " - render: " +world.getCpt_render() + " - nbrMissile: " +world.getListeMissile().size() + " :: " + action.toString() + "\n");
				
	    	
				
	    	} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
	    	
	    	
	    	//System.out.println("L'action du tank choisi est : "+action.toString());
	    	
	    	//pour ne pas etre deborder de missile
			if(world.getCpt_render() > Constants.MAX_RENDER)
			{
				world.setCpt_render(0);
			}
	    	
            //handleDebugInput(deltaTime);
    		mouvementMissile();	
            if(isGameOver())
            {
                //retour menu ou nouvelle g�n�ration
        		deltaTime = 0;
        		world.setPartieTermine(true);
            }
            else
            {
        		//handleInputTank(deltaTime);
            	handleInputTankAutomatisé(action);
                //if(TimeUtils.nanoTime() - gestionVitesseChute > vitesseDuNiveau)
                //{        
            			mouvementAlien();
                        gestionVitesseChute = TimeUtils.nanoTime();
                //}
            }
            
            // check if we need to create a new raindrop
            
            
            testCollisionMissile();
            eliminationMissileHorsLimite();
            
            //verifie a chaque instant si une colonne est vide, afin de faire le m�nage
        	for(int i = 1; i<world.getNombreDeColonnes()+1; i++)
        	{
        		testColonneVide(i);
        	}
            
            world.setScorePartie(world.getScorePartie() + 1);
            //if (TimeUtils.nanoTime() - lastMissile > 1000000000)
            //System.out.println(" cpt render : "+ world.getCpt_render());
            if(world.getCpt_render() == 0)
            {
            	
            	//on avance dans l'ordonnancement pour tirer
            	indiceDesColonnesQuiTire.clear();
            	indiceDesColonnesQuiTire.add(0, compteurOrdonnancement);
            	indiceDesColonnesQuiTire.add(1, compteurOrdonnancement+1);
            	
            	//on tire
            	gestionTirMissileDesAliens();
            	compteurOrdonnancement = compteurOrdonnancement + 2;
            	
            	//System.out.println("compteur : "+compteurOrdonnancement);
            	//System.out.println("compteur+1 : "+ (compteurOrdonnancement+1));
            	//System.out.println("taille ord : "+world.getOrdonnancementDeQuiTire().size());
            	

            	//System.out.println(compteurOrdonnancement);

            	/*
            	for (int i = 0; i < world.getOrdonnancementDeQuiTire().size(); i++) {
            		System.out.println(i + " : " + world.getOrdonnancementDeQuiTire().get(i));
				}
				*/
            	
            	//System.out.println(compteurOrdonnancement);
            	
            	
            	if(compteurOrdonnancement+1 >= world.getOrdonnancementDeQuiTire().size()-1)
            	{
             		compteurOrdonnancement = 0;
            		
            	}
            			
            }
            

    }
            
    
    //verifie si une colonne est vide et retire la colonne de l'ordonnancement
    private void testColonneVide(int c)
    {
    	if(!colonneDejaDetruite.contains(c)) // si pas d�ja su comme supprim�
    	{
        	ArrayList<Integer> elemASupr = new ArrayList<Integer>();
        	boolean trouve = false;
        	for(Alien m : world.getListeAlien())
        	{
        		if(m.getColonne() == c)
        		{
        			trouve = true;
        		}	
        	}
        	
        	//on supprime la colonne
        	if(!trouve)
        	{
        		compteurOrdonnancement = 0;
        		colonneDejaDetruite.add(c);
        		for (int i = 0; i < world.getOrdonnancementDeQuiTire().size(); i++) {
        			if(world.getOrdonnancementDeQuiTire().get(i) == c)
        			{
        				elemASupr.add(world.getOrdonnancementDeQuiTire().get(i));	
        			}
				}
        		
        		world.getOrdonnancementDeQuiTire().removeAll(elemASupr);      	
        	}
    	}
    	
    }
    
    //g�re le tire des aliens
    private void gestionTirMissileDesAliens()
    {

    		Alien a1 = trouveAlienEnBoutDeFil(world.getOrdonnancementDeQuiTire().get(indiceDesColonnesQuiTire.get(0)));
    		Alien a2 = trouveAlienEnBoutDeFil(world.getOrdonnancementDeQuiTire().get(indiceDesColonnesQuiTire.get(1)));
    		
    		
    		
    		
    		
    		if(a1 != null)
    		{
    			Missile m = new MissileAlien(new Vector2(a1.getPosition().x + Constants.TAILLE_ALIEN/2,a1.getPosition().y - Constants.TAILLE_MISSILE));
    			world.getListeMissile().add(m); 
    			world.getListeGameElement().add(m);
    		}
    		if(a2 != null & a2 != a1)
    		{
    			Missile m2 = new MissileAlien(new Vector2(a2.getPosition().x + Constants.TAILLE_ALIEN/2,a2.getPosition().y - Constants.TAILLE_MISSILE));
    			world.getListeMissile().add(m2); 
    			world.getListeGameElement().add(m2);
    			
    			
    		}
    		
    		lastMissile = TimeUtils.nanoTime();
    		
    	}

    
    //trouve l'alien de la colonne qui doit tirer
    private Alien trouveAlienEnBoutDeFil(int c)
    {
    	Alien a = world.getListeAlien().get(0);
    	
    	for(Alien m : world.getListeAlien())
    	{
    		if(m.getColonne() == c)
    		{
    			if((a.getPosition().y >= m.getPosition().y) || (a.getColonne() != c))
    			{
    				a = m;
    			}
    		}
    	}
    	
    	int compteur = 0;
    	//on regarde la colonne est vide
    	for(Alien m : world.getListeAlien())
    	{
    		if(m.getColonne() == c)
    		{
    			compteur++;
    		}
    	}
    	
    	//donc on a pas trouve d'alien meilleur que a
    	if(compteur == 0)
    	{
    		return null;
    	}
    	//sinon on retourne le bon alien
    	return a;
    	
    }
    
    
    
    
    
    
    private void handleInputTankAutomatisé(EActionTank action)
    {
		float x, y = 0; // position du tank

		
		if(action == EActionTank.Shot && (world.isPeutTirer()))
		{
			world.setPeutTirer(false);
			
			Missile m = new MissileTank(new Vector2 (world.getTank().getPosition().x+(Constants.TAILLE_TANK/2),world.getTank().getPosition().y  + Constants.TAILLE_TANK ));
			world.getListeGameElement().add(m);
			world.getListeMissile().add(m);
		}

    	if(action == EActionTank.Right)
    	{
    		if(deplacementPossible("droite"))
    		{
               	x= world.getTank().getPosition().x;
                y= world.getTank().getPosition().y; 
                world.getTank().setPosition(world.getTank().getPosition().set(x+vitesseTank, y));
    		}
        }
    	if(action == EActionTank.Left)
    	{
    		if(deplacementPossible("gauche"))
    		{
            	x= world.getTank().getPosition().x;
                y= world.getTank().getPosition().y; 
                world.getTank().setPosition(world.getTank().getPosition().set(x-vitesseTank, y));
    		}
    	}
    	    	
    	replacementTank();
    }
    
    
    
    
    
    
    
    private void handleInputTank(float deltaTime)
    {
		float x, y = 0; // position du tank

		
		if(((Gdx.input.isKeyPressed(Keys.SPACE)) || (Gdx.input.isKeyPressed(Keys.UP))) && (world.isPeutTirer()))
		{
			world.setPeutTirer(false);
			
			Missile m = new MissileTank(new Vector2 (world.getTank().getPosition().x+(Constants.TAILLE_TANK/2),world.getTank().getPosition().y  + Constants.TAILLE_TANK ));
			world.getListeGameElement().add(m);
			world.getListeMissile().add(m);
		}

    	if(Gdx.input.isKeyPressed(Keys.RIGHT))
    	{
    		if(deplacementPossible("droite"))
    		{
               	x= world.getTank().getPosition().x;
                y= world.getTank().getPosition().y; 
                world.getTank().setPosition(world.getTank().getPosition().set(x+vitesseTank, y));
    		}
        }
    	if(Gdx.input.isKeyPressed(Keys.LEFT))
    	{
    		if(deplacementPossible("gauche"))
    		{
            	x= world.getTank().getPosition().x;
                y= world.getTank().getPosition().y; 
                world.getTank().setPosition(world.getTank().getPosition().set(x-vitesseTank, y));
    		}
    	}
    	
    	replacementTank();
    }

    
    private boolean deplacementPossible(String direction)
    {
    	if(world.getTank().getPosition().x == 0 && direction == "gauche")
    	{
    		return false;
    	}
    	
    	if(world.getTank().getPosition().x == Constants.VIEWPORT_GUI_WIDTH - Constants.TAILLE_TANK && direction == "droite")
    	{
    		return false;
    	}
    	
    	return true;
    }
    
    
    public void replacementTank()
    {
    	//si en dehors de l ecran ï¿½ droite
    	if(world.getTank().getPosition().x > Constants.VIEWPORT_GUI_WIDTH - Constants.TAILLE_TANK*1.5)
    	{
    		world.getTank().setPosition(new Vector2((float) (Constants.VIEWPORT_GUI_WIDTH - Constants.TAILLE_TANK*1.5),world.getTank().getPosition().y));
    	}
    	
    	//si en dehors de l ecran a gauche
    	if(world.getTank().getPosition().x < 0  + Constants.TAILLE_TANK/2)
    	{
    		world.getTank().setPosition(new Vector2(0+ Constants.TAILLE_TANK/2,world.getTank().getPosition().y));
    	}
    }
    
    
    
    public void testCollisionMissile()
    {
    	
    	if(world.getScorePartie() == 177)
    	{
    		System.out.println("on y est");
    		for (int i = 0; i < world.getListeAlien().size(); i++) {
				System.out.print(""+ i + ":x: " + world.getListeAlien().get(i).gethitBox().x+":y:" + world.getListeAlien().get(i).gethitBox().y + "###");
				//System.out.print("rect"+ i + ":x: " + world.getListeAlien().get(i).getPosition().x+":y:" + world.getListeAlien().get(i).getPosition().y + "###");
			}

    	}
    	
    	if(world.getScorePartie() == 1344) //1344
    	{
    		System.out.println("on y est");

    	}
    	
    	ArrayList<GameElement> elemASupr = new ArrayList<GameElement>();
		for(Missile m : world.getListeMissile())
		{    	
			for(GameElement ge : world.getListeGameElement())
	        {
				//si missile pas �gal � lui meme
        		if(!m.equals(ge))
        		{
        			//si on a une collision, supprime ge et m
        			if(m.gethitBox().overlaps(ge.gethitBox()))
        			{
        				//cas fin de partie
        				if(ge instanceof Tank)
        				{
        					lives = 0;
        					//changer de texture ou supprimer le tank
        				}
        				//deux missiles qui se rentrent dedans, on les supprime
        				if(ge instanceof Missile)
        				{
        					elemASupr.add(ge);
        					elemASupr.add(m);
        					// si m est un missile du tank, on peut tirer
        					if (m instanceof MissileTank)
        						world.setPeutTirer(true);
        				}
        				//collision missile et bloc
        				if(ge instanceof Bloc)
        				{
        					elemASupr.add(ge);
        					elemASupr.add(m);
        					// si m est un missile du tank, on peut tirer
        					if (m instanceof MissileTank)
        						world.setPeutTirer(true);
        				}
        				//que ceux du bas qui tire, donc collision possible que avec missiletank
        				if(ge instanceof Alien)
        				{
	        					elemASupr.add(ge);
        					elemASupr.add(m);
        					world.setScorePartie(world.getScorePartie() + ((Alien) ge).getValeurPoint());
//        					Alien test = (Alien) ge;
//        					System.out.println("alien : " + test.getColonne());
        					world.setPeutTirer(true);
        				}		
        			}
        		}
	        } 	
    }
		
		
		world.getListeGameElement().removeAll(elemASupr);
		world.getListeAlien().removeAll(elemASupr);
		world.getListeMissile().removeAll(elemASupr);
		world.getListeBloc().removeAll(elemASupr);
		
//		world.getListeMissile().remove(m);
//		world.getListeMissile().remove(ge);
//		world.getListeGameElement().remove(m);
//		world.getListeGameElement().remove(ge);
    
}
    
    
    
    
    
    public void eliminationMissileHorsLimite() {
    	
    	ArrayList<Missile> listeMissileSuppr = new ArrayList<Missile>();
    	        	
    	for (Missile m : world.getListeMissile()) {
    		if ((m.getPosition().y< -Constants.TAILLE_MISSILE) ) {
    			listeMissileSuppr.add(m);
    		}
    		if ((m.getPosition().y> Constants.VIEWPORT_GUI_HEIGHT + Constants.TAILLE_MISSILE) ) {
    			listeMissileSuppr.add(m);
    			world.setPeutTirer(true);
    		}
    		
    	}    	
    	
    	world.getListeMissile().removeAll(listeMissileSuppr);
    	world.getListeGameElement().removeAll(listeMissileSuppr);
    }
    
    
    
    
    //descente des missiles alien et mont� des missile du tank
    public void mouvementMissile()
    {
    	for(Missile m : world.getListeMissile())
    	{
    		//ils descendent
    		if(m instanceof MissileAlien)
    		{
    			m.setPosition(new Vector2(m.getPosition().x,m.getPosition().y-Constants.VIT_MISSILE_ALIEN));
    		}
    		//ils montent
    		if(m instanceof MissileTank)
    		{
    			m.setPosition(new Vector2(m.getPosition().x,m.getPosition().y+Constants.VIT_MISSILE_TANK));
    		}
    		
    	}
    	
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    // mouvement automatique des aliens de tober
    
    public void mouvementAlien()
    {
            
            float x = 0f;
            float y = 0f;        
            float ecartDeReplacement;
            
        //mouvement droite
            if(world.getDirectionAlienCourante().equals("droite"))
            {
                    for (Alien a : world.getListeAlien()) {
                        x= a.getPosition().x;
                            y= a.getPosition().y;                
                            a.setPosition(a.getPosition().set(x+vitesseAlien, y)); //x+vitesseAlien+1
                    }
            
            }
            
            //mouvement gauche
            if(world.getDirectionAlienCourante().equals("gauche"))
            {
                    for (Alien a : world.getListeAlien()) {
                        x= a.getPosition().x;
                            y= a.getPosition().y;                
                            a.setPosition(a.getPosition().set(x-vitesseAlien, y)); //x-vitesseAlien-1
                    }
                    
            
            }
            
            //mouvement vers le bas
            if(world.getDirectionAlienCourante().equals("bas"))
            {
                    
                    for (Alien a : world.getListeAlien()) {
                        x= a.getPosition().x;
                            y= a.getPosition().y;                
                            a.setPosition(a.getPosition().set(x, y - vitesseAlien));
                    }
                    //compteur de chute, on s'arrete qd = 0
                    sauvegardeDescente = sauvegardeDescente - vitesseAlien;
                    
                    
                    
                    //stop descente
                    if(sauvegardeDescente <= 0)
                    {
                            
                            if(world.getDirectionAlienSauvegarde().equals("droite"))
                            {
                                    world.setDirectionAlienCourante("gauche");
                                    world.setDirectionAlienSauvegarde("gauche");                                        
                                    
                                    for (Alien a : world.getListeAlien()) {
                                        x= a.getPosition().x;
                                            y= a.getPosition().y;                
                                            a.setPosition(new Vector2(x-vitesseAlien,y)); //x - vitesseAlien
                                    }                
                            }
                            
                            else if(world.getDirectionAlienSauvegarde().equals("gauche"))
                            {
                                    world.setDirectionAlienCourante("droite");
                                    world.setDirectionAlienSauvegarde("droite");
                                    
                                    
                                    for (Alien a : world.getListeAlien()) {
                                        x= a.getPosition().x;
                                            y= a.getPosition().y;                
                                            a.setPosition(new Vector2(x+vitesseAlien,y)); //x + vitesseAlien
                                    }
                            }
                    sauvegardeDescente = Constants.DESCENTE_AVANT_STABILISATION;
                    }                
                    
            }
    
            //if(AlienOutOfScreen()) {        // remet en meme temps les aliens en place
            //        world.setDirectionAlienCourante("bas");
            //}
            
            ecartDeReplacement = ecartPourReplacerAlienOutOfScreen();
            if (ecartPourReplacerAlienOutOfScreen()!=0) {
                    world.setDirectionAlienCourante("bas");
                    for (Alien a : world.getListeAlien()) {
                            x= a.getPosition().x;
                            y= a.getPosition().y;        
                            a.setPosition(new Vector2(x+ecartDeReplacement,y));
                    }
            }
            
           
            if (!(world.getListeAlien().size()==0)) {
            	for(Alien m : world.getListeAlien())
            	{
            		if (m.getPosition().y <=Constants.ORDONNEE_LIMITE_FIN_PARTIE ) {
            			System.out.println("depassement");
            			lives = 0;
            		}
            	}  
        	}
            
    }
    

    float ecartPourReplacerAlienOutOfScreen() {
            float ecart = 0;
            
            boolean outOfScreen = false; 
            
            float x = 0 ;
            float y = 0;
            float posX = 0;
            float limite = 0;
            for (int i = 0;( (i< world.getListeAlien().size()) && (outOfScreen==false) )        ; i++) 
            {
                    if (world.getDirectionAlienCourante().equals("droite")) 
                    {
                            posX = world.getListeAlien().get(i).getPosition().x;
                            limite = Constants.VIEWPORT_GUI_WIDTH - world.getListeAlien().get(i).getWidth();
                            if(posX> limite)//- vitesseAlien
                            {
                                    outOfScreen = true;
                                    ecart = limite - posX;
                            }
                    }
                    else if (world.getDirectionAlienCourante().equals("gauche")) 
                    {
                            posX = world.getListeAlien().get(i).getPosition().x ;
                            limite = 0 ;
                            if(posX < limite) //+vitesseAlien
                            {
                                    outOfScreen = true;
                                    ecart = limite - posX ;
                            }
                    }
            }
                            
            return ecart;
    }
    
    boolean AlienOutOfScreen()                                // dans sa forme courante , elle remet en place les aliens
    {
            boolean outOfScreen = false; 
            
            
                            // AVANT ON NE FAISAIT QUE CELA
            for (Alien a : world.getListeAlien()) {
                    if(a.getPosition().x > Constants.VIEWPORT_GUI_WIDTH - a.getWidth()  )//- vitesseAlien
                            outOfScreen = true;
                    
                    if(a.getPosition().x < 0 ) //+vitesseAlien
                            outOfScreen = true;
            }
            
            
            return outOfScreen;
    }

    
    
    
    
}
