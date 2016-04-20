package utils;

import arbres.BinaryTree;

public class Individu {

	private int score;
	private BinaryTree tree;
	

	public Individu(int score, BinaryTree tree) {
		super();
		this.score = score;
		this.tree = tree;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public BinaryTree getTree() {
		return tree;
	}
	public void setTree(BinaryTree tree) {
		this.tree = tree;
	}

	
	
	
}
