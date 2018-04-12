import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JFrame implements serializable
public class ChessGameFrame extends JFrame {
   private JButton buttons[]; // array of buttons to hide portions
   private JTextArea textArea;
   private JTextField textFieldNorth;
   private ChessBoardPanel chessBoard;
   private BorderLayout layout; // borderlayout object

   // set up GUI and event handling
   public ChessGameFrame(String s) {
      super(s); // pass title for window to parent constructor

      layout = new BorderLayout( 5, 5 ); // 5 pixel gaps
      setLayout( layout ); // set frame layout


      // EAST - SHOWS MOVE HISTORY FOR BOTH PLAYERS IN ALGEBRAIC NOTATION NOTATION
      textArea = new JTextArea(0, 12);
      textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
      textArea.setEditable(false);
      add(textArea, BorderLayout.EAST); // add text area to east side

       // CENTER - CHESSBOARD
      chessBoard = new ChessBoardPanel(this); // create a panel for the chess board
      add(chessBoard, BorderLayout.CENTER); // add chess board to center

       // NORTH - DISPLAYS CURRENT PLAYER
      textFieldNorth = new JTextField();
      textFieldNorth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      textFieldNorth.setHorizontalAlignment(JTextField.CENTER);
      // TODO: we should probably have this text resize dynamically with the frame size
      Font font = new Font(textFieldNorth.getFont().getName(), Font.BOLD, 22);
      textFieldNorth.setFont(font);
      textFieldNorth.setEditable(false);
      add(textFieldNorth, BorderLayout.NORTH);

      // WEST - DISPLAYS ALGEBRAIC NOTATION LABELS 1-8
       JPanel west_sub_panel = new JPanel();
       west_sub_panel.setLayout(new GridLayout(8, 1,3,3));
       for (int i = 8; i >= 1; i--) {
           JTextField label = new JTextField();
           label.setBorder(javax.swing.BorderFactory.createEmptyBorder());
           label.setFont(font);
           label.setEditable(false);
           label.setText(String.valueOf(i));
           west_sub_panel.add(label);
       }
       add(west_sub_panel,BorderLayout.WEST);

       // TODO NEED TO ALIGN SOUTH WITH THE BOARD GRID, may need to swap to GridBagLayout
      // SOUTH - DISPLAYS ALGEBRAIC NOTATION LABELS a-h
      JPanel south_sub_panel = new JPanel();
      south_sub_panel.setLayout(new GridLayout(1, 9, 3, 3));
      for (char alphabet = 'a'; alphabet <= 'h'; alphabet++) {
          JTextField label = new JTextField();
          label.setFont(font);
          label.setEditable(false);
          label.setText(String.valueOf(alphabet));
          //label.setBorder(javax.swing.BorderFactory.createEmptyBorder());
          label.setHorizontalAlignment(JTextField.CENTER);
          south_sub_panel.add(label);
      }
      add(south_sub_panel,BorderLayout.SOUTH);
   }

   public ChessBoardPanel getBoard() { return chessBoard; }
   public void appendTextArea(String s) { textArea.append(s + "\n"); }
   public void setNorthTextField(String s) { textFieldNorth.setText(s); }
}