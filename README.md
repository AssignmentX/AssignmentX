# COP3252: Java
## AssignmentX
### Coded by: Zach Sirotto and Ben Cook


#### **ANY DETAILS REGARDING INSTRUCTIONS FOR THE GAME THAT ARE NOT OBVIOUS FROM THE SET OF STANDARD KNOWN INSTRUCTIONS**
* n/a

#### **A DESCRIPTION OF HOW TO USE THE INTERFACE**
* Click squares to select pieces, click squares to move.

#### **Any other important details about your implementation, how to run the program, etc**
* The jar file can be ran with the command: `java -jar` hwx.jar
* Or if you unjar `hwx.jar` then you can run the game with the command: `java ChessGame`

#### **DESCRIPTIONS OF ANY EXTRA FEATURES IMPLEMENTED**
 * New game
 * Save game
 * Load game
 * Highlight valid moves
 * Make invalid moves flash red
 * Sounds

#### **A DESCRIPTION OF THE SEPARATION OF WORK BETWEEN PARTNERS (WHO WAS RESPONSIBLE FOR WHAT PIECES OF THE PROGRAM).**
### **Ben**
 * Move logic for pieces (most of the work)
 * ChessFrame GUI
 * ChessBoard Border GUI
 * Translation between 1d-2d array and vise-versa
 * Validation of move (split between the both of us)
 * Serialization of objects for Saving / Loading
 * Menu Options
 * Valid move predicates (split between both of us)
 * Code refactoring

### **Zach**
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

### **Un-assigned Work** _(This will eventually be removed once the work is delegated)_
 * Castling
 * Threefold repitition rule
 * Stalemate