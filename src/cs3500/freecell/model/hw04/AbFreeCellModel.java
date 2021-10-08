package cs3500.freecell.model.hw04;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

/**
 * Abstract free cell model for the signle and multi move.
 */
public abstract class AbFreeCellModel implements FreecellModel<Card> {

  protected List<ArrayList<Card>> cascadePile;
  protected List<ArrayList<Card>> foundationPile;
  protected Card[] openPile;
  protected boolean started;
  protected Random rand;

  /**
   * The default constructor for both simpleFreeCell and multipleFreeCell
   * initiate the cascade, open and foundation pile as well as make the initial started statues
   * as false.
   */
  public AbFreeCellModel() {
    this.cascadePile = new ArrayList<ArrayList<Card>>();
    this.foundationPile = new ArrayList<ArrayList<Card>>(Arrays.asList(
            new ArrayList<Card>(),
            new ArrayList<Card>(),
            new ArrayList<Card>(),
            new ArrayList<Card>()));
    this.openPile = null;
    this.started = false;
    this.rand = new Random();
  }

  /**
   * take in a seed to shuffle random card deck for test purpuse.
   */
  public AbFreeCellModel(int seed) {
    this.cascadePile = new ArrayList<ArrayList<Card>>();
    this.foundationPile = new ArrayList<ArrayList<Card>>(Arrays.asList(
            new ArrayList<Card>(),
            new ArrayList<Card>(),
            new ArrayList<Card>(),
            new ArrayList<Card>()));
    this.openPile = null;
    this.started = false;
    this.rand = new Random(seed);
  }

  /**
   * Check if a card is valid to move from a cascade pile.
   *
   * @param pile       the cascade list of piles to check
   * @param pileNumber the pile number which below to the cascade
   * @param cardIndex  the card index the pile to be moved starting from
   * @throws IllegalArgumentException if the card is not valid to move from the pile
   */
  protected static void errorCheckMoveFromCascade(List<ArrayList<Card>> pile,
                                                  int pileNumber,
                                                  int cardIndex) throws IllegalArgumentException {
    //an abstract method to be different if in simple or multi move
  }

  /**
   * * Return a valid and complete deck of cards for a game of Freecell. There is
   * * no restriction imposed on the ordering of these cards in the deck. An
   * * invalid deck is defined as a deck that has one or more of these flaws: <ul>
   * * <li>It does not have 52 cards</li> <li>It has duplicate cards</li> <li>It
   * * has at least one invalid card (invalid suit or invalid number) </li> </ul>
   * *
   * * @return the deck of cards as a list
   */
  @Override
  public List<Card> getDeck() {
    List<Card> deckOfCard = new ArrayList<Card>();
    for (int i = 13; i > 0; i--) {
      for (Suit s : Suit.values()) {
        Card cardtoadd = new Card(s, i);
        deckOfCard.add(cardtoadd);
      }
    }
    return deckOfCard;
  }

  /**
   * Deal a new game of freecell with the given deck, with or without shuffling
   * it first. This method first verifies that the deck is valid. It deals the
   * deck among the cascade piles in roundrobin fashion. Thus if there are 4
   * cascade piles, the 1st pile will get cards 0, 4, 8, ..., the 2nd pile will
   * get cards 1, 5, 9, ..., the 3rd pile will get cards 2, 6, 10, ... and the
   * 4th pile will get cards 3, 7, 11, .... Depending on the number of cascade
   * piles, they may have a different number of cards
   *
   * @param deck            the deck to be dealt
   * @param numCascadePiles number of cascade piles
   * @param numOpenPiles    number of open piles
   * @param shuffle         if true, shuffle the deck else deal the deck as-is
   * @throws IllegalArgumentException if the deck is invalid
   */
  @Override
  public void startGame(List<Card> deck, int numCascadePiles,
                        int numOpenPiles, boolean shuffle) throws IllegalArgumentException {
    if (!validdeck(deck)) {
      throw new IllegalArgumentException("this deck are not valid");
    }
    List<Card> allianDeck = new ArrayList<Card>(deck);
    if (shuffle) {
      Collections.shuffle(allianDeck, rand);
    }
    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("we need at least 4 cascade piles");
    }
    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("we need at least 1 open piles");
    }
    //build openPiles
    Card[] open = new Card[numOpenPiles];
    //build cascade piles
    List<ArrayList<Card>> cascade = new ArrayList<ArrayList<Card>>();
    //build separate rows
    for (int i = 0; i < numCascadePiles; i++) {
      cascade.add(new ArrayList<Card>());
    }
    //add the deck into the cascade piles
    int counter = 0;
    while (counter < 52) {
      cascade.get(counter % numCascadePiles).add(allianDeck.get(counter));
      counter = counter + 1;
    }
    this.cascadePile = cascade;
    this.started = true;
    this.foundationPile = new ArrayList<ArrayList<Card>>(Arrays.asList(
            new ArrayList<Card>(),
            new ArrayList<Card>(),
            new ArrayList<Card>(),
            new ArrayList<Card>()));
    this.openPile = open;
  }

  /**
   * testing to varify a valid card deck.
   *
   * @param deck the deck to be dealt
   * @return true if the deck is valid false otherwise
   * @throws IllegalArgumentException if the deck given is null
   */
  protected static boolean validdeck(List<Card> deck) throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("this deck of cards are null");
    }
    boolean valid = true;
    Set<Card> cardSet = new HashSet<Card>();
    Set<Card> fullDeckArray = new HashSet<Card>(deck);
    for (Card card : fullDeckArray) {
      if (card.getVal() < 0 || card.getVal() > 13) {
        valid = false;
      }
      if (cardSet.contains(card)) {
        valid = false;
      } else {
        cardSet.add(card);
      }
    }
    if (cardSet.size() != 52) {
      valid = false;
    }
    return valid;
  }

  /**
   * Check if a card is valid to move from a open pile.
   *
   * @param pile       the open pile to be tested on
   * @param pileNumber the position of the pile within all the open pile
   * @param cardIndex  the position of the card on the pile
   * @throws IllegalArgumentException if the open pile is not valid to move away from
   */
  protected static void errorCheckMoveFromOpen(Card[] pile,
                                               int pileNumber,
                                               int cardIndex) throws IllegalArgumentException {
    if (pileNumber >= pile.length) {
      throw new IllegalArgumentException("Pile number is too big for the open pile size!");
    }
    //can not move if this card is not found on the open pile
    if (pile[pileNumber] == null) {
      throw new IllegalArgumentException("Card not found on open pile");
    }
  }


  /**
   * Check if a card is valid to move to a foundation pile.
   *
   * @param pileToBeAdded  pile to be adeded within the foundation pile
   * @param destPileNumber the pile number relative to the whole foundation pile
   * @param cardToAdd      the card object that is looking to move onto the foundation pile
   * @throws IllegalArgumentException if the foundation pile is not valid to move onto
   */
  protected static void errorCheckToMoveToFound(List<Card> pileToBeAdded,
                                                int destPileNumber,
                                                Card cardToAdd) throws IllegalArgumentException {
    //can't move to an foundation pile when it is out of the bound
    if (destPileNumber > 3) {
      throw new IllegalArgumentException("Pile number is too big for the Foundation pile size!");
    }
    //if the pile is still empty
    if (pileToBeAdded.size() == 0) {
      //only value of 1 can be added on top of the empty pile
      if (cardToAdd.getVal() != 1) {
        throw new IllegalArgumentException("The first card has to value 1!");
      }
    } else {
      //different suits can not be added on top of each other
      if (cardToAdd.getSuit() != pileToBeAdded.get(pileToBeAdded.size() - 1).getSuit()) {
        throw new IllegalArgumentException("The Suit color is differant from this pile");
      }
      //the one on top can only be increment of one
      else if (cardToAdd.getVal() != pileToBeAdded.size() + 1) {
        throw new IllegalArgumentException("This card is not ready to move to the foundation yet!");
      }
    }
  }

  /**
   * Check if a card is valid to move to a cascade pile.
   *
   * @param cascadePile    the whole cascade pile
   * @param destPileNumber the destination pile number to be added
   * @param cardToAdd      the card object to be added into the cascade
   * @throws IllegalArgumentException if the foundation pile is not valid to move onto
   */
  protected static void errorCheckToMoveToCascade(List<ArrayList<Card>> cascadePile,
                                                  int destPileNumber,
                                                  Card cardToAdd) {
    if (destPileNumber > cascadePile.size()) {
      throw new IllegalArgumentException("Pile number is too big for the Foundation pile size!");
    }
    List<Card> pileToBeAdded = cascadePile.get(destPileNumber);
    int pileSize = pileToBeAdded.size();
    if (pileToBeAdded.size() != 0) {
      if (pileToBeAdded.get(pileSize - 1).getVal() - 1 != cardToAdd.getVal()
              || pileToBeAdded.get(pileSize - 1).getSuit().sameColor(cardToAdd.getSuit())) {
        throw new IllegalArgumentException(
                "This card is not ready to move to that other cascade pile  yet!");
      }
    }
  }

  /**
   * Check if a card is valid to move to a cascade pile.
   */
  protected static void errorCheckToMoveToOpen(Card[] openPile,
                                               int destPileNumber,
                                               int numCardInPile) {
    if (destPileNumber < 0 || destPileNumber > openPile.length - 1) {
      throw new IllegalArgumentException("Pile number is invalid");
    }
    if (numCardInPile > 0) {
      throw new IllegalArgumentException("This Pile has been occupied");
    }
  }

  /**
   * Signal if the game is over or not.
   *
   * @return true if game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    return this.started && this.getNumCardsInFoundationPile(0) == 13
            && this.getNumCardsInFoundationPile(1) == 13
            && this.getNumCardsInFoundationPile(2) == 13
            && this.getNumCardsInFoundationPile(3) == 13;
  }


  /**
   * Get the number of cards in a given foundation pile.
   *
   * @param index the index of the foundation pile, starting at 0
   * @return the number of cards in the given foundation pile
   * @throws IllegalArgumentException if the provided index is invalid
   * @throws IllegalStateException    if the game has not started
   */
  @Override
  public int getNumCardsInFoundationPile(int index)
          throws IllegalArgumentException, IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("this game has not started yet!");
    }
    if (index < 0 || index >= this.foundationPile.size()) {
      throw new IllegalArgumentException("Pile Index not valid");
    }
    return this.foundationPile.get(index).size();
  }

  /**
   * Get the number of cascade piles in this game of freecell.
   *
   * @return the number of cascade piles, as an integer, or -1 if the game has not started yet
   */
  @Override
  public int getNumCascadePiles() {
    if (!this.started) {
      return -1;
    } else {
      return this.cascadePile.size();
    }
  }

  /**
   * Get the number of cards in a given cascade pile.
   *
   * @param index the index of the cascade pile, starting at 0
   * @return the number of cards in the given cascade pile
   * @throws IllegalArgumentException if the provided index is invalid
   * @throws IllegalStateException    if the game has not started
   */
  @Override
  public int getNumCardsInCascadePile(int index)
          throws IllegalArgumentException, IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("this game has not started yet!");
    }
    if (index < 0 || index >= this.cascadePile.size()) {
      throw new IllegalArgumentException("Pile Index not valid");
    }
    return this.cascadePile.get(index).size();
  }

  /**
   * Get the number of cards in a given open pile.
   *
   * @param index the index of the open pile, starting at 0
   * @return the number of cards in the given open pile
   * @throws IllegalArgumentException if the provided index is invalid
   * @throws IllegalStateException    if the game has not started
   */
  @Override
  public int getNumCardsInOpenPile(int index)
          throws IllegalArgumentException, IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("this game has not started yet!");
    }
    if (index < 0 || index >= this.openPile.length) {
      throw new IllegalArgumentException("Pile Index not valid");
    }
    if (this.openPile[index] != null) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * Get the number of open piles in this game of freecell.
   *
   * @return the number of open piles, as an integer, or -1 if the game has not started yet
   */
  @Override
  public int getNumOpenPiles() {
    if (!this.started) {
      return -1;
    } else {
      return this.openPile.length;
    }
  }

  /**
   * Get the card at the provided index in the provided foundation pile.
   *
   * @param pileIndex the index of the foundation pile, starting at 0
   * @param cardIndex the index of the card in the above foundation pile, starting at 0
   * @return the card at the provided indices
   * @throws IllegalArgumentException if the pileIndex or cardIndex is invalid
   * @throws IllegalStateException    if the game has not started
   */
  @Override
  public Card getFoundationCardAt(int pileIndex, int cardIndex) throws
          IllegalArgumentException, IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("this game has not started yet!");
    }
    if (pileIndex < 0 || pileIndex >= 4) {
      throw new IllegalArgumentException("Pile Index not valid");
    }
    if (cardIndex < 0 || cardIndex >= this.foundationPile.get(pileIndex).size()) {
      throw new IllegalArgumentException("Card Index not valid");
    }
    return this.foundationPile.get(pileIndex).get(cardIndex);
  }

  /**
   * Get the card at the provided index in the provided cascade pile.
   *
   * @param pileIndex the index of the cascade pile, starting at 0
   * @param cardIndex the index of the card in the above cascade pile, starting at 0
   * @return the card at the provided indices
   * @throws IllegalArgumentException if the pileIndex or cardIndex is invalid
   * @throws IllegalStateException    if the game has not started
   */
  @Override
  public Card getCascadeCardAt(int pileIndex, int cardIndex)
          throws IllegalArgumentException, IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("this game has not started yet!");
    }
    if (pileIndex < 0 || pileIndex >= this.cascadePile.size()) {
      throw new IllegalArgumentException("Pile Index not valid");
    }
    if (cardIndex < 0
            //if this cardIdx is over 52
            || cardIndex >= this.cascadePile.get(pileIndex).size()) {
      throw new IllegalArgumentException("Card Index not valid");
    }
    return this.cascadePile.get(pileIndex).get(cardIndex);
  }

  /**
   * Get the card in the given open pile.
   *
   * @param pileIndex the index of the open pile, starting at 0
   * @return the card at the provided index, or null if there is no card there
   * @throws IllegalArgumentException if the pileIndex is invalid
   * @throws IllegalStateException    if the game has not started
   */
  @Override
  public Card getOpenCardAt(int pileIndex)
          throws IllegalArgumentException, IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("this game has not started yet!");
    }
    if (pileIndex < 0 || pileIndex >= this.openPile.length) {
      throw new IllegalArgumentException("Pile Index not valid");
    }
    return this.openPile[pileIndex];
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
  protected void singleMove(PileType source, int pileNumber,
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
    //can't move a card from the foundation
    if (source == PileType.FOUNDATION) {
      throw new IllegalArgumentException("Do not try to move from Foundation Pile!");
    }
    //can't move from or to a cascade pile when it is out of the bound
    if (source == PileType.CASCADE) {
      errorCheckMoveFromCascade(this.cascadePile,
              pileNumber, cardIndex);
    }
    //can't move from or to an open pile when it is out of the bound
    if (source == PileType.OPEN) {
      errorCheckMoveFromOpen(this.openPile, pileNumber, cardIndex);
    }
    //identify the card to remove from the cascade or open
    Card cardToAdd;
    if (source == PileType.CASCADE) {
      cardToAdd = this.getCascadeCardAt(pileNumber, cardIndex);
    } else {
      cardToAdd = this.getOpenCardAt(pileNumber);
    }
    if ((destination == PileType.FOUNDATION)) {
      errorCheckToMoveToFound(this.foundationPile.get(destPileNumber),
              destPileNumber, cardToAdd);
    }
    if ((destination == PileType.CASCADE)) {
      errorCheckToMoveToCascade(this.cascadePile,
              destPileNumber, cardToAdd);
    }
    if (destination == PileType.OPEN) {
      errorCheckToMoveToOpen(this.openPile,
              destPileNumber, this.getNumCardsInOpenPile(destPileNumber));
    }
    if (source == PileType.OPEN) {
      //identify the card and remove it
      this.openPile[pileNumber] = null;
    } else {
      //identify the card and remove it
      this.cascadePile.get(pileNumber).remove(cardIndex);
    }
    if (destination == PileType.CASCADE) {
      this.cascadePile.get(destPileNumber).add(cardToAdd);
    } else if (destination == PileType.OPEN) {
      this.openPile[destPileNumber] = cardToAdd;
    } else {
      this.foundationPile.get(destPileNumber).add(cardToAdd);
    }
  }


}
