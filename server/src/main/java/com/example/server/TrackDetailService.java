/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.server;

import java.io.IOException;
import java.util.HashMap;
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
      JsonNode parentNode = objectMapper.readTree(response.getEntity().getContent());
      trackDetail.setTitle(parentNode.get("title").asText());
      trackDetail.setArtist(parentNode.get("artist").get("name").asText());
      return trackDetail;
   }
}
