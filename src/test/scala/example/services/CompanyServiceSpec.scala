package example.services

import example.SingletonMySQLContainerSpec
import cats.effect.unsafe.implicits.global

class CompanyServiceSpec extends SingletonMySQLContainerSpec:

  val fixturePathList: Seq[String] = Seq(
    "fixtures/company/setup.sql",
    "fixtures/company/insert.sql"
  )

  "CompanyService" - {
    "Get an company" in {
      val companyService = CompanyService(Some(customTransactor)).use {
        service =>
          for companies <- service.getAllCompanies.map(_.headOption)
          yield assert(companies.exists(_.name == "Example"))
      }
      companyService.unsafeToCompletableFuture()
    }
  }
