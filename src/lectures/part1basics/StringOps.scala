package lectures.part1basics

object StringOps extends App {

  val str: String = "Hello, I am learning Scala"

  // Java String utilities
  println(str.charAt(2))// Char at position 2
  println(str.substring(7, 11)) // Substreeng between position 7 and 10 (11 - 1)
  println(str.split(" ").toList) // Creates a list with the elements splitting them by the character " "
  println(str.startsWith("Hello")) // Checks if the evaluated String starts with the String "Hello"
  println(str.replace(" ", "-")) // Replaces the character " " with "-" in the whole String
  println(str.toLowerCase)
  println(str.length)


  // Scala String utilities
  val aNumberString = "45"
  val aNumber = aNumberString.toInt
  println('a' +: 'a' +: aNumberString :+ 'z' :+ 'z') // +: prepend :+ append
  println(str.reverse)
  println(str.take(2)) // takes the n first chars of the String

  // S-interpolators
  val name = "David"
  val age = 12
  val greeting = s"Hello, my name is $name and I am $age years old"
  val greeting2 = s"Hello, my name is $name and I am $age years old and I will be turning ${age + 1}" // can evaluate complex expressions between brackets ${...}

  // F-interpolators
  val speed = 1.200000056
  val myth = f"$name can eat $speed%2.3f burgers per minute" // 2.2 means: Min. 2 characters and 3 decimals precision
  println(myth)

  // raw-interpoaltors
  println(raw"This is a \n newline") // Takes the \n as a raw chars and doesn't do the new line
  val escaped = "This is a \n newline"
  println(raw"$escaped") // because the \n is inside a val, now this is not raw and make the new line







}
