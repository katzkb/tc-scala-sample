package example.services

import cats.effect.{IO, Resource}
import example.db.Database
import example.models.User
import doobie.implicits._
import doobie.util.transactor.Transactor

case class UserService(xa: Transactor[IO]) {

//  def createTable(): IO[Unit] =
//    Tables.createUserTableQuery.update.run.transact(xa).void

  def getAllUsers: IO[List[User]] =
    sql"SELECT id, name, updated_at, created_at FROM test.users"
      .query[User]
      .to[List]
      .transact(xa)

}

object UserService {
  def apply(): Resource[IO, UserService] =
    Database.transactor.map(new UserService(_))

  def apply(
    customTransactor: Option[Resource[IO, Transactor[IO]]] = None
  ): Resource[IO, UserService] =
    customTransactor match {
      case Some(transactor) => transactor.map(new UserService(_))
      case None             => Database.transactor.map(new UserService(_))
    }
}
