package com.example.shakethephone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.FloatMath;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service implements SensorEventListener {

	// AudioManager audioManager;
	// MediaPlayer mediaPlayer;
	private SensorManager sensorManager = null;
	private Sensor sensor = null;
	// private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
/*	private static final float SHAKE_THRESHOLD_GRAVITY = 0.1F;
	private static final int DOUBLE_SHAKE_MS = 100;
	private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;*/

	private long lastUpdate;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
		Log.e("MyService", "Create service");
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Log.e("MyService", "Create serviceManger");
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Log.e("MyService", "Create Accelerometer");
		sensorManager.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		Log.e("MyService", "Register Accelerometer");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// Toast.makeText(this, "Play music", Toast.LENGTH_SHORT).show();
		Log.e("MyService", "We play music");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
		 // stop the sensor and service
        sensorManager.unregisterListener(this);
        stopSelf();
		Log.e("MyService", "Destroy service");
	}

	// increase volume by one
	public void setRingerOneMore() {
		AudioManager audioManager = (AudioManager) getBaseContext()
				.getSystemService(Context.AUDIO_SERVICE);
		audioManager.adjustVolume(AudioManager.ADJUST_RAISE,
				AudioManager.FLAG_SHOW_UI);
		// audioManager.setRingerMode(AudioManager.ADJUST_LOWER);
	}

	// decrease volume by one
	public void setRingerLower() {
		AudioManager audioManager = (AudioManager) getBaseContext()
				.getSystemService(Context.AUDIO_SERVICE);
		audioManager.adjustVolume(AudioManager.ADJUST_LOWER,
				AudioManager.FLAG_SHOW_UI);
		// audioManager.setRingerMode(AudioManager.ADJUST_RAISE);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		float[] values = event.values;
		// Movement
		float x = values[0];
		float y = values[1];
		float z = values[2];

		float accelaration = 9;
		float accelaration2 = 5;
		float accelationRealz = z ;
		Log.e("MyService", "Get initial y" + accelationRealz);
		float accelationRealx = x ;
		Log.e("MyService", "Get  initial x" + accelationRealx);
		//
		long actualTime = System.currentTimeMillis();
		
	//	Log.e("MyService", "Get time" + actualTime);
		if (accelationRealz > accelaration) {
		//	Log.e("MyService", "Get y" + accelationRealy);
		//	if (lastUpdate - actualTime > 200) {
		//		setRingerLower();
		//		Log.e("MyService", "Reduce one");
		//	} else {
				setRingerOneMore();
				Log.e("MyService", "Increase one");
			}
	//	}
		if (accelationRealx > accelaration2) {
		//	Log.e("MyService", "Get x" + accelationRealx);
		//	if (lastUpdate - actualTime > 200) {
				setRingerLower();
				Log.e("MyService", "Reduce one");
		//	} else {
		//		setRingerOneMore();
		//		Log.e("MyService", "Increase one");
		//	}
		}
		 accelationRealx = 0;
		 accelationRealz  = 0;
		 Log.e("MyService", "Get second z" + y);
	//	lastUpdate = System.currentTimeMillis() + 100;
	//	Log.e("MyService", "Get second time" + lastUpdate);
	}
}