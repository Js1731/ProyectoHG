package Codigo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Dibujo {

    public static int DelayBorde = 10;
    public static int DelayRelleno = 1;

    public static void  _dibujar_pixel(int x, int y, BufferedImage Imagen){
        
        Graphics Lapiz = Imagen.getGraphics();

        try {
            Lapiz.setColor(Utils.ColBorde);
            Lapiz.drawLine(x, y, x, y);
            Thread.sleep(DelayBorde);
            Utils.Vent.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void _reiniciar_imagen(BufferedImage Imagen){

        Graphics Lapiz = Imagen.getGraphics();

        Lapiz.setColor(Color.gray);
        Lapiz.fillRect(0, 0, Imagen.getWidth(), Imagen.getHeight());
    }

    public static void _dibujar_linea(int x1, int y1, int x2, int y2, BufferedImage Imagen){
        
        double px = x1;
        double py = y1;

        int largo = Math.abs(Math.abs(x2 - x1) > Math.abs(y2 - y1) ?  (x2 - x1) : (y2 - y1));

        float dx = (float)(x2 - x1)/(largo);
        float dy = (float)(y2 - y1)/(largo);
        
        for (int i = 0; i < largo; i++) {
            px += dx;
            py += dy;

            _dibujar_pixel(Math.round((float)px), Math.round((float)py), Imagen);
        }
    }

    public static void _dib_rect(int x, int y, int ancho, int alto, BufferedImage Imagen){

        if(Main.Dibujando)
            return;

        Thread Dibujar = new Thread(new Runnable(){
            @Override
            public void run() {

                _reiniciar_imagen(Imagen);
                Main.Dibujando = true;

                _dibujar_linea(x, y, x + ancho, y, Imagen);
                _dibujar_linea(x + ancho, y, x + ancho, y + alto, Imagen);
                _dibujar_linea(x + ancho, y + alto, x, y + alto, Imagen);
                _dibujar_linea(x, y + alto, x, y, Imagen);

                _rellenar(30, 30, Imagen.getRGB(30, 30), Utils.ColRelleno.getRGB(), Imagen);

                Main.Dibujando = false;
            }
        });

        Dibujar.start();
    }

    public static void _dib_tria(int x1, int y1, int x2, int y2, int x3, int y3, BufferedImage Imagen){

        if(Main.Dibujando)
            return;

        Thread Dibujar = new Thread(new Runnable(){
            @Override
            public void run() {
                Graphics Lapiz = Imagen.getGraphics();
                _reiniciar_imagen(Imagen);
                Main.Dibujando = true;

                Lapiz.setColor(Utils.ColBorde);
                _dibujar_linea(x1, y1, x2, y2, Imagen);
                _dibujar_linea(x2, y2, x3, y3, Imagen);
                _dibujar_linea(x3, y3, x1, y1, Imagen);

                _rellenar(30, 30, Imagen.getRGB(30, 30), Utils.ColRelleno.getRGB(), Imagen);

                Main.Dibujando = false;
            }
        });

        Dibujar.start();
    }

    public static void _dib_poligono(int x, int y, int radio, int lados, BufferedImage Imagen){

        if(Main.Dibujando)
            return;

        Thread Dibujar = new Thread(new Runnable(){
            @Override
            public void run() {

                _reiniciar_imagen(Imagen);
                Graphics Lapiz = Imagen.getGraphics();
                Main.Dibujando = true;

                double paso = 2*Math.PI/lados;

                //PUNTO INICIAL
                double px_ini = x + radio;
                double py_ini = y;

                for (double i = paso; i <= 2*Math.PI; i+=paso) {
                    double px = x + radio * Math.cos(i);
                    double py = y + radio * Math.sin(i);

                    Lapiz.setColor(Utils.ColBorde);
                    _dibujar_linea(Math.round((float)px_ini), 
                                   Math.round((float)py_ini), 
                                   Math.round((float)px), 
                                   Math.round((float)py), 
                                   Imagen);
                    
                    //ACTUALIZAR PUNTO INICIAL
                    px_ini = px;
                    py_ini = py;
                }

                _rellenar(30, 30, Imagen.getRGB(30, 30), Utils.ColRelleno.getRGB(), Imagen);

                Main.Dibujando = false;
            }
        });

        Dibujar.start();
    }

    //RELLENAR UN ESPACIO CON UN COLOR
    public static void _rellenar(int X, int Y, int ColOr, int ColNuevo, BufferedImage Imagen){

        if(X < Imagen.getWidth() && X >= 0 && Y >= 0 && Y < Imagen.getHeight()){
            int Col = Imagen.getRGB(X, Y);

            if(Col == ColOr){
                try {
                    Thread.sleep(DelayRelleno);
                    Imagen.setRGB(X, Y, ColNuevo);
                    Utils.Vent.repaint();

                    _rellenar( X + 1, Y, ColOr, ColNuevo, Imagen);
                    _rellenar( X - 1, Y, ColOr, ColNuevo, Imagen);
                    _rellenar( X, Y + 1, ColOr, ColNuevo, Imagen);
                    _rellenar(X, Y - 1, ColOr, ColNuevo, Imagen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
