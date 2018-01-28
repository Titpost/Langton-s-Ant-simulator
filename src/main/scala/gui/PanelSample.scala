package gui

import java.awt.Color

import scala.swing.{Dimension, GridBagPanel, MainFrame, SimpleSwingApplication}

/**
 * @author Tit on 27.01.2018
 */
object PanelSample extends SimpleSwingApplication {

  private val dim = 20
  private val field = Array.ofDim[Tile](dim, dim)

  private object ant {
    private var x: Int = dim >> 1
    private var y: Int = dim >> 1

    def move(): Unit = {

      new Thread(() => {
        val rnd = new scala.util.Random
        while (true) {
          rnd.nextInt(4) match {
            case 0 => if (0  == x) x += 1 else x -= 1
            case 1 => if (dim == x+1) x -= 1 else x += 1
            case 2 => if (0  == y) y += 1 else y -= 1
            case 3 => if (dim == y+1) y -= 1 else y += 1
          }
          field(x)(y).invertColor()
        }
      }).start()
    }
  }

  def top: MainFrame = new MainFrame {

    title = "Langton’s Ant simulator"
    minimumSize = new Dimension( 1200, 1200 )
    contents = gridBagPanel

    //gridBagPanel
  }

  private val gridBagPanel = new GridBagPanel() {

    private val coords = for {
      x <- 0 until dim
      y <- 0 until dim
    } yield (x, y)

    coords.foreach( t => {
      // create new tile
      val tile = new Tile()
      tile.background = Color.BLACK
      tile.invertColor()
      tile.minimumSize = new Dimension( dim, dim )

      // store it to array
      field(t._1)(t._2) = tile

      // put it on panel
      layout += tile -> (t._1, t._2)
    })

    // move your ant
    ant.move()
  }
}