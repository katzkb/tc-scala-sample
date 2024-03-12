package example.services

import cats.effect.{IO, Resource}
import doobie.implicits.*
import doobie.util.transactor.Transactor
import example.db.Database
import example.models.Company

case class CompanyService(xa: Transactor[IO]):

  def getAllCompanies: IO[List[Company]] =
    sql"SELECT id, name, email, updated_at, created_at FROM test.companies"
      .query[Company]
      .to[List]
      .transact(xa)

object CompanyService:
  def apply(): Resource[IO, CompanyService] =
    Database.transactor.map(new CompanyService(_))

  def apply(
      customTransactor: Option[Resource[IO, Transactor[IO]]] = None
  ): Resource[IO, CompanyService] =
    customTransactor match
      case Some(transactor) => transactor.map(new CompanyService(_))
      case None             => Database.transactor.map(new CompanyService(_))
