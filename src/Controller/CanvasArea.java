package Controller;

import Model.Edge;
import Model.Graph;
import Model.Vertex;
import Supports.DialogHelpers;
import Supports.Line;
import Supports.Vector2DHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BTC on 11/23/15.
 */
public class CanvasArea extends JPanel {

   Graph graph;

   Vertex currentVertex;
   Point previousPoint;
   // temporary line
   Line tempLine;

   // events
   void initializeData() {
      graph = new Graph();
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(800, 550);
   }

   public CanvasArea() {
      setBackground(Color.white);
      initializeData();
   }

   public void addVertexAtPosition(Point position) {
      Vertex vertex = new Vertex(position);
     addVertex(vertex);
   }

   public void addVertex(Vertex vertex) {
      graph.addVertex(vertex);
      vertex.setName(String.valueOf(graph.getVerticesCount()));
      repaint();
   }

   public void addEdge(Edge edge) {
      graph.addEdge(edge);

      repaint();
   }

   public Graph getGraph() {
      return graph;
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (tempLine != null) {
         tempLine.draw(g);
      }
      graph.draw(g);
   }

   public void verticeMousePressed(MouseEvent e) {
      if(e.getClickCount() == 2) {
         for (Edge edge : graph.getEdges()) {
            if (edge.contains(e.getPoint())) {
               String input = DialogHelpers.showInPutDialog("Input", "Enter cost: ");
               try {
                  int number = Integer.parseInt(input);
                  edge.setCost(number);
                  repaint();
               } catch (NumberFormatException e1) {
                  // e1.printStackTrace();
               }
            }
         }
         return;
      }

      if (SwingUtilities.isLeftMouseButton(e)) {
         for (Vertex vertex : graph.getVertices()) {
            if (vertex.contains(e.getPoint())) {
               currentVertex = vertex;
               previousPoint = e.getPoint();
               return;
            }
         }

         for (Edge edge : graph.getEdges()) {
            if (edge.contains(e.getPoint())) {

            }
         }
      } else if (SwingUtilities.isRightMouseButton(e)) {
         for (Vertex vertex : graph.getVertices()) {
            if (vertex.contains(e.getPoint())) {
               currentVertex = vertex;
               tempLine = new Line();
               tempLine.setPoint1(e.getPoint());
            }
         }
      }
   }

   public void verticeMouseDragged(MouseEvent e) {
      if (SwingUtilities.isLeftMouseButton(e)) {
         if (currentVertex != null) {
            Point diff = Vector2DHelper.SubstractVector(e.getPoint(), previousPoint);
            currentVertex.setLocation(Vector2DHelper.AddVector(currentVertex.getLocation(), diff));
            previousPoint = e.getPoint();
            repaint();
         }
      } else if(SwingUtilities.isRightMouseButton(e)) {
         if (tempLine != null) {
            tempLine.setPoint2(e.getPoint());
            repaint();
         }
      }
   }

   public void verticeMouseReleased(MouseEvent e) {

         if (tempLine != null) {
            for (Vertex vertex : graph.getVertices()) {
               if (vertex == currentVertex) continue;
               if (vertex.contains(e.getPoint())) {
                  Edge edge = new Edge(currentVertex, vertex);
                  addEdge(edge);
                  break;
               }
            }
            tempLine = null;
            repaint();
         }
         currentVertex = null;
   }
}
