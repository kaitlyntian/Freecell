package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Class for implementing a simple free cell controller.
 */
public class SimpleFreecellController<Card> implements FreecellController<Card> {
  private FreecellModel<Card> model;
  private Readable rd;
  private FreecellView view;

  /**
   * The constructor that takes in model, readable and appendable as argument.
   *
   * @param model the model to used for the controller
   * @param rd    the readable that represent as the input
   * @param ap    the Appendable that represent as the output
   * @throws IllegalArgumentException if the readable and appendable is null
   */
  public SimpleFreecellController(FreecellModel<Card> model, Readable rd, Appendable ap)
          throws IllegalArgumentException {
    if (rd == null) {
      throw new IllegalArgumentException("Readable not valid");
    }
    if (ap == null) {
      throw new IllegalArgumentException("Appendable not valid");
    }
    this.model = model;
    this.rd = rd;
    this.view = new FreecellTextView(model, ap);
  }


  /**
   * Start and play a new game of freecell with the provided deck. This deck
   * should be used as-is. This method returns only when the game is over
   * (either by winning or by quitting)
   * IOException,
   *
   * @param deck        the deck to be used to play this game
   * @param numCascades the number of cascade piles
   * @param numOpens    the number of open piles
   * @param shuffle     shuffle the deck if true, false otherwise
   * @throws IllegalStateException    if writing to the Appendable or reading fails
   * @throws IllegalArgumentException if the model or deck provided to it are null
   */
  @Override
  public void playGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle)
          throws IllegalArgumentException, IllegalStateException {
    //try if there's ioexception then throw IllegalStateException
    try {
      if ((numCascades < 4) || (numOpens < 1)) {
        this.view.renderMessage("Could not start game.");
        return;
      }
      if (this.model == null || deck == null) {
        throw new IllegalArgumentException("deck is null");
      }
      this.model.startGame(deck, numCascades, numOpens, shuffle);
      this.model.startGame(deck, numCascades, numOpens, shuffle);
      //render the initial board
      this.view.renderBoard();
      Scanner scan = new Scanner(this.rd);
      while (!this.model.isGameOver()) {
        String[] commands = new String[3];
        for (int i = 0; i < 3; i++) {
          if (i == 1) {
            //check for card command if it is the second one
            commands[i] = fillinCardCommand(scan);
          } else {
            commands[i] = fillinPileCommand(scan);
          }
          if (commands[i].equalsIgnoreCase("Q")) {
            this.view.renderMessage("Game quit prematurely.");
            return;
          }
        }
        if (commands[2] != null) {
          try {
            model.move(getPiletype(commands[0]),
                    getPileNum(commands[0]) - 1,
                    Integer.parseInt(commands[1]) - 1,
                    getPiletype(commands[2]),
                    getPileNum(commands[2]) - 1);
            this.view.renderBoard();
          } catch (IllegalArgumentException e) {
            String outputMessage = "Invalid move. Try again.\n";
            outputMessage += e.toString();
            outputMessage += "\n";
            this.view.renderMessage(outputMessage);
          }
        }
        if (this.model.isGameOver()) {
          this.view.renderMessage("Game over.");
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Ioexception thrown");
    }
  }

  /**
   * fill in the pile command after validating it.
   *
   * @param scan the scanner from the main playGame
   * @return the correct command
   * @throws IOException if render message uncorrectly
   */
  private String fillinPileCommand(Scanner scan) throws IOException {
    String command;
    if (scan.hasNext()) {
      command = scan.next();
    } else {
      throw new IllegalStateException("there is no line next");
    }
    while (!validatePileCommand(command)) {
      this.view.renderMessage("Please input this piletype and pile number again\n");
      if (scan.hasNext()) {
        command = scan.next();
      }
    }
    return command;
  }

  /**
   * fill in the card command after validating it.
   *
   * @param scan the scanner from the main playGame
   * @return the correct command
   * @throws IOException from the message rendering
   */
  private String fillinCardCommand(Scanner scan) throws IOException {
    String command;
    if (scan.hasNext()) {
      command = scan.next();
    } else {
      throw new IllegalStateException("there is no line next");
    }
    while (!validateNumber(command)) {
      if (command.equalsIgnoreCase("Q")) {
        break;
      }
      this.view.renderMessage("Please input this card index(int) again\n");
      if (scan.hasNext()) {
        command = scan.next();
      }
    }
    return command;
  }

  /**
   * Validate the pile command to see if it is Q, and startwith C F or Q.
   *
   * @param command the string command to check
   * @return true is the command is valid
   */
  private static boolean validatePileCommand(String command) {
    if (command == null) {
      return false;
    }
    if (command.equalsIgnoreCase("q")) {
      return true;
    }
    String pileType;
    String pileNumber;
    pileType = command.substring(0, 1);
    pileNumber = command.substring(1);
    if (!(pileType.equals("C") || pileType.equals("F") || pileType.equals("O"))) {
      return false;
    }
    return validateNumber(pileNumber);
  }

  /**
   * Validate the card command to see if it is an positive int.
   *
   * @param numericString the string command to check
   * @return true is the command is valid
   */
  private static boolean validateNumber(String numericString) {
    if (numericString == null) {
      return false;
    }
    return numericString.matches("\\d+");
  }

  private static PileType getPiletype(String command) {
    String type = command.substring(0, 1);
    if (type.equals("O")) {
      return PileType.OPEN;
    } else if (type.equals("F")) {
      return PileType.FOUNDATION;
    } else {
      return PileType.CASCADE;
    }
  }

  private static int getPileNum(String command) {
    return Integer.parseInt(command.substring(1));
  }
}