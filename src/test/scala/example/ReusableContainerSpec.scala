package example

import cats.effect.{IO, Resource}
import com.dimafeng.testcontainers.MySQLContainer
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import doobie.util.transactor
import example.db.Database
import org.scalatest.freespec.AsyncFreeSpec
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.ext.ScriptUtils
import org.testcontainers.jdbc.JdbcDatabaseDelegate
import org.testcontainers.utility.DockerImageName

import scala.jdk.CollectionConverters.*

trait ReusableContainerSpec extends AsyncFreeSpec with TestContainerForAll:

  val fixturePathList: Seq[String]

  override val containerDef: MySQLContainer.Def = MySQLContainer.Def(
    dockerImageName = DockerImageName.parse("mysql:8.0.33"),
    databaseName = "test",
    username = "root",
    password = ""
  )

  def getTransactor(
      mysqlContainer: MySQLContainer
  ): Resource[IO, transactor.Transactor[IO]] =
    Database.transactor(
      "com.mysql.cj.jdbc.Driver",
      s"jdbc:mysql://${mysqlContainer.container.getHost}:${mysqlContainer.container
          .getMappedPort(3306)}/?useSSL=false",
      "root",
      ""
    )

  override def startContainers(): Containers =
    val mysqlContainer = containerDef.createContainer()
    mysqlContainer.container.setExtraHosts(
      List("host.docker.internal:host-gateway").asJava
    )
    mysqlContainer.container.withInitScript("fixtures/cleanup.sql")
    mysqlContainer.container.withReuse(true)
    mysqlContainer.start()

    val jdbcContainer =
      mysqlContainer.container.asInstanceOf[JdbcDatabaseContainer[Nothing]]

    val databaseDelegate: JdbcDatabaseDelegate =
      new JdbcDatabaseDelegate(jdbcContainer, "")

    fixturePathList.foreach { path =>
      ScriptUtils.runInitScript(databaseDelegate, path)
    }

    mysqlContainer
