package kr.kkiro.roguebox.curses;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.util.ANSIColor;

public class CSimpleButton extends CInteractable {

  public String text = "Button";
  
  public CSimpleButton(String text) {
    this.text = text;
  }
  
  @Override
  public void render(TextGraphics g) {
    g.setBackColor(ANSIColor.GRAY);
    g.setTextColor(ANSIColor.BLACK);
    if(isFocused()) {
      g.setBackColor(ANSIColor.RED);
      g.setTextColor(ANSIColor.WHITE);
    }
    if(!isEnabled()) {
      g.setTextColor(ANSIColor.DARK_GRAY);
    }
    g.drawString(0,0, "<"+text+">");
  }
  
  @Override
  public int getWidth(TextGraphics g) {
    return g.getSize(text)+2;
  }
  
  @Override
  public int getHeight(TextGraphics g) {
    return 1;
  }
  
  @Override
  public UsageEntry[] getUsage() {
    return new UsageEntry[] {new UsageEntry(_("return"), _("accept"))};
  }


}
