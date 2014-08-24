package kr.kkiro.roguebox.curses;

import kr.kkiro.roguebox.util.ANSIColor;

public class CButton extends CInteractable {

  public String text = "Button";
  public int pressed = -1;
  
  @Override
  public boolean receiveKeyTyped(int code) {
    if(code == 10) {
      pressed = 0;
      return true;
    }
    return super.receiveKeyTyped(code);
  }
  
  @Override
  public void render(TextGraphics g) {
    /*if(pressed >= 0) {
      pressed ++;
      if(pressed > 2) {
        pressed = -1;
      }
    }*/
    g.setBackColor(ANSIColor.BLACK);
    g.setTextColor(ANSIColor.WHITE);
    g.fillRect(1, 1, getWidth(g), getHeight(g));
    g.setBackColor(ANSIColor.BLUE);
    g.setTextColor(ANSIColor.WHITE);
    g.fillRect(pressed >= 0? 1 : 0, pressed >= 0? 1 : 0, getWidth(g), getHeight(g));
    BorderDecor.drawBox(g, pressed >= 0? 1 : 0, pressed >= 0? 1 : 0, getWidth(g), getHeight(g));
    if(isFocused()) {
      g.setBackColor(ANSIColor.RED);
      g.setTextColor(ANSIColor.WHITE);
    }
    g.drawString(width/2 - g.getSize(text)/2 + (pressed >= 0? 1 : 0), 1 + (pressed >= 0? 1 : 0), text);
  }

}
