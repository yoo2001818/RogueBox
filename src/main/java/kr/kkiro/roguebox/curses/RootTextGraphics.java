package kr.kkiro.roguebox.curses;

import kr.kkiro.roguebox.util.term.Terminal;
import kr.kkiro.roguebox.util.term.TerminalPosition;

public class RootTextGraphics extends TextGraphics {

  protected Terminal term;
  
  public RootTextGraphics(Terminal term) {
    this.term = term;
    term.setCursorVisible(false);
  }
  
  public Terminal getTerm() {
    return term;
  }
  
  @Override
  public void drawString(int x, int y, String string) {
    term.setBackColor(backColor);
    term.setTextColor(textColor);
    term.setCursor(x, y);
    term.writeString(string);
  }
  
  @Override
  public void drawChar(int x, int y, char c) {
    term.setBackColor(backColor);
    term.setTextColor(textColor);
    term.setCursor(x, y);
    term.writeChar(c);
  }

  @Override
  public void fillRect(int x, int y, int w, int h) {
    term.setBackColor(backColor);
    term.setTextColor(textColor);
    term.setCursor(x, y);
    term.paintRect(x, y, w, h);
  }

  @Override
  public int getHeight() {
    return term.getHeight();
  }

  @Override
  public int getWidth() {
    return term.getWidth();
  }

  @Override
  public TerminalPosition localToGlobal(int x, int y) {
    TerminalPosition pos = new TerminalPosition();
    pos.x = x;
    pos.y = y;
    return pos;
  }

  @Override
  public int getSize(String str) {
    return term.getSize(str);
  }

  @Override
  public int getSize(char c) {
    return term.getSize(c);
  }
  
  @Override
  public void setCursorX(int x) {
    term.setCursorX(x);
    super.setCursorX(x);
  }
  
  @Override
  public void setCursorY(int y) {
    term.setCursorY(y);
    super.setCursorY(y);
  }
  
  @Override
  public void setCursorVisible(boolean visible) {
    term.setCursorVisible(visible);
    super.setCursorVisible(visible);
  }
  

}
