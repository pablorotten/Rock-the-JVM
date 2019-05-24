package lectures.part2oop

object AbstractDataTypes extends App{

  // abstract
  abstract class Animal {
    val creatureType: String
    val secondaryCreatureType: String = "wild"
    def eat: Unit
//    def eat1: Unit
  }

//  val animal: new Animal // can's instantiate an abstract class
  class Dog extends Animal {
    // the override keyword is not mandatory, is implicit since Dog extends Animal and the creatureType attribute and the eat method are not defined
    val creatureType: String = "Canine"
    def eat: Unit = println("crunch crunch")
  }

  // traits
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal: String = "fresh meat"
  }

  trait ColdBlooded

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    val creatureType: String = "croc"
    override val secondaryCreatureType: String = "reptile" // This NEEDS the 'override' because secondaryCreatureType is already defined in Animal
    def eat: Unit = println("nomnomnom") // Here overrides the class Animal
    def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}") // Here overrides the trait Carnivore
  }

  val dog = new Dog
  val croc = new Crocodile
  croc eat dog

  // Traits vs Abstract classes
  // 1 - traits do not have constructor parameters
  // 2 - multiple traits may be inherited by the same class, but only can extend 1 abstract class
  // 3 - traits = behaviors, abstract = "thing"
}
