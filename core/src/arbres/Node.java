package arbres;

import java.io.IOException;
import java.io.OutputStreamWriter;

import arbres.TreePrinter.PrintableNode;

public class Node implements PrintableNode{
    private ElementNoeud key; //ternaire ou decisionelle
    private Node left;
    private Node right;
   
    public Node (ElementNoeud key) {
        this.key = key;
        right = null;
        left = null;
    }

    public Node(Node n) {
		key = n.key;
		right = n.right;
		left = n.left;
	}

    public Node(ElementNoeud key, Node left, Node right) {
		this.key = key;
		this.right = right;
		this.left = left;
	}
    
    public Node copyNode()
    {
	    Node left = null;
	    Node right = null;
	    if (this.left != null) {
	        left = this.left.copyNode();
	    }
	    if (this.right != null) {
	        right = this.right.copyNode();
	    }
	    return new Node(key,left,right);
}
    
    
	public void setKey(ElementNoeud key) {
        this.key = key;
    }

    public ElementNoeud getKey() {
        return key;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getLeft() {
        return left;
    }

    public void setRight(Node right ) {
        this.right = right;
    }

    public Node getRight() {
        return right;
    }

	@Override
	public String getText() {
		return String.valueOf(key.toStringAffichage());
	}
	
	
    public void printTree(OutputStreamWriter out) throws IOException {
        if (right != null) {
            right.printTree(out, true, "");
        }
        printNodeValue(out);
        if (left != null) {
            left.printTree(out, false, "");
        }
        
        out.flush();
    }
    private void printNodeValue(OutputStreamWriter out) throws IOException {
        if (key == null) {
            out.write("<null>");
        } else {
            out.write(key.toStringAffichage());
        }
        out.write('\n');
    }
    // use string and not stringbuffer on purpose as we need to change the indent at each recursion
    private void printTree(OutputStreamWriter out, boolean isRight, String indent) throws IOException {
        if (right != null) {
            right.printTree(out, true, indent + (isRight ? "        " : " |      "));
        }
        out.write(indent);
        if (isRight) {
            out.write(" /");
        } else {
            out.write(" \\");
        }
        out.write("----- ");
        printNodeValue(out);
        if (left != null) {
            left.printTree(out, false, indent + (isRight ? " |      " : "        "));
        }
    }
	
	
}