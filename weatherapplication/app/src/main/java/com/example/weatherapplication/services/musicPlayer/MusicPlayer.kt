import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import com.example.weatherapplication.R

class MusicPlayer(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var currentTrack: Int? = null

    fun playMusicForWeather(condition: String, isDay: Boolean) {
        val trackRes = when {
            !isDay -> R.raw.nightsong
            condition.contains("sunny", ignoreCase = true) ||
                    condition.contains("clear", ignoreCase = true) -> R.raw.sunnysong
            condition.contains("rain", ignoreCase = true) ||
                    condition.contains("drizzle", ignoreCase = true) -> R.raw.nightsong
            condition.contains("snow", ignoreCase = true) ||
                    condition.contains("sleet", ignoreCase = true) -> R.raw.snowsong
            condition.contains("thunder", ignoreCase = true) -> R.raw.thundersong
            else -> R.raw.cloudsong
        }

        // Если тот же трек уже играет - ничего не делаем
        if (currentTrack == trackRes) return

        // Останавливаем и освобождаем предыдущий плеер
        stopCurrentTrack()

        // Запускаем новый трек
        try {
            mediaPlayer = MediaPlayer.create(context, trackRes).apply {
                setVolume(0.3f, 0.3f)
                isLooping = true
                start()
            }
            currentTrack = trackRes
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopCurrentTrack() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
            mediaPlayer = null
        }
        currentTrack = null
    }

    fun release() {
        stopCurrentTrack()
    }

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying ?: false
}