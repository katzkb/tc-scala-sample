package example

import com.dimafeng.testcontainers.MySQLContainer
import org.testcontainers.utility.DockerImageName

import scala.jdk.CollectionConverters.*

object SingletonMySQLContainer:

  // 接続に必要な情報
  val containerDef: MySQLContainer.Def = MySQLContainer.Def(
    dockerImageName = DockerImageName.parse("mysql:8.0.33"),
    databaseName = "test",
    username = "root",
    password = ""
  )

  // DBコンテナを作成して起動
  lazy val mysqlContainer: MySQLContainer =
    val ct = containerDef.createContainer()
    ct.container.setExtraHosts(List("host.docker.internal:host-gateway").asJava)
    ct.container.withConfigurationOverride("mysql_conf")
    ct.start()
    ct
