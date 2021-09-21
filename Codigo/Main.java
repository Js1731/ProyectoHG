package Codigo;


import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class Main {
    public static void main(String[] args) {
        JFrame Vent = new JFrame();
        Vent.setSize(520, 400);
        Vent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel PnPrinc = new JPanel();


        //CREAR CONTENIDO DE LA VENTANA
        JPanel PnTitulo = new JPanel();
        PnTitulo.setPreferredSize(new Dimension(PnTitulo.getPreferredSize().width, 80));

        //CREAR PANEL PARA LA SECCION DE SELECCION DE FORMAS, COLOR Y RELLENO
        JPanel PnOpciones = new JPanel();
        PnOpciones.setLayout(new BoxLayout(PnOpciones, BoxLayout.Y_AXIS));
        
        //CREAR SECCIONES

        Seccion SecFormas = new Seccion("Formas");
        SecFormas.Cont.setLayout(new FlowLayout(FlowLayout.LEFT));
        SecFormas.Cont.add(new BtSelForma());
        SecFormas.Cont.add(new BtSelForma());
        SecFormas.Cont.add(new BtSelForma());
        
        Seccion SecColBorde = new Seccion("Color Borde");
        SecColBorde.Cont.add(new SliderMod(0, 0, 255));
        SecColBorde.Cont.add(new SliderMod(0, 0, 255));
        SecColBorde.Cont.add(new SliderMod(0, 0, 255));

        Seccion SecColRelleno = new Seccion("Color Relleno");
        SecColRelleno.Cont.add(new SliderMod(0, 0, 255));
        SecColRelleno.Cont.add(new SliderMod(0, 0, 255));
        SecColRelleno.Cont.add(new SliderMod(0, 0, 255));

        PnOpciones.add(SecFormas);
        PnOpciones.add(SecColBorde);
        PnOpciones.add(SecColRelleno);


        //CREAR AREA PARA LA IMAGEN
        JPanel PnImagen = new JPanel();
        PnImagen.setPreferredSize(new Dimension(290, 290));

        //ASIGNAR CONSTRAINTS
        SpringLayout MainSP = new SpringLayout();
        PnPrinc.setLayout(MainSP);

        //TITULO
        MainSP.putConstraint(SpringLayout.WEST, PnTitulo, 0, SpringLayout.WEST, PnPrinc);
        MainSP.putConstraint(SpringLayout.NORTH, PnTitulo, 0, SpringLayout.NORTH, PnPrinc);
        MainSP.putConstraint(SpringLayout.EAST, PnTitulo, 0, SpringLayout.EAST, PnPrinc);

        //IMAGEN
        MainSP.putConstraint(SpringLayout.NORTH, PnImagen, 0, SpringLayout.SOUTH, PnTitulo);
        MainSP.putConstraint(SpringLayout.EAST, PnImagen, 0, SpringLayout.EAST, PnPrinc);
        MainSP.putConstraint(SpringLayout.SOUTH, PnImagen, 0, SpringLayout.SOUTH, PnPrinc);

        //SECCION FORMAS
        MainSP.putConstraint(SpringLayout.EAST, PnOpciones, 0, SpringLayout.WEST, PnImagen);
        MainSP.putConstraint(SpringLayout.WEST, PnOpciones, 0, SpringLayout.WEST, PnPrinc);
        MainSP.putConstraint(SpringLayout.NORTH, PnOpciones, 0, SpringLayout.SOUTH, PnTitulo);
        MainSP.putConstraint(SpringLayout.SOUTH, PnOpciones, 0, SpringLayout.SOUTH, PnPrinc);

        PnPrinc.add(PnTitulo);
        PnPrinc.add(PnOpciones);
        PnPrinc.add(PnImagen);

        Vent.add(PnPrinc);

        Vent.setUndecorated(true);
        Vent.setVisible(true);
    }
}