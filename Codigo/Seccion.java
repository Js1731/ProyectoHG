package Codigo;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Seccion extends JPanel{

    JPanel Cont = new JPanel();

    public Seccion(String titulo){
        setLayout(new BorderLayout());
        JLabel LbTitulo = new JLabel(titulo);

        add(LbTitulo, BorderLayout.NORTH);
        add(Cont, BorderLayout.CENTER);
    }
}