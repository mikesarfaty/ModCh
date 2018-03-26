package main.scala.modules

import java.text.SimpleDateFormat
import java.util.Date

import main.scala.{ChatModule, ReadOut}

import scala.collection.mutable.ArrayBuffer;

/**
  * Date add-on for the server. Probably the simplest add-on.
  */
class DateModule extends ChatModule {

  val hms = new SimpleDateFormat("hh':'mm':'ss a '('z')'")
  val date = new SimpleDateFormat("MM/dd/yyyy")
  val datetime = new SimpleDateFormat("hh':'mm':'ss a '('z')' 'on' MM/dd/yyyy")
  val commands = Array("date", "time", "datetime")

  /**
    * Does this module have a command given the message received?
    */
  override def isCommand(message: (String, String)): Boolean = {
    if (message._2.length < 1) {
      false
    }
    else {
      val stripped = message._2.substring(1)
      var result = false
      for (command <- commands) {
        result = result || command.equalsIgnoreCase(stripped)
      }
      result
    }
  }

  /**
    * What does this module choose to do if it contains the command
    */
  override def actOnCommand(message: (String, String), readout: ReadOut): Unit = {
    val stripped = message._2.substring(1)
    if (stripped.equalsIgnoreCase("date")) {
      val sentMessage = (s"${message._1} broadcasts the date to the server", date.format(new Date()))
      readout.sendAll(sentMessage)
    }
    else if (stripped.equalsIgnoreCase("time")) {
      val sentMessage = (s"${message._1} requested server local time", hms.format(new Date()))
      readout.sendAll(sentMessage)
    }
    else if (stripped.equalsIgnoreCase("datetime")) {
      val sentMessage = (s"${message._1} requested server local date/time", datetime.format(new Date()))
      readout.sendAll(sentMessage)
    }
    else {
      throw new UnsupportedOperationException(s"DateModule: Not all cases satisfied. Command: $stripped")
    }
  }

  /**
    * Gets this modules command strings. I.E. the datetime module would return ["time", "date", "now"]
    *
    * @return
    */
  override def commandWords(): ArrayBuffer[String] = {
    ArrayBuffer(commands : _*)
  }
}
