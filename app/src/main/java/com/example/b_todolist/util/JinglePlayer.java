package com.example.b_todolist.util;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.Looper;

public class JinglePlayer {
    private JinglePlayer() {
    }

    public static void playRewardJingle() {
        final ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 80);
        Handler handler = new Handler(Looper.getMainLooper());

        toneGenerator.startTone(ToneGenerator.TONE_PROP_ACK, 160);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2, 180);
            }
        }, 200);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toneGenerator.release();
            }
        }, 450);
    }
}
