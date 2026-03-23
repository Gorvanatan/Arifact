package com.pixelpeek;

import javafx.scene.media.AudioClip;

public class SoundManager {

    private static AudioClip startup;
    private static AudioClip search;
    private static AudioClip success;
    private static AudioClip error;

    public static void init() {
        startup = load("/sounds/startup.mp3");
        search  = load("/sounds/search.mp3");
        success = load("/sounds/success.mp3");
        error   = load("/sounds/error.mp3");
    }

    private static AudioClip load(String path) {
        try {
            return new AudioClip(SoundManager.class.getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("Could not load sound: " + path);
            return null;
        }
    }

    public static void playStartup() { if (startup != null) startup.play(); }
    public static void playSearch()  { if (search  != null) search.play();  }
    public static void playSuccess() { if (success != null) success.play(); }
    public static void playError()   { if (error   != null) error.play();   }
}