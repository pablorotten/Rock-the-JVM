# Rock the JVM: Scala for beginners

# Section 3: Functional Programming in Scala

## What is a Function?

**ALL SCALA FUNCTIONS ARE OBJECTS** (Instances of classes Function1, Function2, etc)

```scala
trait MyFunction[A, B] {
  def apply(element: A): B
}

val doubler = new MyFunction[Int, Int] {
  override def apply(element: Int): Int = element * 2
}

println(doubler(2))
```

There're predefined Functions classes that are always used to define (instantiate) any function. The first areguments
are the function arguments and the last one is the return type.
Can be defined using `Function2[Int, Int, Int]` or with syntactic sugar: `(Int, Int) => Int`

```scala
val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
  override def apply(a: Int, b: Int): Int = a + b
}
```

### Higher order functions

Receive functions as parameters or return other functions as result

```scala
def map[B](transformer: (A => B)): MyList[B]
def flatMap[B](transformer: A => MyList[B]): MyList[B]
def filter(predicate: A => Boolean): MyList[A]
```

### Curried function

Can be called with multiple parameter list

```scala
val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
  override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] {
    override def apply(y: Int): Int = x + y
  }
}

superAdder(3)(2)
```

## Anonymous Functions (Lambdas λ)

A **lambda λ** is a function that is just defined and saved in a **val**.
They just need parameters and the function definition, return type can be inferred.
If a parameter is only used once, can be replace with underscore _

```scala
val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
  override def apply(a: Int, b: Int): Int = a + b
}

// Anonymize
val adderAnonFunc: ((Int, Int) => Int) = (a: Int, b: Int) => a + b
// Syntactic sugar
val adderAnonFuncSugar: ((Int, Int) => Int) =  _ + _
```


## Higher Order Functions (HOFs) and Curries

### Higher Order Functions (HOFs)

Accepts a function as a parameter

```scala
def nTimes(f: Int => Int, n: Int, x: Int): Int = {
  if (n <= 0) x
  else nTimes(f, n-1, f(x))
}
val plusOne = (x: Int) => x + 1

println(nTimes(plusOne, 10, 1))
```

### Curried functions

Functions with multiple parameter lists

```scala
val superAdder: Int => (Int => Int) = (x: Int) => (y: Int) => x + y
superAdder(2)(5) // = 7
```

### HOF + Curried

In standard Format we set the 1st parameter list and save the result function in standardFormat.
So standardFormat is a function that needs a parameter (the 2nd param list of curried Formatter) to be executed

```scala
def curriedFormatter(c: String)(x: Double): String = c.format(x)
val standardFormat: (Double => String) = curriedFormatter("%4.2f")
println(standardFormat(Math.PI))

def curriedAdder(x: Double)(y: Double) = x + y
```

## map, flatMap, filter and for-comprehensions

Some list utilities:


```scala
val list = List(1, 2, 3)
list.head
list.tail

list.map(_ + 1)
list.map(_ + " is a number")

list.filter(_ % 2 == 0) // gets only even numbers f the list
```

flatMap() makes a **map** over the collection and then a **flatten**

```scala
  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')
  val animals = List("🐯", "🦄")

  val combinations = numbers.flatMap(number => chars.flatMap(char => animals.map(animal => s"$animal$number$char")))
  // List(🐯1a, 🦄1a, 🐯1b, 🦄1b, 🐯2a, 🦄2a, 🐯2b, 🦄2b)
```

This can be difficult to read, that's why we have the **for-comprehensions**.
Can also use guards to add conditions

```scala
val forCombinations = for {
  number <- numbers if number % 2 == 0 //guard to filter even numbers
  char <- chars
  animal <- animals
} yield s"$animal$number$char"
```

## Sequences: List, Array, Vector

* C: The operation takes (fast) constant time.
* eC: The operation takes effectively constant time, but this might depend on some assumptions such as maximum length of a vector or distribution of hash keys.
* L: The operation is linear, that is it takes time proportional to the collection size.
* -: The operation is not supported.

|Collection	| head | tail | apply | update | prepend | append | insert|
|-----------|------|------|-------|--------|---------|--------|-------|
|List	      |  C	 | C	  | L	    | L	     | C	     | L	    | -     |
|Array	    |  C	 | L	  | C	    | C	     | -	     | -	    | -     |
|Vector	    |  eC  | eC	  | eC  	| eC	   | eC	     | eC	    | -     |

https://docs.scala-lang.org/overviews/collections/performance-characteristics.html

### Seq

General interface
* Well defined order
* Can be indexed

```scala
val aSequence = Seq(1,3,2,4)

// some operations
aSequence.reverse
aSequence(2)
aSequence ++ Seq(7,5,6)
aSequence.sorted

// Ranges
val aRange: Seq[Int] = 1 until 10
aRange.foreach(println)
(1 to 10).foreach(x => println("Hello"))
```

### List

Immutable linked list
* head, tail, isEmpty are fast O(1)
* most operations are O(n)

```scala  
val aList = List(1,2,3)

// some operations
val prepended1 = 42 :: aList
val prependedAndAppend = 42 +: aList :+ 89)

val apples5 = List.fill(5)("apple")
println(aList.mkString("-|-"))
```

### Array

Equivalent of simple Jav array
* Can be manually constructed with predefined lengths
* Can be mutated (updated)
* Are interoperable with Java's arrays
* Indexing is fast

```scala
val numbers = Array(1,2,3,4)

// predefined type and size initializes all the values for primitives or assign null for others
val threeInts = Array.ofDim[Integer](3) // 0, 0, 0
val threeStrings = Array.ofDim[String](3) // null, null, null

// mutation
numbers(2) = 0  // syntax sugar for numbers.update(2, 0)
println(numbers.mkString(" "))

// arrays and seq
val numbersSeq: Seq[Int] = numbers  // implicit conversion
println(numbersSeq)
```

## Vectors

Default implementation for immutable sequences because is very fast
* Almost constant indexed, read and write: O(log32(n))
* Fast element addition: apprend/prepend
* Good performance for large sizes

```scala

val vector: Vector[Int] = Vector(1,2,3)
// Same opearions as other collections

```