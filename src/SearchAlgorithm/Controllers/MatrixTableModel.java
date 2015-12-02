package SearchAlgorithm.Controllers;

import SearchAlgorithm.Model.Graph;

import javax.swing.table.AbstractTableModel;

/**
 * Created by BTC on 11/29/15.
 */
public class MatrixTableModel extends AbstractTableModel {

   private Graph graph;

   public MatrixTableModel(Graph graph) {
      this.graph = graph;
   }

   @Override
   public String getColumnName(int column) {
      if (column == 0) return "<html><b>V</b></html>";
      return "<html><b>" + graph.getVertices().get(column - 1).getName() + "</b></html>";
   }

   @Override
   public int getRowCount() {
      return graph.getVertices().size();
   }

   @Override
   public int getColumnCount() {
      return graph.getVertices().size() + 1;
   }

   @Override
   public Object getValueAt(int rowIndex, int columnIndex) {
      if (columnIndex == 0) return "<html><b>" + this.graph.getVertices().get(rowIndex).getName() + "</b></html>";

      int value = this.graph.getCostAt(rowIndex, columnIndex - 1);
      return value == Graph.Max_INFINITY ? "<html><font color = #D3D3D3>∞</html>" : value;
   }
}
