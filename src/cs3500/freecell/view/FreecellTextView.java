package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.hw02.SimpleFreecellModel;

import java.io.IOException;
import java.util.Objects;

/**
 * This is the view class that display freecellmodel in text
 * it implements from freecellview.
 */
public class FreecellTextView implements FreecellView {

  FreecellModel<?> modelState;
  final Appendable out;

  public FreecellTextView(FreecellModel<?> modelState, Appendable out) {
    this.modelState = modelState;
    this.out = out;
  }

  public FreecellTextView() {
    this.modelState = new SimpleFreecellModel();
    this.out = new StringBuilder();
  }

  public FreecellTextView(FreecellModel<?> model) {
    this.modelState = model;
    this.out = new StringBuilder();
  }

  /**
   * Return the present state of the game as a string. The string is formatted
   * as follows:
   * <pre>
   * F1:[b]f11,[b]f12,[b],...,[b]f1n1[n] (Cards in foundation pile 1 in order)
   * F2:[b]f21,[b]f22,[b],...,[b]f2n2[n] (Cards in foundation pile 2 in order)
   * ...
   * Fm:[b]fm1,[b]fm2,[b],...,[b]fmnm[n] (Cards in foundation pile m in
   * order)
   * O1:[b]o11[n] (Cards in open pile 1)
   * O2:[b]o21[n] (Cards in open pile 2)
   * ...
   * Ok:[b]ok1[n] (Cards in open pile k)
   * C1:[b]c11,[b]c12,[b]...,[b]c1p1[n] (Cards in cascade pile 1 in order)
   * C2:[b]c21,[b]c22,[b]...,[b]c2p2[n] (Cards in cascade pile 2 in order)
   * ...
   * Cs:[b]cs1,[b]cs2,[b]...,[b]csps (Cards in cascade pile s in order)
   *
   * where [b] is a single blankspace, [n] is newline. Note that there is no
   * newline on the last line
   * </pre>
   *
   * @return the formatted string as above
   */
  public String toString() {
    try {
      String foundString = "";
      for (int i = 0; i < 4; i++) {
        int numInPile = modelState.getNumCardsInFoundationPile(i);
        String cardStringsum = "";
        //for string
        for (int u = 0; u < numInPile; u++) {
          String cardString;
          if (u == numInPile - 1) {
            cardString = " " + modelState.getFoundationCardAt(i, u).toString();
          } else {
            cardString = " " + modelState.getFoundationCardAt(i, u).toString() + ",";
          }
          cardStringsum = cardStringsum + cardString;
        }
        String rowstring = "F" + String.valueOf(i + 1) + ":" + cardStringsum + "\n";
        foundString = foundString + rowstring;
      }
      //open pile
      int numOpen = modelState.getNumOpenPiles();
      String openString = "";
      for (int i = 0; i < numOpen; i++) {
        int numInPile = modelState.getNumCardsInOpenPile(i);
        String cardStringsum = "";
        for (int u = 0; u < numInPile; u++) {
          String cardString;
          if (numInPile == 0) {
            cardString = " ";
          } else if (u == numInPile - 1) {
            cardString = " " + modelState.getOpenCardAt(i).toString();
          } else {
            cardString = " " + modelState.getOpenCardAt(i).toString() + ",";
          }
          cardStringsum = cardStringsum + cardString;
        }
        String rowstring = "O" + String.valueOf(i + 1) + ":" + cardStringsum + "\n";
        openString = openString + rowstring;
      }
      //cascade pile
      int numCasccade = modelState.getNumCascadePiles();
      String casString = "";
      for (int i = 0; i < numCasccade; i++) {
        String rowstring;
        int numInPile = modelState.getNumCardsInCascadePile(i);
        String cardStringsum = "";
        //for string
        for (int u = 0; u < numInPile; u++) {
          String cardString;
          if (u == numInPile - 1) {
            cardString = " " + modelState.getCascadeCardAt(i, u).toString();
          } else {
            cardString = " " + modelState.getCascadeCardAt(i, u).toString() + ",";
          }
          cardStringsum = cardStringsum + cardString;
        }
        if (i == numCasccade - 1) {
          rowstring = "C" + String.valueOf(i + 1) + ":" + cardStringsum;
        } else {
          rowstring = "C" + String.valueOf(i + 1) + ":" + cardStringsum + "\n";
        }
        casString = casString + rowstring;
      }
      return foundString + openString + casString;
    } catch (Exception e) {
      if (e instanceof IllegalArgumentException || e instanceof IllegalStateException) {
        return "";
      } else {
        throw e;
      }
    }
  }

  /**
   * Render the board to the provided data destination. The board should be rendered exactly
   * in the format produced by the toString method above.
   *
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void renderBoard() throws IOException {
    String string = this.toString() + "\n";
    Objects.requireNonNull(string);
    this.out.append(string);
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    Objects.requireNonNull(message);
    this.out.append(message);
  }

}
