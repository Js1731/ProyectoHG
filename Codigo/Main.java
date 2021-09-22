package Codigo;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
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
    public static int Forma = 0;
    public static boolean Dibujando = false;
    public static JPanel PnImagen;
    public static Graphics Lapiz;
    public static BufferedImage Imagen = new BufferedImage(150,150,BufferedImage.TYPE_INT_ARGB);

    public static void  _dibujar_pixel(int x, int y){
        
        try {
            Lapiz.drawLine(x, y, x, y);
            Thread.sleep(Utils.DelayBorde);
            Utils.Vent.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void _reiniciar_imagen(){
        Lapiz.setColor(Color.gray);
        Lapiz.fillRect(0, 0, Imagen.getWidth(), Imagen.getHeight());
    }

    public static void _dibujar_linea(int x1, int y1, int x2, int y2){
        double px = x1;
        double py = y1;

        int largo = Math.abs(Math.abs(x2 - x1) > Math.abs(y2 - y1) ?  (x2 - x1) : (y2 - y1));

        float dx = (float)(x2 - x1)/(largo);
        float dy = (float)(y2 - y1)/(largo);
        
        for (int i = 0; i < largo; i++) {
            px += dx;
            py += dy;

            _dibujar_pixel(Math.round((float)px), Math.round((float)py));
        }
    }

    public static void _dib_rect_statico(int x, int y, int ancho, int alto){

        if(Dibujando)
            return;

        Thread Dibujar = ProcDibujar = new Thread(new Runnable(){
            @Override
            public void run() {

                _reiniciar_imagen();
                Dibujando = true;

                Lapiz.setColor(Utils.ColBorde);
                
                _dibujar_linea(x, y, x + ancho, y);
                _dibujar_linea(x + ancho, y, x + ancho, y + alto);
                _dibujar_linea(x + ancho, y + alto, x, y + alto);
                _dibujar_linea(x, y + alto, x, y);
                
                _rellenar(75, 75, Imagen.getRGB(75, 75), Utils.ColRelleno.getRGB());

                Dibujando = false;
            }
        });

        Dibujar.start();
    }

    public static void _dib_tria_statico(int x1, int y1, int x2, int y2, int x3, int y3){

        if(Dibujando)
            return;

        Thread Dibujar = ProcDibujar = new Thread(new Runnable(){
            @Override
            public void run() {
                _reiniciar_imagen();
                Dibujando = true;

                Lapiz.setColor(Utils.ColBorde);
                _dibujar_linea(x1, y1, x2, y2);
                _dibujar_linea(x2, y2, x3, y3);
                _dibujar_linea(x3, y3, x1, y1);

                _rellenar(75, 75, Imagen.getRGB(75, 75), Utils.ColRelleno.getRGB());

                Dibujando = false;
            }
        });

        Dibujar.start();
    }

    public static void _dib_poligono_statico(int x, int y, int radio, int lados){

        if(Dibujando)
            return;

        Thread Dibujar = ProcDibujar = new Thread(new Runnable(){
            @Override
            public void run() {

                _reiniciar_imagen();

                Dibujando = true;

                double paso = 2*Math.PI/lados;

                //PUNTO INICIAL
                double px_ini = x + radio;
                double py_ini = y;

                for (double i = paso; i <= 2*Math.PI; i+=paso) {
                    double px = x + radio * Math.cos(i);
                    double py = y + radio * Math.sin(i);

                    Lapiz.setColor(Utils.ColBorde);
                    _dibujar_linea(Math.round((float)px_ini), Math.round((float)py_ini), Math.round((float)px), Math.round((float)py));
                    
                    //ACTUALIZAR PUNTO INICIAL
                    px_ini = px;
                    py_ini = py;
                }
                
                _rellenar(75, 75, Imagen.getRGB(75, 75), Utils.ColRelleno.getRGB());
                Dibujando = false;
            }
        });

        Dibujar.start();
    }

    //RELLENAR UN ESPACIO CON UN COLOR
    public static void _rellenar(int X, int Y, int ColOr, int ColNuevo){

        if(X < Imagen.getWidth() && X >= 0 && Y >= 0 && Y < Imagen.getHeight()){
            int Col = Imagen.getRGB(X, Y);

            if(Col == ColOr){
                try {
                    Thread.sleep(Utils.DelayRelleno);
                    
                    Imagen.setRGB(X, Y, ColNuevo);
                    Utils.Vent.repaint();

                    _rellenar( X + 1, Y, ColOr, ColNuevo);
                    _rellenar( X - 1, Y, ColOr, ColNuevo);
                    _rellenar( X, Y + 1, ColOr, ColNuevo);
                    _rellenar(X, Y - 1, ColOr, ColNuevo);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                

            }
        }
    }

    public static void main(String[] args) {
        JFrame Vent = Utils.Vent = new JFrame();
        Vent.setSize(600, 440);
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
        SecFormas.Cont.add(new BtSelForma(){
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                System.out.println("Dibujar Rectangulo");
                Forma = 0;                
            }
        });
        SecFormas.Cont.add(new BtSelForma(){
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                System.out.println("Dibujar Triangulo");
                Forma = 1;
            }
        });
        SecFormas.Cont.add(new BtSelForma(){
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                System.out.println("Dibujar Pentagono");
                Forma = 2;
            }
        });
        
        Seccion SecColBorde = new Seccion("Color Borde");
        
        SlCompBorde SlBordeColR = new SlCompBorde(0, 0, 255);
        SlCompBorde SlBordeColG = new SlCompBorde(0, 0, 255);
        SlCompBorde SlBordeColB = new SlCompBorde(0, 0, 255);

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
                    Utils.DelayBorde = 0;
                    Utils.DelayRelleno = 0;
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
        BotonGenerico BtDibujar = new BotonGenerico(){
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if(Dibujando)
                    return;

                _reiniciar_imagen();
                Utils.DelayBorde = 5;
                Utils.DelayRelleno = 1;

                switch (Forma) {
                    case 0:
                        _dib_rect_statico(30,30, 90, 90);
                    break;

                    case 1:
                        _dib_tria_statico(75, 30, 120, 120, 30, 120);
                    break;
                        
                    case 2:
                        _dib_poligono_statico(75, 75, 50, 7);
                    break;
                }
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

                g.drawImage(Imagen, 0, 0, 300, 300, this);
            }
        };
        PnImagen.setPreferredSize(new Dimension(350, 300));
        PnImagen.setBackground(Color.green);
        Utils.DelayBorde = 5;
        
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

        Listener Lst = new Listener();
        Vent.addMouseListener(Lst);
        Vent.addMouseMotionListener(Lst);
    }
}