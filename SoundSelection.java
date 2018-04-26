/*

    Zach Sirotto and Ben Cook
    Assignment X - Chess!!

*/
    
import java.io.Serializable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;



public class SoundSelection extends JFrame implements Serializable {
    BoxLayout layout;
    JCheckBox soundEffects;
    JCheckBox voiceAssist;
    JPanel panel;
    JTextField textField;
    Font font;

    public SoundSelection() {
        super("Sound"); // pass title for window to parent constructor
        
        setSize(320, 320);   // set frame size
        setLocationRelativeTo(null);      // center frame on screen
        setLayout(new BorderLayout(10,10));

        panel = new JPanel();
        textField = new JTextField("Sound Settings");
        soundEffects = new JCheckBox("Sound Effects");
        voiceAssist = new JCheckBox("Voice Assist");
        layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);

        //panel.setAlignmentY(Component.LEFT_ALIGNMENT);
        //voiceAssist.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textField.setHorizontalAlignment(JTextField.CENTER);
        font = new Font(textField.getFont().getName(), Font.BOLD, 28);
        textField.setFont(font);
        textField.setEditable(false);

        font = new Font(textField.getFont().getName(), Font.BOLD, 22);
        soundEffects.setFont(font);
        voiceAssist.setFont(font);

        add(textField, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        if(ChessGame.soundEffects())
            soundEffects.setSelected(true);
        if(ChessGame.voiceAssist())
            voiceAssist.setSelected(true);

        soundEffects.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                    ChessGame.enableSoundEffects();
                else
                    ChessGame.disableSoundEffects();
            }
        });
        voiceAssist.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                    ChessGame.enableVoiceAssist();
                else
                    ChessGame.disableVoiceAssist();
            }
        });

        panel.add(soundEffects);
        panel.add(voiceAssist);

        panel.setLayout(layout);
        //panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));

        setVisible(true);

    }

}
