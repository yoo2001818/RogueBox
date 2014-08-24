package kr.kkiro.roguebox.util;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import kr.kkiro.roguebox.util.term.BufferedTerminal;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Component.Identifier;

public class ControllerReader implements Runnable {

  protected Map<Component, KeyReading> preMap = new HashMap<Component, KeyReading>();
  protected Controller gamePad;
  protected BufferedTerminal term;
  protected int rumbleValue = 0;
  
  public ControllerReader(Controller gamePad, BufferedTerminal term) {
    this.gamePad = gamePad;
    this.term = term;
    Thread thread = new Thread(this);
    thread.start();
  }
  
  @Override
  public void run() {
    while(true) {
      gamePad.poll();
      handleKey(gamePad.getComponent(Identifier.Button.A), '\n');
      handleKey(gamePad.getComponent(Identifier.Button.B), 0x1B);
      handleAxis(gamePad.getComponent(Identifier.Axis.X), -KeyEvent.VK_LEFT, -KeyEvent.VK_RIGHT);
      handleAxis(gamePad.getComponent(Identifier.Axis.Y), -KeyEvent.VK_UP, -KeyEvent.VK_DOWN);
      if(gamePad.getRumblers().length >= 1) {
        if(rumbleValue > 16) {
          gamePad.getRumblers()[0].rumble(rumbleValue / 256f);
        } else if(rumbleValue == 0) {
          gamePad.getRumblers()[0].rumble(-1);
        }
      }
      rumbleValue -= 32;
      try {
        Thread.sleep(1000/30);
      } catch (InterruptedException e) {
      }
    }
  }
  
  protected void handleKey(Component c, int key) {
    if(!preMap.containsKey(c)) {
      preMap.put(c, new KeyReading());
    }
    float reading = c.getPollData() >= 0.5f ? 1 : 0;
    if(reading != preMap.get(c).reading) {
      preMap.get(c).reading = reading;
      preMap.get(c).time = System.currentTimeMillis()+1000;
      if(reading >= 0.5) {
        rumbleValue = 256;
        term.offerKey(key);
      }
    }
    if(reading >= 0.5 && preMap.get(c).time <= System.currentTimeMillis()) {
      term.offerKey(key);
      preMap.get(c).time = System.currentTimeMillis() + 40;
    }
  }
  
  protected void handleAxis(Component c, int minKey, int maxKey) {
    if(!preMap.containsKey(c)) {
      preMap.put(c, new KeyReading());
    }
    float reading = c.getPollData() >= 0.5f ? 1 : (c.getPollData() <= -0.5f ? -1 : 0);
    if(reading != preMap.get(c).reading) {
      preMap.get(c).reading = reading;
      preMap.get(c).time = System.currentTimeMillis()+1000;
      if(reading >= 0.5f) {
        term.offerKey(maxKey);
        rumbleValue = 256;
      } else if(reading <= -0.5f) {
        term.offerKey(minKey);
        rumbleValue = 256;
      }
    }
    if(preMap.get(c).time <= System.currentTimeMillis()) {
      if(reading >= 0.5f) {
        term.offerKey(maxKey);
      } else if(reading <= -0.5f) {
        term.offerKey(minKey);
      } 
      preMap.get(c).time = System.currentTimeMillis() + 40;
    }
  }
  
  public static class KeyReading {
    public float reading;
    public long time;
  }

}
