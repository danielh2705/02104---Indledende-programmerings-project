package src;

import javafx.scene.media.AudioClip;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;

/* INSTRUCTIONS/IMPORTANT

The AudioClip class is NOT capable of using all audio files. We'll stick to WAV for practicalities sake.
This means we have to convert files to WAV files. This is easily done online.

Use this to load audio. allLoader() uses load() across all files in /sounds.
This means you can continously add new files to SfxReg without changing anything elsewhere.
    SfxPlayer.allLoader();

Following methods can all be used to play/configure audio:

    audioPlayer() allows for audio playback + volume changing:
        SfxPlayer.audioPlayer("[name]", [double]);

    play() allows for audio playback
        SfxPlayer.play("[name]");

    setVolume() allows for volume changing
        SfxPlayer.setVolume("[name]", [double]);
*/

public final class SfxPlayer {

    // creates hashmap
    private static final Map<String, AudioClip> clips = new HashMap<>();
    private SfxPlayer() {}

    public static void load(String name, String filename) {
        // finds location/URL of file and makes it usable in AudioClip
        URL url = SfxPlayer.class
                .getClassLoader()
                .getResource(filename);

        // if url has the value of null, then the sound doesn't exist in /sounds.
        // it most likely means you made a typo during call.
        if (url == null) {
            throw new IllegalArgumentException(
                "sfx sound not found in /sounds: " + filename
            );
        }

        AudioClip clip = new AudioClip(url.toExternalForm());

        // sends name and usable audio to hashmap
        clips.put(name, clip);
    }

    // play method that allows playback of audio without defining audio. Uses default volume of 1.0.
    public static void play(String name) {
        AudioClip clip = clips.get(name);
        if (clip != null) {
            clip.play();
        }
    }

    // setVolume method that allows configuration of volume between 0.0 and 1.0
    public static void setVolume(String name, double volume) {
        AudioClip clip = clips.get(name);
        if (clip != null) {
            clip.setVolume(Math.max(0.0, Math.min(1.0, volume)));
        }
    }

    // combined method that allows user to call audio playback and volume.
    public static void audioPlayer(String name, double volume) {
        AudioClip clip = clips.get(name);
        if (clip != null) {
            clip.setVolume(Math.max(0.0, Math.min(1.0, volume)));
            clip.play();
        }
    }

    // allLoader method allows loading of all saved audios in sound registry.
    // no need for many lines of load(), just a single line as such:
    // SfxPlayer.allLoader()
    public static void allLoader() {
        for (SfxReg s : SfxReg.values()) {
            SfxPlayer.load(s.name(), s.path());
        }
    }

}
