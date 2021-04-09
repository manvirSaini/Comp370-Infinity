package com.example.infinity_courseproject.ui.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.SoundPool;

import com.example.infinity_courseproject.R;


public class SoundPoolUtil {
    private static SoundPoolUtil soundPoolUtil;
    private SoundPool soundPool;


    public static SoundPoolUtil getInstance(Context context) {
        if (soundPoolUtil == null)
            soundPoolUtil = new SoundPoolUtil(context);
        return soundPoolUtil;
    }

    @SuppressLint("NewApi")
    private SoundPoolUtil(Context context) {
//        soundPool = new SoundPool(3, AudioManager.STREAM_SYSTEM, 0);
        soundPool = new SoundPool.Builder().build();

        soundPool.load(context, R.raw.click_music, 1);

    }

    public void play(int number) {
//        Log.v(MyApplication.TAG, "SoundPool number: " + number);
        soundPool.play(number, 1, 1, 0, 0, 1);
    }

    public static void isplay( ) {

            if (MyApplication.shengyin==1)
                MyApplication.soundPoolUtil.play(1);

    }
}
