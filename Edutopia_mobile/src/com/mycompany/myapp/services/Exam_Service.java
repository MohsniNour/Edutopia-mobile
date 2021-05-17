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
import com.mycompany.myapp.entities.exam;

import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Amine
 */
public class Exam_Service{
    public ArrayList<exam> exams;
    public static Exam_Service instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
     public Exam_Service() {
        req = new ConnectionRequest();
    }

    public static Exam_Service getInstance() {
        if (instance == null) {
            instance = new Exam_Service();
        }
        return instance;
    }
     public ArrayList<exam> parseexam(String jsonText) {
        try {
            exams = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReclamationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) ReclamationListJson.get("root");

            for (Map<String, Object> obj : list) {
                exam e = new exam();

                float id = Float.parseFloat(obj.get("idExam").toString());
                e.setIdExam((int) id);
                e.setType(obj.get("type").toString());
                e.setStartDate(obj.get("startDate").toString());
                e.setFinishDate(obj.get("finishDate").toString());
                e.setStartDate(obj.get("startDate").toString());
                e.setCreatedDate(obj.get("createdDate").toString());
                exams.add(e);
            }
        } catch (IOException ex) {
            System.out.println("Exception in parsing reclamations ");
        }
        return exams;
    }

    public ArrayList<exam> findAll(int id_s) {
        String url = Statics.BASE_URL + "exam/exam_bysubject_Mobile/"+id_s;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
    @Override
    public void actionPerformed(NetworkEvent evt) {
        exams =parseexam(new String(req.getResponseData()));
        req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return exams;
    }
    
    public void delete_Examn(int id_e) {
    String url = Statics.BASE_URL + "exam/Delete_M/"+id_e;
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