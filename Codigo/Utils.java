package Codigo;
import java.awt.Color;

import javax.swing.JFrame;

public class Utils {

    public static JFrame Vent = null;
    public static int DelayBorde = 10;
    public static int DelayRelleno = 1;

    public static Color ColBorde = Color.white;
    public static Color ColRelleno = Color.BLACK;

    public static Color ColGrisOsc = new Color(27,27,27);
    public static Color ColGris = new Color(51,51,51);
    public static Color ColGrisClaro = new Color(127,127,127);

    public static int clamp(int val, int valmin, int valmax){
        return val > valmax ? valmax : val < valmin ? valmin : val;
    }
}
