package com.hnguyen.childrenlearninggame.model.Components;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import com.hnguyen.childrenlearninggame.MainGamePanel;
import com.hnguyen.childrenlearninggame.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hnguyen on 12/16/15.
 */
public class Voice {
    private static String TAG = Voice.class.getSimpleName();
    private static final String[] alphabets = {"a","b","c","d","e","f","g","h","i","j","k","l",
                                                "m","n","o","p","q","r","s","t","u","v","w","x",
                                                "y","z"};
    private Map<String,String> soundCommands;
    private static final Map<String, String> numNames;
    static {
        numNames = new HashMap<String,String>();
        numNames.put("1","one");
        numNames.put("2","two");
        numNames.put("3","three");
        numNames.put("4","four");
        numNames.put("5","five");
        numNames.put("6","six");
        numNames.put("7","seven");
        numNames.put("8","eight");
        numNames.put("9","nine");

    }
    private Map<String,Integer> soundMap;
    private SoundPool sounds;

    private Context context;
    private Boolean loaded = false;
    public Voice(Context context){
        this.context = context;
        sounds = buildSoundPool();
        sounds.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        soundCommands = new HashMap<String, String>();
        for (String letter:alphabets) {
            int fileID = MainGamePanel.getId(letter,R.raw.class);
            int command = sounds.load(this.context,fileID,1);
            soundCommands.put(letter,String.valueOf(command));
        }
        for (String key:numNames.keySet()){
            int fileID = MainGamePanel.getId(numNames.get(key), R.raw.class);
            Log.d(TAG,"key="+key+",,,fileID="+Integer.toString(fileID));
            int command = sounds.load(this.context,fileID,1);
            soundCommands.put(key, String.valueOf(command));
        }

        for (String key:soundCommands.keySet()){
            Log.d(TAG,"key="+key);
        }
    }


    public void saidCommand(Character c) {
        String command = Character.toString(c).toLowerCase();
        if( soundCommands.get(command) !=null ) {
            int soundCommand = Integer.parseInt(soundCommands.get(command));
            Log.d(TAG, "saidCommand=" + Character.toString(c));
            if (loaded) {
                sounds.play(soundCommand, 1.0f, 1.0f, 0, 0, 1.0f);
                sounds.stop(soundCommand);
            }
        }
    }


    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool buildSoundPool(){
        SoundPool soundPool;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
            return soundPool;
        } else {
            return buildBeforeAPI21();
        }
    }
    @SuppressWarnings("deprecation")
    private SoundPool buildBeforeAPI21() {
        SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        return soundPool;
    }

}
