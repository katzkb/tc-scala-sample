package example.services

import cats.effect.unsafe.implicits.global
import example.ReusableContainerSpec

class UserServiceWithReuseSpec extends ReusableContainerSpec:

  val fixturePathList: Seq[String] = Seq(
    "fixtures/user/setup.sql",
    "fixtures/user/insert.sql"
  )

  "UserService" - {
    "Get an user" in {
      withContainers { mysqlContainer =>
        val userService = UserService(Some(getTransactor(mysqlContainer))).use { service =>
          for users <- service.getAllUsers.map(_.headOption)
          yield assert(users.exists(_.name == "Alice"))
        }
        userService.unsafeToFuture()
      }
    }
  }
