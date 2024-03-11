package example.services

import example.SingletonMySQLContainerSpec
import cats.effect.unsafe.implicits.global

class UserServiceSpec extends SingletonMySQLContainerSpec {
  val fixturePathList: Seq[String] = Seq(
    "fixtures/setup.sql",
    "fixtures/insert.sql"
  )

  "UserService" - {
    "Get all users" in {
      val userService = UserService(Some(customTransactor)).use { service =>
        for {
          users <- service.getAllUsers
        } yield {
          assert(users.length == 1)
        }
      }
      userService.unsafeRunSync()
    }
  }
}
