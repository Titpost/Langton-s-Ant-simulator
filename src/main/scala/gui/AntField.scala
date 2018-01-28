package gui

import java.awt.{Color, Graphics2D}

import scala.swing.{Dimension, MainFrame, Panel, Rectangle, SimpleSwingApplication}

/**
 * @author Tit on 27.01.2018
 */
object AntField extends SimpleSwingApplication {

  private val dim = 1000
  private val field = Array.ofDim[State](dim, dim)
  def at(x: Int, y: Int): State = {
    if (null == field(x)(y)) {
      field(x)(y) = new State()
    }
    field(x)(y)
  }

  def top: MainFrame = new MainFrame {
    minimumSize = new Dimension( dim, dim )
    title = "Langton’s Ant simulator"
    contents = ui
  }


  private lazy val ui = new Panel {
    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)
      for {
        x <- 0 until dim
        y <- 0 until dim }
      {
        g.setColor(if (at(x, y).colored)
          Color.black
        else
          Color.white)
        g.drawRect(x, y, 1, 1)
      }
    }
  }


  private object ant {
    private var x: Int = dim >> 1
    private var y: Int = dim >> 1
    private var dir: Int = 3

    def go(config: String, update: (Int, Int) => Unit): Unit = {
      new Thread(() => {
        while (x < dim && y < dim && x >= 0 && y >= 0) {

          at(x, y).step = (at(x, y).step + 1) % config.length

          if (config.charAt(at(x, y).step) != 'R') {
            // flip the color of the square,
            at(x, y).colored = true
            // At a white square, turn 90° right,
            dir = (dir + 1) % 4
          }
          else {
            // flip the color of the square,
            at(x, y).colored = false
            // At a black square, turn 90° left,
            dir = (dir + 3) % 4
          }

          // move forward one unit
          dir match {
            case 0 => y += 1
            case 1 => x += 1
            case 2 => y -= 1
            case 3 => x -= 1
          }
        }
      }).start()
    }
  }


  // move your ant
  ant.go("RL", (x, y) => {
    ui.repaint(new Rectangle(x, y, 1, 1))})
}