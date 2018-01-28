package gui

import java.awt.Color

import scala.swing.{Button, Component}

/**
 * @author Tit on 27.01.2018
 */
class Tile extends Button {

  def invertColor() : Unit = {
    if (Color.BLACK == background) {
      background = Color.WHITE
    } else {
      background = Color.BLACK
    }
  }
}
