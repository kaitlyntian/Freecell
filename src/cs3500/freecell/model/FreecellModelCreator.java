package cs3500.freecell.model;

import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiFreeCellModel;

/**
 * The fractory class to produce a freecell model depending on the gametype input desired.
 */
public class FreecellModelCreator {

  /**
   * The kind of game that player can choose to play.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * The method to create a FreecellModel depending on the type of the game inputted.
   * @param type the type of the game desired to play
   * @return a freecellmodel
   */
  public static FreecellModel<Card> create(GameType type) {
    FreecellModel<Card> modelToReturn = null;
    if (type == GameType.MULTIMOVE) {
      modelToReturn = new MultiFreeCellModel();
    } else {
      modelToReturn = new SimpleFreecellModel();
    }
    return modelToReturn;
  }
}
