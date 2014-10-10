name := """brokenMacwireParsing"""

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies += "com.softwaremill.macwire" %% "macros" % "0.7.1"


initialize ~= {_ =>
  System.setProperty("macwire.debug", "")
}