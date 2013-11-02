package com.dplay;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
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
import com.deezer.sdk.player.event.OnPlayerProgressListener;
import com.deezer.sdk.player.impl.DefaultPlayerFactory;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;
import com.dplay.beans.Track;
import com.dplay.util.Http;

public class Play extends Activity implements OnClickListener, OnPlayerProgressListener{
	
	/** Deezer appId. */
	public final static String APP_ID = "126945";
		
	/** Permissions requested on Deezer accounts. */
	private final static String[] PERMISSIONS = new String[] {"basic_access"};
	
	 private Player player;
	 private DeezerConnect deezerConnect;
	 
	 Button playButton;
	 
	 Button stopButton;
	 
	 String IMEI;
	 
	 String deezerAccessToken;
	 
	 long currentSongLength = 30000;
	 
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
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		
		try {
		    player = new DefaultPlayerFactory(getApplication(), deezerConnect, new WifiAndMobileNetworkStateChecker()).createPlayer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		player.stop();
		player.release();
	}



	@Override
	public void onClick(View v) {
		if (v.equals(playButton)) {
			System.out.println("semmelway:: onClick(): play");
			
			// 11-01 22:22:19.607: I/System.out(12572): semmelway:: id=70211268
			// 11-01 22:22:19.607: I/System.out(12572): semmelway:: preview=http://cdn-preview-4.deezer.com/stream/49698147904af24d6119259668157b01-0.mp3

//			long id = 70211268;
//			Long idLong = new Long(id);
			
			new DownloadNextTrackTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			
		
			
			System.out.println("semmelway:: accessToken = " + deezerAccessToken);
			
			
//			player.init(70211268, "http://cdn-preview-4.deezer.com/stream/49698147904af24d6119259668157b01-0.mp3");
//			player.play();
			
			
		} else if (v.equals(stopButton)){
			System.out.println("semmelway:: onClick(): stop");
			new StopPlayer(false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
	
	
	class FadeUpMediaVolume extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... params) {
			// First store current volume
			AudioManager am = (AudioManager)Play.this.getSystemService(Context.AUDIO_SERVICE);
			int currentVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			
			int volume = 0;
			// Set volume to 0
			am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
			
			while(volume < currentVol) {
				volume++;
			    am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
			    try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
	}
	
	class StopPlayer extends AsyncTask<Void, Void, Void> {
		
		boolean playNextAfter = false;
		
		public StopPlayer(boolean playNextAfter) {
			System.out.println("play next after = " + playNextAfter);
			this.playNextAfter = playNextAfter;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// First store current volume
			AudioManager am = (AudioManager)Play.this.getSystemService(Context.AUDIO_SERVICE);
			int origVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			System.out.println("origVol = " + origVol);
			
			int volume = origVol;
			
			while(volume > 0) {
				volume--;
			    am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
			    try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// Make sure 0 i set
			am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
			 try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			System.out.println("stopping player");
			
			player.stop();
			
			 try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			System.out.println("setting back volume to " + origVol);
			
			// Set back original volume
			 am.setStreamVolume(AudioManager.STREAM_MUSIC, origVol, 0);
			
			return null;
		}
		
		
		
		@Override
		protected void onPostExecute(Void result) {
			if (playNextAfter) {
				System.out.println("starting next");
				new DownloadNextTrackTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
		}

		
	}
	
	class DownloadNextTrackTask extends AsyncTask<Void, Integer, Long> {
		
		protected Long doInBackground(Void... v) {
			String id;
			try {
				String response;
				try {
					response = Http.get("http://54.229.66.8/server/api/playnext", null);
				} catch(UnknownHostException u) {
					try {
						response = Http.get("http://54.229.66.8/server/api/playnext", null);
					} catch(UnknownHostException uu) {
						
						try {
							response = Http.get("http://54.229.66.8/server/api/playnext", null);
						} catch(UnknownHostException uuu) {
							
							response = Http.get("http://54.229.66.8/server/api/playnext", null);
							
							
						}
						
						
					}
					
					
					
				}
				
				
				
				
				System.out.println("semmelway:: "+ response);
				
			JSONObject responseObject = new JSONObject(response);
			
			id = responseObject.getString("id");
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			return Long.parseLong(id);
			
		}
		
		protected void onPostExecute(Long id) {
			new DownloadTrackTask(deezerAccessToken, IMEI).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id);
		}
		
		
		
	}
	
	class DownloadTrackTask extends AsyncTask<Long, Integer, Track> {
		
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
				//System.out.println("semmelway:: request = " + request);
				System.out.println("semmelway:: imei = " + mImei);
				
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
			
			
//			if (track.getStream() == null) {
				player.init(track.getId(), track.getPreview());
				currentSongLength = 25000;
//			} else {
//				System.out.println("init, stream=" + track.getStream());
//				player.init(track.getId(), track.getStream());
//			}
				
		
			
		    player.setPlayerProgressInterval(1000);
		    player.addOnPlayerProgressListener(Play.this);
		    new FadeUpMediaVolume().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			player.play();
		}
		
		
	}

	@Override
	public void onPlayerProgress(long pos) {
		if(pos>=currentSongLength) {
			System.out.println("Starting stop player task");
			new StopPlayer(true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			player.removeOnPlayerProgressListener(this);
			
			
		}	
	}

}
