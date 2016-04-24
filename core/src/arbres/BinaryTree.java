package arbres;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import model.World;
import utils.Constants;
import utils.EActionTank;

public class BinaryTree
{
    public static Node root;

    public BinaryTree(ElementNoeud data)
    {
        setRoot(new Node(data));
    }

    
    public BinaryTree(Node n)
    {
        setRoot(new Node(n));
    }
    
    
    
    public void add(Node parent,Node child, String orientation)
    {
        if(orientation=="left")
        {
           parent.setLeft(child);
        }
        else if (orientation=="right")
        {
            parent.setRight(child);
        }

    }
    
    
    public static BinaryTree generationTreeAleatoire()
    {
    	int nombreAleatoire = foncRandom(EComparaisonFeatures.values().length);
    	//creation racine <- fonction
    	BinaryTree b = new BinaryTree((new Fonctions(EComparaisonFeatures.values()[nombreAleatoire])));

    	b.ajoutAleatoireNoeud(1, getRoot(), Constants.HAUTEUR_MAX_ARBRE);
    	
    	return b;
    }
    
    
    
    public void ajoutAleatoireNoeud(int hauteur, Node root, int hauteurMaxArbre)
    {
    	//si on a d�pass� la hauteur, on force un noeud ternaire
    	if(hauteur==hauteurMaxArbre)
    	{
    		//fils gauche
    		int nombreAleatoire = foncRandom(EActionTank.values().length);
    		Node gauche = new Node(new ElementTernaire(EActionTank.values()[nombreAleatoire]));
    		this.add(root, gauche, "left");
    		
    		//fils droit
    		
    		nombreAleatoire = foncRandom(EActionTank.values().length);
    		Node droit = new Node(new ElementTernaire(EActionTank.values()[nombreAleatoire]));
    		this.add(root, droit, "right");
    	}
    	else
    	{
    		//gauche
    		//decision ou ternaire?
    		int nb = foncRandom(2);
    		if(nb==0) //ternaire
    		{
        		int nombreAleatoire = foncRandom(EActionTank.values().length);
        		Node gauche = new Node(new ElementTernaire(EActionTank.values()[nombreAleatoire]));
        		this.add(root, gauche, "left");    			
    		}
    		else //fonction
    		{
    	    	int nombreAleatoire = foncRandom(EComparaisonFeatures.values().length);
    	    	Node gauche = new Node(new Fonctions(EComparaisonFeatures.values()[nombreAleatoire]));
    	    	this.add(root, gauche, "left");
    	    	ajoutAleatoireNoeud(hauteur+1, gauche, hauteurMaxArbre);
	
    		}
    		
    		
    		//droit
    		//decision ou ternaire?
    		nb = foncRandom(2);
    		if(nb==0) //ternaire
    		{
        		int nombreAleatoire = foncRandom(EActionTank.values().length);
        		Node droit = new Node(new ElementTernaire(EActionTank.values()[nombreAleatoire]));
        		this.add(root, droit, "right");    			
    		}
    		else //fonction
    		{
    	    	int nombreAleatoire = foncRandom(EComparaisonFeatures.values().length);
    	    	Node droit = new Node(new Fonctions(EComparaisonFeatures.values()[nombreAleatoire]));
    	    	this.add(root, droit, "right");
    	    	ajoutAleatoireNoeud(hauteur+1, droit, hauteurMaxArbre);	
    		}
	
    	}
    
    }
    
    public static int foncRandom(int max)
    {
    	Random rand = new Random();
		int nombreAleatoire = rand.nextInt(max);
		return nombreAleatoire;
    }

	public static Node getRoot() {
		return root;
	}

	public static void setRoot(Node root) {
		BinaryTree.root = root;
	}
    
	
	// va parcourir l'arbre et donner une décision au tank par rapport au parcours effectué de l'arbre
	public EActionTank decisionTank(World w) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		
		Node noeudEnCours = BinaryTree.root;
		
		while(noeudEnCours.getKey() instanceof Fonctions)
		{
			
			//System.out.println(noeudEnCours.getKey().toStringAffichage());
			Method method = null;
			boolean test = false;
			try {
				method = noeudEnCours.getKey().getClass().getMethod(noeudEnCours.getKey().toStringMethod(),World.class);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}

			test = (Boolean) method.invoke(noeudEnCours.getKey(),w);
			//System.out.println(test);
			//si le resultat de la fonction est vrai, on part dans le fils droit
			if(test == true)
			{
				noeudEnCours = noeudEnCours.getRight();
			}
			//si le resultat de la fonction est faux, on part dans le fils gauche
			else
			{
				noeudEnCours = noeudEnCours.getLeft();
			}	
			
		}
		
		ElementTernaire elem = (ElementTernaire) noeudEnCours.getKey();
		return elem.action;
		
	}
	
	
	
}
