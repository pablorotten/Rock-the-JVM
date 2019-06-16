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