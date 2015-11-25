package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BTC on 11/24/15.
 */
public class Graph {
   List<Vertex> vertices;
   List<Edge> edges;

   public Graph() {
      this.vertices = new ArrayList<>();
      this.edges = new ArrayList<>();
   }

   public void addVertex(Vertex vertex) {
      vertices.add(vertex);
      vertex.setName(String.valueOf(vertices.size()));
   }

   public void addEdge(Edge edge) {
      edges.add(edge);
      Vertex vertex1 = edge.getVertex1();
      vertex1.addEdge(edge);

      Vertex vertex2 = edge.getVertex2();
      Edge edge2 = new Edge(vertex2, vertex1);
      vertex2.addEdge(edge2);
      edges.add(edge2);
   }

   public int getVerticesCount() {
      return vertices.size();
   }

   public int getEdgesCount() {
      return edges.size();
   }

   public void reset() {
      for (Vertex vertex: vertices) {
         vertex.resetState();
      }
   }
   public List<Vertex> getVertices() {
      return vertices;
   }

   public void setVertices(List<Vertex> vertices) {
      this.vertices = vertices;
   }

   public List<Edge> getEdges() {
      return edges;
   }

   public void setEdges(List<Edge> edges) {
      this.edges = edges;
   }

   public void draw(Graphics g) {
      for (Edge edge : this.edges) {
         edge.draw(g);
      }
      for (Vertex vertex : this.vertices) {
         vertex.draw(g);
      }
   }

   @Override
   public String toString() {
      return "Graph{" +
            "V=" + vertices +
            ", E=" + edges +
            '}';
   }
}
