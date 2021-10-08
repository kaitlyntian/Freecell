package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.hw02.Card;

import java.io.InputStreamReader;
import java.util.List;

/**
 * Interface for testing the freecell game controller.
 */
public class MyMainclass {
  /**
   * The main function to build a mock and tie everything together.
   */
  public static void main(String[] arg) {
    FreecellModel theModel = new FreecellModelCreator().create(
            FreecellModelCreator.GameType.MULTIMOVE);
    FreecellController theController = new SimpleFreecellController(theModel,
            new InputStreamReader(System.in), System.out);
    List<Card> orderedList = theModel.getDeck();
    try {
      theController.playGame(orderedList, 8, 4, true);
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
  }
}
