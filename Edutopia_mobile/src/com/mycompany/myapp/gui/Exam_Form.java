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
import com.mycompany.myapp.MyApplication;
import com.mycompany.myapp.entities.Question;
import com.mycompany.myapp.entities.exam;
import com.mycompany.myapp.services.Exam_Service;
import com.mycompany.myapp.services.Question_Service;
import java.util.Date;



/**
 *
 * @author Amine
 */
public class Exam_Form  extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    public Exam_Form(Form previous,int id_S)
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

           this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
           new MyApplication().start();
        });
    }
    
     public MultiButton  addItem_exam(exam c) {

        MultiButton m = new MultiButton();
        String    url = "http://127.0.0.1:8000/exam.png";
        m.setTextLine1(c.getType());
        m.setTextLine2(c.getStartDate());
         
        m.setTextLine3(c.getFinishDate());
          
        m.setEmblem(theme.getImage("round.png"));
        Image imge;
        EncodedImage enc;
        enc = EncodedImage.createFromImage(theme.getImage("round.png"), false);
        imge = URLImage.createToStorage(enc, url, url);
        m.setIcon(imge);
       
        m.addActionListener(l-> {
        Form f2 = new Form("Detail",BoxLayout.y());
           
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
                
            f2.add("Type : "+c.getType()).add("Finish : "+c.getFinishDate()).add("------").add("Questions : ");
                 for (Question q : new Question_Service().findAll(c.getIdExam())) {
            f2.add(addItem_Question(q));

        }
            f2.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
           new MyApplication().start();
        });
            f2.getToolbar().addCommandToOverflowMenu("Supprimer", null, ev -> {
            Exam_Service ex = new Exam_Service();
            ex.delete_Examn(c.getIdExam());
                           
            Dialog.show("Delete", "Delete", "OK", null);
            new MyApplication().start();
        });
        f2.show();    
        }
        );
        
       return m;
         }
       public Container addItem_Question(Question q) {

        Container cn1 = new Container(new BorderLayout());
        Container cn2 = new Container(BoxLayout.y());
    
        Button btn_delete = new Button("Delete");
        btn_delete.addActionListener(lm->{
        Question_Service ex = new Question_Service();
        ex.delete_question(q.getId());
                           
        Dialog.show("Delete", "Delete", "OK", null);
        new MyApplication().start(); 
         });
        cn2.add("Question : "+q.getQuestion()).add("proposition 1 :"+q.getProposition1()).add("proposition 2 : "+q.getProposition2()).add("Proposition 3 : "+q.getProposition3()).add("Proposition 4 : "+q.getProposition4()).add("Bonne reponse : "+q.getBonnereponse()).add(btn_delete).add("----");
        cn1.add(BorderLayout.WEST, cn2);
        return cn1;
         }
}