package com.supershooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by mrsch_000 on 2/8/2017.
 */
public class AudioManager {
    public static final Sound SHOOT = Gdx.audio.newSound(Gdx.files.internal("shoot1.wav"));
    public static final Sound DIE = Gdx.audio.newSound(Gdx.files.internal("die.wav"));
    public static final Sound MUSIC = Gdx.audio.newSound(Gdx.files.internal("BackgroundHorribleMusic.mp3"));

    public AudioManager() {

    }
    public static void pause(){
        MUSIC.pause();

    }

    public static void resume() {
        MUSIC.resume();

    }



}
