package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class LabelStar extends Label  implements Comparable<Label>{

	private float estimation;
	public LabelStar(Node node) {
		super(node);
		this.estimation = Float.POSITIVE_INFINITY;
	}
	public double getEstimation() {
		return estimation;
	}
	public void setEstimation(float estimation) {
		this.estimation = estimation;
	}
	public float getTotalCost() {
		return getCost()+estimation;
	}

	

}
