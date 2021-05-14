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
import com.mycompany.myapp.entities.Course;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Amine
 */
public class Course_Service {
    public ArrayList<Course> Courses;
    public static Course_Service instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public Course_Service() {
        req = new ConnectionRequest();
    }

    public static Course_Service getInstance() {
        if (instance == null) {
            instance = new Course_Service();
        }
        return instance;
    }
     public ArrayList<Course> parseCourse(String jsonText) {
        try {
            Courses = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReclamationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) ReclamationListJson.get("root");

            for (Map<String, Object> obj : list) {
                Course c = new Course();

                float id = Float.parseFloat(obj.get("id").toString());
                c.setId((int) id);
                c.setName(obj.get("name").toString());
                c.setDescription(obj.get("description").toString());
                Map<String, Object> map1 = ((Map<String, Object>) obj.get("idSubject"));
                for (Entry<String, Object> entry : map1.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue(); 
                if(key.equals("idSubject"))
                    {
                    c.setNom_Subject(value.toString());
                  
                    }
                }
                Courses.add(c);
            }

        } catch (IOException ex) {
            System.out.println("Exception in parsing reclamations ");
        }
        return Courses;
    }

    public ArrayList<Course> findAll(int id_s) {
        String url = Statics.BASE_URL + "course/course_bysubjectStudent_Mobile/"+id_s;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Courses =parseCourse(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Courses;
    }
    
     public void Delete_ccourse(int id_c) {
        String url = Statics.BASE_URL + "course/Delete_Mo/"+id_c;
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

