package Supports;

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

   public final int VERTICE_RADIUS = 15;
}
