# COP3252: Java
## AssignmentX
### Coded by: Zach Sirotto and Ben Cook



### **ANY DETAILS REGARDING INSTRUCTIONS FOR THE GAME THAT ARE NOT OBVIOUS FROM THE SET OF STANDARD KNOWN INSTRUCTIONS**
* n/a

### **A DESCRIPTION OF HOW TO USE THE INTERFACE**
* Click squares to select pieces, click squares to move.

### **ANY OTHER IMPORTANT DETAILS ABOUT YOUR IMPLEMENTATION, HOW TO RUN THE PROGRAM, ETC**
**For best rendering and scaling performance, instead of double clicking the jar:**
* The jar file can be ran with the command: `java -jar hwx.jar`
* Or if you unjar `hwx.jar` then you can run the game with the command: `java ChessGame`

### **DESCRIPTIONS OF ANY EXTRA FEATURES IMPLEMENTED**
 * New game
 * Save game
 * Load game
 * Highlight valid moves
 * Make invalid moves flash red
 * Sounds

### **A DESCRIPTION OF THE SEPARATION OF WORK BETWEEN PARTNERS (WHO WAS RESPONSIBLE FOR WHAT PIECES OF THE PROGRAM).**
## **Ben**
 * Move logic for pieces (most of the work)
 * ChessFrame GUI
 * ChessBoard Border GUI
 * Translation between 1d-2d array and vise-versa
 * Validation of move (most of the work)
 * Serialization of objects for Saving / Loading
 * Menu Options
 * Valid move predicates (split between both of us)
 * Code refactoring

## **Zach**
 * Move logic for pieces (some of the work)
 * ChessBoard setup and GUI
 * ChessSquare w/ Chess Piece GUI
 * Check detection
 * Validation of move (some of the work)
 * Valid move predicates (split between both of us)
 * Coloring of squares / re-coloring
 * En passant rule
 * Code refactoring
 * Sound class
 * Piece selection when pawn makes it to the other side of the board

## **Un-assigned Work** _(This will eventually be removed once the work is delegated)_
 * Castling
 * Stalemate
 * Checkmate detection
 * Timer
 * Keep track of captured pieces

#### **HOW TO COMPILE JAR FILE**
`jar -cvfm hwx.jar MANIFEST.MF *.java *.class README.md assets images`
