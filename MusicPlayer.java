package src;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// IMPORTANT
// MediaPlayer has file support for any file type.
// Remember to find some background music.
// Call Musicplayer.start() after launch.

public final class MusicPlayer {

    private static MediaPlayer audioPlayer;
    private MusicPlayer() {}

    public static void start() {
        // guard clause for future "play again" button
        if (audioPlayer != null) {
            return;
        }

        Media media = loadMedia();

        audioPlayer = new MediaPlayer(media); // Mediaplayer requires URI
        audioPlayer.setCycleCount(MediaPlayer.INDEFINITE); // infinite looping of music
        audioPlayer.play();
    }
    private static Media loadMedia() {
        // finds location/URL of file and makes it usable in MediaPlayer
        URL url = MusicPlayer.class
                .getClassLoader() // finds all resources
                .getResource("sounds/[blank].[filetype]"); // returns URL. replace blank with file name.

        // if url has the value of null, then the music doesn't exist in /sounds.
        // it most likely means you made a typo in getResource.
        if (url == null) {
            throw new IllegalArgumentException(
                "music not found in /sounds"
            );
        }

        return new Media(url.toExternalForm());
    }
}

