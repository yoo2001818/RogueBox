package kr.kkiro.roguebox.game.item;

import java.util.ArrayList;
import java.util.List;

import kr.kkiro.roguebox.util.RandomProvider;

public class ItemPicker {

  public static int randomItem(int[] list) {
    return list[RandomProvider.getRandom().nextInt(list.length*100) / 100];
  }
  
  public static void chestOpen(Inventory e) {
    int times = RandomProvider.getRandom().nextInt(4) + 1;
    for(int i = 0; i < times; ++i) {
      if(RandomProvider.getRandom().nextInt(128) < 32) {
        List<Integer> intList = new ArrayList<Integer>();
        if(e.getStack(2) == null) intList.add(2);
        if(e.getStack(4) == null) intList.add(4);
        if(e.getStack(5) == null) intList.add(5);
        if(e.getStack(6) == null) intList.add(6);
        if(e.getStack(7) == null) intList.add(7);
        if(intList.size() >= 1) {
          e.obtainItem(intList.get(RandomProvider.getRandom().nextInt(intList.size())), 1);
          continue;
        }
      }
      e.obtainItem(ItemPicker.randomItem(new int[] {0, 0, 0, 1, 10, 11, 12, 13}), RandomProvider.getRandom().nextInt(3)+1);
    }
  }
  
  public static void ratKill(Inventory e) {
    e.obtainItem(9, RandomProvider.getRandom().nextInt(2)+1);
  }
  
  public static void spiderKill(Inventory e) {
    e.obtainItem(8, RandomProvider.getRandom().nextInt(2)+1);
  }
  
  public static void zombieKill(Inventory e) {
    e.obtainItem(ItemPicker.randomItem(new int[] {1, 6, 10, 11, 12, 13}), RandomProvider.getRandom().nextInt(2)+1);
  }

}
