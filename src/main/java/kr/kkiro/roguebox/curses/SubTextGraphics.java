package kr.kkiro.roguebox.curses;

import kr.kkiro.roguebox.util.term.TerminalPosition;

public class SubTextGraphics extends TextGraphics {

  protected TextGraphics rootEntry;
  protected int x;
  protected int y;
  protected int w;
  protected int h;
  
  public SubTextGraphics(TextGraphics rootEntry, int x, int y, int w, int h) {
    this.rootEntry = rootEntry;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  
  protected boolean checkBounds(int x, int y) {
    return x >= 0 && x < w && y >= 0 && y < h;
  }
  
  @Override
  public void drawString(int x, int y, String string) {
    if(!checkBounds(x, y)) return;
    rootEntry.setBackColor(backColor);
    rootEntry.setTextColor(textColor);
    rootEntry.drawString(x + this.x, y + this.y, string);
  }
  
  @Override
  public void drawChar(int x, int y, char c) {
    if(!checkBounds(x, y)) return;
    rootEntry.setBackColor(backColor);
    rootEntry.setTextColor(textColor);
    rootEntry.drawChar(x + this.x, y + this.y, c);
  }

  @Override
  public void fillRect(int x, int y, int w, int h) {
    int mx = Math.max(x, 0);
    int my = Math.max(y, 0);
    int mw = Math.min(w, this.w);
    int mh = Math.min(h, this.h);
    rootEntry.setBackColor(backColor);
    rootEntry.setTextColor(textColor);
    rootEntry.fillRect(mx + this.x, my + this.y, mw, mh);
  }

  @Override
  public int getHeight() {
    return h;
  }

  @Override
  public int getWidth() {
    return w;
  }

  @Override
  public TerminalPosition localToGlobal(int x, int y) {
    TerminalPosition pos = rootEntry.localToGlobal(x, y);
    pos.x += this.x;
    pos.y += this.y;
    return pos;
  }

  @Override
  public int getSize(String str) {
    return rootEntry.getSize(str);
  }

  @Override
  public int getSize(char c) {
    return rootEntry.getSize(c);
  }
  
  @Override
  public void setCursorX(int x) {
    rootEntry.setCursorX(x + this.x);
    super.setCursorX(x);
  }
  
  @Override
  public void setCursorY(int y) {
    rootEntry.setCursorY(y + this.y);
    super.setCursorY(y);
  }
  
  @Override
  public void setCursorVisible(boolean visible) {
    rootEntry.setCursorVisible(visible);
    super.setCursorVisible(visible);
  }

}
