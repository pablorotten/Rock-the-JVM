package exercices

/*
  Create MyList:, a List of integers
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
  def add(element: Int): MyIntList = new ConsIntList(element, EmptyIntList)
  override def printElements: String = ""
}

class ConsIntList(h: Int, t: MyIntList) extends MyIntList {
  def head: Int = h
  def tail: MyIntList = t
  def isEmpty: Boolean = false
  def add(element: Int): MyIntList = new ConsIntList(element, this)
  override def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
}

object TestMyIntList extends App{
  val list = new ConsIntList(1, new ConsIntList(2, new ConsIntList(3, EmptyIntList)))
  println(list)
  println(list.add(0))
}