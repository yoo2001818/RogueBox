package kr.kkiro.roguebox.scene;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.RogueBox;
import kr.kkiro.roguebox.curses.CBackground;
import kr.kkiro.roguebox.curses.CLabel;
import kr.kkiro.roguebox.curses.CProgressBar;
import kr.kkiro.roguebox.curses.CWindow;
import kr.kkiro.roguebox.curses.TextGraphics;

public class LoadScene extends Scene {

  @Override
  public void init() {
    TextGraphics g = getApp().getGraphics();
    CBackground background = new CBackground(_("gameName"));
    CWindow window = new CWindow(40, 7, _("gameName"));
    background.add(window);
    background.placeCenter(window, g);
    
    CLabel label0 = new CLabel(_("version", _("gameName"), RogueBox.VERSION));
    window.add(label0);
    window.placeCenter(label0, g);
    label0.y = 0;
    
    CLabel credits = new CLabel(_("credits"));
    window.add(credits);
    window.placeCenter(credits, g);
    credits.y = 1;
    
    CLabel label1 = new CLabel(_("loading"));
    window.add(label1);
    window.placeCenter(label1, g);
    label1.y = 2;
    
    CProgressBar bar = new CProgressBar();
    bar.setWidth(window.getWidth(g)-2);
    bar.setHeight(1);
    window.add(bar);
    bar.y = 4;
    
    background.render(g);
    
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    while(bar.percent < 100) {
      bar.percent ++;
      background.render(g);
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    
    getApp().setScene(new ControllerDetectionScene());
  }

  @Override
  public void dispose() {
    super.dispose();
  }

}
