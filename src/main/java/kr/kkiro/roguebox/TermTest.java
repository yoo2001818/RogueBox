package kr.kkiro.roguebox;

import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.term.BufferedTerminal;
import kr.kkiro.roguebox.util.term.GUITerminal;
import kr.kkiro.roguebox.util.term.Terminal;

public class TermTest {

  public static void main(String[] args) {
    GUITerminal guiTerm = new GUITerminal("dd");
    Terminal term = new BufferedTerminal(guiTerm);
    guiTerm.setName("RogueBox");
    term.setCursor(0, term.getHeight()-1);
    term.writeString("잠시만 기다려 주십시오...\n");
    term.setCursorVisible(true);
    /*while(true) {
      String line = term.readLine();
      if(line.isEmpty()) break;
    }*/
    resetScreen(term);
    try {
      synchronized(term) {
        term.wait();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    String message = "RogueBox 버전 0.0.1";
    int messageSize = term.getSize(message)+2;
    drawWindow(term, messageSize+2, 5, "RogueBox");
    term.setTextColor(ANSIColor.BLACK);
    term.setBackColor(ANSIColor.GRAY);
    term.setCursor(term.getWidth()/2 - (messageSize+2)/2+1, term.getHeight()/2 - (5/2)+1);
    term.writeString(message);
    term.setCursorVisible(false);
    try {
      synchronized(term) {
        term.wait();
      }
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    resetScreen(term);
    try {
      synchronized(term) {
        term.wait();
      }
      Thread.sleep(300);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    clearWindow(term, messageSize+2, 5);
    drawWindow(term, term.getWidth()-4, term.getHeight()-5, "사용자 프로필 선택");
    term.setTextColor(ANSIColor.BLACK);
    term.setBackColor(ANSIColor.GRAY);
    term.setCursor(getCenterPosX(term, term.getWidth()-4)+2, getCenterPosY(term, term.getHeight()-5)+1);
    term.writeString("RogueBox에 오신 것을 환영합니다! 로드할 게임 프로필을 선택해 주십시오.");
    term.setCursor(getCenterPosX(term, term.getWidth()-4)+2, getCenterPosY(term, term.getHeight()-5)+3);
    term.writeString("게임 프로필 선택:");
    term.setTextColor(ANSIColor.GRAY);
    term.setBackColor(ANSIColor.BLUE);
    term.paintRect(getCenterPosX(term, term.getWidth()-4)+6, getCenterPosY(term, term.getHeight()-5)+5, term.getWidth()-4-11, term.getHeight()-4-8);
    term.setCursor(getCenterPosX(term, term.getWidth()-4)+6, getCenterPosY(term, term.getHeight()-5)+5);
    term.writeString("[*] 홍길동 (2014/07/31 16:57)");
    term.setCursor(getCenterPosX(term, term.getWidth()-4)+6, getCenterPosY(term, term.getHeight()-5)+5+1);
    term.writeString("[ ] 테스트 (2014/07/31 16:27)");
    term.setCursor(getCenterPosX(term, term.getWidth()-4)+6, getCenterPosY(term, term.getHeight()-5)+5+2);
    term.writeString("[ ] 테스트 (2014/07/31 16:15)");
    term.setCursor(getCenterPosX(term, term.getWidth()-4)+6, getCenterPosY(term, term.getHeight()-5)+5+3);
    term.writeString("[ ] 새 프로필 만들기");
    /*try {
      synchronized(term) {
        term.wait();
      }
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    term.scroll(0,1);
    term.setCursor(0, term.getHeight()-1);
    term.setTextColor(ANSIColor.WHITE);
    term.setBackColor(ANSIColor.BLACK);
    term.writeString("명령어 쉘을 실행합니다.\n언제든지 다시 돌아가려면 exit 명령어를 실행해 주세요.");
    term.scroll(0,1);
    term.setCursor(0, term.getHeight()-1);
    while(true) {
      term.writeString("명령어 쉘 > ");
      term.setCursorVisible(true);
      term.readLine();
    }*/
  }
  
  public static int getCenterPosX(Terminal term, int w) {
    return (int)(term.getWidth()/2f - w/2f);
  }
  
  public static int getCenterPosY(Terminal term, int h) {
    return (int)(term.getHeight()/2f - h/2f);
  }
  
  public static void resetScreen(Terminal term) {
    term.setTextColor(ANSIColor.GRAY);
    term.setBackColor(ANSIColor.BLUE);
    term.paintRect(0, 0, term.getWidth(), term.getHeight());
    term.setCursor(0, 0);
    term.writeString("RogueBox");
    term.setCursor(0, term.getHeight()-1);
    term.setTextColor(ANSIColor.BLACK);
    term.setBackColor(ANSIColor.GRAY);
    term.paintLine();
    term.writeString(" ENTER=확인  TAB=선택  ESC=취소");
    term.setTextColor(ANSIColor.GRAY);
    term.setBackColor(ANSIColor.BLUE);
    //term.writeString("버전 0.0.1 (c) yoo2001818");
  }
  
  public static void clearWindow(Terminal term, int w, int h) {
    term.setCursorVisible(true);
    term.setTextColor(ANSIColor.GRAY);
    term.setBackColor(ANSIColor.BLUE);
    term.paintRect((int)(term.getWidth()/2f - w/2f), (int)(term.getHeight()/2f - h/2f), w+1, h+1);
    term.setCursorVisible(false);
  }
  
  public static void drawWindow(Terminal term, int w, int h, String name) {
    drawWindow(term, (int)(term.getWidth()/2f - w/2f), (int)(term.getHeight()/2f - h/2f), w, h, name);
  }
  
  public static void drawWindow(Terminal term, int x, int y, int w, int h, String name) {
    term.setCursorVisible(true);
    term.setTextColor(ANSIColor.WHITE);
    term.setBackColor(ANSIColor.BLACK);
    term.paintRect(x+1, y+1, w, h);
    term.setTextColor(ANSIColor.BLACK);
    term.setBackColor(ANSIColor.GRAY);
    term.paintRect(x, y, w, h);
    drawBox(term, x, y, w, h);
    int titleSize = term.getSize(name);
    int centerPos = (int)(((w) / 2f - titleSize / 2f) + x);
    term.setCursor(centerPos-1, y);
    term.writeChar('┤');
    term.setCursor(centerPos+titleSize, y);
    term.writeChar('├');
    term.setTextColor(ANSIColor.RED);
    term.setCursor(centerPos, y);
    term.writeString(name);
    term.setCursorVisible(false);
  }
  
  public static void drawBox(Terminal term, int x, int y, int w, int h) {
    StringBuilder textBuilder = new StringBuilder();
    textBuilder.append('┌');
    for(int i = x+1; i < x+w-1; ++i) textBuilder.append('─');
    textBuilder.append('┐');
    term.setCursor(x, y);
    term.writeString(textBuilder.toString());
    for(int i = y+1; i < y+h-1; ++i) {
      term.setCursor(x, i);
      term.writeChar('│');
      term.setCursor(x+w-1, i);
      term.writeChar('│');
    }
    textBuilder.replace(0, 1, "└");
    textBuilder.replace(textBuilder.length()-1, textBuilder.length(), "┘");
    term.setCursor(x, y+h-1);
    term.writeString(textBuilder.toString());
  }

}
