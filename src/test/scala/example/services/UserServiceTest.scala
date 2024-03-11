package example.services

import example.SingletonMySQLContainerSpec
import cats.effect.unsafe.implicits.global

class UserServiceSpec extends SingletonMySQLContainerSpec {

  val fixturePathList: Seq[String] = Seq(
    "fixtures/setup.sql",
    "fixtures/insert.sql"
  )

  "UserService" - {
    "Get an user" in {
      val userService = UserService(Some(customTransactor)).use { service =>
        for {
          users <- service.getAllUsers.map(_.headOption)
        } yield {
          assert(users.exists(_.name == "Alice"))
        }
      }
      userService.unsafeRunSync()
    }
  }
}
