    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Classe;
import com.mycompany.myapp.entities.Subject;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.SubjectService;
import java.util.ArrayList;

/**
 *
 * @author Mrad
 */
public class AjoutSubjectTeacherForm extends Form {
    Form current;
    public AjoutSubjectTeacherForm(Resources res) {
        super("Ajout Matiere",BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current =this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        getContentPane().setScrollVisible(false);
        
        tb.addSearchCommand(e -> {
            
        });
        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();
        
        addTab(swipe,s1, res.getImage("img.jpg"),"","",res);
        
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
        RadioButton mesListes = RadioButton.createToggle("Les Matières", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        Button b = new Button("Ajouter Matière");
        b.setUIID("SelectBar");
        b.addActionListener(e -> {
            new AjoutSubjectTeacherForm(res).show();
        });
        Button d = new Button("Stats");
        d.setUIID("SelectBar");
        d.addActionListener(e -> {
            Form f = createPieChartForm();
            f.show();
        });
         Button k = new Button("Recherche");
        k.setUIID("SelectBar");
        k.addActionListener(e -> {
            new RechercheSubject(res).show();
        });    
        mesListes.addActionListener((e) -> {
            new ListeMatieresForm(res).show();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, mesListes, b, d,k),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

       
        
        TextField idSubject = new TextField("","entrer nom matiere");
        idSubject.setUIID("TextFiealdBlack");
        addStringValue("Matière",idSubject);
        
        
        
        
        ArrayList<Classe> al = SubjectService.getInstance().listClass();
        ArrayList<String> al2 = new ArrayList<>();
        int i=1;
        for (Classe u : al) {
            
            al2.add(u.getName());
            i++;
            
        }
        ArrayList<User> alu = SubjectService.getInstance().listTeachers();
        ArrayList<String> alu2 = new ArrayList<>();
        for (User u : alu) {
            alu2.add(u.getName()+" "+u.getLastName());
        }
        
        String[] characters={};
        AutoCompleteTextField idClass = new AutoCompleteTextField(al2.toArray(characters));
        idClass.addActionListener(e -> ToastBar.showMessage("You picked " + idClass.getText(), FontImage.MATERIAL_INFO));
        idClass.setUIID("TextFiealdBlack");
        addStringValue("Classe",idClass);
        
//        TextField idTeacher = new TextField("","Choisiser l'enseigant");
//        idTeacher.setUIID("TextFiealdBlack");
//        addStringValue("Enseigant",idTeacher);
        String[] character={};
        AutoCompleteTextField idTeacher = new AutoCompleteTextField(alu2.toArray(character));
        idTeacher.addActionListener(e -> ToastBar.showMessage("You picked " + idTeacher.getText(), FontImage.MATERIAL_INFO));
        idTeacher.setUIID("TextFiealdBlack");
        addStringValue("Teacher",idTeacher);
        
        Button btnAjout = new Button("Ajouter");
        addStringValue("", btnAjout);
        
        btnAjout.addActionListener((e) -> {
            try {
                if(idSubject.getText()=="" || idClass.getText()=="" || idTeacher.getText()=="") {
                    Dialog.show("Veuillez vérifier les données","", "Annuler", "ok");
                }
                else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    Subject subject = new Subject();
                    subject.setId_Subject(idSubject.getText().toString());
//                    subject.setId_class(Integer.parseInt(idClass.getText().toString()));
//                    subject.setId_teacher(Integer.parseInt(idTeacher.getText().toString()));
                    int idC = 0;
                    for ( Classe c : al) {
                        if(idClass.getText()==c.getName()){
                            idC = c.getId();
                        }
                    }
                    subject.setId_class(idC);
                    int idU = 0;
                    for (User c : alu) {
                        String test=c.getName()+" "+c.getLastName();
                        if(idTeacher.getText().equals(test))
                            idU = c.getId();
                    }
                    subject.setId_teacher(idU);
                    SubjectService.getInstance().addSubject(subject);
                    
                    iDialog.dispose();
                    new ListeMatieresForm(res).show();
                    
                    refreshTheme();
                }
            }
            catch ( Exception ex) {
                ex.printStackTrace();
            }
        });
        
        
    }
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }
    
    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
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
                                BoxLayout.encloseY(new SpanLabel(text,"LargeWhiteText"),
                                            FlowLayout.encloseIn(),
                                            spacer 
                                )
                        )
                );
        swipe.addTab("",res.getImage("img.jpg"), page1);
    }
    public void bindButtonSelection(Button btn, Label l ) {
        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }
    
    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT , btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();
    }
     
        private DefaultRenderer buildCategoryRenderer(int[] colors)     {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setMargins(new int[]{20, 30, 15, 0});
    for (int color : colors) {
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(color);
        renderer.addSeriesRenderer(r);
    }
    return renderer;
}
    protected CategorySeries buildCategoryDataset(String title, double[] values) {
    CategorySeries series = new CategorySeries(title);
    int k = 0;
    for (double value : values) {
        series.add("Project " + ++k, value);
    }

    return series;
}
    
    public Form createPieChartForm() {
    // Generate the values
    
    double[] values = new double[]{1,4,3};

    // Set up the renderer
    int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA, ColorUtil.YELLOW, ColorUtil.CYAN};
    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitleTextSize(20);
    renderer.setDisplayValues(true);
    renderer.setShowLabels(true);
    renderer.setChartTitle("Nobre Denseignant selon Matiere");
    renderer.setChartTitleTextSize(50);
    renderer.setLabelsTextSize(50);
   
    
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setGradientEnabled(true);
    r.setGradientStart(0, ColorUtil.BLUE);
    r.setGradientStop(0, ColorUtil.GREEN);
    r.setHighlighted(true);
    r.setShowLegendItem(true);
    r.setHighlighted(true);
    

    // Create the chart ... pass the values and renderer to the chart object.
    PieChart chart = new PieChart(buildCategoryDataset("Matieres Stat", values), renderer);

    // Wrap the chart in a Component so we can add it to a form
    ChartComponent c = new ChartComponent(chart);
    Button b = new Button("Back");
    b.addActionListener(e -> {
        current.show();
    });

    // Create a form and show it.
    Form f = new Form("Matieres Stat", new BorderLayout());
    f.add(BorderLayout.CENTER, BoxLayout.encloseY(BoxLayout.encloseY(b),BoxLayout.encloseY(c)));
    return f;

}
}




