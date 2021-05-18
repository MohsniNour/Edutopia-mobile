/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Complaint;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Map;

/**
 *
 * @author Sabrina
 */
public class ServiceComplaint {
    
    
    //singleton
    public static ServiceComplaint instance = null;
    
    //initialisation connection request
    private ConnectionRequest req;
    
    
    public static ServiceComplaint getInstance()
    {
    if(instance == null)
    instance = new ServiceComplaint();
    return instance;
    }
    
    
    //add complaint 
    public ServiceComplaint()
    {
        req = new ConnectionRequest();
    }
       public void addComplaint(Complaint complaint)
       {
         String url = Statics.BASE_URL+"/add?object="+complaint.getObject()+"&description"+complaint.getDescription()+"&status"+complaint.getStatus();
       
         req.setUrl(url);
         req.addResponseListener((e) -> {
             
             String str = new String(req.getResponseData());
             System.out.println("data ==" +str);
         });
         NetworkManager.getInstance().addToQueueAndWait(req);
         
       }
       
       public ArrayList<Complaint>displayComplaints()
       {
           ArrayList<Complaint> result = new ArrayList<>();
           
           String url = Statics.BASE_URL+"/listeC";
           req.setUrl(url);
           
           req.addResponseListener(new ActionListener<NetworkEvent>() {
               @Override
               public void actionPerformed(NetworkEvent evt) {
               JSONParser jsonp;
               jsonp = new JSONParser();
                              try {
                  Map<String,Object>mapComplaints = jsonp.parseJSON(new CharArrayReader (new String(req.getResponseData()).toCharArray()));
                   List<Map<String,Object>> listOfMaps = (list<Map<String,Object>>) mapComplaints.get("roots");
       
                   for(Map<String,Object> obj : listMaps){
                       Complaints co = new Complaint ();
                       
                       float id =Float.parseFloat(obj.get("id").toString());
                       
                       String object = obj.get("object").toString();
                       
                       String description = obj.get("description").toString();
                       
                       String status = Float.parseFloat(obj.get("status").toString());
                       
                       co.setId((int)id);
                       co.setObject(object);
                       co.setDescription(description);
                       co.setStatus((int)status);
                       
                       //insert data into arraylist
                       result.add(co);
                   
                   }
                   }catch(Exception ex ) {
                           ex.printStackparse();
                           }
                   NetworkManager.getInstance().addToQueueAndWait(req);
                   return result;
                   


//detail complaint



