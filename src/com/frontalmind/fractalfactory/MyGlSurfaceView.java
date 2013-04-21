package com.frontalmind.fractalfactory;

import java.io.File;

import org.bsheehan.android.fractalien.core.FractalienCubeGlRenderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

public class MyGlSurfaceView extends GLSurfaceView{

	private FractalienCubeGlRenderer fractalienGlRenderer;

	public MyGlSurfaceView(Context context) {
		super(context);
		this.setEGLContextClientVersion(2);
		this.fractalienGlRenderer = new FractalienCubeGlRenderer(context);
		this.setRenderer(this.fractalienGlRenderer);
		this.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP){
					//MyGlSurfaceView.this.
				}
				
				return true;
			}
		});
	}

	public File getImage() {
		return this.fractalienGlRenderer.getImage();
		
	}

}
