package lectures.part2oop

object Generics extends App{

  class MyListExample[A] { // also trait MyListExample[A]
    // use the type A
  }

  class MyMap[Key, Value]

  val listOfIntegers = new MyListExample[Int]
  val listOfString = new MyListExample[String]

  // generic methods
  object MyListExample {
    def empty[A]: MyListExample[A] = ???
  }

  val emptyListOfIntegers = MyListExample.empty[Int]

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal
  // Question: if Cat extends Animal, does a List of Cats extends a List of Animals??? <= List[Cat] extends List[Animal]?
  // 3 Possible answers:

  // 1. YES, List[Cat] extends List[Animal] <= COVARIANCE
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog) //  This is OK ??? HARD QUESTION => We return a list of animal

  // 2. NO, List[Cat] and List[Animal] are 2 separate things <= INVARIANCE
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal] // new InvariantList[Cat] will fail

  // 3. Hell, NO!!! <= CONTRAVARIANCE
  class ContravariantList[-A]
  val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]

  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  // Bounded types
  class Cage[A <: Animal](animal: A) // only accepts subtypes of Animal
  class Cage1[A >: Animal](animal: A) // supertypes of Animal
  val cage = new Cage(new Animal)
  val cage1 = new Cage(new Dog)

  class Car
  // val newCage = new Cage(new Car) // Throws an error because Car is not an Animal

  // Using COVARIANCE
  class MyListCovariance[+A] {
    // If in the list of A, add a element of B, which is a superclass of A; Then the list of A will turn into a list of B (more generic)
    def add[B >: A](element: B): MyListCovariance[B] = ???
    /*
    A = Cat
    B = Dog => Animal
    If I add a Dog (Animal) in a list of Cats, then I will have a List of Animals
    */
  }

  class AnimalList[+A] {
    def add[B >: A](element: B): AnimalList[B] = ???
  }

  val michu: Cat = new Cat
  val fray: Dog = new Dog
  val monchito: Cat = new Cat
  val catList = new AnimalList[Cat].add(michu) // List of cat
  val animalList1 = catList.add(fray) // List of cat + dog = List of Animals
  val animalList2 = animalList1.add(monchito) // List of cat + dog + cat = still List of Animals
}
