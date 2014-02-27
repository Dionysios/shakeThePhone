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
	private static final double MIN_FORCE_X = 8;
	private static final double MIN_FORCE_Z = 14;
	private static final double MAX_FORCE_Z = 8;

	int counter = 0;
	int counter_Z = 0;

	private static final int MAX_TOTAL_DURATION_OF_SHAKE = 1000;
	private static final int MIN_TOTAL_DURATION_OF_SHAKE = 300;

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
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		sensorManager.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		
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
	}

	// decrease volume by one
	public void setRingerLower() {
		AudioManager audioManager = (AudioManager) getBaseContext()
				.getSystemService(Context.AUDIO_SERVICE);
		audioManager.adjustVolume(AudioManager.ADJUST_LOWER,
				AudioManager.FLAG_SHOW_UI);
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

		float lastX = 0;
		float lasty = 0;
		float lastz = 0;
		// float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);

		double totalMovement_X = x - lastX;
		Log.e("MyService", "Get  initial X" + totalMovement_X);
		double totalMovement_Z = (z + y) - (lasty + lastz);
		Log.e("MyService", "Get  initial z" + totalMovement_Z);
		
		// detect movement
		if (totalMovement_X > MIN_FORCE_X) {
			counter++;
			Log.e("MyService", "Get movements X" + counter);
			x = 0;
		} 
		lastX = x;
		lasty = y;
		lastz = z;
		//else {
			//x = 0;
		//}
		
		if (counter == 2 && totalMovement_X > MIN_FORCE_X) {
			setRingerOneMore();
			Log.e("MyService", "Increase one");
			counter = 0;
		}
		
		if (totalMovement_Z > MIN_FORCE_Z ) {
			counter_Z++;
			Log.e("MyService", "Get movements Z" + counter_Z);
			z = 0;
		} 
		if (counter_Z == 2 && totalMovement_Z > MIN_FORCE_Z) {
			setRingerLower();
			Log.e("MyService", "Decrease one");
			counter_Z = 0;
			totalMovement_Z = 0;
		}

	}
}