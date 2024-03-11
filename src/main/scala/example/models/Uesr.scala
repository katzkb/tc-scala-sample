package example.models

import doobie._
import doobie.implicits.javatimedrivernative._
import doobie.util.Read._
import cats.syntax.apply._
import java.time.LocalDateTime

case class User(
    id: Long,
    name: String,
    updatedAt: LocalDateTime,
    createdAt: LocalDateTime
)

object User {
  implicit val UserMeta: Read[User] =
    (Read[Long], Read[String], Read[LocalDateTime], Read[LocalDateTime]).mapN(
      User.apply
    )
}
