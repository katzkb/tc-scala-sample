package example.models

import doobie.*
import doobie.implicits.javatimedrivernative.*
import doobie.util.Read.*
import cats.syntax.apply.*
import java.time.LocalDateTime

case class Company(
    id: Long,
    name: String,
    email: String,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
)

object Company:
  implicit val CompanyMeta: Read[Company] =
    (
      Read[Long],
      Read[String],
      Read[String],
      Read[LocalDateTime],
      Read[LocalDateTime]
    ).mapN(Company.apply)
