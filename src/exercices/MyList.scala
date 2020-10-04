package exercices

/**
  *
  Create MyList: a List of integers
  head = first element of the list
  tail = remainder of the list
  isEmpty = is this list empty
  add(int) = new list with this element added
  toString = a string representation of the list
  */
abstract class MyIntList {
  def head: Int
  def tail: MyIntList
  def isEmpty: Boolean
  def add(element: Int): MyIntList
  def printElements: String
  override def toString: String = "[" + printElements + "]"
}

object EmptyIntList extends MyIntList {
  def head: Int = throw new NoSuchElementException
  def tail: MyIntList = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Int): MyIntList = new ConsIntList(element, this) // adds in the beginning and puts the current list in the tail
  override def printElements: String = ""
}

class ConsIntList(h: Int, t: MyIntList) extends MyIntList {
  def head: Int = h
  def tail: MyIntList = t
  def isEmpty: Boolean = false
  def add(element: Int): MyIntList = new ConsIntList(element, this) // adds in the beginning and puts the current list in the tail
  override def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
}

object TestMyIntList extends App{
  val list = new ConsIntList(1, new ConsIntList(2, new ConsIntList(3, EmptyIntList)))
  println(list)
  val listZero = list.add(0)
  println(list.add(0))
  println(EmptyIntList.add(1).add(34))
  println(new ConsIntList(1, EmptyIntList).add(1213).add(12345))
}

/**
  * Make MyInyList Generic
  */

abstract class MyGenericList[+A] {
  def head: A
  def tail: MyGenericList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyGenericList[B]
  def printElements: String
  override def toString: String = "[" + printElements + "]"
}

/*
 Nothing' is the subtype of Everything. So the list will be a list of the lowest Supertype that is needed
 */
object EmptyGenericList extends MyGenericList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyGenericList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyGenericList[B] = new ConsGenericList(element, this) // adds in the beginning and puts the current list in the tail
  override def printElements: String = ""
}

class ConsGenericList[+A](h: A, t: MyGenericList[A]) extends MyGenericList[A] {
  def head: A = h
  def tail: MyGenericList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyGenericList[B] = new ConsGenericList(element, this) // adds in the beginning and puts the current list in the tail
  override def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
}

object TestMyGenericList extends App{
  val list = new ConsGenericList(1, new ConsGenericList(2, new ConsGenericList(3, EmptyGenericList)))
  println(list)
  val listZero = list.add(0)
  println(list.add(0))
  println(EmptyGenericList.add(1).add(34))
  println(new ConsGenericList(1, EmptyGenericList).add(1213).add(12345))

  val genericList = new ConsGenericList(1, new ConsGenericList(2.0, new ConsGenericList("TESTING", EmptyGenericList)))

  // The interpreter chooses the lowest Supertype that is needed
  val intList = new ConsGenericList(3, new ConsGenericList(2, new ConsGenericList(1, EmptyGenericList)))
  val longList = intList.add(4L)
  val doubleList = longList.add(5.0)
  val anyValList = doubleList.add(true)
  val anyList = anyValList.add("Seven")
  println(anyList)
}

/**
20. Object-Oriented Exercises: Expanding Our Collection

  1. Generic trait MyPredicate[-T]: With a method test(T) if a value of type T passes a condition. Any subclass of MyPredicate[T] will have to implement this method
  2. Generic trait MyTransformer[-A, B]: With a method transform(A) => B to convert a value of type A into a value of type B
  3. Implement functions on MyList:
      - map(transformer) => MyList
      - filter(predicate) => MyList
      - flatMap(transformer from A to MyList[B]) => MyList[B]

    class EvenPredicate extends MyPredicate[Int]
    class StringToIntTransformer extends MyTransformer[String, Int]

    [1,2,3].map(n * 2) = [2,4,6]
    [1,2,3,4].filter(n % 2) = [2,4]
    [1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
*/

trait MyPredicate[-T] {
  def test(element: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(element: A): B
}

abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String
  override def toString: String = "[" + printElements + "]"

//  def map[B](transformer: MyTransformer[A, B]): MyExpandedList[B]
//  def flatMap[B](transformer: MyTransformer[A, MyExpandedList[B]]): MyExpandedList[B]
//  def filter(predicate: MyPredicate[A]): MyExpandedList[A]
  // Converted into functions:
  def map[B](transformer: (A => B)): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]

  // concatenation
  def ++[B >: A](list: MyList[B]): MyList[B]
}

/*
 Nothing' is the subtype of Everything. So the list will be a list of the lowest Supertype that is needed
 */
case object EmptyList$ extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new ConsList(element, this) // adds in the beginning and puts the current list in the tail
  override def printElements: String = ""

//  def map[B](transformer: MyTransformer[Nothing, B]): MyExpandedList[B] = EmptyExpandedList
//  def flatMap[B](transformer: MyTransformer[Nothing, MyExpandedList[B]]): MyExpandedList[Nothing] = EmptyExpandedList
//  def filter(predicate: MyPredicate[Nothing]): MyExpandedList[Nothing] = EmptyExpandedList
  // Converted into functions:
  def map[B](transformer: Nothing => B): MyList[B] = EmptyList$
  def flatMap[B](transformer: Nothing => MyList[B]): MyList[Nothing] = EmptyList$
  def filter(predicate: Nothing => Boolean): MyList[Nothing] = EmptyList$

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
}

case class ConsList[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = new ConsList(element, this) // adds in the beginning and puts the current list in the tail
  override def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements

  /*
    [1,2,3].filter(n % 2) == 0 =
    [2,3].filter(n % 2) == 0 =
    = new Cons(2, [3].filter(n % 2) == 0))
    = new Cons(2, Empty.filter(n % 2) == 0))
    = new Cons(2, Empty)
   */
  override def filter(predicate: A => Boolean): MyList[A] =
    if (predicate(h)) new ConsList(h, t.filter(predicate))
    else t.filter(predicate)

  /*
    [1,2,3].map(n * 2)
      = new Cons(2, [2,3].map(n * 2))
      = new Cons(2, new Cons(4, [3].map(n * 2)))
      = new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
      = new Cons(2, new Cons(4, new Cons(6, Empty))))
   */
  override def map[B](transformer: A => B): MyList[B] =
    new ConsList(transformer(h), tail.map(transformer))
  /*
    [1,2] ++ [3,4,5]
    = new Cons(1, [2] ++ [3,4,5]
    = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
    = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
   */
  override def ++[B >: A](list: MyList[B]): MyList[B] = new ConsList(h, t ++ list)

  /*
    [1,2].flatMap(n => [n, n+1])
    = [1,2] ++ [2].flatmap(n > [n, n+1])
    = [1,2] ++ [2,3] ++ Empty.flatmap(n > [n, n+1])
    = [1,2] ++ [2,3] ++ Empty
    = [1,2,2,3]
   */
  override def flatMap[B](transformer: A => MyList[B]): MyList[B] =
    transformer(h) ++ t.flatMap(transformer)
}

object TestMyExpandedList extends App{
  val listOfIntegers: MyList[Int] = new ConsList(1, new ConsList(2, new ConsList(3, EmptyList$)))
  val cloneListOfIntegers: MyList[Int] = new ConsList(1, new ConsList(2, new ConsList(3, EmptyList$)))
  val anotherListOfIntegers: MyList[Int] = new ConsList(4, new ConsList(5, EmptyList$))
  val listOfStrings: MyList[String] = new ConsList("Hello", new ConsList("Scala", EmptyList$))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map(new Function1[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }))
  // equivalents
  println(listOfIntegers.map((element: Int) => element * 2))
  println(listOfIntegers.map(_ * 2))

  println(listOfIntegers.filter(new Function1[Int, Boolean] {
    override def apply(element: Int): Boolean = element % 2 == 0
  }))
  // equivalent
  println(listOfIntegers.filter((element: Int) => element % 2 == 0))
  println(listOfIntegers.filter(_ % 2 == 0))

  println(listOfIntegers ++ anotherListOfIntegers)
  println(listOfIntegers.flatMap(new Function1[Int, MyList[Int]] {
    override def apply(element: Int): MyList[Int] = new ConsList(element + 1, new ConsList(element + 1, EmptyList$))
  }))
  // equivalent
  println(listOfIntegers.flatMap((element: Int) => new ConsList(element + 1,  new ConsList(element + 1, EmptyList$))))
  println(listOfIntegers.flatMap(element => new ConsList(element + 1,  new ConsList(element + 1, EmptyList$))))

  println(cloneListOfIntegers == listOfIntegers)
}
