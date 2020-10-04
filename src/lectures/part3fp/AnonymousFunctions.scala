package lectures.part3fp

object AnonymousFunctions extends App{

  val doubler = new Function1[Int, Int] {
    override def apply(x: Int): Int = x * 2
  }

  // anonymous function (Lambda λ)
  val doubler2 = (x: Int) => x * 2
  // shorter
  val doubler3: Int => Int = x => x * 2
  // multiple params in a lambda (need parenthesis)
  val added: (Int, Int) => Int = (a: Int, b: Int) => a + b

  // no params
  val justDoSomething: () => Int = () => 3

  // careful: Calling lambdas without params always with parenthesis!!!
  println(justDoSomething) // function itself
  println(justDoSomething()) // call

  // curly braces with lambdas
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // MOAR syntactic sugar
  val niceIncrementer: Int => Int = _ + 1 // equivalent to (x: Int) => x + 1
  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a, b) => a + b

  /*
    1. MyList: replace all FunctionX calls with lambdas
   */
/**
  listOfIntegers.map(new Function1[Int, Int] { override def apply(element: Int): Int = element * 2 })
  listOfIntegers.map(element => element * 2)
  listOfIntegers.map(_ * 2)

  listOfIntegers.filter(new Function1[Int, Boolean] { override def apply(element: Int): Boolean = element % 2 == 0 })
  listOfIntegers.filter(element => element % 2 == 0)
  listOfIntegers.filter(_ % 2 == 0)

  // Can't use _ because 'element' is used twice
  listOfIntegers.flatMap(new Function1[Int, MyExpandedList[Int]] {
    override def apply(element: Int): MyExpandedList[Int] = new ConsExpandedList(element + 1,  new ConsExpandedList(element + 1, EmptyExpandedList))
  })
  listOfIntegers.flatMap((element: Int) => new ConsExpandedList(element + 1,  new ConsExpandedList(element + 1, EmptyExpandedList)))
  listOfIntegers.flatMap(element => new ConsExpandedList(element + 1,  new ConsExpandedList(element + 1, EmptyExpandedList)))
*/
  /*
    2. Rewrite the "special" adder as an anonymous function
   */
  val superAdder = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int) = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val superAdderAnonFunc = (x: Int) => (y: Int) => x + y
  println(superAdderAnonFunc(5)(6))
}
