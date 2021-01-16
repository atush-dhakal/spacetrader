import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class AudioController {
    private final String bgmPath = "src/assets/retro_platforming.mp3";
    private Media bgmMedia;
    private MediaPlayer bgmMp;

    /**
     * Audio controller controller
     */
    public AudioController() {
        bgmMedia = new Media(new File(bgmPath).toURI().toString());
        bgmMp = new MediaPlayer((bgmMedia));
        bgmMp.setAutoPlay(true);
        bgmMp.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                bgmMp.seek(Duration.ZERO);
                bgmMp.play();
            }
        });
        setVolume(0.25);
    }

    /**
     * Setter method for volume
     * @param d the volume
     */
    public void setVolume(double d) {
        bgmMp.setVolume(d);
    }

    /**
     * Stops the music
     */
    public void stop() {
        bgmMp.stop();
    }

}
