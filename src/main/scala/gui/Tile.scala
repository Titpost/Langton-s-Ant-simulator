package gui

import java.awt.{Color, Rectangle}

import scala.swing.{Dimension, Panel}

/**
 * @author Tit on 27.01.2018
 */
class Tile extends Panel {

  preferredSize = new Dimension(1, 1)


  def invertColor() : Unit = {
    if (Color.BLACK == background) {
      background = Color.WHITE
    } else {
      background = Color.BLACK
    }
  }
}
