package example

import com.dimafeng.testcontainers.MySQLContainer
import org.testcontainers.utility.DockerImageName

import scala.jdk.CollectionConverters.*

object SingletonMySQLContainer:

  val containerDef: MySQLContainer.Def = MySQLContainer.Def(
    dockerImageName = DockerImageName.parse("mysql:8.0.33"),
    databaseName = "test",
    username = "root",
    password = ""
  )

  lazy val mysqlContainer: MySQLContainer =
    val ct = containerDef.createContainer()
    ct.container.setExtraHosts(List("host.docker.internal:host-gateway").asJava)
    ct.container.withConfigurationOverride("mysql_conf")
    ct.start()
    ct

  lazy val url: String =
    s"${mysqlContainer.container.getHost}:${mysqlContainer.container.getMappedPort(3306)}"
