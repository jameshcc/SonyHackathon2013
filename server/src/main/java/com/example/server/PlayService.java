package com.example.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author James
 */
@Component
@Path("/")
@Produces("application/json")
public class PlayService {

   @Autowired
   private PlaylistManager playlistManager;

//   @Path("playlist")
   @GET
   public Playlist getPlaylist() {
      return playlistManager.getPlaylist();
   }

   @GET
   @Path("playing")
   public Track getNowPlaying() {
      Track track = playlistManager.getPlaying();
      if (track == null) {
         track = new Track();
      }
      return track;
   }

   @GET
   @Path("playnext")
   public Track playNext() {
      return playlistManager.playNext();
   }

   @GET
   @Path("vote")
   public Playlist vote(@QueryParam("deezerId") String deezerId) {
      playlistManager.vote(deezerId);
      return playlistManager.getPlaylist();
   }
}
