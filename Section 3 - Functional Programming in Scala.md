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