package kr.kkiro.roguebox.game.item;

import kr.kkiro.roguebox.util.RandomProvider;

public class ItemPicker {

  public static int randomItem(int[] list) {
    return list[RandomProvider.getRandom().nextInt(list.length*100) / 100];
  }

}
