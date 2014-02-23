package com.example.shakethephone;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	public Button increaseBtn;
	private Button decreaseBtn;
	private AudioManager audioManager;
	private SeekBar sb;
	int Volume = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		increaseBtn = (Button) findViewById(R.id.btincrease);
		decreaseBtn = (Button) findViewById(R.id.btdecrease);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void startService(View view) {
		startService(new Intent(getBaseContext(), MyService.class));

		increaseBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// increase the volume and show the ui
				audioManager.adjustVolume(AudioManager.ADJUST_RAISE,
						AudioManager.FLAG_SHOW_UI);
			}
		});

		decreaseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// decrease the volume and show the ui
				audioManager.adjustVolume(AudioManager.ADJUST_LOWER,
						AudioManager.FLAG_SHOW_UI);
			}
		});
		// get the instance of AudioManager class
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	}

	// Method to stop the service
	public void stopService(View view) {
		stopService(new Intent(getBaseContext(), MyService.class));
		super.onDestroy();
	}
}
