# Rock the JVM: Scala for beginners

## Section 2: Object-Oriented Programming in Scala
### 10. 11. Object-Oriented Basics
#### Creating a class
* The constructor **parameters** can be **fields** if they have **val/var**
* Can create a **block {...}** that will be evaluated in every instantiation
* Access to params, fields and methods:
    * Can access to **methods** and **fields** using their names
        * Fields don't need getter/setter methods
        * Methods without parameters can be accessed without parenthesis()
    * Can't access to the **parameters** if they are not **fields**
    * Internally, we can still access to the **parameters** (use **this.parameter** if necessary)
    
```scala
class Class(parameter: String, val field1: Int = 0) {
  val field2 = field1 + 1
  var field3 = parameter
  // this.parameter is not a class field but is still available inside the class
  def method(parameter: String): Unit = println(s"Class parameter: ${this.parameter} Method param: $parameter")
  // this,parameter needs a getter to be accessed.
  // It would be better to just make parameter a field: val parameter
  def getConstructorParam = parameter 
}
// Using the Class
val clazz = new Class("param", 31)
clazz.field1 //accessing to constructor field
clazz.field2 //accessing to class field 
clazz.parameter // Error: parameter is class parameter not field
clazz.getConstructorParam //accessing to class parameter through getter
clazz.method("other param")
```

#### Overriding
* Can override methods and the constructor
* The constructor can only be overriden by another constructor, a different code is not allowed

```scala
class Class(parameter: String, val field1: Int = 0) {
  var field2 = parameter
  // Overriding constructor
  def this(otherParameter: String) = this(parameter, 1) // calls main constructor with 'otherParameter' and '1'
  def this() = this("Second constructor") // calls secondary constructor
  
  // Overriding methods
  def method(): Unit = println(s"Class parameter: ${parameter}")
  def method(parameter: String): Unit = println(s"Class parameter: ${this.parameter} Method param: $parameter")
}
  val clazz1 = new Class("Using second constructor")// using second constructor
  val clazz2 = new Class()// using third constructor
  clazz2.method("Using overridden method")
```
#### Good practices: Immutability and DRY
* **Immutability** is when, instead of changing the instance, return a new instance with the new values
* **DRY** in this case means, that if you override any method, try to use recursion instead repeating the same as the overridden one

```scala
class Person(val name: String, var age: Int) {
  // Gets 1 year older
  def getOlderMutable = age = age + 1 // Side effect: changes the field 'age' of the Instance
  def getOlder = new Person(name, age + 1) // Immutable: creates a new Instance with the new 'age'
  // Gets 'age' years older
  // DRY: Overrides getOlder to get 'yearsOld' older but using the overridden function => DRY
  def getOlder(yearsOld: Int): Person = {
    if (yearsOld <= 0) this
    // 1st 'getOlder' calls the overridden method and returns 1 years older Person
    // 2nd 'getOlder' makes the recursive call but with 1 year less
    else getOlder.getOlder(yearsOld - 1) 
  }
  // Doesn't respect DRY: Creates a new Person again like the overridden method
  // Note: yearsOld should be Int
  def getOlder(yearsOld: Double): Person = new Person(name, age + yearsOld.toInt)
}
```

### 12. 13. Syntactic Sugar: Method Notations
#### "Operators" in Scala
In Scala, we can use use spaces instead of dots and parenthesis for calling fields or methods with 0 or 1 parameters.
```scala
val object = new Class(...)
object field // = object.field
object method // = object.method
object method parameter // = object.method(parameter)
```

##### Infix notation
In the case of methods with 1 parameter, the method acts like an operator because it's between 2 things and yields a 3rd thing.
```scala
val result = object method parameter // = object.method(parameter)
```

In a sum we have thing1 + thing2 = result. So the operator '+' is between 'thing1' and 'thing2' and yields 'resut'
In fact, the operator '+' is and can be used like a method:
```scala
1 + 2
1.+(2)
```
ALL OPERATORS ARE METHODS

We can also define the operator '+' between to objects
```scala
class Person(val name: String, val age: Int) {
  def +(person: Person): String = s"${this.name} is with ${person.name}"
}

val joe = new Person("Joe", 15)
val anne = new Person("Anne", 16)
joe + anne // returns "Joe is with Anne"
```

##### Postfix notation
In the case of fields and methods with 0 parameters, they act like postfix operators
```scala
val joe = new Person("Joe", 15)
joe name // = joe.name
joe learnsScala // calls the joe.learnsScala method
"45" toInt // = "45".toInt()
```
Not very used because may cause confusion reading code!!

##### Prefix notation and unary operators
An unary operator is an operator that is applied to 1 thing and yields a 2nd thing
The only ones we have in Scala are: - + ~ !
Those are also methods, and can be called using **.unary_** and the symbol
```scala
val x = -1 // The unary operator '-' is applied to '1' and returns '-1'
val x1 = !true // '!' is applied to 'true' and returns 'false'
val y = 1.unary_- // = -1
val y1 = true.unary_! // = !true
```

We can also override them in a Class
```scala
class Person(val name: String, val age: Int) {
  def unary_! = s"$name is not a Person"
}

val joe = new Person("Joe", 15)
!joe // returns "Joe is not a Person"
joe.unary_! // = !joe
```

#### Method apply()
If we define in a class the method apply, in can be called adding parenthesis to the object: **object()**
```scala
class Class(val field: String) {
  def apply(): String = s"This is the apply method of $field"
}
val object1 = new Class("Object 1")
object1.apply() // = "This is the apply method of Object 1"
object1 apply()
object1() // Just the object with parenthesis
```
This is very helpful because allows us to treat object in a functional way!

### 14. Scala Objects
#### Class level functionality
Methods and attributes that belong to the class not to the Oject. 
The **'static'** in Java, we access to the Class not to the Instance (or Object)

In java:
```java
class Person { 
  public static final int NUMBER_OF_EYES = 2;
}
Person.NUMBER_OF_EYES // Returns 2, no need to instantiate the class
```

Scala **DOES NOT HAVE** Class-Level Functionality --> NO CONCEPT OF 'static'.
The equivalent is using **OBJECTS**
```scala
object Person {
  val NUMBER_OF_EYES = 2
  def canFly: Boolean = false
}
Person.NUMBER_OF_EYES // = 2
Person.canFly // = false
```

#### Singleton
Objects in scala are also **Singletons** by definition. There's only 1 instance per **Object**
```scala
val mary = Person // Doesn't create a new Instance, just makes the val mary point to the instance/object Person
val john = Person
println(mary == john) // true, the same instance/object

val eli = new Person // Error: Cannot resolve symbol Person
```

#### Companions
The Companion Pattern in Scala is an object and a class with same **scope** and same **name**.
The purpose of this is to have the static/class-level functionality in the **Object** and the instance-level functionality in the **Class**

This is a **Companion**:
```scala
  object Person {
    // 'static'/'class'-level functionality
    val NUMBER_OF_EYES = 2
    def canFly: Boolean = false
  }

  class Person(val name: String) {
    // instance-level functionality
    def getName: String = name
  }
  
  val mary = Person // Points to the Object Instance Person
  val eli = new Person("Elisabeth") // Creates a new Instance from the class Person 
  
  mary.canFly // = 2
  mary.getName // Error: The Object cannot access to a instance-level Method
  
  eli.canFly // Error: The Instance cannot access to a class-level Method
  eli.getName // = 0
```

#### Factory Method
This pattern is implemented with a method whose purpose is **generate instances** of a class using the given parameters
In Scala we do it using the **apply()** method in the **Object Companion** and generating instances of the **Class Companion**:

```scala
object Person {
  // Generates new Instances of the Class Person
  def apply(mother: Person, father: Person): Person = new Person("Bobbie")
}

class Person(val name: String) {
  def getName: String = name
}

val mary = new Person("Mary")
val john = new Person("John")

// Using factory method of Object Person
val bobbie = Person(mary, john)
```

#### Main method
The JVM needs the main function as entry point.
In java we have the `'static void main(String args[])'` method:
```java
public class JavaApp {
  public static void main(String args[]) {
    // your code here
  }
}
```
If we translate this to Scala, the main function would be like this:
```scala
object ScalaApp{
  def main(args: Array[String]): Unit = {
    // your code here
  }
}
```
* object = static
* Unit = void

We can also just extend App and then the object will have implicit the main method
```scala
object ScalaApp extends App {
  // your code here
}
```