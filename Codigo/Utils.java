package Codigo;
import java.awt.Color;

import javax.swing.JFrame;

public class Utils {

    public static JFrame Vent = null;
    public static int DelayBorde = 10;
    public static int DelayRelleno = 1;

    public static Color ColNegro = new Color(27,27,27);

    public static int clamp(int val, int valmin, int valmax){
        return val > valmax ? valmax : val < valmin ? valmin : val;
    }
}
