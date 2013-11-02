package com.dwr.service;

public class Track {

   public String id;
   public String title;
   public String artist;
   private Integer votes;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getArtist() {
      return artist;
   }

   public void setArtist(String artist) {
      this.artist = artist;
   }

   public Integer getVotes() {
      return votes;
   }

   public void setVotes(Integer votes) {
      this.votes = votes;
   }

}
