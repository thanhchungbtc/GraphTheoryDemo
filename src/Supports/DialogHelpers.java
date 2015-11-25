package Supports;

import javax.swing.*;

/**
 * Created by BTC on 11/24/15.
 */
public class DialogHelpers {
   public static void showAlert(String title, Object message) {
      JOptionPane.showMessageDialog(null, message, title, 1);
   }

   public static void showError(String title, Object message) {
      JOptionPane.showMessageDialog(null, message, title, 0);
   }

   public static int showConfirmMessage(String title, Object message, int optionType) {
      return JOptionPane.showConfirmDialog(null, message, title, optionType, 1);
   }

   public static String showInPutDialog(String title, Object message) {
      return JOptionPane.showInputDialog(null, message, title, 1);

   }
}