/*

    Zach Sirotto and Ben Cook
    Assignment X - Chess!!

*/
    
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.URISyntaxException;

public class Sound {
    private File audioFile;
    private AudioInputStream audioStream;
    //private FileInputStream inputStream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip audioClip;
    private byte[] audio;
    private int audioSize;
    private boolean isPlaying;
    private boolean synch;

    public Sound(String filename, boolean synchronize) {
        synch = synchronize;
        try{ // init audio
            //audioFile = new File(getClass().getResource(filename));
            audioFile = new File(filename);
            //inputStream = new FileInputStream(new File(getClass().getResource(filename).toURI()));
            audioStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(filename));
            format = audioStream.getFormat();
            audioSize = (int) (format.getFrameSize() * audioStream.getFrameLength());
            audio = new byte[audioSize];
            info = new DataLine.Info(Clip.class, format);
            audioStream.read(audio, 0, audioSize);
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
        } catch (IOException ex) {
            System.out.println("Error reading the audio file.");
        } catch (Exception ex) {
            // catch all not to mess with game if audio doesnt load mwahahah
            System.out.println("audio didn't load because of the jar.... tooo bad. Q_Q");
            System.out.println("try running the game outside of the jar for audio capability.");
        }
    }

    public void play() {
        synchronized(Sound.class) {
            try { // play sound clip
                audioClip = (Clip) AudioSystem.getLine(info);
                audioClip.open(format, audio, 0, audioSize);
                audioClip.start();
                isPlaying = true;
                if(synch)
                    while(audioClip.getMicrosecondLength() != audioClip.getMicrosecondPosition()) {}
                isStopped();
            } catch (LineUnavailableException ex) {
                System.out.println("Audio line for playing back is unavailable.");
            } catch (Exception ex) {
                // catch all to not mess with game if audio doesnt load mwahaha
            }
        }
    }

    public boolean isPlaying() { return isPlaying; }
    public void isStopped() { isPlaying = false; }
}