import java.io.Serializable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.border.EmptyBorder;

public class PieceSelectionScreen extends JFrame implements Serializable {
    private BorderLayout layout; // borderlayout object

    public PieceSelectionScreen() {
        super("Select a Piece"); // pass title for window to parent constructor
        
        setSize(400, 400);   // set frame size
        setLocationRelativeTo(null);      // center frame on screen
        setUndecorated(true);
        layout = new BorderLayout( 5, 5 ); // 5 pixel gaps

        setLayout( layout ); // set frame layout

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 4, 4));
        panel.setBackground( Color.WHITE );     // set frame background color
        add(panel, BorderLayout.CENTER);
        JTextField textFieldNorth = new JTextField();
        textFieldNorth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textFieldNorth.setHorizontalAlignment(JTextField.CENTER);
        Font font = new Font(textFieldNorth.getFont().getName(), Font.BOLD, 22);
        textFieldNorth.setFont(font);
        textFieldNorth.setEditable(false);
        textFieldNorth.setText("Choose a Piece");
        add(textFieldNorth, BorderLayout.NORTH);

        setVisible( true );               // display frame

        String image;

        image = "./images/png/" + ChessGame.getCurrentPlayer().toLowerCase() + "_" + "rook" + ".png";
        JLabel rook = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
        JPanel rookPanel = new JPanel();
        rookPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
        rook.setVerticalAlignment(JLabel.CENTER);
        rookPanel.add(rook);
        panel.add(rookPanel);

        image = "./images/png/" + ChessGame.getCurrentPlayer().toLowerCase() + "_" + "knight" + ".png";
        JLabel knight = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
        JPanel knightPanel = new JPanel();
        knightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
        knight.setVerticalAlignment(JLabel.CENTER);
        knightPanel.add(knight);
        panel.add(knightPanel);

        image = "./images/png/" + ChessGame.getCurrentPlayer().toLowerCase() + "_" + "bishop" + ".png";
        JLabel bishop = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
        JPanel bishopPanel = new JPanel();
        bishopPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
        bishop.setVerticalAlignment(JLabel.CENTER);
        bishopPanel.add(bishop);
        panel.add(bishopPanel);

        image = "./images/png/" + ChessGame.getCurrentPlayer().toLowerCase() + "_" + "queen" + ".png";
        JLabel queen = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
        JPanel queenPanel = new JPanel();
        queenPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
        queen.setVerticalAlignment(JLabel.CENTER);
        queenPanel.add(queen);
        panel.add(queenPanel);
        
        
        
    }
    
}