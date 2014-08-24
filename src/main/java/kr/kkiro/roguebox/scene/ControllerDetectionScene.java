package kr.kkiro.roguebox.scene;

import static kr.kkiro.roguebox.util.I18n._;
import kr.kkiro.roguebox.curses.CBackground;
import kr.kkiro.roguebox.curses.CComponent;
import kr.kkiro.roguebox.curses.CLabel;
import kr.kkiro.roguebox.curses.CSimpleButton;
import kr.kkiro.roguebox.curses.CWindow;
import kr.kkiro.roguebox.curses.TextGraphics;
import kr.kkiro.roguebox.util.ControllerReader;
import kr.kkiro.roguebox.util.term.BufferedTerminal;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class ControllerDetectionScene extends Scene {
  
  @Override
  public boolean keyTyped(int code, CComponent object) {
    
    if(code == 10) {
      getApp().setScene(new ProfileSelectionScene());
      return true;
    }
    return false;
  }

  @Override
  public void init() {
    Controller activeGamePad = null;
    try {
      ControllerEnvironment environment = ControllerEnvironment.getDefaultEnvironment();
      Controller[] controllers = environment.getControllers();
      for(Controller controller : controllers) {
        if(controller.getType() == Controller.Type.GAMEPAD) {
          activeGamePad = controller;
        }
      }
    } catch (Exception e) {
      
    }
    
    if(getApp().getTerminal() instanceof BufferedTerminal) {
      BufferedTerminal term = (BufferedTerminal)(getApp().getTerminal());
      term.redraw();
    } 
    
    if(activeGamePad != null) {
      TextGraphics g = getApp().getGraphics();
      CBackground background = new CBackground(_("gameName"));
      
      String message = _("controllerDetected",activeGamePad.getName());
      
      int winSize = g.getSize(message)+4;
      
      CWindow window = new CWindow(winSize, 7,  _("controllerDetectedName"));
      background.placeCenter(window, g);
      background.add(window);
      
      CLabel label = new CLabel(message);
      window.add(label);
      window.placeCenter(label, g);
      label.y = 1;
      
      CLabel label2 = new CLabel(_("controllerDetectedUsage"));
      if(getApp().getTerminal() instanceof BufferedTerminal) {
        BufferedTerminal term = (BufferedTerminal)(getApp().getTerminal());
        new ControllerReader(activeGamePad, term);
      } else {
        label2.text = _("controllerDetectedFail");
      }
      window.add(label2);
      window.placeCenter(label2, g);
      label2.y = 2;
      
      CSimpleButton confirm = new CSimpleButton(_("confirm"));
      window.add(confirm);
      window.placeCenter(confirm, g);
      confirm.y = 4;
      confirm.setListener(this);
      
      background.render(g);
      
      startInput(background);
    } else {
      getApp().setScene(new ProfileSelectionScene());
    }
  }

  @Override
  public void dispose() {
    super.dispose();
  }

}
