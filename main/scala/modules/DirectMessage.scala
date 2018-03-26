package main.scala.modules
import main.scala.{ChatModule, ReadOut}
import scala.collection.mutable.ArrayBuffer

/**
  * To send private messages to users
  */
class DirectMessage extends ChatModule {

  val commands = Array("@")

  /**
    * Does this module have a command given the message received?
    */
  override def isCommand(message: (String, String)): Boolean = {
    message._2.length > 1 && commands.contains(message._2.take(1))
  }

  /**
    * What does this module choose to do if it contains the command
    */
  override def actOnCommand(message: (String, String), readout: ReadOut): Unit = {
    val recipAndMessage = """@(\w+)\s(.*)""".r.unanchored
    val recipDM: (String, String) = message._2 match {
      case recipAndMessage(recip, dm) => (recip, dm)
      case _ => null
    }

    readout.clientSend((s"${message._1} => You", recipDM._2), recipDM._1)

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
