package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{
	
	private Node node;
	private boolean mark;
	private float cost;
	private Node predecessor;

	
	
	public Label(Node node) {
		super();
		this.node = node;
		this.mark = false;
		this.cost = Float.POSITIVE_INFINITY;
		this.predecessor = null;
		
	}
	public Node getNode() {
		return this.node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public boolean isMark() {
		return this.mark;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}

	public float getCost() {
		return this.cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public Node getPredecessor() {
		return this.predecessor;
	}

	public void setPredecessor(Node predecessor) {
		this.predecessor = predecessor;
	}

	
	
	@Override
	public int compareTo(Label other) {
		// TODO Auto-generated method stub
		int result;
		if (this.getCost() < other.getCost()) {
			result = -1;
		}
		else if (this.getCost() == other.getCost()) {
			result = 0;
		}
		else {
			result = 1;
		}
		return result;
	}
	
	
	
}