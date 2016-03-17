package arbres;

import java.util.Random;

import utils.Constants;
import utils.EActionTank;

public class BinaryTree
{
    static Node root;

    public BinaryTree(ElementNoeud data)
    {
        root = new Node(data);
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

    	b.ajoutAleatoireNoeud(1, root);
    	
    	return b;
    }
    
    public void ajoutAleatoireNoeud(int hauteur, Node root)
    {
    	//si on a dépassé la hauteur, on force un noeud ternaire
    	if(hauteur==Constants.HAUTEUR_MAX_ARBRE)
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
    	    	ajoutAleatoireNoeud(hauteur+1, gauche);
	
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
    	    	ajoutAleatoireNoeud(hauteur+1, droit);	
    		}
	
    	}
    
    }
    
    public static int foncRandom(int max)
    {
    	Random rand = new Random();
		int nombreAleatoire = rand.nextInt(max);
		return nombreAleatoire;
    }
    
}
