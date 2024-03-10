package example

import com.dimafeng.testcontainers.MySQLContainer
import org.scalatest.BeforeAndAfterAll
import org.scalatest.freespec.AsyncFreeSpec
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.ext.ScriptUtils
import org.testcontainers.jdbc.JdbcDatabaseDelegate

trait SingletonMySQLContainerSpec extends AsyncFreeSpec with BeforeAndAfterAll {

  final def mysqlContainer: MySQLContainer = SingletonMySQLContainer.mySQLContainer

  final private val jdbcContainer:    JdbcDatabaseContainer[_] = mysqlContainer.container.asInstanceOf[org.testcontainers.containers.JdbcDatabaseContainer[_]]
  final private val databaseDelegate: JdbcDatabaseDelegate     = new JdbcDatabaseDelegate(jdbcContainer, "")

  val fixturePathList: Seq[String]

  override def beforeAll(): Unit = {
    super.beforeAll()
    ScriptUtils.runInitScript(databaseDelegate, "fixtures/cleanup.sql")
    fixturePathList.foreach { path =>
      ScriptUtils.runInitScript(databaseDelegate, path)
    }
  }

  val stmt = SingletonMySQLContainer.stmt
  val url = SingletonMySQLContainer.url
}
