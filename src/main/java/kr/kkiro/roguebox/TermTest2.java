package kr.kkiro.roguebox;

import kr.kkiro.roguebox.curses.CBackground;
import kr.kkiro.roguebox.curses.CComponent;
import kr.kkiro.roguebox.curses.CInteractable;
import kr.kkiro.roguebox.curses.CLabel;
import kr.kkiro.roguebox.curses.CList;
import kr.kkiro.roguebox.curses.CSimpleButton;
import kr.kkiro.roguebox.curses.CWindow;
import kr.kkiro.roguebox.curses.IActionListener;
import kr.kkiro.roguebox.curses.ListItem;
import kr.kkiro.roguebox.curses.RootTextGraphics;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.util.term.BufferedTerminal;
import kr.kkiro.roguebox.util.term.GUITerminal;
import kr.kkiro.roguebox.util.term.Terminal;

public class TermTest2 {

  public static void main(String[] args) {
    GUITerminal guiTerm = new GUITerminal("RogueTerm");
    //ANSITerminal guiTerm = new ANSITerminal(System.in, System.out);
    Terminal term = new BufferedTerminal(guiTerm);
    //Terminal term = guiTerm;
    //guiTerm.setName("RogueBox");
    
    term.writeString("\n불러오는 중입니다. 잠시만 기다려 주세요...");
    waitSec(1000);
    TextGraphics graphics = new RootTextGraphics(term);
    CBackground background = new CBackground("RogueBox");
    
    background.setWidth(term.getWidth());
    background.setHeight(term.getHeight());
    
    background.render(graphics);
    waitFor(term);
    
    {
      CWindow window = new CWindow(40, 5, "RogueBox");
      background.placeCenter(window, graphics);
      
      CLabel label = new CLabel("RogueBox 버전 0.0.1");
      window.placeCenter(label, graphics);
      window.add(label);
      
      background.add(window);
      background.render(graphics);
      waitSec(2000);
      background.remove(window);
      background.render(graphics);
      waitSec(500);
    }
    {
      CWindow window = new CWindow(77, 19, "사용자 프로필 선택");
      background.placeCenter(window, graphics);
      
      CLabel label = new CLabel("RogueBox에 오신 것을 환영합니다! 로드할 게임 프로필을 선택해 주십시오.");
      label.x = 1;
      label.y = 0;
      window.add(label);
      
      CLabel label2 = new CLabel("게임 프로필 선택:");
      label2.x = 1;
      label2.y = 2;
      window.add(label2);
      
      CList list = new CList();
      
      final class DummyEntry implements ListItem {

        public String value;
        
        public DummyEntry(String value) {
          this.value = value;
        }
        
        @Override
        public String getLabel() {
          return value;
        }
        
        @Override
        public void action() {
        }
        
      }
      list.list.add(new DummyEntry("새 프로필 만들기"));
      list.x = 3;
      list.y = 4;
      list.setWidth(window.getWidth(graphics)-8);
      list.setHeight(window.getHeight(graphics)-8);
      window.add(list);
      
      CSimpleButton button = new CSimpleButton("확인");
      window.placeCenter(button, graphics);
      button.y = window.getHeight(graphics) - 3;
      window.add(button);
      button.setListener(new IActionListener() {
        
        @Override
        public boolean keyTyped(int code, CComponent obj) {
          if(code == '\n') {
          }
          return false;
        }
      });
      
      background.add(window);
      CInteractable focused;
      focused = background.nextFocus();
      while(true) {
        background.render(graphics);
        int input = term.readChar();
        if(input == 0) continue;
        if(input == 9) {
          focused = background.nextFocus();
          continue;
        }
        focused.receiveKeyTyped(input);
      }
    }
  }
  
  public static void waitSec(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
    }
  }
  
  public static void waitFor(Object o) {
    synchronized (o) {
      try {
        o.wait();
      } catch (InterruptedException e) {
      }
    }
  }

}
