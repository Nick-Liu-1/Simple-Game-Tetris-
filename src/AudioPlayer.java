import java.io.*;
import javax.sound.sampled.*;


public class AudioPlayer
{
    Long currentFrame;
    Clip clip;

    String status;

    private AudioInputStream audioInputStream;
    private static String filePath;
    private boolean loop;

    // constructor to initialize streams and clip
    public AudioPlayer(String filePath, boolean loop)  {
        // create AudioInputStream object
        this.filePath = filePath;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create clip reference
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        // open audioInputStream to the clip
        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.loop = loop;

    }


    // Method to play the audio
    public void play()
    {
        //start the clip
        clip.start();
        status = "play";
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Method to pause the audio
    public void pause()
    {
        currentFrame = clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        play();
    }

    // Method to restart the audio
    public void restart() {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        play();
    }

    // Method to stop the audio
    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    // Method to reset audio stream
    public void resetAudioStream() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}