package kr.kkiro.roguebox.curses;

import kr.kkiro.roguebox.util.ANSIColor;

public class CProgressBar extends CComponent {

  public int percent = 0;
  
  @Override
  public void render(TextGraphics g) {
    int pwidth = width * percent / 100;
    String ps = percent+"%";
    g.setBackColor(ANSIColor.BLUE);
    g.setTextColor(ANSIColor.WHITE);
    g.fillRect();
    g.drawString(width/2 - ps.length()/2 - 1, height/2, ps);
    g.setBackColor(ANSIColor.RED);
    g.fillRect(0, 0, pwidth, height);
    if(width/2 - ps.length()/2 - 1 < pwidth) {
      g.drawString(width/2 - ps.length()/2 - 1, height/2, ps.substring(0, Math.min(ps.length(), pwidth - (width/2 - ps.length()/2 - 1))));
    }
  }

}
