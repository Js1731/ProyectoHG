package Codigo;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;


/**
 * Slider con una area que puede ser modificada sobreescribiendo el metodo {@link #paintComponent(Graphics)}
 */

@SuppressWarnings(value = "serial")
public class SliderMod extends JPanel implements MouseInputListener {

    public int ValorMin = 0;
    public int ValorMax = 0;
    public int Valor = 0;
    private ArrayList<CambioValorList> CambioListener = new ArrayList<CambioValorList>();

    /**
     * Slider con una area que puede ser modificada sobreescribiendo el metodo {@link #paintComponent(Graphics)}
     * @param valor Valor Incial
     * @param valorMin
     * @param valorMax
     */
    public SliderMod(int valor, int valorMin, int valorMax) {
        ValorMin = valorMin;
        ValorMax = valorMax;
        Valor = valor;
        setBackground(Color.lightGray);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void AgregarCambioValList(CambioValorList CVList){
        CambioListener.add(CVList);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getParent().getWidth(), 20);
    }

    //----------------------------------------------------------------ACTUALIZAR VALORES DEL SLIDER Y NOTIFICAR EL CAMBIO
    @Override
    public void mousePressed(MouseEvent e) {
        float Pos = (float) e.getX()/getWidth();
        Valor = Utils.clamp(Math.round(Pos*Math.abs(ValorMax - ValorMin) + ValorMin),ValorMin, ValorMax);

        try{
            for (CambioValorList cambioValorList : CambioListener) {
                cambioValorList.CambioValor(this);
            }            
        }catch(NullPointerException ex){}

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        float Pos = (float) e.getX()/getWidth();
        Valor = Utils.clamp(Math.round(Pos*Math.abs(ValorMax - ValorMin) + ValorMin),ValorMin, ValorMax);

        try{
            for (CambioValorList cambioValorList : CambioListener) {
                cambioValorList.CambioValor(this);
            }                
        }catch(NullPointerException ex){}

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int PosX = Math.round((float)(Valor - ValorMin)/(ValorMax - ValorMin)*getWidth());

        g.fillRect(0, 0, PosX, getHeight());

        g.setColor(Color.gray);
        g.drawString(""+Valor, 10, 15);
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
}
