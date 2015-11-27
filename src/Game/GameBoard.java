package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by BTC on 11/25/15.
 */
public class GameBoard extends JPanel{

   Maze maze;
   Cat cat;
   public GameBoard() {
      maze = new Maze(20, 20);
      this.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            try {
               panelMouseClick(e);
            } catch (InterruptedException e1) {
               e1.printStackTrace();
            }
         }
      });
      cat = new Cat(maze, new Point(0, 0));
   }

   @Override
   public void paint(Graphics g) {
      super.paint(g);
      maze.draw(g);
      cat.draw(g);
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(maze.getWidthInPixel(), maze.getHeightInPixel());
   }

   private void panelMouseClick(MouseEvent e) throws InterruptedException {
      Point clickedPoint = e.getPoint();
      Point coord = maze.coordAtPosition(clickedPoint);
      if (coord == null) return;
      cat.moveTo(coord);
      SwingWorker<Void, Object> worker = new SwingWorker<Void, Object>() {
         @Override
         protected Void doInBackground() throws Exception {
            while (cat.popStep()) {
               repaint();
               Thread.sleep(50);
            }
            return null;
         }
      };
      worker.execute();
   }

   public static void main(String[] args) {
      JFrame frame = new JFrame();
      GameBoard gameBoard = new GameBoard();
      frame.setContentPane(gameBoard);
      frame.pack();
      frame.setVisible(true);
   }
}
