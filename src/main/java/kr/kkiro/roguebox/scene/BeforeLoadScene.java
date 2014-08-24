package kr.kkiro.roguebox.scene;

import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.term.Terminal;

public class BeforeLoadScene extends Scene {

  public BeforeLoadScene() {
  }

  @Override
  public void init() {
    Terminal term = getApp().getTerminal();
    term.clear();
    term.setCursor(0, 0);
    term.setBackColor(ANSIColor.BLACK);
    term.setTextColor(ANSIColor.GRAY);
    term.setCursorVisible(true);
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
    }
    term.writeString("Starting virtual terminal..."); 
    try {
      Thread.sleep(700);
    } catch (InterruptedException e) {
    }
    term.writeString("\nRogueBox starting up...");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
    term.setCursorVisible(false);
    getApp().setScene(new LoadScene());
  }

}
