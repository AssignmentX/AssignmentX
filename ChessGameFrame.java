import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.BorderFactory;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JFrame implements serializable
public class ChessGameFrame extends JFrame implements ActionListener 
{
   private JButton buttons[]; // array of buttons to hide portions
   private ChessBoardPanel chessBoard;
   private final String names[] = { "North", "South", 
      "East", "West"};
   private BorderLayout layout; // borderlayout object

   // set up GUI and event handling
   public ChessGameFrame(String s)
   {
      super(s);

      layout = new BorderLayout( 5, 5 ); // 5 pixel gaps
      setLayout( layout ); // set frame layout

      buttons = new JButton[ names.length ]; // set size of array

      chessBoard = new ChessBoardPanel(); // create a panel for the chess board
      chessBoard.setBackground(Color.WHITE); // set background to white
      chessBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
      add( chessBoard, BorderLayout.CENTER ); // add chess board to center

      //create JButtons and register listeners for them
      for ( int count = 0; count < names.length; count++ ) 
      {
         buttons[ count ] = new JButton( names[ count ] );
         buttons[ count ].addActionListener( this );
      }

      add( buttons[ 0 ], BorderLayout.NORTH ); // add button to north
      add( buttons[ 1 ], BorderLayout.SOUTH ); // add button to south
      add( buttons[ 2 ], BorderLayout.EAST ); // add button to east
      add( buttons[ 3 ], BorderLayout.WEST ); // add button to west
      
   }

   // handle button events
   public void actionPerformed( ActionEvent event ) {
   //{
   //   // check event source and lay out content pane correspondingly
   //   for ( JButton button : buttons )
   //   {
   //      if ( event.getSource() == button )
   //         button.setVisible( false ); // hide button clicked
   //      else
   //         button.setVisible( true ); // show other buttons
   //   } // end for

      layout.layoutContainer( getContentPane() ); // lay out content pane
   }
}