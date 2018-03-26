package main.scala
import java.net.Socket
import java.util.Date
import modules.DateModule
import modules.DirectMessage

import scala.collection.mutable.ArrayBuffer

class ReadIn(val clients: ArrayBuffer[Client], var readout: ReadOut) extends Runnable {

  var messages = new ArrayBuffer[(String, String)]
  val modules = Array(new DateModule(), new DirectMessage())

  /**
    * Gets messages from user input repeatedly.
    */
  override def run(): Unit = {
    while (true) {
      for (client <- clients; message = client.getMessage(); if message != null) {
        messages += message
      }
      Thread.sleep(100)
    }
  }

  /**
    * Gets the messages the inthread has read since the last time it's been polled.
    * @return The messages the inthread has read since the last poll
    */
  def grabMessages(): ArrayBuffer[(String, String)] = {
    val result = new ArrayBuffer[(String, String)]()
    for (s <- messages) {
      if (!parseCommand(s)) {
        result += s
      }
    }
    messages = new ArrayBuffer[(String, String)]()
    result
  }

  /**
    * Given the modules we're using, do stuff with the message
    * @param message
    * @return
    */
  def parseCommand(message: (String, String)): Boolean = {
    for (addon <- modules) {
      if (addon.isCommand(message)) {
        addon.actOnCommand(message, readout)
        return true
      }
    }
    false
  }

  /**
    * @deprecated
    * Deprecated, might have a future use
    * @param to Who to send the message to
    * @param msg What the message is
    */
  def uinform(to: String, msg: String): Unit = {
    for (c <- clients; if c.name.equals(to)) {
      c.sendMessage(("Server Notification", msg))
    }
  }

  /**
    * @deprecated
    * Signify server error to user
    * @param user the user to send a server error message to
    * @param message description of error message
    */
  def serror(user: String, message: String): Unit = {
    for (c <- clients; if c.name.equals(user)) {
      c.sendMessage(("ERROR", message))
    }
  }

  /**
    * Adds a client to this readIn object
    * @param c the client to add
    */
  def addClient(c: Client): Unit = {
    clients += c
    println("client added")
  }

}
