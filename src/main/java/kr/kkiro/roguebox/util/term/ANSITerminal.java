package kr.kkiro.roguebox.util.term;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import kr.kkiro.roguebox.TerminalPosition;
import kr.kkiro.roguebox.util.ANSIColor;

public class ANSITerminal extends Terminal {

  private static final char ESC = 0x1b;
  
  private ANSIColor currentBack = ANSIColor.BLACK;
  private ANSIColor currentFront = ANSIColor.WHITE;
  private TerminalPosition currentPos = new TerminalPosition();
  
  private InputStream in;
  private PrintStream out;
  
  private int width;
  private int height;
  
  public ANSITerminal(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
    
    String[] cmd = {"/bin/sh", "-c", "stty raw -echo isig</dev/tty"};
    try {
      Runtime.getRuntime().exec(cmd);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        String[] cmd = {"/bin/sh", "-c", "stty echo cooked</dev/tty"};
        try {
          Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        System.out.print(ESC);
        System.out.print("[0m");
        System.out.print(ESC);
        System.out.print("[2J");
        System.out.print(ESC);
        System.out.print("[1;1f");
        System.out.print(ESC);
        System.out.print("[?25h");
        System.out.println("RogueBox interrupted\n");
        System.out.flush();
      }
    });
    
    //Query size first
    /*out.print(ESC);
    out.print("[18t");
    try {
      while(true) {
        char readed = (char)in.read();
        out.print(readed);
        if(readed == '[') {
          StringBuilder readS = new StringBuilder();
          while(true) {
            readed = (char)in.read();
            if(readed != ';' && readed != 't') break;
            readS.append(readed);
          }
          if(!readS.toString().equals("8")) return;
          readS = new StringBuilder();
          while(true) {
            readed = (char)in.read();
            if(readed != ';' && readed != 't') break;
            readS.append(readed);
          }
          width = Integer.parseInt(readS.toString());
          readS = new StringBuilder();
          while(true) {
            readed = (char)in.read();
            if(readed != ';' && readed != 't') break;
            readS.append(readed);
          }
          height = Integer.parseInt(readS.toString());
          return;
        }
      }
    } catch (IOException e) {
      
    }*/
    width = 80;
    height = 24;
    /*
    while(s.next(ESC+"^\\[\\[8;") != null) {
      width = s.nextInt();
      s.next(";");
      height = s.nextInt();
      s.next("t");
    }*/
  }
  
  @Override
  public void scroll(int x, int y) {
    //x is unsupported; why even I need that?
    if(y > 0) {
      out.print(ESC);
      out.print("["+y+"S");
    } else {
      out.print(ESC);
      out.print("["+-y+"T");
    }
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public void writeChar(char c) {
    int charWidth = getSize(c);
    if(c == '\n') {
      cursorX = 0;
      cursorY += 1;
      if(cursorY >= height) {
        scroll(0,1);
        cursorY -= 1;
      }
      return;
    }
    if(cursorX >= width-charWidth+1) {
      cursorX = 0;
      cursorY += 1;
    }
    if(cursorX < 0) {
      cursorX = 0;
    }
    if(cursorY < 0) {
      cursorY = 0;
    }
    if(cursorY >= height) {
      scroll(0,1);
      cursorY -= 1;
    }
    if(currentBack != backColor) {
      out.print(ESC);
      out.print('[');
      out.print(backColor.getBackCode());
      out.print('m');
      currentBack = backColor;
    }
    if(currentFront != textColor) {
      out.print(ESC);
      out.print('[');
      out.print(textColor.getTextCode());
      out.print('m');
      currentFront = textColor;
    }
    if(currentPos.x != cursorX || currentPos.y != cursorY) {
      out.print(ESC);
      out.print('[');
      out.print(cursorY+1);
      out.print(';');
      out.print(cursorX+1);
      out.print('H');
      currentPos.x = cursorX;
      currentPos.y = cursorY;
    }
    out.print(c);
    currentPos.x = cursorX;
    currentPos.y = cursorY;
  }

  @Override
  public int getSize(String str) {
    int size = 0;
    for(int i = 0; i < str.length(); ++i) {
      size += getSize(str.charAt(i));
    }
    return size;
  }

  @Override
  public int getSize(char c) {
    return c > 0xFF ? 2 : 1;
  }

  @Override
  public int readChar(int timeout) {
    try {
      int charr = in.read();
      if(charr == 13) return '\n';
      if(charr == 10) return '\n';
      return charr;
    } catch (IOException e) {
      return 0;
    }
  }
  
  @Override
  public void setCursorVisible(boolean visible) {
    if(visible != showCursor) {
      if(visible) {
        out.print(ESC+"[?25h");
      } else {
        out.print(ESC+"[?25l");
      }
    }
    super.setCursorVisible(visible);
  }

}
