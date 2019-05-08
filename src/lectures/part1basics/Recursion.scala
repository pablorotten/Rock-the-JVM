package lectures.part1basics

import lectures.part1basics.Functions.isPrime

import scala.annotation.tailrec

object Recursion extends App {
  def factorial(n: Int): Int =
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + " - I first need factorial of " + (n - 1))
      val result = n * factorial(n - 1) // The recursive call expression is HERE
      println("Computed factorial of " + n)

      result // This is the last expression
    }

  // factorial is not a _TAIL_ RECURSIVE function because his last expression is not the recursive call

  println(factorial(10))
  //  println(factorial(5000)) // CRASHES with this number with a Stack Overflow error.

  def anotherFactorial(n: Int): BigInt = {
    @tailrec
    def factHelper(x: Int, accumulator: BigInt): BigInt =
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator) // TAIL RECURSION = use recursive call as the LAST expression

    factHelper(n, 1)
  }

  // factHelper is a _TAIL_ RECURSIVE function because his last expression is the recursive call

  /*
    anotherFactorial(10) = factHelper(10, 1)
    = factHelper(9, 10 * 1)
    = factHelper(8, 9 * 10 * 1)
    = factHelper(7, 8 * 9 * 10 * 1)
    = ...
    = factHelper(2, 3 * 4 * ... * 10 * 1)
    = factHelper(1, 1 * 2 * 3 * 4 * ... * 10)
    = 1 * 2 * 3 * 4 * ... * 10
   */

  println(anotherFactorial(20000))

  // WHEN YOU NEED LOOPS, USE _TAIL_ RECURSION.

  /*
    1.  Concatenate a string n times using tail recursion
    2.  IsPrime function tail recursive
    3.  Fibonacci function, tail recursive.
   */

  // 1.
  def concatenateTailRecursion(aString: String, times: Int): String = {
    @tailrec
    def doConcatenation(x: Int, acumulator: String): String = {
      if (x <= 1) acumulator
      else doConcatenation(x - 1, acumulator + aString)
    }

    doConcatenation(times, aString)
  }

  println(concatenateTailRecursion("Text ", 10))

  // Daniel's solution
  @tailrec
  def concatenateTailrecDaniel(aString: String, n: Int, accumulator: String): String =
    if (n <= 0) accumulator
    else concatenateTailrecDaniel(aString, n - 1, aString + accumulator)

  println(concatenateTailrecDaniel("hello", 3 , ""))

  // 2.
  def isPrime(n: Int): Boolean = {
    @tailrec
    def checkIfPrime(divisor: Int): Boolean ={
      if(divisor <= 1) true
      else
      if(n % divisor == 0) false
      else checkIfPrime(divisor - 1)
    }

    checkIfPrime(n/2)
  }

  println(isPrime(37))
  println(isPrime(2003))
  println(isPrime(37 * 12))

  // Daniel's solution
  def isPrimeDaniel(n: Int): Boolean = {
    @tailrec
    def isPrimeTailrec(t: Int, isStillPrime: Boolean): Boolean =
      if (!isStillPrime) false
      else if (t <= 1) true
      else isPrimeTailrec(t - 1, n % t != 0 && isStillPrime) // isStillPrime here is not really needed

    isPrimeTailrec(n/2, true)
  }

  println(isPrime(37))
  println(isPrime(2003))
  println(isPrime(37 * 12))

  // 3. Daniel solution
  /**
    * Starts from the base case and stops when the limit (n) is reached:
    * So the base case is fibonacci(3) which is 1 + 1 = 2
    *
    * If the user enters n = 3 which is the base case, in the first call of fiboTailrec, we already will have i = n and just return the initial last + nextToLast = 1 + 1 = 2
    * If is a bigger number
    * @param n
    * @return
    */
  def fibonacci(n: Int): Long = {
    // Fibonacci: 1 1 2 3 5 8 13 21
    @tailrec
    def fiboTailrec(i: Int, last: Int, nextToLast: Int): Int = // last is fibonacci(n - 1), nextToLast is fibonacci(n - 2)
    if (i >= n) last
    else fiboTailrec(i + 1, last + nextToLast, last)

    if (n <= 2) 1
    else fiboTailrec(2, 1, 1)
  }

  println(fibonacci(3))
  println(fibonacci(5))
  println(fibonacci(8))
}
