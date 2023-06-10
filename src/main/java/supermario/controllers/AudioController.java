package supermario.controllers;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class AudioController {
    private static final Map<String, AudioInputStream> cache = new HashMap<>();
    private static final Map<String, Clip> playingClips = new HashMap<>();

    public static AudioInputStream loadWavAudioWithName(String audioName) {
        if (cache.get(audioName) == null) {
            try {
                InputStream bufferedInputStream = AudioController.class.getResourceAsStream("/audio/" + audioName + ".wav");
                if (bufferedInputStream == null)
                    throw new RuntimeException("Audio file " + audioName + " does not exist.");
                bufferedInputStream = new BufferedInputStream(bufferedInputStream);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
                cache.put(audioName, audioInputStream);
                return audioInputStream;
            } catch (UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else
            return cache.get(audioName);
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
            clip.start();
            clip.loop(loopCount);
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
