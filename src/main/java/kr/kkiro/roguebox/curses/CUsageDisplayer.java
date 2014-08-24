package kr.kkiro.roguebox.curses;

import kr.kkiro.roguebox.util.ANSIColor;
import static kr.kkiro.roguebox.util.I18n._;

public class CUsageDisplayer extends CComponent {
  
  @Override
  public int getHeight(TextGraphics g) {
    return 1;
  }
  
  @Override
  public void render(TextGraphics g) {
    g.setBackColor(ANSIColor.GRAY);
    g.setTextColor(ANSIColor.BLACK);
    g.fillRect(0, 0, g.getWidth(), 1);
    int position = 0;
    /*if(!getParent().currentFocus().isFocusLocked()) {
      position ++;
      g.drawString(position, 0, _("usage",_("tab"),_("move")));
      position += g.getSize(_("usage",_("tab"),_("move")));
    }*/
    UsageEntry[] usages = getParent().currentFocus().getUsage();
    for(UsageEntry usage : usages) {
      position ++;
      String message = _("usage",usage.key,usage.action);
      g.drawString(position, 0, message);
      position += g.getSize(message);
    }
  }

}
