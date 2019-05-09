package lectures.part1basics

object CBNvsCBV extends App{

  // Evaluates the 'x' parameter before entering into the function and passes the actual value of it
  def calledByValue(x: Long): Unit = {
    println("by value: " + x)
    println("by value: " + x)
  }

  // The 'x' parameter is not evaluated, pass the expression literally and only evaluates the expression every time is used into the function
  def calledByName(x: => Long): Unit = {
    println("by name: " + x)
    println("by name: " + x)
  }

  calledByValue(System.nanoTime())
  calledByName(System.nanoTime())

  // Throws Stack Overflow Exception
  def infinite(): Int = 1 + infinite()

  def printFirst(x: Int, y: => Int) = println(x)

  // It will fail because it evaluates expresion passed as the 'x' parameter before passing it to the function
//  printFirst(infinite(), 34)

  // Works because passes the infinite() function by name, and inside the function is never used, so it's never evaluate
  printFirst(34, infinite()) // will not fail

}
