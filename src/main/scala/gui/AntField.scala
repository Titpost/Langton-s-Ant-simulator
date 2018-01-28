package gui

import java.awt.{Color, Graphics2D}

import scala.swing.{Dimension, MainFrame, Panel, Rectangle, SimpleSwingApplication}

/**
 * @author Tit on 27.01.2018
 */
object AntField extends SimpleSwingApplication {

  private val dim = 1000
  private val field = Array.ofDim[Int](dim, dim)
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
        g.setColor(if (0 == field(t._1)(t._2))
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
    private var dir: Int = 3

    def go(config: String, update: (Int, Int) => Unit): Unit = {
      new Thread(() => {
        while (true) {

          if (0 == field(x)(y)) {
            // At a white square, turn 90° right,
            dir = (dir + 1) % 4
            // flip the color of the square,
            field(x)(y) = 1
          }
          else {
            // At a black square, turn 90° left,
            dir = (dir + 3) % 4
            // flip the color of the square,
            field(x)(y) = 0
          }

          // move forward one unit
          dir match {
            case 0 => if (dim == y+1) return else y += 1
            case 1 => if (dim == x+1) return else x += 1
            case 2 => if (0 == y) return else y -= 1
            case 3 => if (0 == x) return else x -= 1
            case _ => x = 100000
          }

          println(s"dir = $dir: x = $x, y = $y");
        }
      }).start()
    }

/*    private def invertColor(x: Int, y: Int, update: (Int, Int) => Unit) : Unit = {
      if (field(x)(y)) {
        field(x)(y) = false
      } else {
        field(x)(y) = true
      }
      update.apply(x, y)
    }*/
  }


  // move your ant
  ant.go("LLRRRLRLRLLR", (x, y) => {
    ui.repaint(new Rectangle(x, y, 1, 1))})
}