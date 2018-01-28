package gui

import java.awt.{Color, Graphics2D}

import scala.swing.{Dimension, MainFrame, Panel, Rectangle, SimpleSwingApplication}

/**
 * @author Tit on 27.01.2018
 */
object AntField extends SimpleSwingApplication {

  private val dim = 700
  private val field = Array.ofDim[Boolean](dim, dim)
  private val coords = for {
    x <- 0 until dim
    y <- 0 until dim
  } yield (x, y)


  def top: MainFrame = new MainFrame {
    minimumSize = new Dimension( dim, dim )
    title = "Langton’s Ant simulator"
    contents = ui
  }


  private lazy val ui = new Panel {
    preferredSize = new Dimension(200, 200)

    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)
      coords.foreach( t => {
        g.setColor(if (field(t._1)(t._2))
          Color.white
        else
          Color.black)
        g.drawRect(t._1, t._2, 1, 1)
      })
    }
  }


  private object ant {
    private var x: Int = dim >> 1
    private var y: Int = dim >> 1

    def go(update: (Int, Int) => Unit): Unit = {
      new Thread(() => {
        val rnd = new scala.util.Random
        while (true) {
          rnd.nextInt(4) match {
            case 0 => if (0  == x) x += 1 else x -= 1
            case 1 => if (dim == x+1) x -= 1 else x += 1
            case 2 => if (0  == y) y += 1 else y -= 1
            case 3 => if (dim == y+1) y -= 1 else y += 1
          }
          invertColor(x, y, update)
        }
      }).start()
    }

    private def invertColor(x: Int, y: Int, update: (Int, Int) => Unit) : Unit = {
      if (field(x)(y)) {
        field(x)(y) = false
      } else {
        field(x)(y) = true
      }
      update.apply(x, y)
    }
  }


  // move your ant
  ant.go((x, y) => {
    ui.repaint(new Rectangle(x, y, 1, 1))})
}