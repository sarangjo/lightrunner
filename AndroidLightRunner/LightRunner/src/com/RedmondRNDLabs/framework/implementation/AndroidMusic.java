package com.RedmondRNDLabs.framework.implementation;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import com.RedmondRNDLabs.framework.Music;

public class AndroidMusic implements Music, OnCompletionListener,
		OnSeekCompleteListener, OnPreparedListener, OnVideoSizeChangedListener {
	
	public AndroidMusic(AssetFileDescriptor assetDescriptor) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnSeekCompleteListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnVideoSizeChangedListener(this);
            
        } catch (Exception e) {
            throw new RuntimeException("Couldn't load music");
        }
    }

	MediaPlayer mediaPlayer;
	boolean isPrepared = false;

	@Override
	public void play() {
		if (this.mediaPlayer.isPlaying())
			return;

		try {
			synchronized (this) {
				if (!isPrepared)
					mediaPlayer.prepare();
				mediaPlayer.start();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		if (this.mediaPlayer.isPlaying() == true) {
			this.mediaPlayer.stop();

			synchronized (this) {
				isPrepared = false;
			}
		}
	}

	@Override
	public void pause() {
		if (this.mediaPlayer.isPlaying())
			mediaPlayer.pause();
	}

	@Override
	public void setLooping(boolean looping) {
		mediaPlayer.setLooping(looping);

	}

	@Override
	public void setVolume(float volume) {
		mediaPlayer.setVolume(volume, volume);
	}

	@Override
	public boolean isPlaying() {
		return this.mediaPlayer.isPlaying();
	}

	@Override
	public boolean isStopped() {
		return !isPrepared;
	}

	@Override
	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	@Override
	public void dispose() {
		if (this.mediaPlayer.isPlaying()) {
			this.mediaPlayer.stop();
		}
		this.mediaPlayer.release();

	}

	@Override
	public void seekBegin() {
		 mediaPlayer.seekTo(0);
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		 synchronized (this) {
             isPrepared = true;
          }
	}

	@Override
	public void onSeekComplete(MediaPlayer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		synchronized (this) {
            isPrepared = false;
        }

	}

}
