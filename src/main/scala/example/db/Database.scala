package example.db

import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import doobie.util.meta.Meta

object Database:

  def transactor(
      driverClassName: String,
      url: String,
      user: String,
      pass: String
  ): Resource[IO, Transactor[IO]] =
    for
      ce <- ExecutionContexts.fixedThreadPool[IO](32)
      xa <- HikariTransactor.newHikariTransactor[IO](
        driverClassName,
        url,
        user,
        pass,
        ce
      )
    yield xa

  implicit val CustomMeta: Meta[java.time.LocalDateTime] =
    Meta.Advanced.other[java.time.LocalDateTime]("TIMESTAMP")

  val transactor: Resource[IO, Transactor[IO]] =
    transactor(
      "com.mysql.cj.jdbc.Driver",
      "jdbc:mysql://localhost:53306/test?useSSL=false",
      "root",
      ""
    )
