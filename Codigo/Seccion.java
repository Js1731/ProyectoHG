package Codigo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Seccion extends JPanel{

    JPanel Cont = new JPanel();

    public Seccion(String titulo){
        setLayout(new BorderLayout());

        setBackground(Utils.ColGris);

        Cont.setOpaque(false);

        JLabel LbTitulo = new JLabel(titulo);
        LbTitulo.setFont(Utils.FnNormal);
        LbTitulo.setForeground(Color.white);
        LbTitulo.setPreferredSize(new Dimension(30,40));

        add(LbTitulo, BorderLayout.NORTH);
        add(Cont, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Utils.ColGrisClaro);
        g.drawLine(0, 35, getWidth(), 35);
    }
}