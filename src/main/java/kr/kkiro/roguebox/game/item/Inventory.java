package kr.kkiro.roguebox.game.item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
  
  protected List<ItemType> contents;
  protected ItemBank bank;
  
  public Inventory(ItemBank bank) {
    this.bank = bank;
    contents = new ArrayList<ItemType>();
  }
  
  public ItemBank getBank() {
    return bank;
  } 

}
