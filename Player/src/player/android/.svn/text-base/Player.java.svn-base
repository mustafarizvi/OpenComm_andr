package player.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Player extends Activity {
    /** Called when the activity is first created. */
	private static final String TAG = "Player";
	private File oFile, oFile1;
	private byte[] byteData = null;	
    private byte[] byteData1 = null;
    TrackManager t;
    HashMap<Integer, Integer> soundMap;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        t = new TrackManager();
        
        int soundid1 = t.addSound("/sdcard/sample.wav");
        int soundid2 = t.addSound("/sdcard/sample2.wav");
        soundMap = new HashMap<Integer, Integer>();
        soundMap.put(1, soundid1);
        soundMap.put(2, soundid2);
        
        Button play = null;
        play = (Button)this.findViewById(R.id.play);
        play.setOnClickListener(new Button.OnClickListener() { 
			public void onClick(View arg0) {
		    	EditText txt = (EditText) findViewById(R.id.txt_play);
		    	Log.v(TAG, "playfield=" + txt.getText());
		    	if (txt.getText().length()==0)
		    		startPlaying();
		    	else {
		    		int streamID = Integer.parseInt(txt.getText().toString());
		    		Log.v(TAG, "playfield1=" + streamID);
		    		playOneStream(streamID);
		    	}
		    		
			}
        });
        
        Button pause = null;
        pause = (Button)this.findViewById(R.id.pause);
        pause.setOnClickListener(new Button.OnClickListener() { 
			public void onClick(View arg0) {
		    	EditText txt = (EditText) findViewById(R.id.txt_pause);
		    	Log.v(TAG, "playfield=" + txt.getText());
		    	if (txt.getText().length()==0)
		    		pauseAllStreams();
		    	else {
		    		int streamID = Integer.parseInt(txt.getText().toString());
		    		Log.v(TAG, "playfield1=" + streamID);
		    		pauseOneStream(streamID);
		    	}
			}
        });
        
        Button clear = null;
        clear = (Button)this.findViewById(R.id.stop);
        clear.setOnClickListener(new Button.OnClickListener() { 
			public void onClick(View arg0) {
				EditText txt = (EditText) findViewById(R.id.txt_toggle);
				stopPlayingAll();
			}
        });
        
        Button toggle = null;
        toggle = (Button)this.findViewById(R.id.toggle);
        toggle.setOnClickListener(new Button.OnClickListener() { 
			public void onClick(View arg0) {
		    	EditText txt = (EditText) findViewById(R.id.txt_toggle);
				if (txt.getText().length()==0)
		    		toggleAllStreams();
		    	else {
		    		int streamID = Integer.parseInt(txt.getText().toString());
		    		Log.v(TAG, "playfield1=" + streamID);
		    		toggleOneStream(streamID);
		    	}
			}
        });
    }
	
	private void startPlaying() {
		t.playSound(soundMap.get(1), -1);
		t.playSound(soundMap.get(2), 1);
	}
	
	private void playOneStream(int streamID) {
		t.playSound(streamID, -1);
	}
	
	private void stopPlayingAll() {
		t.stopAllSounds(soundMap);
	}
	
	private void stopPlayingSound(int streamID) {
		t.stopSound(streamID);
	}
	
	private void toggleAllStreams () {
		t.toggleAllSounds(soundMap);
	}
	
	private void toggleOneStream (int streamID) {
		t.toggleSound(streamID);
	}
	
	private void pauseAllStreams () {
		t.pauseAllSound(soundMap);
	}
	
	private void pauseOneStream (int streamID) {
		t.pauseSound(streamID);
	}
}