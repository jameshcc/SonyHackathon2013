/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.server;

import com.dwr.service.Track;
import com.dwr.service.Tracks;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author James
 */
//@Component
public class PlaylistManager {

   private static final List<Track> tracks = new ArrayList<Track>();
   private static Track playing = null;
   private final TrackDetailService trackDetailService;

//   @Autowired
   public PlaylistManager(TrackDetailService trackDetailService) {
      this.trackDetailService = trackDetailService;
   }

   public List<Track> getTracks() {
      synchronized (tracks) {
         return new ArrayList<Track>(tracks);
      }
   }

   public void vote(String deezerId) throws IOException {
      synchronized (tracks) {
         boolean found = false;
         for (Track track : tracks) {
            if (track.getId().equals(deezerId)) {
               track.setVotes(track.getVotes() + 1);
               found = true;
               break;
            }
         }
         if (!found) {
            Track track = new Track();
            TrackDetail trackDetail = trackDetailService.get(deezerId);
            track.setId(deezerId);
            track.setVotes(1);
            track.setTitle(trackDetail.getTitle());
            track.setArtist(trackDetail.getArtist());
            tracks.add(track);
         }
         Collections.sort(tracks, new Comparator<Track>() {

            @Override
            public int compare(Track o1, Track o2) {
               //Note: Reversed
               return o2.getVotes().compareTo(o1.getVotes());
            }
         });
      }
   }

   public Tracks getPlaylist() {
      Tracks playlist = new Tracks();
      playlist.setTracks(getTracks());
      playlist.setPlaying(playlist.getPlaying());
      return playlist;
   }

   public Track getPlaying() {
      return playing;
   }

   public Track playNext() {
      synchronized (tracks) {
         if (!tracks.isEmpty()) {
            playing = tracks.remove(0);
         }
      }
      return playing;
   }
}
