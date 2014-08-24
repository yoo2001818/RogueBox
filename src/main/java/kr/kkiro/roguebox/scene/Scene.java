package kr.kkiro.roguebox.scene;

import java.awt.event.KeyEvent;

import kr.kkiro.roguebox.curses.CComponent;
import kr.kkiro.roguebox.curses.CContainer;
import kr.kkiro.roguebox.curses.CInteractable;
import kr.kkiro.roguebox.curses.IActionListener;
import kr.kkiro.roguebox.util.TermApplication;

public abstract class Scene implements IActionListener {

  protected TermApplication app;
  protected CInteractable focus;
  private boolean exitInput = false;
  
  public TermApplication getApp() {
    return app;
  }
  
  public void setApp(TermApplication app) {
    this.app = app;
  }
  
  public abstract void init();
  public void dispose() {
    exitInput = true;
  }
  
  public CInteractable getFocus() {
    return focus;
  }
  
  public void setFocus(CInteractable focus) {
    this.focus = focus;
  }
  
  public void startInput(CContainer root) {
    if(focus == null) focus = root.nextFocus();
    while(!exitInput) {
      root.render(app.getGraphics());
      int input = app.getTerminal().readChar();
      //System.out.println(input);
      if(focus.receiveKeyTyped(input)) continue;
      if(input == 0) continue;
      if(input == 9 || input == -KeyEvent.VK_RIGHT || input == -KeyEvent.VK_DOWN) {
        focus = root.nextFocus();
        continue;
      }
      if(input == 0x40000009 || input == -KeyEvent.VK_LEFT || input == -KeyEvent.VK_UP) {
        focus = root.prevFocus();
        continue;
      }
    }
  }
  
  @Override
  public boolean keyTyped(int code, CComponent object) {
    return false;
  }

}
