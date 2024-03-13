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

  final def mysqlContainer: MySQLContainer =
    SingletonMySQLContainer.mysqlContainer

  final private val jdbcContainer: JdbcDatabaseContainer[Nothing] =
    mysqlContainer.container.asInstanceOf[JdbcDatabaseContainer[Nothing]]

  final private val databaseDelegate: JdbcDatabaseDelegate =
    new JdbcDatabaseDelegate(jdbcContainer, "")

  val fixturePathList: Seq[String]

  val customTransactor: Resource[IO, transactor.Transactor[IO]] =
    Database.transactor(
      "com.mysql.cj.jdbc.Driver",
      s"jdbc:mysql://${SingletonMySQLContainer.url}?useSSL=false",
      "root",
      ""
    )

  override def beforeAll(): Unit =
    super.beforeAll()
    ScriptUtils.runInitScript(databaseDelegate, "fixtures/cleanup.sql")
    fixturePathList.foreach { path =>
      ScriptUtils.runInitScript(databaseDelegate, path)
    }
