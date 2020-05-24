package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.EmptyPriorityQueueException;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    public void initialisation(ArrayList <Node> Nodes,ArrayList <Label> Labels, BinaryHeap <Label> Heap) {
    	final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
       
        final int nbNodes = graph.size();
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
    }

    public ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        ArrayList <Label> Labels = new ArrayList<Label>();
        ArrayList <Node> Nodes= new ArrayList<Node>();
        BinaryHeap <Label> Heap = new BinaryHeap<Label>();
        
        if(data.getOrigin()==data.getDestination()) { //test de feasabilité
        	return new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        long startTime=System.currentTimeMillis();   //获取开始时间  
        
        initialisation(Nodes,Labels, Heap);
     try {
        int count = 0;
        boolean fin = false;
      while(count < nbNodes && fin == false) {
        	
        	Label x = Heap.deleteMin();
        	if (x.getNode() == data.getDestination()) {
				fin = true;
			}
            //int index = Labels.indexOf(x);
            Labels.get(x.getNode().getId()).setMark(true); //why node id can be used in labels???
            notifyNodeReached(Labels.get(x.getNode().getId()).getNode());
            System.out.println("marquage"+count+"  "+nbNodes);
        	count++;
            for(int i = 0; i < x.getNode().getNumberOfSuccessors();i++) {
            	Label tmp = Labels.get(x.getNode().getSuccessors().get(i).getDestination().getId());
            	if(!tmp.isMark()) {
            		float ttmp = x.getCost()+x.getNode().getSuccessors().get(i).getLength();
            		if(tmp.getCost()> ttmp) {
            			tmp.setCost(ttmp);
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
                  		//int index3 = Nodes.indexOf(tmp.getPredecessor());
                        int index3 = tmp.getPredecessor().getId();
                  		System.out.println("arc");
                  		tmp = Labels.get(index3);
                  		
               }
               catch(NullPointerException e2) {
            	   System.out.println("NullPointerException e2");
                       System.out.println("sort");
                       Collections.reverse(arcs);
                       if(arcs.size()>0) {
                    	   if(arcs.get(arcs.size()-1).getDestination() != data.getDestination())
                        	   return new ShortestPathSolution(data, Status.INFEASIBLE);
                       }
                       else {
                    	       return new ShortestPathSolution(data, Status.INFEASIBLE);
                       }
                       solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
                       Path tmpp =  new Path(graph, arcs);
                       System.out.println(tmpp.isValid());
                       long endTime=System.currentTimeMillis(); //fin  
                       System.out.println("temps effectué： "+(endTime-startTime)+"ms");  
                       return solution;
               }
          		
          		
          	}
          	System.out.println("sort");
          	Collections.reverse(arcs);
          	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
          	Path tmpp =  new Path(graph, arcs);
            System.out.println(tmpp.isValid());
            long endTime=System.currentTimeMillis(); //fin  
            System.out.println("temps effectué： "+(endTime-startTime)+"ms");  
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
                   //int index3 = Nodes.indexOf(tmp.getPredecessor());
                     int index3 = tmp.getPredecessor().getId();
               		System.out.println("arc");
               		tmp = Labels.get(index3);
               }
               catch(NullPointerException e3) {
            	   System.out.println("NullPointerException e3");
                       System.out.println("sort");
                       Collections.reverse(arcs);
                       if(arcs.size()>0) {
                    	   if(arcs.get(arcs.size()-1).getDestination() != data.getDestination())
                        	   return new ShortestPathSolution(data, Status.INFEASIBLE);
                       }
                       else {
                    	       return new ShortestPathSolution(data, Status.INFEASIBLE);
                       }
                       solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
                       Path tmpp =  new Path(graph, arcs);
                       System.out.println(tmpp.isValid());
                       long endTime=System.currentTimeMillis(); //fin  
                       System.out.println("temps effectué： "+(endTime-startTime)+"ms");  
                       return solution;
               }
          		
          		
          	}
          	System.out.println("sort");
          	Collections.reverse(arcs);
          	if(arcs.size()>0) {
         	   if(arcs.get(arcs.size()-1).getDestination() != data.getDestination())
             	   return new ShortestPathSolution(data, Status.INFEASIBLE);
            }
            else {
         	       return new ShortestPathSolution(data, Status.INFEASIBLE);
            }
          	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
          	Path tmpp =  new Path(graph, arcs);
            System.out.println(tmpp.isValid());
            long endTime=System.currentTimeMillis(); //fin  
            System.out.println("temps effectué： "+(endTime-startTime)+"ms");   
          	return solution;
        }
    

          	
          	
          	
    }
}
