package cs3500.freecell.model.hw02;

import java.util.Objects;

/**
 * This is the class of the Card. It is composed of suit and rank(val in int)
 */
public class Card {

  private final Suit suit;
  private final int val;

  /**
   * the default constructor that consitute Card.
   * @param suit : the suit of card
   * @param val : the rank of card
   * @throws IllegalArgumentException if the value is not between 0 to 13
   */
  public Card(Suit suit, int val) throws IllegalArgumentException {
    if (val < 0 || val > 13) {
      throw new IllegalStateException("this value is not valid for card");
    }
    this.suit = suit;
    this.val = val;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return this.val == card.val && suit == card.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(suit, val);
  }


  @Override
  public String toString() {
    String formatedVal;
    switch (this.val) {
      case (1):
        formatedVal = "A";
        break;
      case (11):
        formatedVal = "J";
        break;
      case (12):
        formatedVal = "Q";
        break;
      case (13):
        formatedVal = "K";
        break;
      default:
        formatedVal = String.valueOf(this.val);
    }
    return formatedVal + this.suit.getValue();
  }

  /**
   * Getter to fetch the value of the card suit.
   *
   * @return Suit of the card
   */
  public Suit getSuit() {
    return suit;
  }

  /**
   * Getter to fetch the value of the card rank.
   *
   * @return Value of the card
   */
  public int getVal() {
    return val;
  }


}