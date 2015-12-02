package SearchAlgorithm.View;

import SearchAlgorithm.Model.Edge;
import SearchAlgorithm.Model.Graph;
import SearchAlgorithm.Model.Vertex;
import Supports.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by 130708 on 2015/11/26.
 */
public class CanvasArea extends JPanel {
   public CanvasAreaDataChangedListenner dataChangedListenner;
   // model
   Graph graph;

   // keep track the current selected vertex
   Vertex currentVertex;

   // variable use for drag a vertex
   Point previousPoint;
   // temporary line
   Line tempLine;
   // is able to add new vertex to canvas when mouse pressed?
   public boolean inAddMode = true;

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(500, 500);
   }

   public CanvasArea() {
      setBackground(Config.BACKGROUND_COLOR);
      addMouseListener(new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent e) {
            if (e.getClickCount() == 2) {
               verticeMouseDoubleClicked(e);
            } else
               verticeMousePressed(e);
         }

         @Override
         public void mouseReleased(MouseEvent e) {
            verticeMouseReleased(e);
         }
      });
      addMouseMotionListener(new MouseMotionAdapter() {
         @Override
         public void mouseDragged(MouseEvent e) {
            verticeMouseDragged(e);
         }
      });
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (tempLine != null) {
         tempLine.draw(g);
      }
      if (graph != null)
         graph.draw(g);
   }

   public void verticeMouseDoubleClicked(MouseEvent e) {
      if (graph == null) return;
      Edge clickedEdge = graph.getEdgeAtPoint(e.getPoint());
      if (clickedEdge == null) return;
      String input = DialogHelpers.showInPutDialog("Input", "Enter cost: ");
      try {
         int number = Integer.parseInt(input);
         graph.setCostForEdge(clickedEdge, number);
         if (dataChangedListenner != null) dataChangedListenner.costChanged(this, number);
         repaint();
      } catch (NumberFormatException exception) {
         Log.Instance().push("Input not a number");
      }
   }

   public void verticeMousePressed(MouseEvent e) {
      if (graph == null) return;
      Vertex selectedVertex = graph.getVertexAtPoint(e.getPoint());
      Edge clickedEdge = graph.getEdgeAtPoint(e.getPoint());
      if (clickedEdge != null) return;
      if (selectedVertex == null) {
         if (inAddMode && SwingUtilities.isLeftMouseButton(e)) {
            Vertex newVertex = new Vertex(e.getPoint());
            graph.addVertex(newVertex);
            if (dataChangedListenner != null) dataChangedListenner.vertexAdded(this, newVertex);
            repaint();
         }
         return;
      }
      currentVertex = selectedVertex;
      if (SwingUtilities.isLeftMouseButton(e)) {
         previousPoint = e.getPoint();
      } else if (SwingUtilities.isRightMouseButton(e)) {
         tempLine = new Line();
         tempLine.setPoint1(e.getPoint());
      }
   }

   public void verticeMouseDragged(MouseEvent e) {
      if (graph == null) return;
      if (SwingUtilities.isLeftMouseButton(e)) {
         if (currentVertex == null) return;

         Point diff = Vector2DHelper.SubstractVector(e.getPoint(), previousPoint);
         currentVertex.setPosition(Vector2DHelper.AddVector(currentVertex.getPosition(), diff));
         previousPoint = e.getPoint();
         repaint();

      } else if (SwingUtilities.isRightMouseButton(e)) {
         if (tempLine != null) {
            tempLine.setPoint2(e.getPoint());
            repaint();
         }
      }
   }

   public void verticeMouseReleased(MouseEvent e) {
      if (graph == null) return;
      if (tempLine != null) {
         tempLine = null;
         Vertex selectedVertex = graph.getVertexAtPoint(e.getPoint());
         if (selectedVertex != null && selectedVertex != currentVertex) {
            Edge edge = new Edge(currentVertex, selectedVertex);
            graph.addEdge(edge);
            dataChangedListenner.edgeAdded(this, edge);
         }
         repaint();
      }
      currentVertex = null;
   }

   public void setGraph(Graph graph) {
      this.graph = graph;
      repaint();
   }

}