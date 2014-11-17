package SMATP3.model.strategies;

import SMATP3.model.Snapshot;
import SMATP3.utils.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

class Vertex implements Comparable<Vertex> {
    private final Position position;
    private int minDistance = Integer.MAX_VALUE;
    private Vertex previous;
    
    public Vertex(Position argName) { this.position = argName; }
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
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
	private Snapshot snap;
	private Vertex source;
	
	public Dijkstra(Snapshot snap, Position source) {
		this.snap = snap;
		this.source = new Vertex(source);
	}
	
	
	
    private void computePaths() {
        this.source.setMinDistance(0);
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(this.source);

      	while (!vertexQueue.isEmpty()) {
      		Vertex u = vertexQueue.poll();

      		// Visit each edge exiting u
            for (Position e : this.snap.getEmptyNeighbourhood(u.getPosition())) {
                Vertex v = new Vertex(e);
                int distanceThroughU = u.getMinDistance() + 1;
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
        List<Position> path = new ArrayList<Position>();
        for (Vertex vertex = new Vertex(target); vertex != null; vertex = vertex.getPrevious()) {
            path.add(vertex.getPosition());
        }
        Collections.reverse(path);
        return path;
    }
}
