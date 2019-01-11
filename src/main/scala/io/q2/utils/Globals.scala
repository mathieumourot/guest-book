package io.q2.utils

import com.typesafe.config._

object Globals {
  val cfg: Config = ConfigFactory.load
  val appCfg: Config = cfg.getConfig("guest.book")
}
