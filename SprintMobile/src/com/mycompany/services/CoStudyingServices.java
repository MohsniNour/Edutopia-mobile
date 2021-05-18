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
import com.mycompany.entities.Co_Studying;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rayen
 */
public class CoStudyingServices {

    public static CoStudyingServices instance = null;

    private ConnectionRequest req;
    public static boolean resultOK = true;

    public static CoStudyingServices getInstance() {
        if (instance == null) {
            instance = new CoStudyingServices();
        }
        return instance;
    }

    public CoStudyingServices() {
        req = new ConnectionRequest();
    }

    public void addCos(Co_Studying co) {

        int type = 0;
        if (null == co.getType()) {
            System.out.println("Choose a type");
        } else {
            switch (co.getType()) {
                case "Opportunité":
                    type = 1;
                    break;
                case "Résumé":
                    type = 2;
                    break;
                case "Freelance":
                    type = 3;
                    break;
                case "Offre Stage":
                    type = 4;
                    break;
                default:
                    System.out.println("Choose a type");
                    break;
            }
        }

        String url = Statics.BASE_URL + "/co/studying/addCos?description=" + co.getDescription() + "&type=" + type + "&niveau=" + co.getNiveau() + "&idStudent=" + co.getId_student() + "&rating=" + co.getRating() + "";
        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());///reponse json
            System.out.println("data=" + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public ArrayList<Co_Studying> displayCos() {
        ArrayList<Co_Studying> result = new ArrayList<>();
        String url = Statics.BASE_URL + "/co/studying/displayCos";
        req.setUrl(url);
        req.addResponseListener((NetworkEvent evt) -> {
            JSONParser jsonp;
            jsonp = new JSONParser();
            try {
                Map<String, Object> mapCoStudying = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapCoStudying.get("root");
                for (Map<String, Object> obj : listOfMaps) {
                    Co_Studying promo = new Co_Studying();
                    float id = Float.parseFloat(obj.get("id").toString());
                    String description = obj.get("description").toString();
                    String file = obj.get("file").toString();
                    String type = obj.get("type").toString();
                    String strType = type.substring(6, type.length() - 1);
                    String niveau = obj.get("niveau").toString();
                    float rating = Float.parseFloat(obj.get("rating").toString());
                    String idStudent = obj.get("idStudent").toString();
                    String strIdStudent = idStudent.substring(6, idStudent.length() - 1);

                    promo.setId((int) id);
                    promo.setDescription(description);
                    promo.setRating((int) rating);
                    promo.setFile(file);
                    promo.setNiveau(niveau);
                    promo.setType(strType);
                    promo.setId_student(strIdStudent);

                    //inset data in array list result
                    result.add(promo);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROOR");
            }
        });
        //  return null;
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

    public Co_Studying detailCos(int id, Co_Studying co) {
        String url = Statics.BASE_URL + "/co/studying/detailCos?" + id;
        req.setUrl(url);
        String str = new String(req.getResponseData());

        req.addResponseListener((ev) -> {

            JSONParser jsonp = new JSONParser();
            try {
                Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                co.setId(Integer.parseInt(obj.get("id").toString()));
                co.setDescription(obj.get("description").toString());
                co.setRating(Integer.parseInt(obj.get("rating").toString()));
                co.setType(obj.get("type").toString());
                co.setNiveau(obj.get("niveau").toString());
                co.setFile(obj.get("file").toString());

            } catch (IOException ex) {
                System.out.println("erreur related to sql\n" + ex.getMessage());
            }
            System.out.println("data =>" + str);

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return co;

    }

    ArrayList<String> listType = new ArrayList<>();

    private ArrayList<String> addCo(String jsonText) throws IOException {

        JSONParser j = new JSONParser();
        Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(
                jsonText.toCharArray()));

        List<String> list = (List<String>) tasksListJson.get("root");

        for (String obj : list) {
            listType.add(obj);
        }
        return listType;
    }

    public ArrayList<String> getAllType() {
        String url = Statics.BASE_URL + "/costudyingtype/JSON";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    listType = addCo(new String(req.getResponseData()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return listType;
    }

    public boolean deleteCos(int id) {
        String url = Statics.BASE_URL + "/co/studying/deleteCos?id=" + id;
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

    public boolean updateCos(Co_Studying co) {
        int type = 0;
        if (null == co.getType()) {
            System.out.println("Choose a type");
        } else {
            switch (co.getType()) {
                case "Opportunité":
                    type = 1;
                    break;
                case "Résumé":
                    type = 2;
                    break;
                case "Freelance":
                    type = 3;
                    break;
                case "Offre Stage":
                    type = 4;
                    break;
                default:
                    System.out.println("Choose a type");
                    break;
            }
        }
        String url = Statics.BASE_URL + "/co/updateCos?id=" + co.getId() + "description=" + co.getDescription() + "&type=" + type + "&niveau=" + co.getNiveau() + /*"&idStudent=" + co.getId_student() +*/ "&rating=" + co.getRating() + "";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;// code resp 200 http ?
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

}
