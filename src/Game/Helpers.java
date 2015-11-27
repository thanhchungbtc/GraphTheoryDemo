package Game;

/**
 * Created by BTC on 11/25/15.
 */
public class Helpers {

   public static boolean isNumberInRange(int number, int min, int max) {
//      return !(number < min || number > max);
      return number >= min && number <= max;
   }
}
