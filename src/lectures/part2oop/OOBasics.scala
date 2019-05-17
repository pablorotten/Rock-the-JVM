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


// class parameters are NOT FIELDS
