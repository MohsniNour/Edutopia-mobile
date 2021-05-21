package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Complaint;
import com.mycompany.gui.BaseForm;
import com.mycompany.services.ServiceComplaint;
import java.util.ArrayList;

/**
 *
 * @author IBTIHEL
 */
public class ListComplaintForm extends BaseForm {
    
       Form current;
       Resources theme = UIManager.initFirstTheme("/theme");
      public ListComplaintForm(Resources res)
    { 
       super("Liste des réclamations",BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);
        this.getToolbar().addSearchCommand(e ->{  
        });
        this.getToolbar().addCommandToOverflowMenu("back", null, ev -> {
        new NewsfeedForm(theme).show();
        });
        super.addSideMenu(res);
        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();
        addTab(swipe,s1,res.getImage("el.PNG"),"","",res);
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Les reclamations", barGroup);
        mesListes.setUIID("SelectBar");
//        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
//        liste.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

     

        mesListes.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
//            updateArrowPosition(mesListes, arrow);
        });
//        bindButtonSelection(mesListes, arrow);
//        // special case for rotation
//        addOrientationListener(e -> {
//            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
//        });

        ArrayList<Complaint> alAct = ServiceComplaint.getInstance().findAll();
        for(Complaint c : alAct) {
            String urlImage="el.PNG";
            Image placeHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
            addButton(urlim,c,res);
            ScaleImageLabel image = new ScaleImageLabel(urlim);
            Container containerImg = new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        }
}

  public void addTab(Tabs swipe,Label spacer, Image image, String string, String text , Resources res){
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (image.getHeight()<size){
            image = image.scaledHeight(size);
        }
        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2 ) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overLay = new Label("","ImageOverLay");
        Container page1 = 
                LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(new SpanLabel(text,"LargeWhiteText"),FlowLayout.encloseIn(),
                                            spacer 
                                )
                        )
                );
        swipe.addTab("",res.getImage("enq.jpg"), page1);
    }  
     
//     private void updateArrowPosition(Button btn, Label l) {
//        l.getUnselectedStyle().setMargin(LEFT , btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
//        l.getParent().repaint();
//    }
//     public void bindButtonSelection(Button btn, Label l ) {
//        btn.addActionListener(e -> {
//            if (btn.isSelected()) {
//                updateArrowPosition(btn, l);
//            }
//        });
//    }

    private void addButton(Image img, Complaint re, Resources res) {
       int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width,height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        Label objectTxt = new Label("Objet : "+re.getObject(),"NewTopLine2");
        Label descriptionTxt = new Label("Description : "+re.getDescription(),"NewTopLine2");
        Label statusActivityTxt = new Label("Etat : "+re.getStatus(),"NewTopLine2");
        Label lSupp = new Label(" Supprimer ");
        lSupp.setUIID("NewsTopLine");
        Style supprimerStyle = new Style(lSupp.getUnselectedStyle());
        supprimerStyle.setFgColor(0xf21f1f);
        FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
        lSupp.setIcon(supprimerImage);
       
        lSupp.addPointerPressedListener(l -> {         
//        ServieComplaint.getInstance().Delete_activity(act.getId());
//        Form hi =new Form();
//                new Activity_List_Form(hi,act.getId_Course(),res).show();   
        });
        Label s = new Label("-----------------------------------------------------------------------------------------------------------------------------");
//        lActions.addPointerPressedListener(l -> { 
//            Form hi =new Form();
//            new WorkDone_List_Form(hi,act.getId(),theme).show();   
//        });
        cnt.add(BorderLayout.WEST, BoxLayout.encloseY(BoxLayout.encloseX(objectTxt),BoxLayout.encloseX(descriptionTxt),BoxLayout.encloseX(statusActivityTxt),BoxLayout.encloseX(s)));
        add(cnt);
    }
}