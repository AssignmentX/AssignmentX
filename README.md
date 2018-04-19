# COP3252: Java
## AssignmentX
### Coded by: Zach Sirotto and Ben Cook


#### _*Any details regarding instructions for the game that are not obvious from the set of standard known instructions*_
##### n/a

#### _*A description of how to use the interface*_
##### Click squares to select pieces, click squares to move.

#### _*Any other important details about your implementation, how to run the program, etc*_
##### The jar file can be ran with the command:
    java -jar hwx.jar
##### Or if you unjar `hwx.jar` then you can run the game with the command:
    java ChessGame

#### _*Descriptions of any extra features implemented*_
 * New game
 * Save game
 * Load game
 * Highlight valid moves
 * Make invalid moves flash red
 * Sounds

#### _*A description of the separation of work between partners (who was responsible for what pieces of the program).*_
_*Ben*_
 * Move logic for pieces (most of the work)
 * ChessFrame GUI
 * ChessBoard Border GUI
 * Translation between 1d-2d array and vise-versa
 * Validation of move (split between the both of us)
 * Serialization of objects for Saving / Loading
 * Menu Options
 * Valid move predicates (split between both of us)
 * Code refactoring

_*Zach*_
 * Move logic for pieces (some of the work)
 * ChessBoard setup and GUI
 * ChessSquare GUI
 * Chess Pieces GUI
 * Check detection
 * Validation of move (split between the both of us)
 * Checkmate detection
 * Valid move predicates (split between both of us)
 * Coloring of squares / re-coloring
 * En passant rule
 * Code refactoring

_*Un-assigned Work*_ _(This will eventually be removed once the work is delegated)_
 * Castling
 * Threefold repitition rule
 * Stalemate