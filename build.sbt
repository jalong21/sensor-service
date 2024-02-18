import com.typesafe.sbt.packager.docker.DockerChmodType.UserGroupWriteExecute
import com.typesafe.sbt.packager.docker.DockerPermissionStrategy.CopyChown
import com.typesafe.sbt.packager.docker._
name := "sensor-service"
version := "0.1"
lazy val root = (project in file(".")).enablePlugins(PlayScala)
scalaVersion := "2.13.5"

dockerChmodType := UserGroupWriteExecute
dockerPermissionStrategy := CopyChown

libraryDependencies ++= Seq(ehcache,
  ws,
  guice,
  filters,
  "net.sf.ehcache" % "ehcache" % "2.10.6",
  "redis.clients" % "jedis" % "5.0.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "org.mockito" % "mockito-core" % "3.9.0" % Test)
