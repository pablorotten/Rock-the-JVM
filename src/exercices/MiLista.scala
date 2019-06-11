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
abstract class MiGenericLista {
  def head: Int
  def tail: MiGenericLista
  def isEmpty: Boolean
  def add(element: Int): MiGenericLista
  def printElements: String
  override def toString: String = "[" + printElements + "]"
}

object EmptyGenericLista extends MiGenericLista {
  def head: Int = throw new NoSuchElementException
  def tail: MiGenericLista = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Int): MiGenericLista = new ConsGenericLista(element, EmptyGenericLista)
  override def printElements: String = ""
}

class ConsGenericLista(h: Int, t: MiGenericLista) extends MiGenericLista {
  def head: Int = h
  def tail: MiGenericLista = t
  def isEmpty: Boolean = false
  def add(element: Int): MiGenericLista = new ConsGenericLista(element, this)
  override def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
}

object TestMiGenericLista extends App{
  val list = new ConsGenericLista(1, new ConsGenericLista(2, new ConsGenericLista(3, EmptyGenericLista)))
  println(list)
  println(list.add(0))
}