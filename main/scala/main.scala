package main.scala
import java.io.{DataOutputStream, PrintWriter}
import java.net.ServerSocket

import scala.collection.mutable.ArrayBuffer

/**
  * A general idea of how the server works:
  * 1. One thread handles sending data
  * 2. One thread handles reading data
  * 3. One thread handles accepting new users
  */
object main extends App {
  val readin = new ReadIn(new ArrayBuffer[Client](), null)
  val readout = new ReadOut(new ArrayBuffer[Client](), readin)
  readin.readout = readout

  val server = new ServerSocket(Constants.PORT)
  val inThread = new Thread(readin)
  val outThread = new Thread(readout)
  inThread.start()
  outThread.start()

  while(true) {
    val acceptThread = new Thread(new AcceptUsers(server.accept(), readin, readout))
    acceptThread.start()
  }

}
