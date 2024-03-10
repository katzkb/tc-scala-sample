package example

import com.dimafeng.testcontainers.MySQLContainer
import org.testcontainers.utility.DockerImageName

import java.sql.{ Connection, DriverManager, Statement }
import scala.jdk.CollectionConverters._

object SingletonMySQLContainer {

  val containerDef: MySQLContainer.Def = MySQLContainer.Def(
    dockerImageName = DockerImageName.parse("mysql:8.0"),
    databaseName = "test",
    username = "root",
    password = ""
  )

  val mySQLContainer: MySQLContainer = {
    val ct = containerDef.createContainer()
    ct.container.setExtraHosts(List("host.docker.internal:host-gateway").asJava)
    ct.container.withConfigurationOverride("mysql-conf-override")
    ct.container.withInitScript("fixtures/setup.sql")
    ct.start()
    ct
  }

  val conn: Connection = DriverManager.getConnection(
    s"jdbc:mysql://localhost:${mySQLContainer.container.getMappedPort(3306)}/test",
    "root",
    ""
  )
  val stmt: Statement = conn.createStatement()
  lazy val url: String = mySQLContainer.container.getHost + ":" + mySQLContainer.container.getMappedPort(5432)
}
