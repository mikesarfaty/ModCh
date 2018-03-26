package main.scala
import java.io._
import java.net.{ServerSocket, Socket}

class AcceptUsers(socket: Socket, readin: ReadIn, readout: ReadOut) extends Runnable {


  /**
    * Adds a user by asking their name, and then constructs a client with that name and the corresponding
    *   connection's socket.
    */
  override def run(): Unit = {
    val out = new PrintWriter(socket.getOutputStream)
    val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
    out.write("What is your name?\n")
    out.flush()
    val name = in.readLine()
    println(s"New client set name to: $name")
    val c = new Client(in, out, name)
    readin.addClient(c)
    readout.addToOutput(c)
    c.sendMessage(s"${Constants.SERVER_NAME}", s"${Constants.MOTD} | Displaying you as $name")
  }

}
