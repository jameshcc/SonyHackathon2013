package com.example.server;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@XmlRootElement
public class Playlist {

   private List<Track> tracks;
   private Track playing;

   @XmlElement
   public List<Track> getTracks() {
      return tracks;
   }

   public void setTracks(List<Track> tracks) {
      this.tracks = tracks;
   }

   @XmlElement
   public Track getPlaying() {
      return playing;
   }

   public void setPlaying(Track playing) {
      this.playing = playing;
   }
}
