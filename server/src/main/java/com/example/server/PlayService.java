package com.example.server;

import java.io.IOException;
import java.util.List;
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

   @Autowired
   private TrackDetailService trackDetailService;

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

   @GET
   @Path("search")
   public List<String> search(@QueryParam("query") String query) throws IOException {
      return trackDetailService.search(query);
   }

   @GET
   @Path("trackdetail")
   public TrackDetail getTrackDetail(@QueryParam("id") String id) throws IOException {
      return trackDetailService.get(id);
   }
}
