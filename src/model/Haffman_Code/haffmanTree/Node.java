package model.Haffman_Code.haffmanTree;

import java.io.Serializable;

public class Node implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer possibility;
	private Node left;
	private Node right;
	private Node parent;
	private boolean one = false;  // false - 0, true - 1

	public Node(Integer possibility){
		this.possibility = possibility;
	}

	public Node(Node l, Node r) {
		this.possibility = l.possibility + r.possibility;
		l.setParent(this);
		l.setOne(false);
		r.setParent(this);
		r.setOne(true);
		left = l;
		right = r;
	}

	public Integer getPossibility() {
		return possibility;
	}

	public void setPossibility(Integer possibility) {
		this.possibility = possibility;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public boolean isOne() {
		return one;
	}

	public void setOne(boolean one) {
		this.one = one;
	}
}
