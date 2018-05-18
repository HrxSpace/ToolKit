package com.example.mydemo;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by hrx on 2017/1/20.
 * 音频文件播放管理器
 */

public class MediaPlayerUtil implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private static MediaPlayerUtil instance;
    private MediaPlayer mMediaPlayer = null;

    private MediaPlayerUtil() {

    }

    public static MediaPlayerUtil getInstance() {
        if (instance == null) {
            synchronized (MediaPlayerUtil.class) {
                if (instance == null) {
                    instance = new MediaPlayerUtil();
                }
            }
        }
        return instance;
    }

    /**
     *
     * @param filePath 音频文件路径
     */
    public void startPlay(String filePath, boolean isLooping) {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setLooping(isLooping);
        try {
            mMediaPlayer.setDataSource(filePath);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MediaPlayerUtil", "IOException: ");
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    /**
     * 开始
     */
    public void start() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    /**
     * 指定播放的位置（以毫秒为单位）
     *
     * @param mSec
     */
    public void seekTo(int mSec) {
        if (mMediaPlayer != null) {
            int maxTime = mMediaPlayer.getDuration();
            mSec = mSec > maxTime ? maxTime : mSec;
            mSec = mSec < 0 ? 0 : mSec;
            mMediaPlayer.seekTo(mSec);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

}
