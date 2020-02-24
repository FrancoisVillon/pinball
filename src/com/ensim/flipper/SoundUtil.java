package com.ensim.flipper;

import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class SoundUtil
{
	/**
	 * Play a sound
	 * @param sound The played sound's name (if null : no sound played)
	 */
	public static synchronized void playSound(final String sound)
	{
		if(sound != null)
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
					    String soundPath = "./resources/sound/" + sound + ".wav";
					    InputStream in = new FileInputStream(soundPath);
					    AudioStream audioStream = new AudioStream(in);
					    AudioPlayer.player.start(audioStream);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
