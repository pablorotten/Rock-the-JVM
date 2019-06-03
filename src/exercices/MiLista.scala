package exercices


import scala.annotation.tailrec

/*
  List of integers
  head = first element of the list
  tail = remainder of the list
  isEmpty = is this list empty
  add(int) = new list with this element added
  toString = a string representation of the list
 */
abstract class MiLista(val firstElement: ListElement, val lastElement: ListElement) {
  def head: Int
  def tail: MiLista
  def isEmpty: Boolean
  def add(newNumber: Int): MiLista
  def toString: String
}

class ListElement(val value: Int, var nextElement: ListElement = null) {
}

class MiListaImplementation(firstElement: ListElement = null, lastElement: ListElement = null) extends MiLista(firstElement, lastElement) {
  def head: Int = firstElement.value
  def tail: MiListaImplementation = new MiListaImplementation(firstElement.nextElement, lastElement)
  def isEmpty: Boolean = if (firstElement == null) true else false

  def add(newNumber: Int): MiListaImplementation = {
    val newElement: ListElement = new ListElement(newNumber)
    if(firstElement == null)
      new MiListaImplementation(newElement, newElement)
    else {
      lastElement.nextElement = newElement
      new MiListaImplementation(firstElement, newElement)
    }

  }

  override def toString: String = {
    @tailrec
    def makeString(printElement: ListElement, accumulator: String = ""): String = {
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

object TestMiLista extends App{
  val testList = new MiListaImplementation
  val testList1 = testList.add(1).add(2).add(3)
  println(testList1)
}