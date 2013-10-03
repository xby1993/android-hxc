package com.xby.hxc;

import java.io.IOException;

import net.youmi.android.diy.DiyManager;
import net.youmi.android.diy.banner.DiyAdSize;
import net.youmi.android.diy.banner.DiyBanner;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class PaiActivity extends Activity {
	private int width,height;
	private SurfaceView sView;
	private SurfaceHolder holder;
	private Camera camera;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		DiyManager.initAdObjects(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_pai);
		LinearLayout layout=(LinearLayout)findViewById(R.id.LinearLayout2);
		layout.addView(new DiyBanner(this, DiyAdSize.SIZE_MATCH_SCREENx32));
		WindowManager wm=getWindowManager();
		Display dp=wm.getDefaultDisplay();
		DisplayMetrics dm= new DisplayMetrics();
		dp.getMetrics(dm);
		width=dm.widthPixels;
		height=dm.heightPixels;
		sView=(SurfaceView)findViewById(R.id.surfaceView1);
		holder=sView.getHolder();
		holder.addCallback(new Callback(){
		
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				initCamera();
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				if(camera!=null){
					camera.stopPreview();
					camera.release();
					camera=null;
//					Intent intent=new Intent();
//					intent.setAction("com.xby.hxc.intent.action.MAIN");
//					startActivity(intent);
				}
			}
			
			});
	}
	void initCamera(){
		int cameraId=0;
		if(Camera.getNumberOfCameras()==2){
			cameraId=1;
		}
		camera=Camera.open(cameraId);
		camera.setDisplayOrientation(90);
		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		camera.startPreview();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pai, menu);
		return true;
	}

}
