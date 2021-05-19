/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Complaint;
import com.mycompany.services.ServiceComplaint;

/**
 *
 * @author Sabrina
 */
public class AddComplaintForm extends BaseForm {
    Form current;
    public AddComplaintForm (Resources res) {
         super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout réclamation");
        getContentPane().setScrollVisible(false);
        
        
          
       TextField object = new TextField("", "Entrer un objet");
        object.setUIID("TextFieldBalck");
        addStringValue("Objet", object);

       TextField description = new TextField("", "Entrer une description");
        description.setUIID("TextFieldBalck");
        addStringValue("Description", description);

        TextField status = new TextField("", "Entrer un etat");
        status.setUIID("TextFieldBalck");
        addStringValue("Status", status);
        
        Button btnAdd = new Button ("Add");
        addStringValue("",btnAdd);
        
        //onClick button event
        
        
        btnAdd.addActionListener((e) -> {
             try {
                if (description.getText() == "" || object.getText() == "" || status.getText() == "") {
                    Dialog.show("veuillez verifier vos donnees", "", "annuler", "ok");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();
                      Complaint co = new Complaint(
                            String.valueOf(object.getText()),                         
                            String.valueOf(description.getText()),
                            String.valueOf(status.getText())
                            
                    );

                 
                    // calling adding fct in the services class
                    ServiceComplaint.getInstance().addComplaint(co);
                    iDialog.dispose(); //Remove LOADING after adding
     
                    refreshTheme();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("erreur!!! ");
            }

             
        });  
        
        
    }    
        
             
      private void addStringValue(String s, Component v ){
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }
  }
