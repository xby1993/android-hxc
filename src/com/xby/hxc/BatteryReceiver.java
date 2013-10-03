package com.xby.hxc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
//		Bundle bundle = intent.getExtras();
		// 获取当前电量
//		int cur = bundle.getInt("level");
		// 获取总电量
//		int total = bundle.getInt("scale");
		// 如果当前电量小于35%
//		if (cur * 1.0 / total < 0.15) {
			Toast.makeText(context, "小胖子，电量过低出门记得充好电哦", Toast.LENGTH_LONG)
					.show();
			MediaPlayer mplay = MediaPlayer.create(context, R.raw.pika);
			mplay.start();
//		}

	}
}
