package kr.kkiro.roguebox.util.term;

import java.awt.event.KeyEvent;

import kr.kkiro.roguebox.util.ANSIColor;

public abstract class Terminal {
  
  public static char ESCAPE = 0x1b;
  
  protected int cursorX = 0;
  protected int cursorY = 0;
  
  protected ANSIColor textColor = ANSIColor.WHITE;
  protected ANSIColor backColor = ANSIColor.BLACK;
  protected boolean showCursor = true;
  
  public void setTextColor(ANSIColor color) {
    if(color == null) return;
    this.textColor = color;
  }
  public ANSIColor getTextColor() {
    return this.textColor;
  }
  public void setBackColor(ANSIColor color) {
    if(color == null) return;
    this.backColor = color;
  }
  public ANSIColor getBackColor() {
    return this.backColor;
  }
  
  public void setCursor(int x, int y) {
    setCursorX(x);
    setCursorY(y);
  }
  
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
  
  public abstract void scroll(int x, int y);
  
  public abstract int getWidth();
  public abstract int getHeight();
  
  public void writeString(String string) {
    for(int i = 0; i < string.length(); ++i) {
      writeChar(string.charAt(i));
    }
  }
  
  public abstract void writeChar(char c);
  
  public void clear() {
    setBackColor(ANSIColor.BLACK);
    setTextColor(ANSIColor.WHITE);
    for(int y = 0; y < getHeight(); ++y) {
      for(int x = 0; x < getWidth(); ++x) {
        setCursor(x, y);
        writeChar(' ');
      }
    }
    cursorX = 0;
    cursorY = 0;
  }
  
  public void paintRect(int x, int y, int w, int h) {
    int beforeX = cursorX;
    int beforeY = cursorY;
    setBackColor(backColor);
    setTextColor(textColor);
    for(int py = y; py < y+h; ++py) {
      for(int px = x; px < x+w; ++px) {
        setCursor(px, py);
        writeChar(' ');
      }
    }
    cursorX = beforeX;
    cursorY = beforeY;
  }

  public void paintLine() {
    int beforeX = cursorX;
    int beforeY = cursorY;
    setBackColor(backColor);
    setTextColor(textColor);
    for(int x = 0; x < getWidth(); ++x) {
      setCursor(x, beforeY);
      writeChar(' ');
    }
    cursorX = beforeX;
    cursorY = beforeY;
  }
  
  
  public abstract int getSize(String str);
  public abstract int getSize(char c);
  //public abstract void writeBitmap
  
  public abstract int readChar(int timeout);
  
  public int readChar() {
    return readChar(Integer.MAX_VALUE);
  }
  
  public String readLine() {
    StringBuilder sb = new StringBuilder();
    int position = 0;
    int displayPosition = 0;
    int startPosition = cursorX;
    while(true) {
      int input = readChar();
      if(input < 0) {
        switch(-input) {
          case KeyEvent.VK_LEFT:
            position -= 1;
            if(position < 0) position = 0;
            break;
          case KeyEvent.VK_RIGHT:
            position += 1;
            if(position > sb.length()) position = sb.length();
            break;
          case KeyEvent.VK_HOME:
            position = 0;
            break;
          case KeyEvent.VK_END:
            position = sb.length();
            break;
        }
        displayPosition = getSize(sb.substring(0, Math.min(Math.max(0,sb.length()),position)));
        setCursorX(startPosition+displayPosition);
      } else {
        if(input == '\n') {
          writeChar('\n');
          break;
        }
        if(input == 127) {
          if(sb.length() <= position) continue;
          sb.deleteCharAt(position);
          displayPosition = getSize(sb.substring(0, Math.min(Math.max(0,sb.length()),position)));
          setCursorX(startPosition+displayPosition);
          writeString(sb.substring(position));
          writeChar(' ');
          setCursorX(startPosition+displayPosition);
          continue;
        }
        if(input == 8) {
          if(position == 0) continue;
          sb.deleteCharAt(position-1);
          position -= 1;
          displayPosition = getSize(sb.substring(0, Math.min(Math.max(0,sb.length()),position)));
          setCursorX(startPosition+displayPosition);
          writeString(sb.substring(position));
          writeChar(' ');
          setCursorX(startPosition+displayPosition);
          continue;
        }
        if(Character.isIdentifierIgnorable(input)) {
          continue;
        }
        sb.insert(position, (char)input);
        writeString(sb.substring(position));
        position += 1;
        displayPosition = getSize(sb.substring(0, Math.min(Math.max(0,sb.length()),position)));
        setCursorX(startPosition+displayPosition);
      }
    }
    return sb.toString();
  }
  
  //public abstract char readChar();
}
