package model;

import java.awt.*;
import java.time.LocalDate;

public class Group {

    private String reason;
    private Color color;
    private String ifContains;

    public Group(String reason, Color color, String ifContains) {
        this.reason = reason;
        this.color = color;
        this.ifContains = ifContains;
    }

    public Group(String[] temp) {
        int i=0;
        for (String elem:temp){
            if(i==0){
                this.reason = elem;
            }
            if(i==1){
                try {
                    this.color = Color.decode(elem);
                } catch (Exception e) {
                    color=Color.BLUE;
                }
            }
            if(i==2){
                ifContains=elem;
            }
            i++;
        }
    }

    public String store(){
        return reason + Main.SEPARATOR +
                color.getRGB() + Main.SEPARATOR +
                ifContains + Main.SEPARATOR + Main.ENDSEPARATOR;
    }

    public String getReason() {
        return reason;
    }

    public Color getColor() {
        return color;
    }

    public String getIfContains() {
        return ifContains;
    }

    public static Group groupFromString(String string){
        String[] temp = string.split(Main.SEPARATOR);
        return new Group(temp);
    }
}
