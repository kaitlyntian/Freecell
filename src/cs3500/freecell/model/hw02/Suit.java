package cs3500.freecell.model.hw02;

/**
 * Type of the suits in the card deck.
 */

public enum Suit {
  DIAMEND("♦"),
  CLUB("♣"),
  HEARTS("♥"),
  SPADES("♠");

  private final String value;

  private Suit(String value) {
    this.value = value;
  }


  public String getValue() {
    return value;
  }

  /**
   * method to decide if two suits are the same color
   * heart and diamonds are the same color
   * club and spades are the same color.
   * @ return true if the suit and the given suit is the same color, false otherwise
   */
  public boolean sameColor(Suit other) {
    return (this.value.equals("♥") && (other.value.equals("♦") || other.value.equals("♥")))
            || (this.value.equals("♦") && (other.value.equals("♦") || other.value.equals("♥")))
            || (this.value.equals("♣") && (other.value.equals("♣") || other.value.equals("♠")))
            || (this.value.equals("♠") && (other.value.equals("♠") || other.value.equals("♣")));
  }
}
