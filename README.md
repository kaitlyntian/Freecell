# Freecell
A solitaire game

## Below is a brief discription of this game
Freecell is a Solitaire-type game, which uses the regular deck of 52 suit-value cards. There are four suits: clubs (♣), diamonds (♦), hearts (♥), and spades (♠). Hearts and diamonds are colored red; clubs and spades are colored black. There are thirteen values: ace (written A), two through ten (written 2 through 10), jack (J), queen (Q) and king (K). In Freecell, aces are considered ''low," or less than a two. (In other games, they're considered ''high," or greater than a king.)

The game play is divided among three types of card piles . First, there are four foundation piles, one for each suit (shown in the top right in figure above), and indexed 1 through 4. These four piles are initially empty, and the goal of Freecell is to collect all cards of all suits in their respective foundation piles. A card can be added to a foundation pile if and only if its suit matches that of the pile, and its value is one more than that of the card currently on top of the pile (i.e., the next card in foundation pile 2 in the figure above can only be 3♣). If a foundation pile is currently empty, any ace can be added to it: there is no required ordering of suits in the foundation piles. Note that in the figure above, the 2♣ has an A♣ underneath it, and the 2♥ has an A♥ underneath it, though these aces are not visible.

The second type of pile is the cascade pile (the eight piles in the bottom of figure above), also indexed starting from 1. Usually a game of Freecell has eight cascade piles, but our game will allow as few as four. Because the initial deal of the game is shuffled (see below), a cascade pile may initially contain cards in any order. However, a card from some pile can be moved to the end of a cascade pile if and only if its color is different from that of the currently last card, and its value is exactly one less than that of the currently last card (e.g. in the figure above, the next card in cascade pile 1 can be 4♦ or 4♥ while the next card in cascade pile 3 can be 10♠ or 10♣). This sequence of decreasing-value, alternating-color cards is called a build . (Different variants of Freecell, or other solitaire games, differ primarily in what builds are permitted.)

Finally, the third type of pile is the open pile (top left in figure above). Usually a game of Freecell has four open piles, but our game will allow as few as one. An open pile may contain at most one card. An open pile is usually used as a temporary buffer during the game to hold cards.

## How to Play
Play starts by dealing the full deck of 52 cards among the cascade piles, in a round-robin fashion (e.g. in a game with eight cascade piles, pile 1 will have card indices 0, 8, 16,..., pile 2 will have card indices 1, 9, 17, ... and so on (assuming card indices in the deck begin at 0). The player then moves cards between piles, obeying the rules above. The objective of the game is to collect all cards in the four foundation piles, at which stage the game is over.

## How to run this program

Use controller/Mainclass.java to play this game

#### Moving card 4 from Cascade pile 1 to Open pile 4
c2 c4 o4

#### Moving card 7 from Cascade pile 8 to Foundation pile 2
c7 c8 f2

#### Moving card 1 from Open pile 1 to Foundation pile 3
o1 c8 f3

#### to quit the game
q

Note: 
1. Don't move any cards that a pile don't exist
2. Don't move any cards that exceed from the pile size
3. Don't put any commands that isn't among the above commands
