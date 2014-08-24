package kr.kkiro.roguebox.curses;

import kr.kkiro.roguebox.util.ANSIColor;

public class CLabel extends CComponent {
  
  public String text = "Label";
  
  public CLabel(String text) {
    this.text = text;
  }
  
  @Override
  public void render(TextGraphics g) {
    g.setBackColor(ANSIColor.GRAY);
    g.setTextColor(ANSIColor.BLACK);
    g.drawString(0, 0, text);
  }
  
  @Override
  public int getWidth(TextGraphics g) {
    return g.getSize(text);
  }
  
  @Override
  public int getHeight(TextGraphics g) {
    return 1;
  }

}
