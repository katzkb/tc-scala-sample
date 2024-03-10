package example

import java.sql.DriverManager

class HelloSpec extends SingletonMySQLContainerSpec {

  val fixturePathList: Seq[String] = Seq(
    "fixtures/setup.sql"
  )

  "Check connection" in {
    val connection = DriverManager.getConnection(url, "root", "")
    // 接続できることをアサート
    assert(connection.isValid(10))
  }
}
