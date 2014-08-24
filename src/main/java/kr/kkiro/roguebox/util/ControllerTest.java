package kr.kkiro.roguebox.util;


import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Rumbler;

public class ControllerTest {

  public static void main(String[] args) throws InterruptedException {
    ControllerEnvironment environment = ControllerEnvironment.getDefaultEnvironment();
    Controller[] controllers = environment.getControllers();
    for(int i = 0; i<controllers.length; ++i) {
      System.out.println(controllers[i].getType().toString()+": "+controllers[i].getName() + " on Port "+controllers[i].getPortNumber());
      Controller controller = controllers[i];
      if(!controller.getType().equals(Type.GAMEPAD)) {
        System.out.println("Skipping non-gamepad controller");
        continue;
      }
      Component[] components = controller.getComponents();
      System.out.println(" -- Components --");
      for(int j = 0; j < components.length; ++j) {
        Component component = components[j];
        System.out.println(j +" - "+component.getName() +" - "+(component.isAnalog() ? "Analog" : "Digital") +" - "+component.getPollData());
      }
      Rumbler[] rumblers = controller.getRumblers();
      System.out.println(" -- Rumblers --");
      for(int j = 0; j < rumblers.length; ++j) {
        Rumbler rumbler = rumblers[j];
        System.out.println(j + " - "+rumbler.getAxisName());
      }
      Component analog1, analog2, digitswitch, digittoggle;
      System.out.println("Press key for Rumble key");
      analog1 = detectComp(controller);
      System.out.println("Press key for another Rumble key");
      analog2 = detectComp(controller);
      System.out.println("Press key for Rumbler Switch key");
      digitswitch = detectComp(controller);
      System.out.println("Press key for Rumbler Toggle key");
      digittoggle = detectComp(controller);
      System.out.println("Started!");
      int pointer = 0;
      boolean[] enabled = new boolean[rumblers.length];
      try {
        float previous = 0f;
        float previous2 = 0f;
        while(true) {
          controller.poll();
          float value = Math.min(1.99f, Math.abs(analog1.getPollData()) *2 + Math.abs(analog2.getPollData()) *2);
          for(int k = 0; k < rumblers.length; ++k) {
            if(enabled[k]) rumblers[k].rumble(Math.abs(value));
          }
          if(previous != digitswitch.getPollData()) {
            previous = digitswitch.getPollData();
            if(previous >= 0.5f) {
              pointer ++;
              if(pointer >= rumblers.length) {
                pointer = 0;
              }
              System.out.println("Selecting rumbler "+pointer);
            }
          }
          if(previous2 != digittoggle.getPollData()) {
            previous2 = digittoggle.getPollData();
            if(previous2 >= 0.5f) {
              enabled[pointer] = !enabled[pointer];
              System.out.println("Rumbler "+pointer+" is now "+(enabled[pointer] ? "enabled" : "disabled"));
            }
          }
          Thread.sleep(20);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  public static Component detectComp(Controller controller) {
    Component[] components = controller.getComponents();
    float[] toggle = new float[components.length];
    controller.poll();
    for(int j = 0; j < components.length; ++j) {
      Component component = components[j];
      toggle[j] = component.getPollData();
    }
    Component chosen;
    label1:
    while(true) {
      controller.poll();
      for(int j = 0; j < components.length; ++j) {
        Component component = components[j];
        if(Math.abs(component.getPollData() - toggle[j]) > 0.3) {
          System.out.println(j +" - "+component.getName() +" - "+(component.isAnalog() ? "Analog" : "Digital") +" - "+component.getPollData() + " Selected");
          System.out.println("Now, please release it");
          chosen = component;
          break label1;
        }
        toggle[j] = component.getPollData();
      }
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
      }
    }
    while(true) {
      controller.poll();
      if(Math.abs(chosen.getPollData()) < 0.2) return chosen;
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
      }
    }
  }

}
