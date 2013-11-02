/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dplay.tracks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author pontusjohansen
 */
public class Trackdata {
    
    public List<Track> getPlayList(String trackName){
        List<Track> tracks = new ArrayList<Track>();
        
        return tracks;
    }
    
    public List<Track> search(String trackName){
        List<Track> tracks = new ArrayList<Track>();
        
        return tracks;
    }

    public void vote(String id){
        
    }
    
    public void addToPlaylist(String id){
        
    }
    
    public static void main(String... args) throws ClientProtocolException, IOException{


		CloseableHttpClient httpclient = HttpClients.createDefault();
		//URLCodec.encodeUrl("http://api.deezer.com/search?q=eye of the tiger");
		//URLCodec.encodeUrl(arg0, arg1)
		String url = java.net.URLEncoder.encode("eye of the tiger", "ISO-8859-1");
		HttpGet httpget = new HttpGet("http://api.deezer.com/search?q="+url);
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			
			// Get from deezer
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(instream));
					StringBuffer buff = new StringBuffer();
					String inputLine;
					while ((inputLine = in.readLine()) != null){
						System.out.println(inputLine);
						buff.append(inputLine);
					}

					// Parse json response
					//Track track = mapper.readValue(buff.toString(), Track.class);
					ObjectMapper mapper = new ObjectMapper();
					JsonNode actualObj = mapper.readTree(buff.toString());
					System.out.println(actualObj.toString());
					JsonNode data = actualObj.get("data");

					if(data != null && data.isArray()){


						Iterator<JsonNode> iterator = data.getElements();

						while(iterator.hasNext()){
							JsonNode jnode = iterator.next();
							System.out.println(jnode.toString());
							JsonNode idNode = jnode.get("id");
							System.out.println(idNode.toString());
						}

					}
					//System.out.println("Id: " + track.getId());
					// do something useful
				} finally {
					instream.close();
				}
			}
		} finally {
			response.close();
		}


	}
    
    
}
