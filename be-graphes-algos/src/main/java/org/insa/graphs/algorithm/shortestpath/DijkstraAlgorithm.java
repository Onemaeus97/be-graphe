package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.algorithm.utils.EmptyPriorityQueueException;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        ArrayList <Label> Labels = new ArrayList<Label>();
        ArrayList <Node> Nodes= new ArrayList<Node>();
        BinaryHeap <Label> Heap = new BinaryHeap<Label>();
        //int indexorigin;
        for(int i = 0; i < nbNodes;i++) { //initialisation
        	Nodes.add(graph.get(i));
        	if(data.getOrigin()==graph.get(i)) {
        		//indexorigin = i;
        		Label origin = new Label(graph.get(i));
        		origin.setCost(0);
        		Heap.insert(origin);
        		Labels.add(origin);
        	}
        	else {
        		Label tmp = new Label(graph.get(i));
        		Labels.add(tmp);
        	}
        }
        
        
        
      try {
        int count = 0;
        while(count < nbNodes) {
        	Label x = Heap.findMin();
            Heap.deleteMin();
            int index = Labels.indexOf(x);
            Labels.get(index).setMark(true);
            notifyNodeReached(Labels.get(index).getNode());
            System.out.println("marquage"+count+"  "+nbNodes);
        	count++;
            for(int i = 0; i < x.getNode().getNumberOfSuccessors();i++) {
            	int index2 = Nodes.indexOf(x.getNode().getSuccessors().get(i).getDestination());
            	if(!Labels.get(index2).isMark()) {
            		if(Labels.get(index2).getCost()>x.getCost()+x.getNode().getSuccessors().get(i).getLength()) {
            			Labels.get(index2).setCost(x.getCost()+x.getNode().getSuccessors().get(i).getLength());
            			Labels.get(index2).setPredecessor(x.getNode());
            			try
            	    	{
            				Heap.remove(Labels.get(index2));
            				Heap.insert(Labels.get(index2));
            	    	}
            	    	catch(ElementNotFoundException e) // 写法规定？ exception后面跟个e
            	    	{
            	    		Heap.insert(Labels.get(index2));
            	    	}
            		}
            		 
            	}
            }
        }
        	ArrayList<Arc> arcs = new ArrayList<>();
            // ArrayList<Node> chemin = new ArrayList<>();
              int indexdestination = Nodes.indexOf(data.getDestination()); 
          	Label tmp = Labels.get(indexdestination);
          	for(int i =0;i<Labels.size();i++) {
          		System.out.println(Labels.get(i).getPredecessor());
          	}
              
          	while(tmp.getPredecessor() != null) {
          		boolean trouve = false;
          		 try {
                       for(int i = 0;i < tmp.getPredecessor().getNumberOfSuccessors() && !trouve; i++) {
                      	if(tmp.getPredecessor().getSuccessors().get(i).getDestination()==tmp.getNode()) {
                   		arcs.add(tmp.getPredecessor().getSuccessors().get(i));
                   			    //change tmp
                   		int index3 = Nodes.indexOf(tmp.getPredecessor());
                   		System.out.println("arc");
                   		tmp = Labels.get(index3);
                   		trouve = true;
                   		}
                     }
               }
               catch(NullPointerException e2) {
                       System.out.println("sort");
                       Collections.reverse(arcs);
                       solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
                       return solution;
               }
          		
          		
          	}
          	System.out.println("sort");
          	Collections.reverse(arcs);
          	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
          	return solution;
        }
      
      
      
      catch(EmptyPriorityQueueException e2) {
        	ArrayList<Arc> arcs = new ArrayList<>();
            // ArrayList<Node> chemin = new ArrayList<>();
              int indexdestination = Nodes.indexOf(data.getDestination()); 
          	Label tmp = Labels.get(indexdestination);
          	for(int i =0;i<Labels.size();i++) {
          		System.out.println(Labels.get(i).getPredecessor());
          	}
        	while(tmp.getPredecessor() != null) {
          		boolean trouve = false;
          		 try {
                       for(int i = 0;i < tmp.getPredecessor().getNumberOfSuccessors() && !trouve; i++) {
                      	if(tmp.getPredecessor().getSuccessors().get(i).getDestination()==tmp.getNode()) {
                   		arcs.add(tmp.getPredecessor().getSuccessors().get(i));
                   			    //change tmp
                   		int index3 = Nodes.indexOf(tmp.getPredecessor());
                   		System.out.println("arc");
                   		tmp = Labels.get(index3);
                   		trouve = true;
                   		}
                     }
               }
               catch(NullPointerException e3) {
                       System.out.println("sort");
                       Collections.reverse(arcs);
                       solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
                       return solution;
               }
          		
          		
          	}
          	System.out.println("sort");
          	Collections.reverse(arcs);
          	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
          	return solution;
        }
    

          	
          	
          	
          	
    }
}
