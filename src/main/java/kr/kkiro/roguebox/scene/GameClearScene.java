package kr.kkiro.roguebox.scene;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.CBackground;
import kr.kkiro.roguebox.curses.CComponent;
import kr.kkiro.roguebox.curses.CLabel;
import kr.kkiro.roguebox.curses.CSimpleButton;
import kr.kkiro.roguebox.curses.CWindow;
import kr.kkiro.roguebox.curses.TextGraphics;

public class GameClearScene extends Scene {

  public boolean success = false;
  
  public GameClearScene(boolean success) {
    this.success = success;
  }
  
  @Override
  public boolean keyTyped(int code, CComponent object) {
    
    if(code == 10) {
      getApp().setScene(new GameScene());
      return true;
    }
    return false;
  }

  @Override
  public void init() {
    TextGraphics g = getApp().getGraphics();
    CBackground background = new CBackground(_("gameName"));
    
    String message = _(success ? "gameClear" : "playerDied");
    
    int winSize = g.getSize(message)+4;
    
    CWindow window = new CWindow(winSize, 7,  _("gameOverWindow"));
    background.placeCenter(window, g);
    background.add(window);
    
    CLabel label = new CLabel(message);
    window.add(label);
    window.placeCenter(label, g);
    label.y = 1;
    
    CLabel label2 = new CLabel(_("okToRestart"));
    window.add(label2);
    window.placeCenter(label2, g);
    label2.y = 2;
    
    CSimpleButton confirm = new CSimpleButton(_("ok"));
    window.add(confirm);
    window.placeCenter(confirm, g);
    confirm.y = 4;
    confirm.setListener(this);
    
    background.render(g);
    
    startInput(background);
  }
  
}
