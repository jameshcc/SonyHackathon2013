/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author James
 */
@Component
public class PlaylistManager {

   private final List<Track> tracks = new ArrayList<Track>();
   private Track playing = null;

   public List<Track> getTracks() {
      synchronized (tracks) {
         return new ArrayList<Track>(tracks);
      }
   }

   public void vote(String deezerId) {
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
            track.setId(deezerId);
            track.setVotes(1);
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

   public Playlist getPlaylist() {
      Playlist playlist = new Playlist();
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
