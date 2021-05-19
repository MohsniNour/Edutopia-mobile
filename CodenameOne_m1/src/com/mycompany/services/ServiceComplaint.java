/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Complaint;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Sabrina
 */
public class ServiceComplaint {
    
    
    //singleton
    public static ServiceComplaint instance = null;
    
    public ArrayList<Complaint>complaints;
    public boolean resultOK;
    
    
    //initialisation connection request
    private ConnectionRequest req;
    
    
    public static ServiceComplaint getInstance()
    {
    if(instance == null)
    instance = new ServiceComplaint();
    return instance;
    }
    
    
    public ArrayList<Complaint> parseActivity(String jsonText) {
        
        try {
            complaints = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> complaintsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) complaintsListJson.get("root");

            for (Map<String, Object> obj : list) {
                Complaint com = new Complaint();

                float id = Float.parseFloat(obj.get("id").toString());
                com.setId((int) id);
                com.setObject(obj.get("object").toString());
                com.setDescription(obj.get("description").toString());
                com.setStatus(obj.get("status").toString());
                
            
                
                complaints.add(com);
            }

        } catch (IOException ex) {
            System.out.println("Exception in parsing activities ");
        }
        return complaints;
    }
    public ArrayList<Complaint> findAll() {
        String url = Statics.BASE_URL + "complaint/listeC/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                complaints =parseActivity(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return complaints;
    }
    
    
    //add complaint 
    public ServiceComplaint()
    {
        req = new ConnectionRequest();
    }
       public void addComplaint(Complaint complaint)
       {
         String url = Statics.BASE_URL+"complaint/add?object="+complaint.getObject()+"&description="+complaint.getDescription()+"&status="+complaint.getStatus();
       
         req.setUrl(url);
         req.addResponseListener((e) -> {
             
             String str = new String(req.getResponseData());
             System.out.println("data ==" +str);
         });
         NetworkManager.getInstance().addToQueueAndWait(req);
         
       }}
       
      