/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
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
import com.mycompany.myapp.entities.Activity;
import com.mycompany.myapp.services.Activity_Service;

/**
 *
 * @author ADMIN
 */
public class Activity_List_Form extends Form{
    
    Resources theme = UIManager.initFirstTheme("/theme");

    public Activity_List_Form(Form hi,int id_C) {
        
        super("activities",BoxLayout.y());
        this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...
            Display.getInstance().callSerially(() -> {
            this.removeAll();
            for (Activity act : new Activity_Service().findAll(id_C)) {

            this.add(addItem_Activity(act));
        }
              this.revalidate();
            });
        });
        this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
        new Course_Form(hi,4).show();
        });
    }
    
    public MultiButton  addItem_Activity(Activity act) {

        MultiButton m = new MultiButton();
        String    url = "http://127.0.0.1:8000/activity.png";
        m.setTextLine1(act.getName());
        m.setTextLine2(act.getStatus());
        m.setEmblem(theme.getImage("del.png"));
        Image imge;
        EncodedImage enc;
        enc = EncodedImage.createFromImage(theme.getImage("round.png"), false);
        imge = URLImage.createToStorage(enc, url, url);
        m.setIcon(imge);
        
        m.addActionListener(aaaa->{
        Activity_Service sv = new Activity_Service();
        sv.Delete_activity(act.getId());
        Dialog.show("Delete", "Delete", "OK", null);
        new MyApplication().start();
        });
        
        return m;
         }

    
    
    
    
}
