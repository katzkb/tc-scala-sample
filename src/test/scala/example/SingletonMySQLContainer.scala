package example

import com.dimafeng.testcontainers.MySQLContainer
import org.testcontainers.utility.DockerImageName

import scala.jdk.CollectionConverters._

object SingletonMySQLContainer {

  val containerDef: MySQLContainer.Def = MySQLContainer.Def(
    dockerImageName = DockerImageName.parse("mysql:8.0.31"),
    databaseName = "test",
    username = "root",
    password = ""
  )

  val mySQLContainer: MySQLContainer = {
    val ct = containerDef.createContainer()
    ct.container.setExtraHosts(List("host.docker.internal:host-gateway").asJava)
    ct.container.withConfigurationOverride("mysql_conf")
    ct.container.withInitScript("fixtures/setup.sql")
    ct.start()
    ct
  }

  private val port = mySQLContainer.container.getMappedPort(3306)
  lazy val url: String = s"${mySQLContainer.container.getHost}:${port}"
}
