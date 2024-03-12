package example.models

import doobie.*
import doobie.implicits.javatimedrivernative.*
import doobie.util.Read.*
import cats.syntax.apply.*
import java.time.LocalDateTime

case class User(
    id: Long,
    name: String,
    updatedAt: LocalDateTime,
    createdAt: LocalDateTime
)

object User:
  implicit val UserMeta: Read[User] =
    (Read[Long], Read[String], Read[LocalDateTime], Read[LocalDateTime]).mapN(
      User.apply
    )
