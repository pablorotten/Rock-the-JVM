package lectures.part3fp

import scala.annotation.tailrec

/**
  * Created by Daniel.
  */
object TuplesAndMaps extends App {

  // tuples = finite ordered "lists"
  val aTuple = (2, "hello, Scala")  // Tuple2[Int, String] = (Int, String)

  println(aTuple._1)  // 2
  println(aTuple.copy(_2 = "goodbye Java"))
  println(aTuple.swap)  // ("hello, Scala", 2)

  // Maps - keys -> values
  val aMap: Map[String, Int] = Map()

  val phonebook = Map(("Jim", 555), "Daniel" -> 789, ("JIM", 9000)).withDefaultValue(-1)
  // a -> b is sugar for (a, b)
  println(phonebook)

  // map ops
  println(phonebook.contains("Jim"))
  println(phonebook("Mary"))

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing
  println(newPhonebook)

  // functionals on maps
  // map, flatMap, filter
  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(phonebook.filterKeys(x => x.startsWith("J")))
  // mapValues
  println(phonebook.mapValues(number => "0245-" + number))

  // conversions to other collections
  println(phonebook.toList)
  println(List(("Daniel", 555)).toMap)
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))

  /*
    1.  What would happen if I had two original entries "Jim" -> 555 and "JIM" -> 900 and run
        this:   println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

        !!! careful with mapping keys.

    2.  Overly simplified social network based on maps
        Person = String
        - add a person to the network
        - remove
        - friend (mutual)
        - unfriend

        - number of friends of a person
        - person with most friends
        - how many people have NO friends
        - if there is a social connection between two people (direct or not)
   */

  // 1. First Jim will be overridden
  val testMap = Map("Jim" -> 555,"JIM" -> 900)
  println(testMap.map(pair => pair._1.toLowerCase -> pair._2))

  // 2.
  println("---------- exercise 2.1 ----------")
  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    network + (person -> Set())
  }

  def friend(network: Map[String, Set[String]], a: String, b: String) = {
    network + (a -> (network(a) + b)) + (b -> (network(b) + a))
  }

  def unfriend(network: Map[String, Set[String]], a: String, b: String) = {
    network + (a -> (network(a) - b)) + (b -> (network(b) - a))
  }

  def remove(network: Map[String, Set[String]], person: String) = {
    val newNetwork = network.filterKeys(_ != person)
    newNetwork.mapValues(_ - person)
  }

  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Bob"), "Mary")
  println("network:" + network)
  println(friend(network, "Bob", "Mary"))
  println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  // Jim,Bob,Mary
  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println("testNet:" + testNet)

  println("---------- exercise 2.2 ----------")

  def nFriends(network: Map[String, Set[String]], person: String) = {
    if (!network.contains(person)) 0
    else network(person).size
  }

  println(nFriends(testNet, "Bob"))

//  def maxFriends(network: Map[String, Set[String]]) = {
//    network.reduce((x, y) => x._2.size > y._2.size)
//  }

  def mostFriends(network: Map[String, Set[String]]) = {
    network.maxBy(pair => pair._2.size)._1
  }

  println(mostFriends(testNet))

  def nPeopleWithNoFriends(network: Map[String, Set[String]]) = {
    network.filter(_._2.size == 0).size
  }

  println(nPeopleWithNoFriends(testNet))

  def socialConnection(network: Map[String, Set[String]], user1: String, user2: String) = {
    (network(user1) ++ network(user1).flatMap(myFriend => network(myFriend))).contains(user2)
  }

  println(socialConnection(testNet, "Mary", "Jim"))
  println(socialConnection(network, "Mary", "Bob"))
}