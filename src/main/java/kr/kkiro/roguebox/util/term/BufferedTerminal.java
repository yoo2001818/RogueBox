package kr.kkiro.roguebox.util.term;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import kr.kkiro.roguebox.util.ANSIColor;

public class BufferedTerminal extends Terminal implements Runnable {

  protected TermCharacter[][] enterBuffer;
  protected TermCharacter[][] exitBuffer;
  protected LinkedBlockingQueue<Integer> inputQueue = new LinkedBlockingQueue<Integer>();
  
  protected boolean altered = false;
  protected boolean forceDraw = false;
  
  protected Object flushWaiter = new Object();
  
  protected Terminal term;
  
  public BufferedTerminal(Terminal term) {
    this.term = term;
    Thread thread = new Thread(this);
    thread.start();
    Runnable inputRunnable = new Runnable() {
      @Override
      public void run() {
        while(true) {
          BufferedTerminal.this.inputQueue.offer(BufferedTerminal.this.term.readChar());
          try {
            Thread.sleep(1000/60);
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    };
    Thread inputThread = new Thread(inputRunnable);
    inputThread.start();
    enterBuffer = new TermCharacter[getHeight()][getWidth()];
    exitBuffer = new TermCharacter[getHeight()][getWidth()];
    for(int y = 0; y < getHeight(); ++y) {
      for(int x = 0; x < getWidth(); ++x) {
        enterBuffer[y][x] = new TermCharacter();
        enterBuffer[y][x].text = ANSIColor.WHITE;
        enterBuffer[y][x].back = ANSIColor.BLACK;
        enterBuffer[y][x].c = ' ';
        exitBuffer[y][x] = new TermCharacter();
        exitBuffer[y][x].text = ANSIColor.WHITE;
        exitBuffer[y][x].back = ANSIColor.BLACK;
        exitBuffer[y][x].c = ' ';
      }
    }
  }
  
  @Override
  public void scroll(int x, int y) {
    boolean didShow = showCursor;
    showCursor = false;
    TermCharacter[][] newBuffer = new TermCharacter[getHeight()][getWidth()];
    for(int py = 0; py < getHeight(); ++py) {
      int newPos = py-y;
      newPos = newPos % getHeight();
      if(newPos < 0) newPos = getHeight() + newPos;
      if(newPos >= getHeight()) newPos = newPos - getHeight();
      TermCharacter[] newBufferX = new TermCharacter[getWidth()];
      for(int px = 0; px < getWidth(); ++px) {
        int newPosX = px-x;
        newPosX = newPosX % getWidth();
        if(newPosX < 0) newPosX = getWidth() + newPosX;
        if(newPosX >= getWidth()) newPosX = newPosX - getWidth();
        newBufferX[newPosX] = exitBuffer[py][px].clone();
      }
      newBuffer[newPos] = newBufferX;
    }
    for(int py = 0; py < getHeight(); ++py) {
      boolean doClearLine = py >= getHeight()-y || py < -y;
      for(int px = 0; px < getWidth(); ++px) {
        if(doClearLine || (px >= getWidth()-x || px < -x)) {
          newBuffer[py][px].c = ' ';
          newBuffer[py][px].back = ANSIColor.BLACK;
          newBuffer[py][px].text = ANSIColor.WHITE;
        }
        exitBuffer = newBuffer;
        /*setTextColor(newBuffer[py][px].text);
        setBackColor(newBuffer[py][px].back);
        setCursor(px, py);
        writeChar(newBuffer[py][px].c);*/
      }
    }
    showCursor = didShow;
  }

  public LinkedBlockingQueue<Integer> getQueue() {
    return inputQueue;
  }
  
  public void offerKey(int key) {
    inputQueue.offer(key);
  }
  
  @Override
  public int getWidth() {
    return term.getWidth();
  }

  @Override
  public int getHeight() {
    return term.getHeight();
  }

  @Override
  public void writeChar(char c) {
    altered = true;
    int charWidth = getSize(c);
    if(c >= 0x2500 && c <= 0x257F) charWidth = 1;
    if(c == '\n') {
      cursorX = 0;
      cursorY += 1;
      if(cursorY >= getHeight()) {
        scroll(0,1);
        cursorY -= 1;
      }
      return;
    }
    if(cursorX >= getWidth()-charWidth+1) {
      cursorX = 0;
      cursorY += 1;
    }
    if(cursorY >= getHeight()) {
      scroll(0,1);
      cursorY -= 1;
    }
    exitBuffer[cursorY][cursorX].c = c;
    for(int i = 0; i < charWidth; ++i) {
      if(i != 0) exitBuffer[cursorY][cursorX].c = ' ';
      exitBuffer[cursorY][cursorX].back = backColor;
      exitBuffer[cursorY][cursorX].text = textColor;
      cursorX += 1;
    }
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
  public int readChar(int timeout) {
    try {
      return inputQueue.poll(timeout, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      return 0;
    }
  }
  
  public void redraw() {
    forceDraw = true;
    altered = true;
    flush();
    forceDraw = false;
  }
  
  @Override
  public void clear() {
    super.clear();
    forceDraw = true;
  }
  
  public void flush() {
    synchronized (this) {
      if(altered) {
        if(altered) term.setCursorVisible(false);
        altered = false;
        for(int y = 0; y < getHeight(); ++y) {
          for(int x = 0; x < getWidth(); ++x) {
            if(altered) {
              altered = false;
              y = 0;
              x = 0;
            }
            if(!exitBuffer[y][x].equals(enterBuffer[y][x]) || forceDraw) {
              enterBuffer[y][x] = exitBuffer[y][x].clone();
              term.setBackColor(enterBuffer[y][x].back);
              term.setTextColor(enterBuffer[y][x].text);
              term.setCursor(x,y);
              term.writeChar(enterBuffer[y][x].c);
            }
          }
        }
      }
      if(getCursorX() != term.getCursorX() || getCursorY() != term.getCursorY() || getCursorVisible() != term.getCursorVisible()) {
        term.setCursorVisible(getCursorVisible());
        term.setCursorX(getCursorX());
        term.setCursorY(getCursorY());
      }
      forceDraw = false;
      this.notifyAll();
    }
  }

  @Override
  public void run() {
    while(true) {
      try {
        Thread.sleep(1000/60);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      flush();
    }
  }

}
