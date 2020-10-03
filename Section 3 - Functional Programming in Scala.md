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


