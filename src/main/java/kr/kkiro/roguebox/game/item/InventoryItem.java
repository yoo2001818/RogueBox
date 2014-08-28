package kr.kkiro.roguebox.game.item;

public abstract class InventoryItem {

  protected int quantity = 0;
  
  public InventoryItem(int quantity) {
    this.quantity = quantity;
  }
  
  public boolean isWasteable() {
    return true;
  }
  
  public int getQuantity() {
    return quantity;
  }
  
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  
  public void useQuantity(int quantity) {
    this.quantity -= quantity;
  }
  
}
