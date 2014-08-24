package kr.kkiro.roguebox.util;

import java.awt.Color;

public enum ANSIColor {
  BLACK(new Color(0,0,0), 0, false),
  RED(new Color(170,0,0), 1, false),
  GREEN(new Color(0,170,0), 2, false),
  YELLOW(new Color(170,85,0), 3, false),
  BLUE(new Color(0,0,170), 4, false),
  MAGENTA(new Color(170,0,170), 5, false),
  CYAN(new Color(0,170,170), 6, false),
  GRAY(new Color(170,170,170), 7, false),
  DARK_GRAY(new Color(85,85,85), 0, true),
  LIGHT_RED(new Color(255,85,85), 1, true),
  LIGHT_GREEN(new Color(85,255,85), 2, true),
  LIGHT_YELLOW(new Color(255,255,85), 3, true),
  LIGHT_BLUE(new Color(85,85,255), 4, true),
  LIGHT_MAGENTA(new Color(255,85,255), 5, true),
  LIGHT_CYAN(new Color(85,255,255), 6, true),
  WHITE(new Color(255,255,255), 7, true);
  
  private Color c;
  private int code;
  private boolean intersity;
  
  public Color getColor() {
    return c;
  }
  
  public int getCode() {
    return code;
  }
  
  public boolean getIntersity() {
    return intersity;
  }
  
  public int getBackCode() {
    return (intersity ? 100 : 40) + code;
  }
  
  public int getTextCode() {
    return (intersity ? 90 : 30) + code;
  }
  
  private ANSIColor(Color c, int code, boolean intersity) {
    this.c = c;
    this.code = code;
    this.intersity = intersity;
  }
}
