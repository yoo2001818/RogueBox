package kr.kkiro.roguebox.curses;


public abstract class CComponent {
  public int x = 0;
  public int y = 0;
  public boolean visible = true;
  
  protected int width;
  protected int height;
  protected CContainer parent;
  
  public int getX() {
    return x;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public int getY() {
    return y;
  }
  
  public void setY(int y) {
    this.y = y;
  }
  
  public int getWidth(TextGraphics g) {
    return width;
  }
  
  public void setWidth(int width) {
    this.width = width;
  }
  
  public int getHeight(TextGraphics g) {
    return height;
  }
  
  public void setHeight(int height) {
    this.height = height;
  }
  
  public abstract void render(TextGraphics g);
  
  public CContainer getParent() {
    return parent;
  }
  
  public void setParent(CContainer parent) {
    this.parent = parent;
  }
  
  public void setVisible(boolean visible) {
    this.visible = visible;
  }
  
  public boolean isVisible() {
    return visible;
  }
  
}
