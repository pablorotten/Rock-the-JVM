package lectures.part2oop

object AnonymousClasses extends App{

  abstract class Animal {
    def eat: Unit
  }

  // anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("hahhahaha")
  }

  /*
    equivalent with:
    class AnonymousClasses$$anon$1 extends Animal {
      override def eat: Unit = println("hahhahaha")
    }
    val funnyAnimal: Animal = new AnonymousClasses$$anon$1
   */

  println(funnyAnimal.getClass)

  class Person(name: String) {
    def sayHi: Unit = println(s"Hi, my name is $name, how can I help?")
  }

  val jim = new Person("Jim") {
    override def sayHi: Unit = println(s"Hi, my name is Jim, how can I help?")
  }

  //////////////

  abstract class AbstractClass(parameter: String) {
    val attribute: Any
    def method: Unit
  }

  trait Trait {
    val attribute: Any
    def method: Unit
  }

  class NormalClass(parameter: String) {
    val attribute: Any = ???
    def method: Unit = ???
  }

  val anonymousClass1: AbstractClass = new AbstractClass(parameter = "Parameter") {
    override val attribute: Any = "Attribute"
    override def method: Unit = println("Method")
  }
  val anonymousClass2: Trait = new Trait {
    override val attribute: Any = "Attribute"
    override def method: Unit = println("Method")
  }
  val anonymousClass3: NormalClass = new NormalClass(parameter = "Parameter") {
    override val attribute: Any = "Attribute"
    override def method: Unit = println("Method")
  }
}
