package kr.kkiro.roguebox.scene;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.CBackground;
import kr.kkiro.roguebox.curses.CComponent;
import kr.kkiro.roguebox.curses.CLabel;
import kr.kkiro.roguebox.curses.CSimpleButton;
import kr.kkiro.roguebox.curses.CSingleList;
import kr.kkiro.roguebox.curses.CUsageDisplayer;
import kr.kkiro.roguebox.curses.CWindow;
import kr.kkiro.roguebox.curses.ListItem;
import kr.kkiro.roguebox.curses.TextGraphics;

public class ProfileSelectionScene extends Scene {

  private CSimpleButton button;
  private CSingleList list;
  
  @Override
  public boolean keyTyped(int code, CComponent object) {
    if(code == '\n' && object == button) {
      list.chosenEntry.action();
      return true;
    }
    return false;
  }

  @Override
  public void init() {
    TextGraphics g = getApp().getGraphics();
    CBackground background = new CBackground(_("gameName"));
    CWindow window = new CWindow(77, 19, _("profileSelectName"));
    background.placeCenter(window, g);
    background.add(window);
    
    CLabel label = new CLabel(_("profileSelectMsg", _("gameName")));
    label.x = 1;
    label.y = 0;
    window.add(label);
    
    CLabel label2 = new CLabel(_("profileSelect"));
    label2.x = 1;
    label2.y = 2;
    window.add(label2);
    
    list = new CSingleList();
    list.list.add(new NewProfileEntry());
    list.x = 3;
    list.y = 4;
    list.setWidth(window.getWidth(g)-8);
    list.setHeight(window.getHeight(g)-8);
    window.add(list);
    
    button = new CSimpleButton(_("ok"));
    window.placeCenter(button, g);
    button.y = window.getHeight(g) - 3;
    button.setListener(this);
    window.add(button);
    
    CUsageDisplayer usd = new CUsageDisplayer();
    background.add(usd);
    usd.x = 0;
    usd.setWidth(g.getWidth());
    usd.y = background.getHeight(g)-1;
    
    startInput(background);
  }

  @Override
  public void dispose() {
    super.dispose();
  }
  
  public class NewProfileEntry implements ListItem {

    @Override
    public String getLabel() {
      return _("profileSelectNew");
    }

    @Override
    public void action() {
      ProfileSelectionScene.this.getApp().setScene(new GameScene());
    }
    
  }

}
