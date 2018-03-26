package main.scala
import java.io.{BufferedReader, PrintWriter}

/**
  * Represents one user on the server
  * @param in Takes information in from user.
  * @param out Sends information to user.
  * @param name The name/id of the user
  */
class Client(in: BufferedReader, out: PrintWriter, val name: String) {

  /**
    * Gets a message from this client if it's ready to send one
    * @return A String 2-tuple: ([Name of Client], [Message Client sent])
    */
  def getMessage(): (String, String) = {
    if (!in.ready()) {
      null
    }
    else {
      val message = in.readLine();
      println(s"[sending] $name : $message")
      (s"$name", s"$message")
    }
  }

  /**
    * Sends a message to this client
    * @param contents A String tuple: ([Who message is from], [Message Contents])
    */
  def sendMessage(contents: (String, String)): Unit = {
    if (!name.equals(contents._1)) {
      out.write(s"${contents._1} : ${contents._2}\n")
      out.flush()
    }
  }


}
