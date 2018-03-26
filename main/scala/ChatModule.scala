package main.scala

import scala.collection.mutable.ArrayBuffer

trait ChatModule {

  /**
    * Does this module have a command given the message received?
    */
  def isCommand(message: (String, String)): Boolean

  /**
    * What does this module choose to do if it contains the command
    */
  def actOnCommand(message: (String, String), readout: ReadOut): Unit

  /**
    * Gets this modules command strings. I.E. the datetime module would return ["time", "date", "now"]
    * @return
    */
  def commandWords(): ArrayBuffer[String]
}
