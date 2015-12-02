package Supports;

import java.util.Date;
import java.util.Stack;

/**
 * Created by BTC on 11/28/15.
 */
public class Log {
   private static Log _instance;
   private Stack<Object> description;
   private LogContentChangedListenner listenner;

   private Log() {
      description = new Stack<>();
   }

   public void addContentChangedListener(LogContentChangedListenner listenner) {
      this.listenner = listenner;
   }
   public String getDescription() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < description.size(); i++) {
         sb.append(description.get(i));
      }
      return sb.toString();
   }

   public void push(Object value) {
      description.push(value);
      if (listenner != null)
         listenner.logContentChanged(this);
   }
   public static Log Instance() {
      if (_instance == null) _instance = new Log();
      return _instance;
   }

}


