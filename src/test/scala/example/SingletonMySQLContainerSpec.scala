package example

import cats.effect.{IO, Resource}
import com.dimafeng.testcontainers.MySQLContainer
import doobie.util.transactor
import example.db.Database
import org.scalatest.BeforeAndAfterAll
import org.scalatest.freespec.AnyFreeSpec
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.ext.ScriptUtils
import org.testcontainers.jdbc.JdbcDatabaseDelegate

trait SingletonMySQLContainerSpec extends AnyFreeSpec with BeforeAndAfterAll:
  
  val fixturePathList: Seq[String]

  final def mysqlContainer: MySQLContainer =
    SingletonMySQLContainer.mysqlContainer

  private val jdbcContainer: JdbcDatabaseContainer[Nothing] =
    mysqlContainer.container.asInstanceOf[JdbcDatabaseContainer[Nothing]]

  private val databaseDelegate: JdbcDatabaseDelegate =
    new JdbcDatabaseDelegate(jdbcContainer, "")

  final val customTransactor: Resource[IO, transactor.Transactor[IO]] =
    Database.transactor(
      "com.mysql.cj.jdbc.Driver",
      s"${jdbcContainer.getJdbcUrl}?useSSL=false",
      "root",
      ""
    )

  override def beforeAll(): Unit =
    super.beforeAll()
    ScriptUtils.runInitScript(databaseDelegate, "fixtures/cleanup.sql")
    fixturePathList.foreach { path =>
      ScriptUtils.runInitScript(databaseDelegate, path)
    }
