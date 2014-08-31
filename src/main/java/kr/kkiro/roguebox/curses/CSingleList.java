package kr.kkiro.roguebox.curses;

import static kr.kkiro.roguebox.util.I18n._;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import kr.kkiro.roguebox.util.ANSIColor;

public class CSingleList extends CInteractable {

public List<ListItem> list = new ArrayList<ListItem>();
  
  public ListItem chosenEntry = null;
  
  public int currentIndex = 0;
  public int scrollIndex = 0;
  
  @Override
  public void render(TextGraphics g) {
    currentIndex = Math.max(0,Math.min(list.size()-1, currentIndex));
    if(currentIndex - scrollIndex < 0) {
      scrollIndex = currentIndex;
    }
    if(currentIndex - scrollIndex >= height) {
      scrollIndex = currentIndex - height + 1;
    }
    if(chosenEntry == null && list.size() >= 1) {
      chosenEntry = list.get(currentIndex);
    }
    g.setBackColor(ANSIColor.BLUE);
    g.setTextColor(ANSIColor.GRAY);
    g.fillRect();
    for(int i = scrollIndex; i < Math.min(list.size(), scrollIndex+height); ++i) {
      if(currentIndex == i && isFocused()) {
        g.setBackColor(ANSIColor.RED);
        g.setTextColor(ANSIColor.GRAY);
      } else if(currentIndex == i) {
        g.setBackColor(ANSIColor.BLUE);
        g.setTextColor(ANSIColor.WHITE);
      } else {
        g.setBackColor(ANSIColor.BLUE);
        g.setTextColor(ANSIColor.GRAY);
      }
      g.fillRect(0, i-scrollIndex, width-2, 1);
      g.drawString(1, i-scrollIndex, list.get(i).getLabel());
    }
    // draw scrollbar
    //g.setBackColor(ANSIColor.RED);
    //g.fillRect(width-1, 0, 1, height);
    // calculate
    int scrollHeight = Math.min(height, height*height/Math.max(1,list.size()));
    int scrollY = scrollIndex * (height - scrollHeight) / Math.max(1, (list.size()-height));
    g.setBackColor(ANSIColor.BLUE);
    g.setTextColor(ANSIColor.RED);
    for(int i = scrollY; i < scrollY + scrollHeight; ++i) {
      g.drawChar(width-1, i, '▋');
    }
  }
  
  @Override
  public boolean receiveKeyTyped(int code) {
    boolean consumed = false;
    if(-code == KeyEvent.VK_DOWN) {
      currentIndex += 1;
      consumed = true;
    }
    if(-code == KeyEvent.VK_UP) {
      currentIndex -= 1;
      consumed = true;
    }
    if((code == ' ' || code == '\n')) {
      chosenEntry.action();
      consumed = true;
    }
    currentIndex = Math.max(0,Math.min(list.size()-1, currentIndex));
    if(list.size() >= 1) chosenEntry = list.get(currentIndex);
    if(currentIndex - scrollIndex < 0) {
      scrollIndex = currentIndex;
    }
    if(currentIndex - scrollIndex >= height) {
      scrollIndex = currentIndex - height + 1;
    }
    if(consumed) return true;
    return super.receiveKeyTyped(code);
  }
  
  @Override
  public UsageEntry[] getUsage() {
    return new UsageEntry[] {new UsageEntry(_("updown"), _("move")), new UsageEntry(_("return"), _("select"))};
  }

}
