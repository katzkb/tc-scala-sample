package example.services

import cats.effect.unsafe.implicits.global
import example.ReusableContainerSpec

class CompanyServiceWithReuseSpec extends ReusableContainerSpec:

  val fixturePathList: Seq[String] = Seq(
    "fixtures/company/setup.sql",
    "fixtures/company/insert.sql"
  )

  "CompanyService" - {
    "Get an company" in {
      withContainers { mysqlContainer =>
        val companyService = CompanyService(Some(getTransactor(mysqlContainer))).use { service =>
          for companies <- service.getAllCompanies.map(_.headOption)
          yield assert(companies.exists(_.name == "Example"))
        }
        companyService.unsafeToFuture()
      }
    }
  }
