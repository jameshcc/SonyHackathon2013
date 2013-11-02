package com.example.server;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author James
 */
public class TrackDetail {

   private String title;
   private String artist;
   private String album;

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

   @XmlElement
   public String getAlbum() {
      return album;
   }

   public void setAlbum(String album) {
      this.album = album;
   }
}
