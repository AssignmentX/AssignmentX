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

public class Sound {
    private File audioFile;
    private AudioInputStream audioStream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip audioClip;
    private byte[] audio;
    private int audioSize;
    //private LineListener listener;
    private boolean isPlaying;
    private boolean synch;

    public Sound(String filename, boolean synchronize) {
        synch = synchronize;
        // init audio
        try{
            audioFile = new File(filename);
            audioStream = AudioSystem.getAudioInputStream(audioFile.toURI().toURL());
            format = audioStream.getFormat();
            audioSize = (int) (format.getFrameSize() * audioStream.getFrameLength());
            audio = new byte[audioSize];
            info = new DataLine.Info(Clip.class, format);
            audioStream.read(audio, 0, audioSize);

            //listener = new LineListener() {
            //    public void update(LineEvent event) {
            //        if(audioClip.getMicrosecondLength() == audioClip.getMicrosecondPosition()) {
            //        //if (event.getType() == Type.STOP) {
            //            isStopped();
            //            return;
            //        }
            //    }
            //};



        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            //ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error reading the audio file.");
            //ex.printStackTrace();
        }
    }

    public void play() {
        //new Thread(new Runnable() {
        //    public void run() {
        //        synchronized(Sound.class) {
        //            try { // play sound clip
        //                audioClip = (Clip) AudioSystem.getLine(info);
        //                audioClip.open(format, audio, 0, audioSize);
        //                audioClip.start();
        //                //while(audioClip.isRunning()) { System.out.println("waiting"); }
        //                while(audioClip.getMicrosecondLength() != audioClip.getMicrosecondPosition()) {}
        //            } catch (LineUnavailableException ex) {
        //                System.out.println("Audio line for playing back is unavailable.");
        //                //ex.printStackTrace();
        //            }
        //        }
        //    }
        //}).start();
        synchronized(Sound.class) {
            try { // play sound clip
                audioClip = (Clip) AudioSystem.getLine(info);
                audioClip.open(format, audio, 0, audioSize);
                audioClip.start();
                isPlaying = true;
                if(synch) {
                    while(audioClip.getMicrosecondLength() != audioClip.getMicrosecondPosition()) {}
                }
                isStopped();
            } catch (LineUnavailableException ex) {
                System.out.println("Audio line for playing back is unavailable.");
                //ex.printStackTrace();
            }
        }
    }

    public boolean isPlaying() { return isPlaying; }
    public void isStopped() { isPlaying = false; }
}

/*
class SoundThread extends Thread implements Runnable {
    public void run() {
        for(Sound sound : ChessGame.getSoundQueue()) {
            // play sound and wait for it to finish
            sound.play();
            // fix this for synchronization
            //while(sound.isPlaying()) {
                //try {
                //    Thread.sleep(10);
                //} catch(InterruptedException e) {}
            //}
        }
        // clear the sound queue
        ChessGame.getSoundQueue().clear();
    }
}
*/