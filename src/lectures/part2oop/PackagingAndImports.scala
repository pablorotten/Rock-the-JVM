package lectures.part2oop

// import playground._ // imports everything of the package. Only used if really needed, best practice is to import what you *really* need
import playground.{PrinceCharming, Cinderella => Princess} // can use aliases to rename imports

import java.util.Date
import java.sql.{Date => SqlDate}

object PackagingAndImports extends App {

  // package members are accessible by their simple name
  val writer = new Writer("Pablo", "RockTheJVM", 2020)

  // import the package
  val princess = new /* Cinderella */ Princess // In the import we renamed Cinderella into Princess

  val princess2 = new playground.Cinderella // Fully Qualified name

  // packages are in hierarchy: lectures.part2oop >> package.subpackage
  // matching the structure folder: the folder package contains the folder subpackage

  // Package object
  // can use package object part2oop members without importing
  sayHello
  println(SPEED_OF_LIGHT)

  // imports
  val prince = new PrinceCharming

  // Name conflicts in imports

  val date = new Date // import assummes that is the 1st imported Date: util.Date
  // If you want to use java.sql.Date:
  val sqlDate = new java.sql.Date(2020, 6, 17) // 1. Use Fully Qualified names
  val sqlDate2 = new SqlDate(2020, 6, 17) // 2. Use aliasing

  // Default imports: Automatic imports by default
  // * java.lang: String, Object, Exception
  // * scala: Int, Nothing, Function
  // * scala.Predef: println, ???


}
