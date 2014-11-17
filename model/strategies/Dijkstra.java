package SMATP3.model.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import SMATP3.model.Snapshot;
import SMATP3.utils.Position;

class Vertex implements Comparable<Vertex> {
	/** position of the vertex */
    private final Position position;
    /** minimal distance to go to this vertex */
    private int minDistance = Integer.MAX_VALUE;
    /** previous vertex used to go here */
    private Vertex previous;
    
    public Vertex(Position argName) { this.position = argName; }
    public int compareTo(Vertex other) {
        return Integer.compare(minDistance, other.minDistance);
    }
    
    public Position getPosition() {
    	return this.position;
    }
    
    public Vertex getPrevious()  {
    	return this.previous;
    }
    
    public int getMinDistance() {
    	return this.minDistance;
    }
    
    public void setMinDistance(int value) {
    	this.minDistance = value;
    }
    
    public void setPrevious(Vertex v) {
    	this.previous = v;
    }
}

public class Dijkstra {
	/** the snapshot used for this algorithm */
	private Snapshot snap;
	/** the beginning position */ 
	private Vertex source;
	private HashMap<Position, Vertex> vertices;
	
	public Dijkstra(Snapshot snap, Position source) {
		this.snap = snap;
		this.source = new Vertex(source);
		this.vertices = new HashMap<Position, Vertex>();
        this.vertices.put(this.source.getPosition(), this.source);
	}
	
	
	/**
	 * Set the minDistance of all vertices
	 * 
	 */
    private void computePaths() {
    	// begin with score of 0
        this.source.setMinDistance(0);
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        
        // adding the source to the queue to execute
      	vertexQueue.add(this.source);

      	while (!vertexQueue.isEmpty()) {
      		// get the first vertex to use in the queue
      		Vertex u = vertexQueue.poll();

      		// Visit each edge exiting u
            for (Position e : this.snap.getEmptyNeighbourhood(u.getPosition())) {
            	// the neighbour to test
            	Vertex v = this.vertices.get(e);
            	if (v == null) {
            		v = new Vertex(e);
            		this.vertices.put(e, v);
            	}
                // adding 1 to the distance (the cost of moving)
                int distanceThroughU = u.getMinDistance() + 1;
                // if this is 
                if (distanceThroughU < v.getMinDistance()) {
                	vertexQueue.remove(v);
                	v.setMinDistance(distanceThroughU);
                	v.setPrevious(u);
                	vertexQueue.add(v);
                }
            }
        }
    }

    public List<Position> getShortestPathTo(Position target) {
    	this.computePaths();
    	String s = "route : ";
        List<Position> path = new ArrayList<Position>();
        for (Vertex vertex = this.vertices.get(target); vertex != null; vertex = vertex.getPrevious()) {
            path.add(vertex.getPosition());
            s += ", " + vertex.getPosition();
        }
System.out.println(s);
        Collections.reverse(path);
        return path;
    }
}
