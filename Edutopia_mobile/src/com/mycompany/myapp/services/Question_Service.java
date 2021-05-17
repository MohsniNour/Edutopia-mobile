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
import com.mycompany.myapp.entities.Question;

import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Amine
 */
public class Question_Service {
    public ArrayList<Question> Questions;
    public static Question_Service instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public Question_Service() {
        req = new ConnectionRequest();
    }

    public static Question_Service getInstance() {
        if (instance == null) {
            instance = new Question_Service();
        }
        return instance;
    }
     public ArrayList<Question> parseQuestion(String jsonText) {
        try {
            Questions = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReclamationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) ReclamationListJson.get("root");

            for (Map<String, Object> obj : list) {
                Question q = new Question();

                float id = Float.parseFloat(obj.get("id").toString());
                q.setId((int) id);
                q.setQuestion(obj.get("question").toString());
                q.setProposition1(obj.get("proposition1").toString());
                q.setProposition2(obj.get("proposition2").toString());
                q.setProposition3(obj.get("proposition3").toString());
                q.setProposition4(obj.get("proposition4").toString());
                q.setBonnereponse(obj.get("bonnereponse").toString());   
                Questions.add(q);
            }

        } catch (IOException ex) {
            System.out.println("Exception in parsing reclamations ");
        }

        return Questions;
    }

    public ArrayList<Question> findAll(int id_q) {
        String url = Statics.BASE_URL + "exam/List_Ques/"+id_q;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
    @Override
    public void actionPerformed(NetworkEvent evt) {
        Questions =parseQuestion(new String(req.getResponseData()));
        req.removeResponseListener(this);
        }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Questions;
    }
    
    public void delete_question(int id_q) {
     String url = Statics.BASE_URL + "question/Delete_Mobile/"+id_q;
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