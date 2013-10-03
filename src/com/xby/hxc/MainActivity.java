package com.xby.hxc;

import java.util.ArrayList;
import java.util.List;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.diy.DiyManager;
import net.youmi.android.smart.SmartBannerManager;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.android.common.logging.Log;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.richmedia.MediaListActivity;
import com.google.ads.AdRequest;

public class MainActivity extends Activity {
	private com.google.ads.AdView adViewg;
	private static final int mes = 541;
	private MediaPlayer mplay;
	Button setTags = null;
	Button delTags = null;
	Vibrator vb;
	class XMessage {

		public XMessage() {
			vb = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
			//
		}

		public void send(int mesa) {
			if (mesa == mes) {
				Toast.makeText(MainActivity.this, "让我打一下你的小胖脸",
						Toast.LENGTH_LONG).show();
				vb.vibrate(3000);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Resources resource = this.getResources();
		String pkgName = this.getPackageName();
		AdManager.getInstance(this).init("179215b1ba065979","9e503a153fb66b6e", false);
		SmartBannerManager.init(this);
		SmartBannerManager.show(this);
		 //实例化广告条
		net.youmi.android.banner.AdView adView = new net.youmi.android.banner.AdView(this, AdSize.FIT_SCREEN);
	    //获取要嵌入广告条的布局
	    LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
	    //将广告条加入到布局中
	    adLayout.addView(adView); 
		 // 以查询资源的方式查询 AdView 并加载请求。
	    com.google.ads.AdView adViewg = (com.google.ads.AdView)this.findViewById(R.id.ad);
	    adViewg.loadAd(new AdRequest());
	    setTags = (Button) findViewById(resource.getIdentifier("btn_setTags", "id", pkgName));
		delTags = (Button) findViewById(resource.getIdentifier("btn_delTags", "id", pkgName));
		setTags.setOnClickListener(setTagsButtonListner());
		delTags.setOnClickListener(deleteTagsButtonListner());
		final Button b1 = (Button) findViewById(R.id.button1);
		mplay = MediaPlayer.create(MainActivity.this, R.raw.pika);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				b1.setText(R.string.see);
				new XMessage().send(mes);
				if (mplay.isPlaying()) {
					mplay.pause();
				}
				mplay.start();

			}
		});

		final Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mplay.isPlaying())
					mplay.stop();
				if (mplay != null)
					mplay.release();
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("弱弱地询问一句")
						.setMessage("快点承认你就是小胖子")
						.setPositiveButton("我承认",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										System.exit(0);

									}
								}).show();
			}
		});
		final Button b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("com.xby.hxc.intent.action.pai");
				startActivity(intent);
			}
		});
		final Button b4 = (Button) findViewById(R.id.button4);
		b4.setOnClickListener(richMediaButtonListner());
		final Button b5 = (Button) findViewById(R.id.button5);
		b5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 以apikey的方式登录，一般放在主Activity的onCreate中
				PushManager.startWork(getApplicationContext(),
						PushConstants.LOGIN_TYPE_API_KEY,
						Utils.API_KEY);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, 1, 0, "关于");
		menu.add(0,2,0,"推荐");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem mi) {
		if (mi.getItemId() == 1) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("关于")
					.setMessage("小白杨作品：hxc-version:1.0")
					.setPositiveButton("ok",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
								}
							}).show();
		}else if(mi.getItemId()==2){
//			Intent intent = new Intent();
//			intent.setAction("com.xby.hxc.ady");
//			startActivity(intent);
			DiyManager.showRecommendWall(this);
		}
		return true;

	}

	private OnClickListener richMediaButtonListner() {
		return new View.OnClickListener() {

			public void onClick(View v) {
				Intent sendIntent = new Intent();
				sendIntent.setClass(MainActivity.this, MediaListActivity.class);
				sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
				MainActivity.this.startActivity(sendIntent);
			}
		};
	}
	@Override
	public void onStart() {
		super.onStart();
		
		PushManager.activityStarted(this);
	
		onNewIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// 如果要统计Push引起的用户使用应用情况，请实现本方法，且加上这一个语句
		setIntent(intent);
		mplay = MediaPlayer.create(MainActivity.this, R.raw.pika);
		boolean isMsm=intent.getBooleanExtra(Utils.MSM, false);
		if(isMsm){
			if (mplay!=null&&mplay.isPlaying()) {
				mplay.pause();
			}
			mplay.start();
			if(vb==null){
				vb = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
				vb.vibrate(3000);
			}
		}
		
		handleIntent(intent);
	}

	@Override
	public void onStop() {
		super.onStop();
		PushManager.activityStoped(this);
	}
	@Override
	  public void onDestroy() {
	    // Destroy the AdView.
	    if (adViewg != null) {
	      adViewg.destroy();
	    }

	    super.onDestroy();
	  }
	
	/**
	 * 处理Intent
	 * 
	 * @param intent
	 *            intent
	 */
	/**
	 * @param intent
	 */
	/**
	 * @param intent
	 */
	private void handleIntent(Intent intent) {
		String action = intent.getAction();

		if (Utils.ACTION_RESPONSE.equals(action)) {

			String method = intent.getStringExtra(Utils.RESPONSE_METHOD);

			if (PushConstants.METHOD_BIND.equals(method)) {
				String toastStr = "";
				int errorCode = intent.getIntExtra(Utils.RESPONSE_ERRCODE, 0);
				if (errorCode == 0) {
					String content = intent
							.getStringExtra(Utils.RESPONSE_CONTENT);
					String appid = "";
					String channelid = "";
					String userid = "";

					try {
						JSONObject jsonContent = new JSONObject(content);
						JSONObject params = jsonContent
								.getJSONObject("response_params");
						appid = params.getString("appid");
						channelid = params.getString("channel_id");
						userid = params.getString("user_id");
					} catch (JSONException e) {
						Log.e(Utils.TAG, "Parse bind json infos error: " + e);
					}

					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(this);
					Editor editor = sp.edit();
					editor.putString("appid", appid);
					editor.putString("channel_id", channelid);
					editor.putString("user_id", userid);
					editor.commit();


					toastStr = "绑定成功，登陆成功";
				} else {
					toastStr = "绑定错误，无法登陆, 错误代码: " + errorCode;
					if (errorCode == 30607) {
						Log.d("Bind Fail", "update channel token-----!");
					}
				}

				Toast.makeText(this, toastStr, Toast.LENGTH_LONG).show();
			}
		
		} else if (Utils.ACTION_MESSAGE.equals(action)) {
			String message = intent.getStringExtra(Utils.EXTRA_MESSAGE);
			String summary = "新消息来啦:\n\t";
			Log.e(Utils.TAG, summary + message);
			JSONObject contentJson = null;
			String contentStr = message;
			try {
				contentJson = new JSONObject(message);
				contentStr = contentJson.toString(4);
			} catch (JSONException e) {
				Log.d(Utils.TAG, "Parse message json exception.");
			}
			summary += contentStr;
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(summary);
			builder.setCancelable(true);
			Dialog dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			
		} else {
			Log.i(Utils.TAG, "Activity normally start!");
		}
	}
	
	private OnClickListener deleteTagsButtonListner() {
		return new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				LinearLayout layout = new LinearLayout(MainActivity.this);
				layout.setOrientation(LinearLayout.VERTICAL);

				final EditText textviewGid = new EditText(MainActivity.this);
				textviewGid.setHint("请输入多个标签，以英文逗号隔开");
				layout.addView(textviewGid);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setView(layout);
				builder.setPositiveButton("删除标签",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								List<String> tags = getTagsList(textviewGid
										.getText().toString());

								PushManager.delTags(getApplicationContext(),
										tags);

							}
						});
				builder.show();

			}
		};
	}

	private OnClickListener setTagsButtonListner() {
		return new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 设置标签,以英文逗号隔开
				LinearLayout layout = new LinearLayout(MainActivity.this);
				layout.setOrientation(LinearLayout.VERTICAL);

				final EditText textviewGid = new EditText(MainActivity.this);
				textviewGid.setHint("请输入多个标签，以英文逗号隔开");
				layout.addView(textviewGid);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setView(layout);
				builder.setPositiveButton("设置标签",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								List<String> tags = getTagsList(textviewGid
										.getText().toString());
								PushManager.setTags(getApplicationContext(),
										tags);
							}

						});
				builder.show();

			}
		};
	}
	private List<String> getTagsList(String originalText) {

		List<String> tags = new ArrayList<String>();
		int indexOfComma = originalText.indexOf(',');
		String tag;
		while (indexOfComma != -1) {
			tag = originalText.substring(0, indexOfComma);
			tags.add(tag);

			originalText = originalText.substring(indexOfComma + 1);
			indexOfComma = originalText.indexOf(',');
		}

		tags.add(originalText);
		return tags;
	}
}
