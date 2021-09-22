package Codigo;

import java.awt.Color;
import java.awt.Graphics;

public class SlCompBorde extends SliderMod{

    public SlCompBorde(int valor, int valorMin, int valorMax) {
        super(valor, valorMin, valorMax);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int PosX = Math.round((float)(Valor - ValorMin)/(ValorMax - ValorMin)*getWidth());

        g.setColor(Utils.ColBorde);
        g.fillRect(0, 0, PosX, getHeight());

        g.setColor(Color.WHITE);
        g.drawString(""+Valor, 10, 15);
    }
}
