/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.server;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@XmlRootElement
public class Track {

   private String id;
   private Integer votes;

   @XmlElement
   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   @XmlElement
   public Integer getVotes() {
      return votes;
   }

   public void setVotes(Integer votes) {
      this.votes = votes;
   }
}
