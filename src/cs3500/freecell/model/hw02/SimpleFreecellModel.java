package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw04.AbFreeCellModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class of the Simple Freecell Model which
 * implement all the methods from free cell model to
 * run a simple model for the free cell.
 */
public class SimpleFreecellModel extends AbFreeCellModel {

  /**
   * This is the default model54 constructor for the simple freecell model.
   */
  public SimpleFreecellModel() {
    super();
  }

  /**
   * take in a seed to shuffle random card deck for test purpuse.
   */
  public SimpleFreecellModel(int seed) {
    super(seed);
  }

  /**
   * Move a card from the given source pile to the given destination pile, if
   * the move is valid.
   *
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source
   *                       pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is not possible {@link
   *                                  PileType})
   * @throws IllegalStateException    if a move is attempted before the game has
   *                                  starts
   */
  @Override
  public void move(PileType source, int pileNumber,
                   int cardIndex, PileType destination, int destPileNumber)
          throws IllegalArgumentException {
    super.singleMove(source,pileNumber,cardIndex,destination,destPileNumber);
  }

  protected static void errorCheckMoveFromCascade(List<ArrayList<Card>> pile,
                                                int pileNumber, int cardIndex) {
    if (pileNumber >= pile.size()) {
      throw new IllegalArgumentException("Pile number is too big for the cascade pile size!");
    }
    int thisPileSize = pile.get(pileNumber).size();
    if (cardIndex >= thisPileSize) {
      throw new IllegalArgumentException("Card not found on cascade pile");
    }
    if (cardIndex != thisPileSize - 1) {
      throw new IllegalArgumentException("This card is invalid to remove from the pile");
    }
  }

}





