package com.dplay;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.deezer.sdk.DeezerConnect;
import com.deezer.sdk.DeezerConnectImpl;
import com.deezer.sdk.DeezerError;
import com.deezer.sdk.DialogError;
import com.deezer.sdk.DialogListener;
import com.deezer.sdk.OAuthException;
import com.deezer.sdk.player.Player;
import com.deezer.sdk.player.impl.DefaultPlayerFactory;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;
import com.dplay.beans.Track;
import com.dplay.util.Http;

public class Play extends Activity implements OnClickListener{
	
	/** Deezer appId. */
	public final static String APP_ID = "126945";
		
	/** Permissions requested on Deezer accounts. */
	private final static String[] PERMISSIONS = new String[] {};
	
	 private Player player;
	 private DeezerConnect deezerConnect;
	 
	 Button playButton;
	 
	 Button stopButton;
	 
	 String IMEI;
	 
	 String deezerAccessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		playButton = (Button)findViewById(R.id.play);
		playButton.setOnClickListener(this);
		
		stopButton = (Button)findViewById(R.id.stop);
		stopButton.setOnClickListener(this);
		
		TelephonyManager tm = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);

		IMEI = tm.getDeviceId();
		
		System.out.println("semmelway:: IMEI = " + IMEI);
		
		
		
		
		
		deezerConnect = new DeezerConnectImpl(APP_ID);
		
		deezerConnect.authorize(this, PERMISSIONS, new MyDialogHandler());
		
		if (deezerConnect.isSessionValid()) {
			deezerAccessToken = deezerConnect.getAccessToken();
		}
		
		
		
		
		
		try {
		    player = new DefaultPlayerFactory(getApplication(), deezerConnect, new WifiAndMobileNetworkStateChecker()).createPlayer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void onClick(View v) {
		if (v.equals(playButton)) {
			System.out.println("semmelway:: onClick(): play");
			
			// 11-01 22:22:19.607: I/System.out(12572): semmelway:: id=70211268
			// 11-01 22:22:19.607: I/System.out(12572): semmelway:: preview=http://cdn-preview-4.deezer.com/stream/49698147904af24d6119259668157b01-0.mp3

			long id = 70211268;
			Long idLong = new Long(id);
			
			new DownloadTrackTask(deezerAccessToken, IMEI).execute(idLong);
			
		
			
			System.out.println("semmelway:: accessToken = " + deezerAccessToken);
			
			
//			player.init(70211268, "http://cdn-preview-4.deezer.com/stream/49698147904af24d6119259668157b01-0.mp3");
//			player.play();
			
			
		} else if (v.equals(stopButton)){
			System.out.println("semmelway:: onClick(): stop");
			player.stop();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}
	
	/** Handle DeezerConnect callbacks. */
	private class MyDialogHandler implements DialogListener {
		@Override
			public void onComplete(final Bundle values) {

			deezerAccessToken = deezerConnect.getAccessToken();
			System.out.println("semmelway::: onComplete, accessToken = " + deezerAccessToken);
			
			
	 
		}
		 
		@Override
			public void onDeezerError(final DeezerError deezerError) {
			System.out.println("semmelway:: deezerError");
	 
		}
		 
		@Override
			public void onError(final DialogError dialogError) {
			System.out.println("semmelway:: dialogError");
		}
		 
		@Override
			public void onCancel() {
			System.out.println("semmelway:: Canceled");
	 
		}
		 
		@Override
			public void onOAuthException(OAuthException oAuthException) {
			System.out.println("semmelway:: OAuthException");
		}
	}
	
	private class DownloadTrackTask extends AsyncTask<Long, Integer, Track> {
		
		String mToken;
		
		String mImei;
		
		public DownloadTrackTask(String token, String imei) {
			mToken = token;
			mImei = imei;
			
		}
		
		
		protected Track doInBackground(Long... id) {
			Track track = null;
			try {
				String request = "http://api.deezer.com/track/" + id[0].toString();
				System.out.println("semmelway:: request = " + request);
				
				Map<String,String> params = new HashMap<String, String>();
				params.put("access_token", mToken);
				params.put("imei", mImei);
				
			track = new Track(Http.get(request, params));
			} catch(Exception ioe) {
				ioe.printStackTrace();
				return null;
			}
			
			return track;
		}
		
		protected void onPostExecute(Track track) {
			
			
			if (track.getStream() == null) {
				player.init(track.getId(), track.getPreview());
			} else {
				System.out.println("init, stream=" + track.getStream());
				player.init(track.getId(), track.getStream());
			}
			
			player.play();
		}
		
		
	}



}
