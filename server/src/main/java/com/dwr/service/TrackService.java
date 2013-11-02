package com.dwr.service;


import com.dwr.service.Track;
import com.dwr.service.Tracks;
import com.example.server.PlayService;

public class TrackService
{

    PlayService service = new PlayService();
    
    
    public Track getTrack()
    {
        
    	Track track = new Track();
    	track.setId("theid");
    	track.setTitle("thetitle");
        return track;
    }
    
    public Tracks search(String trackstr){
    	System.out.println("Track: " + trackstr);
    	Track track = new Track();
    	track.setId("theid");
    	track.setTitle("thetitle");
    	track.setArtist("Theartist");
    	Track track2 = new Track();
    	track2.setId("theid2");
    	track2.setTitle("thetitle2");
    	track2.setArtist("Theartist");
    	Tracks tracks = new Tracks();
    	tracks.getTracks().add(track);
    	tracks.getTracks().add(track2);
        return tracks;
    }
    
    public Tracks getPlaylist(){
        
        
    	Tracks tracks = new Tracks();
        tracks.setTracks(service.getPlaylist().getTracks());
        /*Track track = new Track();
    	track.setId("theid");
    	track.setTitle("thetitle");
    	Track track2 = new Track();
    	track.setArtist("Theartist");
    	track2.setId("theid2");
    	track2.setTitle("thetitle2");
    	Tracks tracks = new Tracks();
    	tracks.getTracks().add(track);
    	tracks.getTracks().add(track2);
    	track2.setArtist("Theartist");*/
        return tracks;
    }
    
    public void vote(String id){
    	System.out.println("id: " + id);
    }
    
    public void addTrack(String id){
    	System.out.println("id: " + id);
    }
}
