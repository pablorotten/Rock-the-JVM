package lectures.part2oop

object InheritanceAndTraits extends App {

  // single class inheritance
  sealed class Animal { // Sealed means that this class can be only overridden in this FILE
    val creatureType = "wild"

    // Access modifiers
    private def eat1 = println("nom") // only accessed by Animals in Class level
    protected def eat2 = println("nomnom") // can be accessed by Animals and Subclasses in Class level
    def eat3 = println("nomnomnom") // Can be accessed everywhere
    final def eat4 = println("nomnomnomnom") // This final method cannot be overridden, can also be used for classes
  }


  val animal = new Animal
  animal.eat3 // eat1 and eat2 are inaccessible for an Animal instance

  class Cat extends Animal {
    override def eat3 = println("slurp slurp slurp")
    def crunch = {
      eat2
      println("crunch crunch")
    }
  }

  val cat = new Cat
  cat.eat3 // eat1 and eat2 are inaccessible for Cat
  cat.crunch

  // Constructor
  class Person(name: String, age: Int) { // Person class with Constructor
    def this(name: String) = this(name, 0) // Secondary constructor
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name, age) // Adult needs to call the Person constructor
  class Adult1(name: String, age: Int, idCard: String) extends Person(name) // Adult1 is using the secondary constructor

  // Overriding
  class Dog(override val creatureType: String = "domestic") extends Animal { // Overriding Animal 'creatureType' attribute in the constructor
    // Overriding public and protected methods of Animal
    override def eat2 = println("crunch crunch") // overriding the protected eat2 and make it public. So now, a Dog instance can eat2, but still an Animal install can't
    override def eat3 = {
      super.eat3 // calling the superClass method, in this case Animal eat3
      println("crunch crunch crunch")
    }
  }

  class Dog1(dogType: String = "domestic") extends Animal {
    override val creatureType = dogType  // Overriding Animal attribute in the class definition using a parameter
  }

  val dog = new Dog
  dog.eat2
  dog.eat3
  println(dog.creatureType)

  // type substitution: polymorphism
  val unknownAnimal: Animal = new Dog("K9")
  val unknownAnimal1: Animal = new Cat()
  unknownAnimal.eat3 // unknownAnimal is an Animal and a Dog. So we can call the Animal method eat3 but it will use the overridden Dog implementation
  unknownAnimal1.eat3 // this unknownAnimal is a Cat, will call the Cat eat3 implementation
}
