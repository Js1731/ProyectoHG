package Codigo;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

///CLASE PARA MOVER LA VENTANA
class Listener implements MouseInputListener {

    int DifX;
    int DifY;

    public void mousePressed(MouseEvent e) {
        DifX = e.getXOnScreen() - Utils.Vent.getLocation().x;
        DifY = e.getYOnScreen() - Utils.Vent.getLocation().y;
    }

    public void mouseDragged(MouseEvent e) {
        JFrame Vent = (JFrame) e.getSource();
        Vent.setLocation(e.getXOnScreen() - DifX, e.getYOnScreen() - DifY);

    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
}

public class Main {

    public static Thread ProcDibujar;
    public static int FormaAct = 0;
    public static boolean Dibujando = false;

    public static JPanel PnImagen;
    public static BufferedImage Imagen = new BufferedImage(60,60,BufferedImage.TYPE_INT_ARGB);
    public static Graphics Lapiz;

   

    public static void main(String[] args) {
        JFrame Vent = Utils.Vent = new JFrame();
        Vent.setSize(650, 550);

        Dimension Pantalla = Toolkit.getDefaultToolkit().getScreenSize();

        Vent.setLocation(Pantalla.width/2 - 650/2, Pantalla.height/2 - 550/2);
        Vent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel PnPrinc = new JPanel();
        PnPrinc.setBackground(Utils.ColGris);

        //CREAR CONTENIDO DE LA VENTANA
        JPanel PnTitulo = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(Utils.ColGrisClaro);
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        };
        PnTitulo.setBackground(Utils.ColGrisOsc);
        PnTitulo.setPreferredSize(new Dimension(PnTitulo.getPreferredSize().width, 80));


        JLabel LbTitulo = new JLabel("Proyecto 1");
        LbTitulo.setForeground(Color.WHITE);
        LbTitulo.setFont(Utils.FnTitulo);

        JLabel LbSubTitulo = new JLabel("Herramientas de Computacion Grafica");
        LbSubTitulo.setFont(Utils.FnNormal);
        LbSubTitulo.setForeground(Utils.ColGrisClaro);

        JLabel LbNombre1 = new JLabel("Joshua Lopez 8-970-791");
        LbNombre1.setForeground(Color.WHITE);
        LbNombre1.setFont(Utils.FnNormal);

        JLabel LbNombre2 = new JLabel("Jair Labrador 9-923-233");
        LbNombre2.setForeground(Color.WHITE);
        LbNombre2.setFont(Utils.FnNormal);

        BotonGenerico BtCerrar = new BotonGenerico(){

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Vent.dispose();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D)g;

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(MouseEncima){
                    g2.setColor(Utils.ColGris);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }


                g2.setColor(Color.WHITE);
                g2.drawLine(10, 10, 30, 30);
                g2.drawLine(30, 10, 10, 30);
            }
        };

        BtCerrar.setPreferredSize(new Dimension(40, 40));
        BtCerrar.setOpaque(false);

        SpringLayout SpTitulo = new SpringLayout();
        PnTitulo.setLayout(SpTitulo);

        SpTitulo.putConstraint(SpringLayout.WEST, LbTitulo, 20, SpringLayout.WEST, PnTitulo);
        SpTitulo.putConstraint(SpringLayout.NORTH, LbTitulo, 15, SpringLayout.NORTH, PnTitulo);

        SpTitulo.putConstraint(SpringLayout.WEST, LbSubTitulo, 20, SpringLayout.WEST, PnTitulo);
        SpTitulo.putConstraint(SpringLayout.NORTH, LbSubTitulo, 0, SpringLayout.SOUTH, LbTitulo);

        SpTitulo.putConstraint(SpringLayout.EAST, BtCerrar, -20, SpringLayout.EAST, PnTitulo);
        SpTitulo.putConstraint(SpringLayout.NORTH, BtCerrar, 5, SpringLayout.NORTH, LbTitulo);

        SpTitulo.putConstraint(SpringLayout.EAST, LbNombre1, -20, SpringLayout.WEST, BtCerrar);
        SpTitulo.putConstraint(SpringLayout.NORTH, LbNombre1, 20, SpringLayout.NORTH, PnTitulo);

        SpTitulo.putConstraint(SpringLayout.EAST, LbNombre2, -20, SpringLayout.WEST, BtCerrar);
        SpTitulo.putConstraint(SpringLayout.NORTH, LbNombre2, 0, SpringLayout.SOUTH, LbNombre1);

        PnTitulo.add(LbTitulo);
        PnTitulo.add(LbSubTitulo);
        PnTitulo.add(LbNombre1);
        PnTitulo.add(LbNombre2);
        PnTitulo.add(BtCerrar);

        //CREAR PANEL PARA LA SECCION DE SELECCION DE FORMAS, COLOR Y RELLENO
        JPanel PnOpciones = new JPanel();
        PnOpciones.setLayout(new BoxLayout(PnOpciones, BoxLayout.Y_AXIS));
        
        //CREAR SECCIONES

        Seccion SecFormas = new Seccion("Formas");
        SecFormas.Cont.setLayout(new FlowLayout(FlowLayout.LEFT));
        SecFormas.Cont.add(new BtSelForma("Rectangulo", 0));
        SecFormas.Cont.add(new BtSelForma("Triangulo", 1));
        SecFormas.Cont.add(new BtSelForma("Rombo", 2));
        SecFormas.Cont.add(new BtSelForma("Pentagono", 3));
        SecFormas.Cont.add(new BtSelForma("Heptagono", 4));
        
        Seccion SecColBorde = new Seccion("Color Borde");
        
        SlCompBorde SlBordeColR = new SlCompBorde(255, 0, 255);
        SlCompBorde SlBordeColG = new SlCompBorde(255, 0, 255);
        SlCompBorde SlBordeColB = new SlCompBorde(255, 0, 255);

        SecColBorde.Cont.add(SlBordeColR);
        SecColBorde.Cont.add(SlBordeColG);
        SecColBorde.Cont.add(SlBordeColB);


        Seccion SecColRelleno = new Seccion("Color Relleno");

        SlCompRelleno SlRellenoColR = new SlCompRelleno(0, 0, 255);
        SlCompRelleno SlRellenoColG = new SlCompRelleno(0, 0, 255);
        SlCompRelleno SlRellenoColB = new SlCompRelleno(0, 0, 255);

        SecColRelleno.Cont.add(SlRellenoColR);
        SecColRelleno.Cont.add(SlRellenoColG);
        SecColRelleno.Cont.add(SlRellenoColB);


        CambioValorList Listener = new CambioValorList(){

            @Override
            public void CambioValor(SliderMod pnBarra) {

                if(Dibujando){
                    Dibujo.DelayBorde = 0;
                    Dibujo.DelayRelleno = 0;
                }

                Vent.repaint();

                Utils.ColBorde = new Color(SlBordeColR.Valor, SlBordeColG.Valor, SlBordeColB.Valor); 
                Utils.ColRelleno = new Color(SlRellenoColR.Valor, SlRellenoColG.Valor, SlRellenoColB.Valor); 
            }
        };


        SlBordeColR.AgregarCambioValList(Listener);
        SlBordeColG.AgregarCambioValList(Listener);
        SlBordeColB.AgregarCambioValList(Listener);

        SlRellenoColR.AgregarCambioValList(Listener);
        SlRellenoColG.AgregarCambioValList(Listener);
        SlRellenoColB.AgregarCambioValList(Listener);


        JPanel PnBtDibujar = new JPanel();
        PnBtDibujar.setBackground(Utils.ColGris);
        BotonGenerico BtDibujar = new BotonGenerico(){
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                

                if(Dibujando){
                    Dibujo.DelayBorde = 0;
                    Dibujo.DelayRelleno = 0;
                    
                    return;
                }

                Dibujo._reiniciar_imagen(Imagen);
                Dibujo.DelayBorde = 5;
                Dibujo.DelayRelleno = 2;

                switch (FormaAct) {
                    case 0:
                        Dibujo._dib_rect(10,10, 40, 40, Imagen);
                    break;

                    case 1:
                        Dibujo._dib_poligono(30, 30, 20, 3, Imagen);
                    break;
                        
                    case 2:
                        Dibujo._dib_poligono(30, 30, 20, 4, Imagen);
                    break;

                    case 3:
                        Dibujo._dib_poligono(30, 30, 20, 5, Imagen);
                    break;

                    case 4:
                        Dibujo._dib_poligono(30, 30, 20, 6, Imagen);
                    break;
                }
            }  

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                setOpaque(false);

                if(!Dibujando){
                    if(MouseEncima)
                        g.setColor(Color.lightGray);
                    else
                        g.setColor(Utils.ColGrisClaro);

                }else
                    g.setColor(Utils.ColGrisOsc);

                g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g.setColor(Color.WHITE);
                g.drawString("Dibujar", 30, 18);

            }
        };
        BtDibujar.setPreferredSize(new Dimension(100, 30));
        BtDibujar.setBackground(Color.CYAN);

        PnBtDibujar.add(BtDibujar);

        PnOpciones.add(SecFormas);
        PnOpciones.add(SecColBorde);
        PnOpciones.add(SecColRelleno);
        PnOpciones.add(PnBtDibujar);


        //CREAR AREA PARA LA IMAGEN
        
        //INICIAR IMAGEN
        Lapiz = Imagen.getGraphics();
        PnImagen = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(Imagen, 0, 0, getWidth(), getHeight(), this);
            }
        };
        PnImagen.setPreferredSize(new Dimension(400, 300));
        PnImagen.setBackground(Utils.ColGrisClaro);
        Dibujo.DelayBorde = 5;
        
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
        MainSP.putConstraint(SpringLayout.EAST, PnOpciones, -20, SpringLayout.WEST, PnImagen);
        MainSP.putConstraint(SpringLayout.WEST, PnOpciones, 20, SpringLayout.WEST, PnPrinc);
        MainSP.putConstraint(SpringLayout.NORTH, PnOpciones, 0, SpringLayout.SOUTH, PnTitulo);
        MainSP.putConstraint(SpringLayout.SOUTH, PnOpciones, 0, SpringLayout.SOUTH, PnPrinc);

        PnPrinc.add(PnTitulo);
        PnPrinc.add(PnOpciones);
        PnPrinc.add(PnImagen);

        Vent.add(PnPrinc);

        Vent.setUndecorated(true);
        Vent.setVisible(true);

        Listener Lst = new Listener();
        Vent.addMouseListener(Lst);
        Vent.addMouseMotionListener(Lst);
    }
}