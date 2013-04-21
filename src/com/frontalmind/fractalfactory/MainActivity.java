package com.frontalmind.fractalfactory;

import java.io.File;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    private MyGlSurfaceView mGLSurfaceView;
	private WakeLock wakeLock;

    /*
	 * Detect if device is OpenGL ES 2.0-compatible
	 * @return
	 */
	private boolean detectOpenGLES20() 
	{
		final ActivityManager am =
			(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo info = am.getDeviceConfigurationInfo();
		return (info.reqGlEsVersion >= 0x20000);
	}
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		this.wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "Fractal Tag");
		this.wakeLock.acquire();
		this.mGLSurfaceView = new MyGlSurfaceView(this);
		if (detectOpenGLES20()) {
			// Tell the surface view we want to create an OpenGL ES 2.0-compatible
			// context, and set an OpenGL ES 2.0-compatible renderer.
			//this.mGLSurfaceView.setRenderer(new FractalienCubeGlRenderer(this));
		} else {
			Log.e("bob", "OpenGL ES 2.0 not supported on device.  Exiting...");
			finish();

		}
		setContentView(this.mGLSurfaceView);    
	}

	private void saveFractal()
	{
		File imageFile = this.mGLSurfaceView.getImage();
		if (imageFile == null){
			Log.e("bob", "Image File not created.");
			return;
		}
			
		
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(imageFile.getAbsolutePath());
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);

	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item) 
	{
	       switch (item.getItemId()){
           case R.id.menu_save:
           saveFractal();
           break;
     }
     return true;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
	@Override
	protected void onDestroy(){
		this.wakeLock.release();
		super.onDestroy();
	}
}
