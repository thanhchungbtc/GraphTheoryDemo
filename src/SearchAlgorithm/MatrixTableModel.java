package SearchAlgorithm;

import Model.*;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by BTC on 11/26/15.
 */
public class MatrixTableModel extends AbstractTableModel {

  Graph graph;

   public MatrixTableModel(Graph graph) {
      this.graph = graph;
   }

   @Override
   public String getColumnName(int column) {
      if (column == 0) return "";
      return graph.vertices.get(column - 1).getName();
   }

   @Override
   public int getRowCount() {
      return graph.vertices.size() + 1;
   }

   @Override
   public int getColumnCount() {
      return graph.vertices.size() + 1;
   }

   @Override
   public Object getValueAt(int rowIndex, int columnIndex) {
      if (rowIndex == 0 && columnIndex == 0) return "";
      if (rowIndex == 0) {
         return graph.vertices.get(columnIndex - 1);
      }
      if (columnIndex == 0) {
         return graph.vertices.get(rowIndex - 1);
      }
      return graph.adjacentMatrix[rowIndex][columnIndex];
   }
}
