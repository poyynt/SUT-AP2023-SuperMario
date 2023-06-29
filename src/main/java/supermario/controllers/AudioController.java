package supermario.controllers;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class AudioController {
    private static final Map<String, Clip> playingClips = new HashMap<>();
    private static boolean muted = false;

    public static AudioInputStream loadWavAudioWithName(String audioName) {
            InputStream inputStream = AudioController.class.getResourceAsStream("/audio/" + audioName + ".wav");
            if (inputStream == null)
                throw new RuntimeException("Audio file " + audioName + " does not exist.");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        try {
            return AudioSystem.getAudioInputStream(bufferedInputStream);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void playWavAudioOnChannel(String channelName, String audioName, int loopCount) {
        try {
            if (playingClips.get(channelName) != null) {
                playingClips.get(channelName).stop();
                playingClips.get(channelName).close();
                playingClips.remove(channelName);
            }
        }
        catch (Exception ignored) {

        }
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(loadWavAudioWithName(audioName));
            ((BooleanControl) clip.getControl(BooleanControl.Type.MUTE)).setValue(muted);
            clip.loop(loopCount);
            playingClips.put(channelName, clip);
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopAudioOnChannel(String channelName) {
        if (playingClips.get(channelName) != null) {
            Clip clip = playingClips.remove(channelName);
            clip.stop();
            clip.close();
        }
    }

    public static void mute() {
        muted = true;
        for (Clip c: playingClips.values()) {
            BooleanControl muteControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            muteControl.setValue(true);
        }
    }

    public static void unMute() {
        muted = false;
        for (Clip c: playingClips.values()) {
            BooleanControl muteControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            muteControl.setValue(false);
        }
    }
}
