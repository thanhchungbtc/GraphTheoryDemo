package Supports;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 * Created by BTC on 11/23/15.
 */
public class Config {
   private static Config _instance;
   private Config() { }
   public static Config Instance() {
      if (_instance == null) _instance = new Config();
      return _instance;
   }
   
   public static void setLookAndFeel() {
	   try {
         UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();    
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }

   public static final int VERTICE_RADIUS = 15;
   public static final int MAX_NUBMER_OF_VERTEX = 50;
}
