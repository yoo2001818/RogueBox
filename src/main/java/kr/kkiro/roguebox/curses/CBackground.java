package kr.kkiro.roguebox.curses;

import kr.kkiro.roguebox.util.ANSIColor;

public class CBackground extends CContainer {
  public String message = "Background";
  
  public CBackground(String message) {
    this.message = message;
  }
  
  @Override
  public void render(TextGraphics g) {
    g.setBackColor(ANSIColor.BLUE);
    g.setTextColor(ANSIColor.GRAY);
    g.fillRect();
    g.drawString(0, 0, message);
    super.render(g);
  }
  
  @Override
  public int getWidth(TextGraphics g) {
    return g.getWidth();
  }
  
  @Override
  public int getHeight(TextGraphics g) {
    return g.getHeight();
  }
}
