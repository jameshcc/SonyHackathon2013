package com.example.server;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
public class TrackDetail {

   private String title;
   private String artist;

   @XmlElement
   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   @XmlElement
   public String getArtist() {
      return artist;
   }

   public void setArtist(String artist) {
      this.artist = artist;
   }
}
