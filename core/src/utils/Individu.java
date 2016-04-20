package utils;

import arbres.Node;

public class Individu {

	private int score;
	private Node tree;
	

	public Individu(int score, Node tree) {
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
	public Node getTree() {
		return tree;
	}
	public void setTree(Node tree) {
		this.tree = tree;
	}

	
	
	
}
