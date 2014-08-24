package kr.kkiro.roguebox.curses;

import kr.kkiro.roguebox.TerminalPosition;
import kr.kkiro.roguebox.util.ANSIColor;

public abstract class TextGraphics {
  
  protected ANSIColor backColor = ANSIColor.BLACK;
  protected ANSIColor textColor = ANSIColor.WHITE;
  protected int cursorX = 0;
  protected int cursorY = 0;
  protected boolean showCursor = true;
  
  public void setBackColor(ANSIColor backColor) {
    this.backColor = backColor;
  }
  
  public ANSIColor getBackColor() {
    return backColor;
  }
  
  public void setTextColor(ANSIColor textColor) {
    this.textColor = textColor;
  }
  
  public ANSIColor getTextColor() {
    return textColor;
  }
  
  public abstract int getSize(String str);
  public abstract int getSize(char c);
  
  public abstract void drawString(int x, int y, String string);
  public abstract void drawChar(int x, int y, char c);
  
  public abstract void fillRect(int x, int y, int w, int h);
  
  public void setCursorX(int x) {
    this.cursorX = Math.max(0,Math.min(getWidth()-1,x));
  }
  
  public int getCursorX() {
    return this.cursorX;
  }
  
  public void setCursorY(int y) {
    this.cursorY = Math.max(0,Math.min(getHeight()-1,y));
  }
  
  public int getCursorY() {
    return this.cursorY;
  }
  
  public void setCursorVisible(boolean visible) {
    showCursor = visible;
  }
  
  public boolean getCursorVisible() {
    return showCursor;
  }
  
  public void fillRect() {
    fillRect(0, 0, getWidth(), getHeight());
  }
  
  public abstract int getHeight();
  public abstract int getWidth();
  
  public TextGraphics subSet(int x, int y, int w, int h) {
    return new SubTextGraphics(this, x, y, w, h);
  }
  
  public abstract TerminalPosition localToGlobal(int x, int y);
}
