/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Question;
import com.mycompany.myapp.entities.exam;
import com.mycompany.myapp.services.Exam_Service;
import com.mycompany.myapp.services.Question_Service;
import java.util.Date;

/**
 *
 * @author Amine
 */
public class Calandar_Exam_Form extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    public Calandar_Exam_Form(Form previous,int id_S)
    {
           super("Exam",BoxLayout.y());
           
           
        this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();
           
           
            for (exam c : new Exam_Service().findAll(id_S)) {

            this.add(addItem_exam(c));

        }
            this.revalidate();
            });
        });

        this.getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : this.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                this.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : this.getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
            
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                            || line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                this.getContentPane().animateLayout(150);
            }
        }, 4);



        
    }
    
     public MultiButton  addItem_exam(exam c) {
        MultiButton m = new MultiButton();
        Form f2 = new Form("Calendar",BoxLayout.y());
           
        Calendar cld = new Calendar();
        Date date1;  
        String res =  c.getStartDate().substring(0, 10);
        System.out.println(res);
        try {   
                date1 = new SimpleDateFormat("yyy-MM-dd").parse(res);
                System.out.println(date1);
                cld.setDate(date1);
            } catch (Exception ex) {
                System.out.println(ex.getMessage()); 
            }
        f2.add(cld);    
                
            f2.add("Type : "+c.getType()).add("Finish : "+c.getFinishDate());
            f2.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
           new ListMatiereStudent(theme).show();
        });

        f2.show();    

        
       return m;
         }
}