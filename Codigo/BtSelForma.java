package Codigo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class BtSelForma extends BotonGenerico{

    private int Forma;

    public BtSelForma(String Texto, int fr){
        super();

        Forma = fr;

        JLabel LbTexto = new JLabel(Texto);
        LbTexto.setForeground(Color.white);
        LbTexto.setFont(Utils.FnNormal);

        add(LbTexto);

        setPreferredSize(new Dimension(100,30));
        setBackground(Utils.ColGrisClaro);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(MouseEncima){
            g.setColor(Color.cyan);
            g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        }

        if(Main.Forma == Forma){
            g.setColor(Utils.ColGris);
            g.fillRect(0, 0, getWidth()-1, getHeight()-1);
        }
    }
}
