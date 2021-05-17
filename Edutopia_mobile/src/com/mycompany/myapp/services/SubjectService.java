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
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.entities.Subject;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mrad
 */
public class SubjectService {
    
    private ConnectionRequest req;
    public static SubjectService instance = null;
    
    public static SubjectService getInstance(){
        if (instance==null)
            instance = new SubjectService();
        return instance;
    }
    
    public SubjectService(){
        req = new ConnectionRequest();
    }
    
    public void addSubject(Subject s){
        String url = "http://127.0.0.1:8000/subject/mobile/addSubject?idSubject="+s.getId_Subject()+"&idClass="+s.getId_class()+"&idTeacher="+s.getId_teacher()+"&createdBy="+s.getCreated_by();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public ArrayList<Subject> displaySubject() {
        ArrayList<Subject> result = new ArrayList<>();
        String url = "http://127.0.0.1:8000/subject/mobile/displaySubject";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            public void actionPerformed(NetworkEvent evnt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String,Object> mapSubjects = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapSubjects.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Subject s = new Subject();
                        double id = (double) obj.get("id");
                        String id_Subject = obj.get("idSubject").toString();
                        
                        
                        String id_Class = obj.get("idClass").toString();
                        String id_Teacher = obj.get("idTeacher").toString();
                        
                        s.setId((int) id);
                        s.setId_Subject(id_Subject);
                        s.setId_class(Integer.parseInt(String.valueOf(id_Class.charAt(4))));
                        s.setId_teacher(Integer.parseInt(String.valueOf(id_Teacher.charAt(4))));
                        result.add(s);
                     
                    }
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
    
    public Subject detailsSubject(int id) {
        String url = "http://127.0.0.1:8000/subject/mobile/detailSubject?id="+id;
        Subject s = new Subject();
        req.setUrl(url);
        String str = new String(req.getResponseData());
        req.addResponseListener((e) -> {
        JSONParser jsonp;
        jsonp = new JSONParser();
        try {
            Map<String,Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
            String id_Subject = obj.get("idSubject").toString();         
            String id_Class = obj.get("idClass").toString();
            String id_Teacher = obj.get("idTeacher").toString();
            s.setId(id);
            s.setId_Subject(id_Subject);
            s.setId_class(Integer.parseInt(String.valueOf(id_Class.charAt(4))));
            s.setId_teacher(Integer.parseInt(String.valueOf(id_Teacher.charAt(4))));
        }catch(IOException  ex){
            System.out.println("error related to sql "+ex.getMessage());
        }
    });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return s;
    } 
    
    public ArrayList<User> listTeachers() {
        ArrayList<User> result = new ArrayList<>();
        String url = "http://127.0.0.1:8000/subject/mobile/teachers";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            public void actionPerformed(NetworkEvent evnt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String,Object> mapTeachers = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapTeachers.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        User s = new User();
                        double id = (double) obj.get("id");
                        String name = obj.get("name").toString();
                        String lname = obj.get("lastName").toString();
                        
                        s.setId((int) id);
                        s.setName(name);
                        s.setLastName(lname);
                        result.add(s);
                       
                    }
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
    
    public ArrayList<Classe> listClass() {
        ArrayList<Classe> result = new ArrayList<>();
        String url = "http://127.0.0.1:8000/subject/mobile/classes";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            public void actionPerformed(NetworkEvent evnt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String,Object> mapClasses = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapClasses.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Classe s = new Classe();
                        double id =(double) obj.get("id");
                        String name = obj.get("name").toString();
         
                        s.setId((int) id);
                        s.setName(name);
                        result.add(s);
                    }
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return result;
    }
    
    public Classe detailsClasse(int id) {
        String url = "http://127.0.0.1:8000/subject/mobile/detailClasse?id="+id;
        Classe c = new Classe();
        req.setUrl(url);
        String str = new String(req.getResponseData());
        req.addResponseListener((e) -> {
        JSONParser jsonp;
        jsonp = new JSONParser();
        try {
            Map<String,Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
//            System.out.println(obj.toString());
//             for(Map<String, Object> objj : listOfMaps) {
                        String name = (String) obj.get("name");
//                        double idd = (double) obj.get("id");
//                        c.setId((int) idd);
                        c.setName(name);
//                    }
//            System.out.println("test f classe"+c.toString());
            
        }catch(IOException  ex){
            System.out.println("error related to sql "+ex.getMessage());
        }
    });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return c;
    } 
     
    public User detailsUser(int id) {
        String url = "http://127.0.0.1:8000/subject/mobile/detailUser?id="+id;
        User c = new User();
        req.setUrl(url);
        String str = new String(req.getResponseData());
        req.addResponseListener((e) -> {
        JSONParser jsonp;
        jsonp = new JSONParser();
        try {
            Map<String,Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
//            System.out.println(obj.toString());
            List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) obj.get("root");
            
//            for(Map<String, Object> objj : listOfMaps) {
                String name = (String) obj.get("name");
                String lname = (String) obj.get("lastName");
//                String role = obj.get("role").toString();
//                System.out.println("aaaaaaaaaaaa"+obj.toString());
//                double idd = (double) obj.get("id");
                
                c.setName(name);
                c.setLastName(lname);
//                c.setId((int) idd);
//                System.out.println("test f user "+c.toString());
//            }
        }catch(IOException  ex){
            System.out.println("error related to sql "+ex.getMessage());
        }
//
        });
        System.out.println("datevvv ==="+str);
        NetworkManager.getInstance().addToQueueAndWait(req);
        return c;
    } 
    
    public boolean deleteSubject(int id){
        String url = "http://127.0.0.1:8000/subject/mobile/deleteSubject?id="+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                  req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return true;
    }
}
