package arbres;
import utils.EActionTank;
import arbres.TreePrinter;
import model.World;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import arbres.BinaryTree;
public class mainarbre {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

		
		BinaryTree b = BinaryTree.generationTreeAleatoire();
		
		TreePrinter.print(b.root);
		
		b.root.printTree(new OutputStreamWriter(System.out));

		//System.out.println(out.toString());
		
		/*
		BinaryTree theTree = new BinaryTree(new Fonctions(EComparaisonFeatures.TankxSupMissilePlusProche));

	
		Node n1=new Node(new Fonctions(EComparaisonFeatures.TankxSupAlienPlusProchex));
        Node n2=new Node(new ElementTernaire(EActionTank.Left));
        Node n3=new Node(new ElementTernaire(EActionTank.Left));
        Node n4=new Node(new ElementTernaire(EActionTank.Right));

		
		theTree.add(BinaryTree.root, n1, "left");

		theTree.add(BinaryTree.root,n2,"right");
		theTree.add(n1,n3,"left");
		theTree.add(n1,n4,"right");
		

		Method method = null;
		boolean test = false;
		try {
			method = n1.getKey().getClass().getMethod(n1.getKey().toStringMethod(),World.class);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test = (Boolean) method.invoke(n1.getKey(),new World(new ArrayList<Integer>()));
		
		System.out.println(test);
		
		TreePrinter.print(BinaryTree.root);
		*/
		
}

}
