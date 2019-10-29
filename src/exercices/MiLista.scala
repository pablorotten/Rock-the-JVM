package exercices

import scala.annotation.tailrec

/**
  * My version of the exercise MyList
  *
  * List of integers
  * head = first element of the list
  * tail = remainder of the list
  * isEmpty = is this list empty
  * add(int) = new list with this element added
  * toString = a string representation of the list
  */
abstract class MiListaInt(val firstElement: ListaIntElement, val lastElement: ListaIntElement) {
  def head: Int
  def tail: MiListaInt
  def isEmpty: Boolean
  def add(newNumber: Int): MiListaInt
  def toString: String
}

class ListaIntElement(val value: Int, var nextElement: ListaIntElement = null) {
}

class MiListaIntImplementation(firstElement: ListaIntElement = null, lastElement: ListaIntElement = null) extends MiListaInt(firstElement, lastElement) {
  def head: Int = firstElement.value
  def tail: MiListaIntImplementation = new MiListaIntImplementation(firstElement.nextElement, lastElement)
  def isEmpty: Boolean = if (firstElement == null) true else false

  def add(newNumber: Int): MiListaIntImplementation = {
    val newElement: ListaIntElement = new ListaIntElement(newNumber)
    if(firstElement == null)
      new MiListaIntImplementation(newElement, newElement)
    else {
      lastElement.nextElement = newElement
      new MiListaIntImplementation(firstElement, newElement)
    }

  }

  override def toString: String = {
    @tailrec
    def makeString(printElement: ListaIntElement, accumulator: String = ""): String = {
      if(printElement == null) {
        accumulator
      }
      else {
        makeString(printElement.nextElement, s"$accumulator${printElement.value}, ")
      }
    }
    makeString(firstElement)
  }
}

object TestMiListaInt extends App{
  val testList = new MiListaIntImplementation
  val testList1 = testList.add(1).add(2).add(3)
  println(testList1)
}

/**
  * My version of the exercise MyList Generic
  *
  * Make MyInyList Generic
  */
abstract class MiGenericLista[+A] {
  def head: A
  def tail: MiGenericLista[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MiGenericLista[B]
  def printElements: String
  override def toString: String = "[" + printElements + "]"
}

/*
Any is Supertype of Everything, so we are starting from the top. If we use 'Any' for the Empty list,
the List will be always a list of 'Any'. This will work but is not very precise.
It's better to use 'Nothing'. So the list will be a list of the lowest Supertype that is needed
 */
object EmptyGenericLista extends MiGenericLista[Any] {
  def head: Any = throw new NoSuchElementException
  def tail: MiGenericLista[Any] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Any](element: B): MiGenericLista[B] = new ConsGenericLista(element, this)
  override def printElements: String = ""
}

class ConsGenericLista[+A](h: A, t: MiGenericLista[A]) extends MiGenericLista[A] {
  def head: A = h
  def tail: MiGenericLista[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MiGenericLista[B] = new ConsGenericLista(element, this)
  override def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
}

object TestMiGenericLista extends App {
  // The list is always a ConsGenericLista[Any] since EmptyGenericLista is a list of Any
  val intList = new ConsGenericLista(3, new ConsGenericLista(2, new ConsGenericLista(1, EmptyGenericLista)))
  val longList = intList.add(4L)
  val doubleList = longList.add(5.0)
  val anyValList = doubleList.add(true)
  val anyList = anyValList.add("Seven")
  println(anyList)
}

/**
20. Object-Oriented Exercises: Expanding Our Collection

  1. Generic trait MyPredicate[-T]: With a method test(T) if a value of type T passes a condition.
      Any subclass of MyPredicate[T] will have to implement this method test(T)
  2. Generic trait MyTransformer[-A, B]: With a method transform(A) => B to convert a value of type A into a value of type B
*/

trait MiPredicate[-T] {
  def test(condition: T): Boolean
}

class EvenPredicate extends MiPredicate[Int] {
  override def test(condition: Int): Boolean = condition % 2 == 0
}

trait MiTransformer[-A, B] {
  def transform(value: A): B
}

class StringToIntTransformer extends MiTransformer[String, Int] {
  override def transform(value: String): Int = value.toInt
}

/**
  3. Implement functions on MyList:
        - map(transformer) => MyList
        - filter(predicate) => MyList
        - flatMap(transformer from A to MyList[B]) => MyList[B]

      class EvenPredicate extends MyPredicate[Int]
      class StringToIntTransformer extends MiTransformer[String, Int]

      [1,2,3].map(n * 2) = [2,4,6]
      [1,2,3,4].filter(n % 2) = [2,4]
      [1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
*/

abstract class MyExpandedLista[+A] {
  def head: A
  def tail: MyExpandedLista[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyExpandedLista[B]
  def printElements: String
  override def toString: String = "[" + printElements + "]"
  // New MyPredicate inline that take as parameter the implementation of test. Returns the current list applying the test function to the elements
  def map[B](transformer: MyTransformer[A, B]): MyExpandedLista[B]
  // New MyPredicate inline that take as parameter the implementation of test. The elements of the current list that satisfies test
  def filter(predicate: MyPredicate[A]): MyExpandedLista[A]
  // The test do the transformation
  def flatMap[B](transformer: MyTransformer[A, MyExpandedLista[B]]): MyExpandedLista[A]
}

/*
 Nothing' is the subtype of Everything. So the list will be a list of the lowest Supertype that is needed
 */
//object EmptyExpandedLista extends MyExpandedLista[Nothing] {
//  def head: Nothing = throw new NoSuchElementException
//  def tail: MyExpandedLista[Nothing] = throw new NoSuchElementException
//  def isEmpty: Boolean = true
//  def add[B >: Nothing](element: B): MyExpandedLista[B] = new ConsExpandedLista(element, this) // adds in the beginning and puts the current list in the tail
//  override def printElements: String = ""
//
//  def map[B](transformer: MyTransformer[Nothing, B]): MyExpandedLista[B] = EmptyExpandedLista
//  def filter(predicate: MyPredicate[Nothing]): MyExpandedLista[Nothing] = EmptyExpandedLista
//  def flatMap[B](transformer: MyTransformer[Nothing, MyExpandedLista[B]]): MyExpandedLista[Nothing] = EmptyExpandedLista
//}

//class ConsExpandedLista[+A](h: A, t: MyExpandedLista[A]) extends MyExpandedLista[A] {
//  def head: A = h
//  def tail: MyExpandedLista[A] = t
//  def isEmpty: Boolean = false
//  def add[B >: A](element: B): MyExpandedLista[B] = new ConsExpandedLista(element, this) // adds in the beginning and puts the current list in the tail
//  override def printElements: String =
//    if (t.isEmpty) "" + h
//    else h + " " + t.printElements
//
//  override def filter(predicate: MyPredicate[A]): MyExpandedLista[A] =
//    if (predicate.test(head)) new ConsExpandedLista(h, t.filter(predicate))
//    else t.filter(predicate)
//
//  override def map[B](transformer: MyTransformer[A, B]): MyExpandedLista[B] =
//
//}
//
//object TestMyExpandedLista extends App{
//  val list = new ConsExpandedLista(1, new ConsExpandedLista(2, new ConsExpandedLista(3, EmptyExpandedLista)))
//  println(list)
//  val listZero = list.add(0)
//  println(list.add(0))
//  println(EmptyExpandedLista.add(1).add(34))
//  println(new ConsExpandedLista(1, EmptyExpandedLista).add(1213).add(12345))
//
//  val genericLista = new ConsExpandedLista(1, new ConsExpandedLista(2.0, new ConsExpandedLista("TESTING", EmptyExpandedLista)))
//
//  // The interpreter chooses the lowest Supertype that is needed
//  val intLista = new ConsExpandedLista(3, new ConsExpandedLista(2, new ConsExpandedLista(1, EmptyExpandedLista)))
//  val longLista = intLista.add(4L)
//  val doubleLista = longLista.add(5.0)
//  val anyValLista = doubleLista.add(true)
//  val anyLista = anyValLista.add("Seven")
//  println(anyLista)
//}