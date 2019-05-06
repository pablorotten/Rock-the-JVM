package lectures.part1basics

object Expressions extends App {

  val x = 1 + 2 // EXPRESSION
  println(x)

  println(2 + 3 * 4)
  // Mathematical Expressions: + - * / & | ^ << >> >>> (right shift with zero extension)

  println(1 == x)
  // Relational Expressions: == != > >= < <=

  println(!(1 == x))
  // Boolean Expressions: ! && ||

  var aVariable = 2
  aVariable += 3 // Side effects: -= *= /= , Change the value of a variable
  println(aVariable)

  // Instructions (DO) vs Expressions

  // IF expression
  val aCondition = true
  val aConditionedValue = if(aCondition) 5 else 3 // IF EXPRESSION
  println(aConditionedValue)
  println(if(aCondition) 5 else 3)
  println(1 + 3)

  var i = 0
  var aWhile = while(i < 10) {
    println(i)
    i += 1
  }

  // NEVER WRITE THIS AGAIN.

  // EVERYTHING in Scala is an Expression!

  val aWeirdValue = (aVariable = 3) // Unit == void
  println(aWeirdValue)

  // side effects: println(), whiles, reassigning

  // Code blocks

  val aCodeBlock = {
    val y = 2
    val z = y +1

    if (z > 2) "hello" else "goodbye"
  }

  // 1. difference between "hello world" vs println("hello world")?
  // "hello world" is a value of String and println("hello wolrd") is a Expression which is a side effect that prints a String
  // 2.

  val someValue = {
    2 < 3
  }
  // someValue = true

  val someOtherValue = {
    if (someValue) 239 else 986
    42
  }
  // someOtherValue = 42
}
