import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import java.awt.Graphics;
import java.awt.Graphics2D;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JFrame implements serializable
public class ChessGameFrame extends JFrame {
   private JButton buttons[]; // array of buttons to hide portions
   private JTextArea textArea;
   private ChessBoardPanel chessBoard;
   private final String names[] = { "North", "South", "West"};
   private BorderLayout layout; // borderlayout object

   // set up GUI and event handling
   public ChessGameFrame(String s) {
      super(s); // pass title for window to parent constructor

      layout = new BorderLayout( 5, 5 ); // 5 pixel gaps
      setLayout( layout ); // set frame layout

      textArea = new JTextArea(0, 10);
      textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
      textArea.setEditable(false);
      add(textArea, BorderLayout.EAST); // add text area to east side

      chessBoard = new ChessBoardPanel(this); // create a panel for the chess board
      add(chessBoard, BorderLayout.CENTER); // add chess board to center
   }

   public void appendTextArea(String s) {
      textArea.append(s + "\n");
   }
}