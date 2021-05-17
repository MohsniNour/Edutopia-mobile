/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import com.mycompany.myapp.entities.Course;
import com.mycompany.myapp.services.Course_Service;

/**
 *
 * @author Amine
 */
public class Course_Form extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    public Course_Form(Form previous,int id_S)
    {
        super("Courses",BoxLayout.y());
        this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...
            Display.getInstance().callSerially(() -> {
            this.removeAll();
            for (Course c : new Course_Service().findAll(id_S)) {

            this.add(addItem_Course(c));
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
        this.getContentPane().animateLayout(150);}}, 4);     
        this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {

 Form hi = new Form("Projet", BoxLayout.y());
 hi.getToolbar().addCommandToOverflowMenu("back", null, evv -> {
           new ListeMatieresForm(theme).show();
        });
       Button btn_course = new Button("Course");
       hi.add(btn_course);
       btn_course.addActionListener(aaqq->{
           
           // 1 adhika id_Subjeect ttbdl hasb subject
       
       new Course_Form(hi,id_S).show();
       });
         Button btn_exam = new Button("Exam");
       hi.add(btn_exam);
       btn_exam.addActionListener(aaqq->{
           
           // 1 adhika id_Subjeect ttbdl hasb subject
       
       new Exam_Form(hi,id_S).show();
       });
        hi.show();

        });
    }
    
     public MultiButton  addItem_Course(Course c) {

        MultiButton m = new MultiButton();
        String    url = "http://127.0.0.1:8000/book.jpg";
        m.setTextLine1(c.getName());
        m.setTextLine2(c.getDescription());
        m.setTextLine3(c.getNom_Subject());
        m.setEmblem(theme.getImage("delete.png"));
        Image imge;
        EncodedImage enc;
        enc = EncodedImage.createFromImage(theme.getImage("hd.png"), false);
        imge = URLImage.createToStorage(enc, url, url);
        m.setIcon(imge);
        
        m.addActionListener(aaaa->{
        Course_Service sv = new Course_Service();
        sv.Delete_ccourse(c.getId());
        Dialog.show("Delete", "Delete", "OK", null);
        new ListeMatieresForm(theme).show();
        });
        
        return m;
         }
}
