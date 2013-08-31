package com.ajjpj.amapper.core

import org.apache.log4j.Logger

/**
 * @author arno
 */
abstract class AMapperLogger {
  def debug(msg: => String)
  protected def info(msg: => String)
  protected def warn(msg: String = "exception", exc: Exception = null)
  protected def error(msg: String = "exception", exc: Exception = null)

  def deferredWithoutInitial(path: Path) = warn("Object mapped as 'deferred' without previously being mapped in the primary hierarchy @ " + path)
  def severalExistingTargetsForSource(path: Path, s: AnyRef) = warn("Several existing target elements for source element " + s + "@" + path)
}

object AMapperLogger {
  var showDebug = false
  var showInfo = true

  val StdOut = new AMapperLogger {
    def preamble = Thread.currentThread.getName + "@" + System.currentTimeMillis + ": "

    def debug(msg: => String)                        {if(showDebug) println (preamble + "DEBUG " + msg)}
    protected def info (msg: => String)              {if(showInfo) println (preamble + "INFO  " + msg)}
    protected def warn (msg: String, exc: Exception) {println (preamble + "WARN  " + msg); if(exc != null) exc.printStackTrace(System.out)}
    protected def error(msg: String, exc: Exception) {println (preamble + "ERROR " + msg); if(exc != null) exc.printStackTrace(System.out)}
  }

  val Log4J = new AMapperLogger {
    val log = Logger.getLogger("org.ajjpj.amapper")

    def debug(msg: => String) {if(showDebug && log.isDebugEnabled) log.debug(msg)}
    protected def info(msg: => String) {if(log.isInfoEnabled) log.info(msg)}
    protected def warn(msg: String, exc: Exception) {log.warn(msg, exc)}
    protected def error(msg: String, exc: Exception) {log.error(msg, exc)}
  }

  val defaultLogger = try {
    Log4J
  }
  catch {
    case _: Exception => StdOut
  }
}

class AMapperException(msg: String, path: Path) extends RuntimeException (msg + "@" + path)
