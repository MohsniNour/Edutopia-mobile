/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Activity;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author ADMIN
 */
public class Activity_Service {
    public ArrayList<Activity> activities;
    public static Activity_Service instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public Activity_Service() {
        this.req = new ConnectionRequest();
    }
   
    public static Activity_Service getInstance() {
        if (instance == null) {
            instance = new Activity_Service();
        }
        return instance;
    }
    
    public ArrayList<Activity> parseActivity(String jsonText) {
        try {
            activities = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> activitiesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) activitiesListJson.get("root");

            for (Map<String, Object> obj : list) {
                Activity act = new Activity();

                float id = Float.parseFloat(obj.get("id").toString());
                act.setId((int) id);
                act.setName(obj.get("name").toString());
                Map<String, Object> map1 = ((Map<String, Object>) obj.get("idCourse"));
                for (Entry<String, Object> entry : map1.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue(); 
                if(key.equals("idCourse"))
                    {
                    act.setName(value.toString());
                  
                    }
                }
                activities.add(act);
            }

        } catch (IOException ex) {
            System.out.println("Exception in parsing activities ");
        }
        return activities;
    }
    public ArrayList<Activity> findAll(int id_C) {
        String url = Statics.BASE_URL + "activity/activity_byCourseStudent_Mobile/"+id_C;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                activities =parseActivity(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return activities;
    }
    
     public void Delete_activity(int id_act) {
        String url = Statics.BASE_URL + "activity/Delete_Mob/"+id_act;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
              
            req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
     
    }
}
