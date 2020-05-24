package org.insa.graphs.algorithm.test;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class AlgoCompare {


	
	// List of nodes
	private static Node[] nodes;

	private static ShortestPathSolution  invalidPathSolution;
	private ShortestPathSolution dijkstraSolution,aStarSolution,bellmanFordSolution;
	private static Graph graph;
	private static List<Node> nodesGraph;
	private ShortestPathData shortestPathData;

	private DijkstraAlgorithm dijkstraAlgorithm;
	private AStarAlgorithm aStarAlgorithm;
	private BellmanFordAlgorithm bellmanFordAlgorithm;
	// ArcInspector use for tests
	private static List<ArcInspector> listArcInspector;
	
	// Graph reader use for tests
	private static GraphReader reader;

	@BeforeClass
	public static void initAll() throws IOException {
		
		listArcInspector = ArcInspectorFactory.getAllFilters();
	}
	/*
	 * Chemin invalide
	 */
	@Test
	public void testInvalidGraph() {
		
		 
		// 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes
        nodes = new Node[5];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }
		//Arc a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null); supprimer le lien
		Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
		Node.linkNodes(nodes[0], nodes[4], 15, speed20, null);
		Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
		Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
		Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
		Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
		Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
		Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
		Node.linkNodes(nodes[4], nodes[0], 10, speed10, null);
		Graph invalidGraph = new Graph("ID", "", Arrays.asList(nodes), null);
		
		shortestPathData = new ShortestPathData(invalidGraph, nodes[0], nodes[1], listArcInspector.get(0));
		dijkstraAlgorithm = new DijkstraAlgorithm(shortestPathData);
		invalidPathSolution = dijkstraAlgorithm.doRun();
		assertTrue(!invalidPathSolution.isFeasible());
		invalidPathSolution = dijkstraAlgorithm.doRun();
		assertTrue(!invalidPathSolution.isFeasible());

	}
	/*
	 * test carre map
	 */
	@Test
	public void testCarre() throws IOException {
		String mapName = "/Users/wangyifan/Desktop/be-graphe/maps/carre.mapgr";
		
		reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph=reader.read();
		nodesGraph=graph.getNodes();
		
		shortestPathData = new ShortestPathData(graph, nodesGraph.get(1), nodesGraph.get(7), listArcInspector.get(0));
		
		dijkstraAlgorithm = new DijkstraAlgorithm(shortestPathData);
		dijkstraSolution=dijkstraAlgorithm.doRun();
				
		aStarAlgorithm = new AStarAlgorithm(shortestPathData);
		aStarSolution = aStarAlgorithm.doRun();
		
		bellmanFordAlgorithm = new BellmanFordAlgorithm(shortestPathData);
		bellmanFordSolution=bellmanFordAlgorithm.doRun();
		
		System.out.println(dijkstraSolution.getPath().getMinimumTravelTime());
		
		double resultatDijkstra=dijkstraSolution.getPath().getMinimumTravelTime();
		double resultatAStar=aStarSolution.getPath().getMinimumTravelTime();
		double resultatBellmanFord=bellmanFordSolution.getPath().getMinimumTravelTime();
		
		System.out.println(resultatDijkstra);
		
		assertTrue(resultatDijkstra==resultatBellmanFord);
		assertTrue(resultatDijkstra==resultatAStar);
		

	}
	/*
	 * test PathNull
	 */
	@Test
	public void testPathNull() {
		
		// Create nodes
		nodes = new Node[5];
		for (int i = 0; i < nodes.length; ++i) {
			nodes[i] = new Node(i, null);
		}
		
		
		Graph invalidGraph = new Graph("ID", "", Arrays.asList(nodes), null);
		
		shortestPathData = new ShortestPathData(invalidGraph, nodes[0], nodes[0], listArcInspector.get(0));
		
		dijkstraAlgorithm = new DijkstraAlgorithm(shortestPathData);
		dijkstraSolution=dijkstraAlgorithm.doRun();
				
		aStarAlgorithm = new AStarAlgorithm(shortestPathData);
		aStarSolution = aStarAlgorithm.doRun();
		
		bellmanFordAlgorithm = new BellmanFordAlgorithm(shortestPathData);
		bellmanFordSolution=bellmanFordAlgorithm.doRun();
		
		
		assertTrue(!dijkstraSolution.isFeasible());
		assertTrue(!aStarSolution.isFeasible());
		assertTrue(!bellmanFordSolution.isFeasible());
	}
	/*
	 * test insa map 1
	 */
	@Test
	
	public void testINSA() throws IOException {
		String mapName = "/Users/wangyifan/Desktop/be-graphe/maps/insa.mapgr";
		
		reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph=reader.read();
		nodesGraph=graph.getNodes();
		
		shortestPathData = new ShortestPathData(graph, nodesGraph.get(180), nodesGraph.get(78), listArcInspector.get(0));
		
		dijkstraAlgorithm = new DijkstraAlgorithm(shortestPathData);
		dijkstraSolution=dijkstraAlgorithm.doRun();
				
		aStarAlgorithm = new AStarAlgorithm(shortestPathData);
		aStarSolution = aStarAlgorithm.doRun();
		
		bellmanFordAlgorithm = new BellmanFordAlgorithm(shortestPathData);
		bellmanFordSolution=bellmanFordAlgorithm.doRun();
		
		System.out.println(dijkstraSolution.getPath().getMinimumTravelTime());
		
		double resultatDijkstra=dijkstraSolution.getPath().getMinimumTravelTime();
		double resultatAStar=aStarSolution.getPath().getMinimumTravelTime();
		double resultatBellmanFord=bellmanFordSolution.getPath().getMinimumTravelTime();
		
		System.out.println(resultatDijkstra);
		
		assertTrue(resultatDijkstra==resultatBellmanFord);
		assertTrue(resultatDijkstra==resultatAStar);
		

	}
	/*
	 * test insa map 2
	 */
	@Test
	public void testINSA2() throws IOException {
		String mapName = "/Users/wangyifan/Desktop/be-graphe/maps/insa.mapgr";
		
		reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph=reader.read();
		nodesGraph=graph.getNodes();
		
		shortestPathData = new ShortestPathData(graph, nodesGraph.get(99), nodesGraph.get(178), listArcInspector.get(0));
		
		dijkstraAlgorithm = new DijkstraAlgorithm(shortestPathData);
		dijkstraSolution=dijkstraAlgorithm.doRun();
				
		aStarAlgorithm = new AStarAlgorithm(shortestPathData);
		aStarSolution = aStarAlgorithm.doRun();
		
		bellmanFordAlgorithm = new BellmanFordAlgorithm(shortestPathData);
		bellmanFordSolution=bellmanFordAlgorithm.doRun();
		
		System.out.println(dijkstraSolution.getPath().getMinimumTravelTime());
		
		double resultatDijkstra=dijkstraSolution.getPath().getMinimumTravelTime();
		double resultatAStar=aStarSolution.getPath().getMinimumTravelTime();
		double resultatBellmanFord=bellmanFordSolution.getPath().getMinimumTravelTime();
		
		System.out.println(resultatDijkstra);
		
		assertTrue(resultatDijkstra==resultatBellmanFord);
		assertTrue(resultatDijkstra==resultatAStar);
		

	}
	/*
	 * test bordeaux
	 */
	@Test
	public void testBordeaux() throws IOException {
		String mapName = "/Users/wangyifan/Desktop/be-graphe/maps/bordeaux.mapgr";
		
		reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph=reader.read();
		nodesGraph=graph.getNodes();
		
		shortestPathData = new ShortestPathData(graph, nodesGraph.get(2133), nodesGraph.get(728), listArcInspector.get(0));
		
		dijkstraAlgorithm = new DijkstraAlgorithm(shortestPathData);
		dijkstraSolution=dijkstraAlgorithm.doRun();
				
		aStarAlgorithm = new AStarAlgorithm(shortestPathData);
		aStarSolution = aStarAlgorithm.doRun();
		
		bellmanFordAlgorithm = new BellmanFordAlgorithm(shortestPathData);
		bellmanFordSolution=bellmanFordAlgorithm.doRun();
		
		System.out.println(dijkstraSolution.getPath().getMinimumTravelTime());
		
		double resultatDijkstra=dijkstraSolution.getPath().getMinimumTravelTime();
		double resultatAStar=aStarSolution.getPath().getMinimumTravelTime();
		double resultatBellmanFord=bellmanFordSolution.getPath().getMinimumTravelTime();
		
		System.out.println(resultatDijkstra);
		
		assertTrue(resultatDijkstra==resultatBellmanFord);
		assertTrue(resultatDijkstra==resultatAStar);
		

	}
	/*
	 * test bordeaux2
	 */
	@Test
	public void testBordeaux2() throws IOException {
		String mapName = "/Users/wangyifan/Desktop/be-graphe/maps/bordeaux.mapgr";
		
		reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		graph=reader.read();
		nodesGraph=graph.getNodes();
		
		shortestPathData = new ShortestPathData(graph, nodesGraph.get(2153), nodesGraph.get(828), listArcInspector.get(0));
		
		dijkstraAlgorithm = new DijkstraAlgorithm(shortestPathData);
		dijkstraSolution=dijkstraAlgorithm.doRun();
				
		aStarAlgorithm = new AStarAlgorithm(shortestPathData);
		aStarSolution = aStarAlgorithm.doRun();
		
		bellmanFordAlgorithm = new BellmanFordAlgorithm(shortestPathData);
		bellmanFordSolution=bellmanFordAlgorithm.doRun();
		
		System.out.println(dijkstraSolution.getPath().getMinimumTravelTime());
		
		double resultatDijkstra=dijkstraSolution.getPath().getMinimumTravelTime();
		double resultatAStar=aStarSolution.getPath().getMinimumTravelTime();
		double resultatBellmanFord=bellmanFordSolution.getPath().getMinimumTravelTime();
		
		System.out.println(resultatDijkstra);
		
		assertTrue(resultatDijkstra==resultatBellmanFord);
		assertTrue(resultatDijkstra==resultatAStar);
		

	}
}
	