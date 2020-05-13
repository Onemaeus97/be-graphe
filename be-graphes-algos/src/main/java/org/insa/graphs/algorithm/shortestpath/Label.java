package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{
	
	private Node node;
	private boolean mark;
	private float cost;
	private Node predecessor;
	private boolean inHeap;
	
	
	public Label(Node node) {
		super();
		this.node = node;
		this.mark = false;
		this.cost = Float.POSITIVE_INFINITY;
		this.predecessor = null;
		this.inHeap = false;
		
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
	public float getTotalCost() {
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

	public boolean isInHeap() {
		return this.inHeap;
	}
	public void setInHeap() {
		this.inHeap = true;
	}

	
	@Override
	public int compareTo(Label other) {
		// TODO Auto-generated method stub
		
		return (int)(this.getTotalCost()-other.getTotalCost());
	}
	
	
	
}