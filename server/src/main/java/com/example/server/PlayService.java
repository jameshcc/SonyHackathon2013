package com.example.server;

import com.dwr.service.Track;
import com.dwr.service.Tracks;
import java.io.IOException;
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
@Path("/oldservice")
@Produces("application/json")
public class PlayService {

//   @Autowired
   private PlaylistManager playlistManager;

//   @Autowired
   private TrackDetailService trackDetailService;

//   @Path("playlist")
   @GET
   public Tracks getPlaylist() {
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
   public Tracks vote(@QueryParam("deezerId") String deezerId) throws IOException{
      playlistManager.vote(deezerId);
      return playlistManager.getPlaylist();
   }

   @GET
   @Path("search")
   public Tracks search(@QueryParam("query") String query) throws IOException {
      return trackDetailService.search(query);
   }

   @GET
   @Path("trackdetail")
   public TrackDetail getTrackDetail(@QueryParam("id") String id) throws IOException {
      return trackDetailService.get(id);
   }
}
