package lectures.part3fp

object WhatsAFunction extends App {

  // DREAM: use functions as first class elements
  // Problem: comes from OOP

  trait MyFunction[A, B] {
    def apply(element: A): B
  }

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  // function types = Function1[A, B]
  val stringToIntConverter = new Function[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("3" + 4))

  // the type of adder is the syntactic sugar ((Int, Int) => Int)
  val adder = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  val adder2: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  val adder3 = new ((Int, Int) => Int) {
    override def apply(a: Int, b: Int): Int = a + b
  }

  // Function types Function2[A, B, R] === (A,B) => R

  // ALL SCALA FUNCTIONS ARE OBJECTS (Instances of classes Function1, Function2, etc)

  /*
    1. a function which takes 2 strings and concatenates them
    2. Transform the MyPredicate and MyTransformer into function types
    3. Define a function which takes an Int and returns another function which takes an Int and returns an Int
      - What's the type of this function?
      - How to do it?
   */

  // 1. a function which takes 2 strings and concatenates them
  val concatenator = new ((String, String) => String) {
    override def apply(string1: String, string2: String): String = string1 + string2
  }

  println(concatenator("first ", "second"))


  // 2. Transform the MyPredicate and MyTransformer into function types

  // MyPredicate can be replace with Function1[Int, Boolean] or just (Int => Boolean)
  trait MyPredicate[-T] {
    def test(element: T): Boolean
  }

  // predicate: MyPredicate[A] >> predicate: A => Boolean
  // predicate.test(_) >> predicate(_)

  trait MyTransformer[-A, B] {
    def transform(element: A): B
  }

  // transformer: MyTransformer[-A, B] >> transformer: A => B
  // transformer.transform(_) >> transformer(_)

  // 3. Define a function which takes an Int and returns another function which takes an Int and returns an Int

  val intToFunction =  new (Int => (Int => Int)) {
    override def apply(v1: Int): Int => Int = new (Int => Int) {
      override def apply(v2: Int): Int = v1 + v2
    }
  }

  println(intToFunction(2)(2))
  // Solution

  val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val adder34 = superAdder(3)
  println(adder34(4))
  println(superAdder(3)(2)) // curried function, Can be called with multiple parameter list
}
