package kr.kkiro.roguebox.util.term;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

import kr.kkiro.roguebox.util.ANSIColor;
import kr.kkiro.roguebox.util.FontUtil;


public class GUITerminal extends Terminal {

  protected int width;
  protected int height;
  protected Dimension fontSize;
  protected Font font;
  protected TermCharacter[][] buffer;
  protected JFrame frame;
  protected TerminalRenderer renderer;
  protected FontMetrics fontMetrics;
  protected int blinkTimer;
  protected Thread renderThread;
  protected LinkedBlockingQueue<Integer> inputQueue = new LinkedBlockingQueue<Integer>();
  protected Object keyNotifier = new Object();
  protected float lagRate = 0.8f;
  
  public GUITerminal(String name) {
    this(name, 80, 24);
  }
  
  public GUITerminal(String name, int width, int height) {
    this(name, width, height, FontUtil.getPreferredFont());
  }
  
  public GUITerminal(String name, int width, int height, Font font) {
    
    this.width = width;
    this.height = height;
    buffer = new TermCharacter[height][width];
    for(int y = 0; y < height; ++y) {
      for(int x = 0; x < width; ++x) {
        buffer[y][x] = new TermCharacter();
        buffer[y][x].text = ANSIColor.WHITE;
        buffer[y][x].back = ANSIColor.BLACK;
        buffer[y][x].c = ' ';
      }
    }
    this.setFont(font);
    
    renderer = new TerminalRenderer();
    renderThread = new Thread(renderer);
    renderThread.start();
    
    frame = new JFrame(name);
    frame.setBackground(Color.BLACK);
    frame.setFocusTraversalKeysEnabled(false);
    //frame.setResizable(false);
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(renderer, BorderLayout.CENTER);
    frame.pack();
    frame.validate();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/roguebox.png")));
    frame.addKeyListener(renderer);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e)
      {
        renderer.halt = true;
          System.exit(0);
      }
    });
  }
  
  public void setName(String name) {
    frame.setTitle(name);
  }
  
  @Override
  public void setCursorX(int x) {
    blinkTimer = 0;
    super.setCursorX(x);
  }
  
  @Override
  public void setCursorY(int y) {
    blinkTimer = 0;
    super.setCursorY(y);
  }
  
  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }
  
  public Dimension getFontSize() {
    return fontSize;
  }
  
  public Dimension getTermSize() {
    return new Dimension(fontSize.width * width, fontSize.height * height);
  }
  
  public Font getFont() {
    return font;
  }
  
  public void setLagRate(float lagRate) {
    this.lagRate = lagRate;
  }
  
  public float getLagRate() {
    return lagRate;
  }
  
  public void setFont(Font font) {
    if(font == null) return;
    this.font = font;
    BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = bufferedImage.createGraphics();
    fontMetrics = graphics.getFontMetrics(font);
    fontSize = new Dimension(fontMetrics.charWidth('w'), fontMetrics.getHeight());
    if(frame != null) {
      frame.pack();
      frame.validate();
    }
  }
  
  @Override
  public void writeChar(char c) {
    int charWidth = Math.round((float)fontMetrics.charWidth(c) / fontSize.width);
    if(c >= 0x2500 && c <= 0x257F) charWidth = 1;
    if(c == '▋') charWidth = 1;
    blinkTimer = 0;
    if(c == '\n') {
      cursorX = 0;
      cursorY += 1;
      if(cursorY >= height) {
        scroll(0,1);
        cursorY -= 1;
      }
      return;
    }
    if(cursorX >= width-charWidth+1) {
      cursorX = 0;
      cursorY += 1;
    }
    if(cursorY >= height) {
      scroll(0,1);
      cursorY -= 1;
    }
    synchronized (buffer) {
      buffer[cursorY][cursorX].c = c;
      for(int i = 0; i < charWidth; ++i) {
        if(i != 0) buffer[cursorY][cursorX].c = ' ';
        buffer[cursorY][cursorX].back = backColor;
        buffer[cursorY][cursorX].text = textColor;
        cursorX += 1;
      }
    }
    try {
      if(Math.random() > lagRate)
      Thread.sleep(1 * Math.max(1,charWidth));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public int readChar(int timeout) {
    try {
      return inputQueue.poll(timeout, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      return 0;
    } catch (NullPointerException e) {
      return 0;
    }
  }

  @Override
  public void scroll(int x, int y) {
    boolean didShow = showCursor;
    showCursor = false;
    TermCharacter[][] newBuffer = new TermCharacter[height][width];
    for(int py = 0; py < height; ++py) {
      int newPos = py-y;
      newPos = newPos % height;
      if(newPos < 0) newPos = height + newPos;
      if(newPos >= height) newPos = newPos - height;
      TermCharacter[] newBufferX = new TermCharacter[width];
      for(int px = 0; px < width; ++px) {
        int newPosX = px-x;
        newPosX = newPosX % width;
        if(newPosX < 0) newPosX = width + newPosX;
        if(newPosX >= width) newPosX = newPosX - width;
        newBufferX[newPosX] = buffer[py][px].clone();
      }
      newBuffer[newPos] = newBufferX;
    }
    for(int py = 0; py < height; ++py) {
      boolean doClearLine = py >= height-y || py < -y;
      for(int px = 0; px < width; ++px) {
        if(doClearLine || (px >= width-x || px < -x)) {
          newBuffer[py][px].c = ' ';
          newBuffer[py][px].back = ANSIColor.BLACK;
          newBuffer[py][px].text = ANSIColor.WHITE;
        }
        setTextColor(newBuffer[py][px].text);
        setBackColor(newBuffer[py][px].back);
        setCursor(px, py);
        writeChar(newBuffer[py][px].c);
      }
    }
    showCursor = didShow;
    //buffer = newBuffer;
  }
  
  protected int getPosX(double x) {
    return (int)(fontSize.width * x);
  }
  
  protected int getPosY(double y) {
    return (int)(fontSize.height * y);
  }

  @Override
  public int getSize(String str) {
    return fontMetrics.stringWidth(str) / fontSize.width;
  }
  

  @Override
  public int getSize(char c) {
    return fontMetrics.charWidth(c) / fontSize.width;
  }
  
  protected class TerminalRenderer extends JComponent implements Runnable, KeyListener, MouseInputListener {
    
    public boolean halt = false;
    
    private static final long serialVersionUID = 8883428893505570764L;

    @Override
    public Dimension getPreferredSize() {
      return GUITerminal.this.getTermSize();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
      synchronized (buffer) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(font);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        blinkTimer ++;
        Dimension fontSize = GUITerminal.this.fontSize;
        //Draw background.
        for(int y = 0; y < height; ++y) {
          for(int x = 0; x < width; ++x) {
            g2d.setColor(buffer[y][x].back.getColor());
            g2d.fillRect(fontSize.width * x, fontSize.height * y, fontSize.width, fontSize.height);
          }
        }
        //Draw foreground.
        for(int y = 0; y < height; ++y) {
          for(int x = 0; x < width; ++x) {
            g2d.setColor(buffer[y][x].text.getColor());
            switch(buffer[y][x].c) {
              case '│':
                g2d.drawLine(getPosX(x+0.5), getPosY(y), getPosX(x+0.5), getPosY(y+1));
              break;
              case '─':
                g2d.drawLine(getPosX(x), getPosY(y+0.5), getPosX(x+1), getPosY(y+0.5));
              break;
              case '┐':
                g2d.drawLine(getPosX(x), getPosY(y+0.5), getPosX(x+0.5), getPosY(y+0.5));
                g2d.drawLine(getPosX(x+0.5), getPosY(y+0.5), getPosX(x+0.5), getPosY(y+1));
              break;
              case '┌':
                g2d.drawLine(getPosX(x+0.5), getPosY(y+0.5), getPosX(x+1), getPosY(y+0.5));
                g2d.drawLine(getPosX(x+0.5), getPosY(y+0.5), getPosX(x+0.5), getPosY(y+1));
              break;
              case '└':
                g2d.drawLine(getPosX(x+0.5), getPosY(y+0.5), getPosX(x+1), getPosY(y+0.5));
                g2d.drawLine(getPosX(x+0.5), getPosY(y), getPosX(x+0.5), getPosY(y+0.5));
              break;
              case '┘':
                g2d.drawLine(getPosX(x), getPosY(y+0.5), getPosX(x+0.5), getPosY(y+0.5));
                g2d.drawLine(getPosX(x+0.5), getPosY(y), getPosX(x+0.5), getPosY(y+0.5));
              break;
              case '┤':
                g2d.drawLine(getPosX(x), getPosY(y+0.5), getPosX(x+0.5), getPosY(y+0.5));
                g2d.drawLine(getPosX(x+0.5), getPosY(y), getPosX(x+0.5), getPosY(y+1));
              break;
              case '├':
                g2d.drawLine(getPosX(x+0.5), getPosY(y+0.5), getPosX(x+1), getPosY(y+0.5));
                g2d.drawLine(getPosX(x+0.5), getPosY(y), getPosX(x+0.5), getPosY(y+1));
              break;
              case '┬':
                g2d.drawLine(getPosX(x), getPosY(y+0.5), getPosX(x+1), getPosY(y+0.5));
                g2d.drawLine(getPosX(x+0.5), getPosY(y+0.5), getPosX(x+0.5), getPosY(y+1));
              break;
              case '┴':
                g2d.drawLine(getPosX(x), getPosY(y+0.5), getPosX(x+1), getPosY(y+0.5));
                g2d.drawLine(getPosX(x+0.5), getPosY(y), getPosX(x+0.5), getPosY(y+0.5));
              break;
              case '┼':
                g2d.drawLine(getPosX(x), getPosY(y+0.5), getPosX(x+1), getPosY(y+0.5));
                g2d.drawLine(getPosX(x+0.5), getPosY(y), getPosX(x+0.5), getPosY(y+1));
              break;
              case '╴':
                g2d.drawLine(getPosX(x), getPosY(y+0.5), getPosX(x+0.5), getPosY(y+0.5));
              break;
              case '╵':
                g2d.drawLine(getPosX(x+0.5), getPosY(y), getPosX(x+0.5), getPosY(y+0.5));
              break;
              case '╶':
                g2d.drawLine(getPosX(x+0.5), getPosY(y+0.5), getPosX(x+1), getPosY(y+0.5));
              break;
              case '╷':
                g2d.drawLine(getPosX(x+0.5), getPosY(y+0.5), getPosX(x+0.5), getPosY(y+1));
              break;
              case '▋':
                g2d.fillRect(getPosX(x)+1, getPosY(y), fontSize.width - 2, fontSize.height);
              break;
              default:
                g2d.drawString(Character.toString(buffer[y][x].c), fontSize.width * x, fontSize.height * (y+1) - fontMetrics.getDescent() - 1);
              break;
            }
          }
        }
        if(showCursor && (blinkTimer/30)%2 == 0) {
          cursorY = Math.max(0,Math.min(height-1,cursorY));
          cursorX = Math.max(0,Math.min(width-1,cursorX));
          TermCharacter cursorPos = buffer[cursorY][cursorX];
          g2d.setColor(new Color(255 - cursorPos.back.getColor().getRed(), 255 - cursorPos.back.getColor().getGreen(), 255 - cursorPos.back.getColor().getBlue()));
          g2d.fillRect(fontSize.width * cursorX, fontSize.height * cursorY, fontSize.width * GUITerminal.this.getSize(cursorPos.c), fontSize.height);
          //g2d.fillRect(fontSize.width * cursorX, fontSize.height * cursorY, 1, fontSize.height);
          g2d.setColor(new Color(255 - cursorPos.text.getColor().getRed(), 255 - cursorPos.text.getColor().getGreen(), 255 - cursorPos.text.getColor().getBlue()));
          g2d.drawString(Character.toString(cursorPos.c), fontSize.width * cursorX, fontSize.height * (cursorY+1) - fontMetrics.getDescent() - 1);
        }
      }
    }

    @Override
    public void run() {
      while(true) {
        if(halt) return;
        this.repaint();
        //24fps is enough
        try {
          Thread.sleep(1000/60);
        } catch (InterruptedException e) {
          break;
        }
      }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
      inputQueue.offer((int)(e.getKeyChar()) + (e.isShiftDown() ? 0x40000000 : 0));
    }

    @Override
    public void keyPressed(KeyEvent e) {
      /*if(e.isActionKey()) {
        inputQueue.offer(-e.getKeyCode());
      }*/
    }

    @Override
    public void keyReleased(KeyEvent e) {
      if(e.isActionKey()) {
        inputQueue.offer(-e.getKeyCode());
      }
    }
  }

}
