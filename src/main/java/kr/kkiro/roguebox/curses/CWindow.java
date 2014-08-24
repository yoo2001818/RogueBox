package kr.kkiro.roguebox.curses;

import kr.kkiro.roguebox.util.ANSIColor;

public class CWindow extends CContainer {
  public String name = "Unnamed Window";
  
  public CWindow(int width, int height, String name) {
    this.width = width;
    this.height = height;
    this.name = name;
  }
  
  @Override
  public void render(TextGraphics g) {
    g.setBackColor(ANSIColor.BLACK);
    g.setTextColor(ANSIColor.WHITE);
    g.fillRect(1, 1, getWidth(g), getHeight(g));
    g.setBackColor(ANSIColor.GRAY);
    g.setTextColor(ANSIColor.BLACK);
    g.fillRect(0, 0, getWidth(g), getHeight(g));
    BorderDecor.drawBox(g, 0, 0, getWidth(g), getHeight(g));
    int titleSize = g.getSize(name);
    int centerPos = (int)(((getWidth(g)) / 2f - titleSize / 2f));
    g.drawChar(centerPos-1, 0, '┤');
    g.drawChar(centerPos+titleSize, 0, '├');
    g.setTextColor(ANSIColor.RED);
    g.drawString(centerPos, 0, name);
    super.render(g.subSet(1, 1, getWidth(g)-2, getHeight(g)-2));
  }
  
  @Override
  public void placeCenter(CComponent c, TextGraphics g) {
    super.placeCenter(c, g);
    c.x -= 1;
    c.y -= 1;
  }
}
