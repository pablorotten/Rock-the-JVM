package lectures.part2oop

object Objects extends App{

  // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY ('static')
  object Person { // type and only instance
    // 'static'/'class'-level functionality
    val NUMBER_OF_EYES = 2
    def canFly: Boolean = false

    // factory method pattern: his purpose is to generate Persons with the given parameters
    def from(mother: Person, father: Person): Person = new Person("Bobbie")
    def apply(mother: Person, father: Person): Person = new Person("Bobbie") // by convention, we use the apply method for the factory method

  }

  class Person(val name: String) {
    // instance-level functionality
    def getName: String = name
  }
  // This pattern of object + class is called COMPANIONS: Same scope and same name

  Person.NUMBER_OF_EYES
  Person.canFly

  // Scala object = SINGLETON INSTANCE
  val eli = Person // Doesn't create a new Instance, just makes the val 'eli' point to the object/only instance Person
  val paul = Person
  println(eli == paul) // true: same instance/object

  val mary = new Person("Mary") // Creates a new Instance of the class Person
  val john = new Person("John")
  println(mary == john) // false: different instances

  // Using factory method of Object Person
  val bobbie = Person.from(mary, john)
  val other_bobbie = Person(mary, john) // Uses the apply method

  // Scala Applications = Scala object with the very particular method 'def main(args: Array[String]): Unit'
  // The reason is JVM: the main function must have the java like 'public static void main(String args[])'
  // That in Scala the translation is: 'static' -> Object, 'void' -> Unit,

  // We can create an Runnable Object that, instead extending App, implements the main function
  object ObjectMain{
    // implements the main
    def main(args: Array[String]): Unit = {
      // your code here
    }
  }

  // Of course is cleaner to just extend App and have the main method implicit
  object ObjectApp extends App {
    // your code here
  }

}
