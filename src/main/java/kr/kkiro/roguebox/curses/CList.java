package kr.kkiro.roguebox.curses;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import kr.kkiro.roguebox.util.ANSIColor;
import static kr.kkiro.roguebox.util.I18n._;

public class CList extends CInteractable {
  
  public List<ListItem> list = new ArrayList<ListItem>();
  
  public ListItem chosenEntry = null;
  
  public int currentIndex = 0;
  public int scrollIndex = 0;
  
  @Override
  public void render(TextGraphics g) {
    g.setBackColor(ANSIColor.BLUE);
    g.setTextColor(ANSIColor.GRAY);
    g.fillRect();
    for(int i = scrollIndex; i < Math.min(list.size(), scrollIndex+height); ++i) {
      g.setBackColor(ANSIColor.BLUE);
      g.drawString(0, i-scrollIndex, "[ ]");
      
      if(currentIndex == i && isFocused()) {
        g.setBackColor(ANSIColor.RED);
      } else {
        g.setBackColor(ANSIColor.BLUE);
      }
      
      if(chosenEntry == list.get(i)) {
        g.drawString(1, i-scrollIndex, "*");
      } else {
        g.drawString(1, i-scrollIndex, " ");
      }
      
      g.setBackColor(ANSIColor.BLUE);
      g.drawString(4, i-scrollIndex, list.get(i).getLabel());
    }
    // draw scrollbar
    //g.setBackColor(ANSIColor.RED);
    //g.fillRect(width-1, 0, 1, height);
    // calculate
    int scrollHeight = Math.min(height, height*height/list.size());
    int scrollY = scrollIndex * (height - scrollHeight) / (list.size()-height);
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
      chosenEntry = list.get(currentIndex);
      consumed = true;
    }
    currentIndex = Math.max(0,Math.min(list.size()-1, currentIndex));
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
