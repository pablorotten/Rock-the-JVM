package lectures.part3fp

object MapFlatmapFilterFor extends App {

  val list = List(1, 2, 3)
  println(list)
  println(list.head)
  println(list.tail)

  // map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  // print all combinations between two lists
  val numbers = List(1, 2)
  val chars = List('a', 'b')
  val animals = List("🐯", "🦄")

  val combinations = numbers.flatMap(number => chars.map(char => s"$number$char"))
  val combinations2 = numbers.flatMap(number => chars.flatMap(char => animals.map(animal => s"$animal$number$char")))
  println(combinations)
  println(combinations2)

  // foreach
  numbers.foreach(println)

  // for-comprehensions
  val forCombinations = for {
    number <- numbers if number % 2 == 0 //guard to filter even numbers
    char <- chars
    animal <- animals
  } yield s"$animal$number$char"
  println(forCombinations)

  for {
    n <- numbers
  } println(n) // for comprehension for numbers.foreach(println)

  // syntax overload
  list.map { x =>
    x * 2
  }

  /*
    1. MyList supports for comprehensions?
      Those signatures are needed:
      map(f: A => B) => MyList[B]
      filter(p: A => Boolean) => MyList[A]
      flatMap(f: A => MyList[B]) => MyList[B]

    2. A small collection of a most ONE element - Maybe[+T]
      - map, flatMap, filter
   */
}
