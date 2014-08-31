package kr.kkiro.roguebox.game.item;

import kr.kkiro.roguebox.util.RandomProvider;

public class ItemPicker {

  public static int randomItem(int[] list) {
    return list[RandomProvider.getRandom().nextInt(list.length*100) / 100];
  }
  
  public static void chestOpen(Inventory e) {
    int times = RandomProvider.getRandom().nextInt(4) + 1;
    for(int i = 0; i < times; ++i) {
      e.obtainItem(ItemPicker.randomItem(new int[] {0, 0, 0, 1, 2, 4, 5, 6, 7, 10, 11, 12, 13}), RandomProvider.getRandom().nextInt(3)+1);
    }
  }
  
  public static void ratKill(Inventory e) {
    e.obtainItem(9, RandomProvider.getRandom().nextInt(2)+1);
  }
  
  public static void spiderKill(Inventory e) {
    e.obtainItem(8, RandomProvider.getRandom().nextInt(2)+1);
  }
  
  public static void zombieKill(Inventory e) {
    e.obtainItem(ItemPicker.randomItem(new int[] {1, 6, 7, 10, 11, 12, 13}), RandomProvider.getRandom().nextInt(2)+1);
  }

}
