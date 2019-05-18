package lectures.part2oop

object OOBasics extends App{

  val person = new Person("John", 26) // using the constructor for instantiate a class
  // accessing to person fields
  println(person.age)
  println(person.valField)
  println(person.first_name)

  person.greet("Daniel")
}

class Person(name: String, val age: Int = 0) { // Constructor. Class parameters can be fields if we put a val/var before
  // body: Implementation of the class Person. This will be evaluated in every instantiation
  val valField = 2 // field
  var varField = "new varField"
  val first_name = name

  println("Person's body block!!")

  // method of the class Person.
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name") // this.name is not a class field but is still avail able with the class definition

  // overloading: Same method with different parameters
  def greet(): Unit = println(s"Hi. I am $name") // This name is the same as this.name of above. But this time this is not needed because there's only 1 name

  // multiple constructors. Secondary constructors can only be calls to other constructors
  def this(name: String) = this(name, 0) // calls main constructor
  def this() = this("John Doe") // call previous secondary constructor
}
// Exercises
/*
  Novel and a Writer

  Writer: first name, surname, year
    method fullname

  Novel: name, year of release, author
  - authorAge // age of the author at the year release
  - IsWrittenBy(author)
  - copy(new year of release) = new instance of Novel with a new year release
 */

class Writer(val firstName: String, val surname: String, val year: Int) {
  def fullName: String = s"$firstName $surname"
}

class Novel(val name: String, val releaseYear: Int, val author: Writer) {
  val authorAge = releaseYear - author.year
  def isWrittenBy(author: Writer): Boolean = this.author == author
  def copy(newYear: Int) = new Novel(this.name, newYear, this.author)
}

object testNovelAndWriter extends App {
  val writer = new Writer("Name", "Surname", 20)
  print(writer.fullName)
}

/*
  Counter class
    - receives an Int value
    - method current count
    - method to increment/decrement => new Counter incremented or decremented by 1
    - overload increment/decrement to receive an amount => new Counter incremented or decremented by amount
 */

class Counter(val value: Int) {
//def currentCount = value // not needed since "value" is a parameter + val, so it's a field. The getter is implied, otherwise we should use this function to get it
  def increment = new Counter(value + 1) // Immutability: Don't modify anything, just create a new instance with the new value. Instances are fixed
  def increment(amount: Int) = new Counter(value + amount) // Side effect: Changes the value of the instance
  def decrement = new Counter(value - 1)
  def decrement(amount: Int) = new Counter(value - amount)
}

// Use recursion and Don't Repeat Yourself!!
class CounterDRY(val value: Int) {
  def increment = {
    println("incrementing")
    new CounterDRY(value + 1)
  }
  def increment(amount: Int): CounterDRY = {
    if (amount <= 0) this
    else increment.increment(amount - 1)
  }
  def decrement = {
    println("decrementing")
    new CounterDRY(value - 1)
  }
  def decrement(amount: Int): CounterDRY = {
    if (amount <= 0) this
    else decrement.decrement(amount + 1)
  }
}

// A Person with and without DRY and Immutability
class Person1(val name: String, var age: Int) {
  // Gets 1 year older
  def getOlderMutable = age = age + 1 // Side effect: changes the field 'age' of the Instance
  def getOlder = new Person1(name, age + 1) // Immutability: creates a new Instance with the new 'age'
  // Gets 'age' years older
  // DRY: Overrides getOlder to get 'yearsOld' older but using the overridden function => DRY
  def getOlder(yearsOld: Int): Person1 = {
    if (yearsOld <= 0) this
    // 1st 'getOlder' calls the overridden method and returns 1 years older Person1
    // 2nd 'getOlder' makes the recursive call but with 1 year less
    else getOlder.getOlder(yearsOld - 1)
  }
  // Not DRY: Creates a new Person1 again like the overridden method
  // Note: yearsOld should be Int
  def getOlder(yearsOld: Double): Person1 = new Person1(name, age + yearsOld.toInt)
}

object exercises extends App{

  val person = new Person1("John", 26) // using the constructor for instantiate a class
  // accessing to person fields
  println(person.age)
  println(person.getOlder.age)
  println(person.getOlder(10).age)
  val counter = new CounterDRY(10)
  println("final count " + counter.increment(5).value)
}
