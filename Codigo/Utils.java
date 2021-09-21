package Codigo;
import java.awt.Color;

public class Utils {
    public static Color ColNegro = new Color(27,27,27);

    public static int clamp(int val, int valmin, int valmax){
        return val > valmax ? valmax : val < valmin ? valmin : val;
    }
}
