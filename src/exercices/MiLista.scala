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

/**
  * Any is Supertype of Everything, so we are starting from the top. If we use 'Any' for the Empty list,
  * the List will be always a list of 'Any'. This will work but is not very precise.
  *
  * It's better to use 'Nothing'. So the list will be a list of the lowest Supertype that is needed
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