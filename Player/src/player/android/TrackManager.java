package player.android;

import java.util.HashMap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

class StreamStatus {
	private float leftVol;
	private float rightVol;
	private boolean playing; 
	
	public StreamStatus(){
		leftVol = 0.0f;
		rightVol = 0.0f;
		playing = false;
	}
	public float getLeftVol() {
		return leftVol;
	}
	public float getRightVol() {
		return rightVol;
	}
	public void setLeftVol(float vol) {
		leftVol = vol;
	}
	public void setRightVol(float vol) {
		rightVol = vol;
	}
	public boolean isPlaying() {
		return playing;
	}
	public void setPlayStatus(boolean status) {
		playing = status;
	}
}

public class TrackManager {
    //int intSize;
    //Set<AudioTrack> tracks;
    SoundPool sounds;
    HashMap<Integer, StreamStatus> streamMap; 
    private static final String TAG = "Track Manager";
    
    public TrackManager() {
    	//intSize = android.media.AudioTrack.getMinBufferSize(8000,
        //        AudioFormat.CHANNEL_CONFIGURATION_MONO,
        //        AudioFormat.ENCODING_PCM_16BIT);
    	//tracks = new HashSet<AudioTrack>();
    	
    	streamMap = new HashMap<Integer, StreamStatus>(); 
    	sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    public int addSound(String path) {
    	int streamID = sounds.load(path, 1);
    	streamMap.put(streamID, new StreamStatus());
    	Log.v(TAG, "Added sound. Id = " + streamID);
    	return streamID;
    }
    
    public boolean removeSound(int streamID) {
    	
    	StreamStatus strStatus = streamMap.get(streamID);
    	if (strStatus==null)
    		return false;
    	
    	sounds.stop(streamID);
    	streamMap.remove(streamID);
    	
    	return sounds.unload(streamID);
    }
    
    public void releaseTrackManager() {
    	sounds.release();
    }

    public boolean playSound(int id, int channel) {
    	float leftVolume = 0.0f;
    	float rightVolume = 0.0f;
    	StreamStatus strStatus = streamMap.get(id);
    	if (strStatus==null)
    		return false;
    	
    	// check if already playing
    	if (strStatus.isPlaying())
    		return true;
    	
    	Log.v(TAG, "Playing = " + id+". Channel = "+channel);
    	
    	if (channel<0){
    		leftVolume = 1.0f;
    		strStatus.setLeftVol(1.0f);
    	}
    	else if (channel>0){
    		rightVolume = 1.0f;
    		strStatus.setRightVol(1.0f);
    	}
    	
    	strStatus.setPlayStatus(true);
    	sounds.play(id, leftVolume, rightVolume, 1, -1, 1.0f);
    	return true;
    }
    
    public void pauseAllSound(HashMap<Integer, Integer> soundMap) {
    	for (int ID: soundMap.keySet()) {
    		int streamID = soundMap.get(ID);
    		pauseSound(streamID);
    	}
    }
    
    public boolean pauseSound(int streamID) {
    	StreamStatus strStatus = streamMap.get(streamID);
    	if (strStatus==null)
    		return false;
    	
    	// check if already playing
    	if (!strStatus.isPlaying())
    		return true;
    	
    	Log.v(TAG, "Pausing sound. Id = " + streamID);
    	
    	strStatus.setPlayStatus(false);
    	sounds.pause(streamID);
    	return true;
    }
    
    public void stopAllSounds(HashMap<Integer, Integer> soundMap) {
    	for (int ID: soundMap.keySet()) {
    		int streamID = soundMap.get(ID);
    		stopSound(streamID);
    	}
    }

    public boolean stopSound(int streamID) {
    	StreamStatus strStatus = streamMap.get(streamID);
    	if (strStatus==null)
    		return false;
    	
    	Log.v(TAG, "Stopping sound. Id = " + streamID);
    	
    	strStatus.setPlayStatus(false);
    	sounds.stop(streamID);
    	
    	return true;
    }
    
    public void toggleAllSounds (HashMap<Integer, Integer> soundMap) {
    	for (int ID: soundMap.keySet()) {
    		int streamID = soundMap.get(ID);
    		toggleSound(streamID);
    	}
    }
    
    public boolean setVolume (int streamID, float left, float right) {
    	StreamStatus strStatus = streamMap.get(streamID);
    	if (strStatus==null)
    		return false;
    	
    	// check for invalid strStatusumes
    	if (left<0.0f || left>1.0f || right<0.0f || right>1.0f)
    		return false;
    	
    	// check if already playing
    	if (!strStatus.isPlaying())
    		return true;
    	
    	Log.v(TAG, "Setting volume for Sound Id = " + streamID);
    	
    	strStatus.setLeftVol(left);
    	strStatus.setRightVol(right);
    	sounds.pause(streamID);
    	strStatus.setPlayStatus(false);
    	sounds.setVolume(streamID, strStatus.getLeftVol(), strStatus.getRightVol());
    	strStatus.setPlayStatus(true);
    	sounds.resume(streamID);
    	
    	return true;
    }
    
    public boolean toggleSound (int streamID) {
    	StreamStatus strStatus = streamMap.get(streamID);
    	if (strStatus==null)
    		return false;
    	
    	// check if already playing
    	if (!strStatus.isPlaying())
    		return true;
    	
    	Log.v(TAG, "Toggling volume for Sound Id = " + streamID);
    	
    	float tmp = strStatus.getLeftVol();
    	strStatus.setLeftVol(strStatus.getRightVol());
    	strStatus.setRightVol(tmp);
    	sounds.pause(streamID);
    	strStatus.setPlayStatus(false);
    	sounds.setVolume(streamID, strStatus.getLeftVol(), strStatus.getRightVol());
    	strStatus.setPlayStatus(true);
    	sounds.resume(streamID);
    	
    	return true;
    }
}