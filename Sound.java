import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

public class Sound {
    private File audioFile;
    private AudioInputStream audioStream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip audioClip;
    private byte[] audio;
    private int audioSize;

    public Sound(String filename) {
        // init audio
        try{
            audioFile = new File(filename);
            audioStream = AudioSystem.getAudioInputStream(audioFile.toURI().toURL());
            format = audioStream.getFormat();
            audioSize = (int) (format.getFrameSize() * audioStream.getFrameLength());
            audio = new byte[audioSize];
            info = new DataLine.Info(Clip.class, format);
            audioStream.read(audio, 0, audioSize);
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            //ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            //ex.printStackTrace();
        }
    }

    public void play() {
        // play sound clip
        try {
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(format, audio, 0, audioSize);
            audioClip.start();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            //ex.printStackTrace();
        }
    }
}