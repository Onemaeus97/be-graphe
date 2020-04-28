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
        //int sizeGraph = graph.size();
       // Label [] tabLabel = new Label[sizeGraph];
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
        boolean fin = false;
      while(count < nbNodes && fin == false) {
        	
        	Label x = Heap.deleteMin();
        	if (x.getNode() == data.getDestination()) {
				fin = true;
			}
            int index = Labels.indexOf(x);
            Labels.get(index).setMark(true);
            notifyNodeReached(Labels.get(index).getNode());
            System.out.println("marquage"+count+"  "+nbNodes);
        	count++;
            for(int i = 0; i < x.getNode().getNumberOfSuccessors();i++) {
            	//int index2 = Nodes.indexOf(x.getNode().getSuccessors().get(i).getDestination());
            	//Label tmp = Labels.get(index2);
            	//Label tmp = tabLabel[x.getNode().getSuccessors().get(i).getDestination().getId()];
            	Label tmp = Labels.get(x.getNode().getSuccessors().get(i).getDestination().getId());
            	if(!tmp.isMark()) {
            		if(tmp.getCost()>x.getCost()+x.getNode().getSuccessors().get(i).getLength()) {
            			tmp.setCost(x.getCost()+x.getNode().getSuccessors().get(i).getLength());
            			tmp.setPredecessor(x.getNode());
            			/*
            			{
            				Heap.remove(tmp);
            				Heap.insert(tmp);
            				}
            				catch(ElementNotFoundException e) // 写法规定？ exception后面跟个e
            				{
            					Heap.insert(tmp);	
            				}*/
            	
            			if(!tmp.isInHeap()) {
            				Heap.insert(tmp);	
            			}
            			else {
            				Heap.remove(tmp);
            				Heap.insert(tmp);
            			}
            			tmp.setInHeap(); 
        
            			
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
          		//boolean trouve = false;
          		 try {  Arc arcmin = null;
                       for(int i = 0;i < tmp.getPredecessor().getNumberOfSuccessors() ; i++) {
                    	
                      	if(arcmin == null && tmp.getPredecessor().getSuccessors().get(i).getDestination()==tmp.getNode()) {
                      		arcmin = tmp.getPredecessor().getSuccessors().get(i);
                        }
                      	if(arcmin != null && arcmin.getLength()>tmp.getPredecessor().getSuccessors().get(i).getLength() &&  tmp.getPredecessor().getSuccessors().get(i).getDestination()==tmp.getNode())
                      		arcmin = tmp.getPredecessor().getSuccessors().get(i);
                       }
                        arcs.add(arcmin);
                  		int index3 = Nodes.indexOf(tmp.getPredecessor());
                  		System.out.println("arc");
                  		tmp = Labels.get(index3);
                  		
               }
               catch(NullPointerException e2) {
            	   System.out.println("NullPointerException e2");
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
    	  System.out.println("EmptyPriorityQueueException e2");
        	ArrayList<Arc> arcs = new ArrayList<>();
            // ArrayList<Node> chemin = new ArrayList<>();
              int indexdestination = Nodes.indexOf(data.getDestination()); 
          	Label tmp = Labels.get(indexdestination);
          	for(int i =0;i<Labels.size();i++) {
          		System.out.println(Labels.get(i).getPredecessor());
          	}
        	while(tmp.getPredecessor() != null) {
          		//boolean trouve = false;
          		 try {
          			Arc arcmin = null;
                    for(int i = 0;i < tmp.getPredecessor().getNumberOfSuccessors() ; i++) {
                 	
                   	if(arcmin == null && tmp.getPredecessor().getSuccessors().get(i).getDestination()==tmp.getNode()) {
                   		arcmin = tmp.getPredecessor().getSuccessors().get(i);
                     }
                   	if(arcmin != null && arcmin.getLength()>tmp.getPredecessor().getSuccessors().get(i).getLength() &&  tmp.getPredecessor().getSuccessors().get(i).getDestination()==tmp.getNode())
                   		arcmin = tmp.getPredecessor().getSuccessors().get(i);
                    }
                     arcs.add(arcmin);
               		int index3 = Nodes.indexOf(tmp.getPredecessor());
               		System.out.println("arc");
               		tmp = Labels.get(index3);
               }
               catch(NullPointerException e3) {
            	   System.out.println("NullPointerException e3");
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
