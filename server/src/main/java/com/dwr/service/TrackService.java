package com.dwr.service;

import com.example.server.PlaylistManager;
import com.example.server.TrackDetailService;
import java.io.IOException;

public class TrackService {

   private TrackDetailService trackDetailService = new TrackDetailService();
   private PlaylistManager playlistManager = new PlaylistManager(trackDetailService);

   public Track getTrack() {

      Track track = new Track();
      track.setId("theid");
      track.setTitle("thetitle");
      return track;
   }

   public Tracks search(String trackstr) throws IOException {
      return trackDetailService.search(trackstr);
   }

   public Tracks getPlaylist() {
      return playlistManager.getPlaylist();

//      Tracks tracks = new Tracks();
//      Track track = new Track();
//      track.setId("theid");
//      track.setTitle("thetitle");
//      Track track2 = new Track();
//      track.setArtist("Theartist");
//      track2.setId("theid2");
//      track2.setTitle("thetitle2");
//      tracks.getTracks().add(track);
//      tracks.getTracks().add(track2);
//      track2.setArtist("Theartist");
//      return tracks;
   }

   public void vote(String id)throws IOException{
      playlistManager.vote(id);
   }

   public void addTrack(String id)throws IOException{
      playlistManager.vote(id);
   }
}
