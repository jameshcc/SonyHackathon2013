package com.example.server;

import com.dwr.service.Track;
import com.dwr.service.Tracks;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author James
 */
@Component
public class TrackDetailService {

   private final Map<String, TrackDetail> trackDetailCache = new HashMap<String, TrackDetail>();
   private final ObjectMapper objectMapper = new ObjectMapper();
   private final HttpClient httpClient = new DefaultHttpClient();

   public Tracks search(String query) throws IOException {
      Tracks tracks = new Tracks();
      String url = java.net.URLEncoder.encode(query, "ISO-8859-1");
      HttpGet httpget = new HttpGet("http://api.deezer.com/search?q=" + url);
      HttpResponse response = httpClient.execute(httpget);
      InputStream is = response.getEntity().getContent();
      try {
         JsonNode node = objectMapper.readTree(is);
         Iterator<JsonNode> iterator = node.get("data").getElements();
         while (iterator.hasNext()) {
            Track track = new Track();
            JsonNode trackNode = iterator.next();
            track.setId(trackNode.get("id").asText());
            track.setTitle(trackNode.get("title").asText());
            track.setArtist(trackNode.get("artist").get("name").asText());
            tracks.getTracks().add(track);
         }
         return tracks;
      } finally {
         is.close();
      }
   }

   public synchronized TrackDetail get(String id) throws IOException {
      TrackDetail trackDetail = trackDetailCache.get(id);
      if (trackDetail == null) {
         trackDetail = createTrackDetail(id);
         trackDetailCache.put(id, trackDetail);
      }
      return trackDetail;
   }

   private TrackDetail createTrackDetail(String id) throws IOException {
      Integer.parseInt(id);
      HttpGet getRequest = new HttpGet("http://api.deezer.com/track/" + id);
      HttpResponse response = httpClient.execute(getRequest);
      TrackDetail trackDetail = new TrackDetail();
      InputStream is = response.getEntity().getContent();
      try {
         JsonNode parentNode = objectMapper.readTree(is);
         trackDetail.setTitle(parentNode.get("title").asText());
         trackDetail.setArtist(parentNode.get("artist").get("name").asText());
         JsonNode albumNode = parentNode.get("album");
         if (albumNode != null) {
            trackDetail.setAlbum(albumNode.get("title").asText());
         }
         return trackDetail;
      } finally {
         is.close();
      }
   }
}
