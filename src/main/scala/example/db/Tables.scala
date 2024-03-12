//package example.db
//
//import doobie.Fragment
//import doobie.implicits.*
//
//object Tables:
//
//  val createUserTableQuery: Fragment =
//    sql"""
//      CREATE TABLE users (
//        id INT AUTO_INCREMENT PRIMARY KEY,
//        name VARCHAR(255) NOT NULL,
//        updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
//      )
//    """
