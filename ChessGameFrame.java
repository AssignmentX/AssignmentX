import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.awt.Graphics;
import java.awt.Graphics2D;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JFrame implements serializable
public class ChessGameFrame extends JFrame {
   private JButton buttons[]; // array of buttons to hide portions
   private JTextArea textArea;
   private JTextField textFieldNorth;
   private JTextField textFieldSouth;
   private ChessBoardPanel chessBoard;
   private BorderLayout layout; // borderlayout object

   // set up GUI and event handling
   public ChessGameFrame(String s) {
      super(s); // pass title for window to parent constructor

      layout = new BorderLayout( 5, 5 ); // 5 pixel gaps
      setLayout( layout ); // set frame layout

      textArea = new JTextArea(0, 12);
      textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
      textArea.setEditable(false);
      add(textArea, BorderLayout.EAST); // add text area to east side

      chessBoard = new ChessBoardPanel(this); // create a panel for the chess board
      add(chessBoard, BorderLayout.CENTER); // add chess board to center

      textFieldNorth = new JTextField();
      textFieldNorth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      textFieldNorth.setHorizontalAlignment(JTextField.CENTER);
      // TODO: we should probably have this text resize dynamically with the frame size
      Font font = new Font(textFieldNorth.getFont().getName(), Font.BOLD, 22);
      textFieldNorth.setFont(font);
      textFieldNorth.setEditable(false);
      add(textFieldNorth, BorderLayout.NORTH);

      // TODO started thinking about how to make position labels, maybe need GridBagLayout
       // TODO or maybe create another JPanel and add it to BorderLayout.South for more control
      /*
      textFieldSouth = new JTextField();
      textFieldSouth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      textFieldSouth.setHorizontalAlignment(JTextField.LEFT);
      textFieldSouth.setEditable(false);
      textFieldSouth.setFont(font);
      textFieldSouth.setText("A");
      add(textFieldSouth, BorderLayout.SOUTH);
      */

   }

   public ChessBoardPanel getBoard() { return chessBoard; }
   public void appendTextArea(String s) { textArea.append(s + "\n"); }
   public void setNorthTextField(String s) { textFieldNorth.setText(s); }
}