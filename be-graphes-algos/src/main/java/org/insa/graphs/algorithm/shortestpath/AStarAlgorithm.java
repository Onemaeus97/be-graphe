package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Point;
import java.util.ArrayList;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.GraphStatistics;
import org.insa.graphs.model.Node;



public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    public float getEstimation(Node node) {
		final ShortestPathData data = getInputData();
		Point pointNode=node.getPoint();
		Point pointNodeDest=data.getDestination().getPoint();
		float distance=(float)Point.distance(pointNode, pointNodeDest);
		Mode m=data.getMode();
		if(m==Mode.LENGTH) {
			return distance;
		}else {
			if(data.getMaximumSpeed()==GraphStatistics.NO_MAXIMUM_SPEED) {
				return distance/data.getGraph().getGraphInformation().getMaximumSpeed();
			}else {
				return distance/data.getMaximumSpeed();
			}
		}
    }
    public void initialisation(ArrayList <Node> Nodes,ArrayList <Label> Labels, BinaryHeap <Label> Heap) {
    	final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
    	for(int i = 0; i < nbNodes;i++) { //initialisation
        	Nodes.add(graph.get(i));
        	if(data.getOrigin()==graph.get(i)) {
        		//indexorigin = i;
        		LabelStar origin = new LabelStar(graph.get(i));
        		origin.setCost(0);
        		origin.setEstimation(getEstimation(graph.get(i)));
        		Heap.insert(origin);
        		Labels.add((LabelStar) origin);
        	}
        	else {
        		LabelStar tmp = new LabelStar(graph.get(i));
        		tmp.setEstimation(getEstimation(graph.get(i)));
        		Labels.add(tmp);
        	}
        }
    }

}
