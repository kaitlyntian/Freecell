package cs3500.freecell.model.hw04;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that can allow the player to move myltiple card.
 */
public class MultiFreeCellModel extends AbFreeCellModel {
  /**
   * This is the default model constructor for the MultiFreeCellModel.
   */
  public MultiFreeCellModel() {
    super();
  }

  /**
   * take in a seed to shuffle random card deck for test purpuse.
   */
  public MultiFreeCellModel(int seed) {
    super(seed);
  }

  @Override
  public void move(PileType source, int pileNumber,
                   int cardIndex, PileType destination, int destPileNumber)
          throws IllegalArgumentException {
    if (!started) {
      throw new IllegalStateException("Do not move before game has started!");
    }
    //can't move from or to negative pilenumbers
    if (pileNumber < 0 || destPileNumber < 0) {
      throw new IllegalArgumentException("Can not input a negative number for the pile number");
    }
    if (cardIndex < 0) {
      throw new IllegalArgumentException("Can not input a negative number for the card index");
    }
    List<Card> pileToAdd = addCascadeCardToPile(cardIndex, pileNumber);
    if (source == PileType.CASCADE && destination == PileType.CASCADE) {
      errorCheckMoveFromCascade(this.cascadePile,
              pileNumber, cardIndex);
      checkThirdCondition(pileToAdd);
      while (this.getNumCardsInCascadePile(pileNumber) > cardIndex) {
        singleMove(source, pileNumber, cardIndex, destination, destPileNumber);
      }
    } else {
      if (source == PileType.CASCADE
              && cardIndex != this.getNumCardsInCascadePile(pileNumber) - 1) {
        throw new IllegalArgumentException("don't move more "
                + "than one card if not to other Cascade Pile!");
      }
      singleMove(source, pileNumber, cardIndex, destination, destPileNumber);
    }
  }

  private void checkThirdCondition(List<Card> pile) {
    int freeOpenPile = 0;
    int freeCasPile = 0;
    for (int i = 0; i < this.getNumOpenPiles(); i++) {
      if (this.getNumCardsInOpenPile(i) == 0) {
        freeOpenPile++;
      }
    }
    for (int i = 0; i < this.getNumCascadePiles(); i++) {
      if (this.getNumCardsInCascadePile(i) == 0) {
        freeCasPile++;
      }
      double maxPileSize = (freeOpenPile + 1) * Math.pow(2, freeCasPile);
      if (pile.size() > maxPileSize) {
        throw new IllegalArgumentException("Pile Size over Limit");
      }
    }
  }

  /**
   * Add card from cascade pile to the pile to be moved to destination pile.
   *
   * @param cardIndex  cardIndex To Move
   * @param pileNumber pileNumber this pile belonged to
   * @return a pile to be move to the destination
   */
  private List<Card> addCascadeCardToPile(int cardIndex, int pileNumber) {
    List<Card> pileToAdd = new ArrayList<Card>();
    for (int i = cardIndex; i < getNumCardsInCascadePile(pileNumber); i++) {
      pileToAdd.add(this.getCascadeCardAt(pileNumber, cardIndex));
    }
    return pileToAdd;
  }

  protected static void errorCheckMoveMultiFromCascade(List<ArrayList<Card>> pile,
                                                       int pileNumber,
                                                       int cardIndex)
          throws IllegalArgumentException {
    if (pileNumber >= pile.size()) {
      throw new IllegalArgumentException("Pile number is too big for the cascade pile size!");
    }
    int thisPileSize = pile.get(pileNumber).size();
    if (cardIndex >= thisPileSize) {
      throw new IllegalArgumentException("Card not found on cascade pile");
    }
    if (cardIndex != thisPileSize - 1) {
      multiCardCheck(pile.get(pileNumber).subList(cardIndex, pile.get(pileNumber).size() - 1));
    }
  }

  /**
   * check if the pile supplying is having one different color from other
   * and value descending from the top.
   *
   * @param pile pile the card pile to check the conditions, must be bigger than size one
   * @throws IllegalArgumentException if the pile is not a valid pile according to the above rule
   */
  private static void multiCardCheck(List<Card> pile) throws IllegalArgumentException {
    Card prevCard = pile.get(0);
    for (int i = 1; i < pile.size(); i++) {
      Card next = pile.get(i);
      if (next.getVal() != prevCard.getVal() - 1
              || next.getSuit().sameColor(prevCard.getSuit())) {
        throw new IllegalArgumentException("Card not valid to move from the pile");
      }
      prevCard = next;
    }
  }

}
