/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
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
import com.mycompany.myapp.services.Activity_Service;
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

            this.add(addItem_Course(previous,c));
        }
              this.revalidate();
            });
        });    
        this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
        new MyApplication().start();
        });
    }
    
     public MultiButton  addItem_Course(Form previous,Course c) {

        MultiButton m = new MultiButton();
        String    url = "http://127.0.0.1:8000/book.jpg";
        m.setTextLine1(c.getName());
        m.setTextLine2(c.getDescription());
        m.setTextLine3(c.getNom_Subject());
        //m.setEmblem(theme.getImage("delete.png"));
        m.setEmblem(theme.getImage("activity.png"));
        Image imge;
        EncodedImage enc;
        enc = EncodedImage.createFromImage(theme.getImage("round.png"), false);
        imge = URLImage.createToStorage(enc, url, url);
        m.setIcon(imge);
        
        
//        m.addActionListener(aaaa->{
//        Course_Service sv = new Course_Service();
//        sv.Delete_ccourse(c.getId());
//        Dialog.show("Delete", "Delete", "OK", null);
//        new MyApplication().start();
//        });
        m.addActionListener(aa->{
         new Activity_List_Form(previous,c.getId()).show();
       });
        
        return m;
         }
}
