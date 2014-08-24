package kr.kkiro.roguebox.util;

import kr.kkiro.roguebox.curses.RootTextGraphics;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.scene.Scene;
import kr.kkiro.roguebox.util.term.Terminal;

public class TermApplication {
  protected Scene scene;
  protected Terminal terminal;
  protected TextGraphics graphics;
  
  public TermApplication(Terminal term) {
    terminal = term;
    graphics = new RootTextGraphics(terminal);
  }
  
  public void setScene(Scene scene) {
    if(this.scene != null) this.scene.dispose();
    this.scene = scene;
    scene.setApp(this);
    scene.init();
  }
  
  public Scene getScene() {
    return scene;
  }
  
  public Terminal getTerminal() {
    return terminal;
  }
  
  public TextGraphics getGraphics() {
    return graphics;
  }
  
}
