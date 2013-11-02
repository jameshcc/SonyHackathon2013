package com.dwr.service;

import java.util.ArrayList;
import java.util.List;

public class Tracks {

   private List<Track> tracks = new ArrayList<Track>();
   private Track playing;

   public List<Track> getTracks() {
      return tracks;
   }

   public void setTracks(List<Track> tracks) {
      this.tracks = tracks;
   }

   public Track getPlaying() {
      return playing;
   }

   public void setPlaying(Track playing) {
      this.playing = playing;
   }

}
