package main.scala
import java.io.{DataOutputStream, PrintWriter}

import scala.collection.mutable.ArrayBuffer

/**
  * Thread focused on sending data to clients.
  * @param clients The clients to send data to.
  * @param from The thread to take data from.
  */
class ReadOut(val clients: ArrayBuffer[Client], val from: ReadIn) extends Runnable {

  /**
    * Adds a client to this thread's client list (clients to send messages to)
    * @param client
    */
  def addToOutput(client: Client): Unit = {
    clients += client
  }

  /**
    * The run loop for this thread. Essentially just send messages over and over again.
    */
  def run(): Unit = {
    while(true) {
      sendAll()
      Thread.sleep(100)
    }
  }

  /**
    * Sends a specific message to every client in the server
    */
  def sendAll(message: (String, String)): Unit = {
    for (client <- clients) {
      client.sendMessage(message)
    }
  }


  /**
    * Sends the messages gathered from readin too all the clients on the server.
    */
  def sendAll(): Unit = {
    val messages = from.grabMessages()
    for (client <- clients; message <- messages; if !message._1.equals(client.name)) {
      client.sendMessage(message)
    }
  }

  /**
    * Sends a message to a specific client
    * @param message The message to send
    * @param name The name of the client to send the message to
    */
  def clientSend(message: (String, String), name: String): Unit = {
    for (client <- clients if client.name.equals(name)) {
      client.sendMessage(message)
      return
    }
  }

}
