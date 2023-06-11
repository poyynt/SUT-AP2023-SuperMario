package supermario.controllers;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class AudioController {
    private static final Map<String, Clip> playingClips = new HashMap<>();

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
        if (playingClips.get(channelName) != null) {
            playingClips.get(channelName).stop();
            playingClips.get(channelName).close();
            playingClips.remove(channelName);
        }
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(loadWavAudioWithName(audioName));
            clip.setFramePosition(0);
            clip.start();
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
}