# Rock the JVM: Scala for begginers

## Section 1: The Absolute Scala Basics
### 2. Values, Variables and Types
### 3. Expressions

#### Instructions vs Expressions

**Instruction** are executed. Is something that you tell the computer to do: change a variable, print to the console.
**Expressions** are evaluated to a value and they have a type:

```scala
val aConditionedValue = if(aCondition) 5 else 3 // IF EXPRESSION
```

The if statement is an **expression** that has a value, which is either 5 or 3. You can print it:

```scala
println(if(aCondition) 5 else 3)
```

Only things like a definition of a val, a class, a package are **Instructions**.

**Everything** in Scala is an **Expression**.

* Mathematical Expressions: + - * / & | ^ << >> >>> (right shift with zero extension)
* Relational Expressions: == != > >= < <=
* Boolean Expressions: ! && ||

```scala
  val x = 1 + 2
  
  println(2 + 3 * 4 - x)
  println(!(1 == x))
  println(1 == x)
```

#### Expressions and Side effects

**Side effects**: Expressions that change the value of a variable

* Side effects: -= *= /= 

```scala
  var aVariable = 2
  aVariable += 3 // 
  println(aVariable)
```

For example, re-assigning a value to a var is an **Expression**
 
```scala
  var aVariable = 1
  val assignmentExpression = (aVariable = 3)
```
Re-assigning 3 to the var aVariable is an **Expression** of type Unit which has the value of (). Is like a Java void Function.

This kind of **Expressions** are **Side effects** because they change the value of a variable.

### 4. Functions

If a function has no parameters, can be called without parentheses. The compiler will give us a warning
```scala
  def aParameterlessFunction(): Int = 42
  
  println(aParameterlessFunction())
  println(aParameterlessFunction)

```

### 5. Type Inference
If there's a recursive function, the compiler can't infer the return type, we must specify it
```scala
  def aRepeatedFunction(aString: String, n: Int) = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n-1)
  }

```

### 6. Recursion
#### Tail Recursion
A recursive function with big numbers may cause a Stack Overflow error:

```scala
  def factorial(n: Int): Int =
    if (n <= 1) 1
    else {
      val result = n * factorial(n-1) // The recursive call expression is HERE
      result // This is the last expression
    }
    
  println(factorial(5000)) // --> Stack Overflow error.
```

This can be solved using **Tail Recursion**: the last expression is the recursive call:

```scala
  @tailrec
  def factorialTailRecursion(n: Int, accumulator: BigInt): BigInt = {
    if (n <= 1) accumulator
    else factorialTailRecursion(n - 1, n * accumulator) // TAIL RECURSION = use recursive call as the LAST expression
  }
  
  factorialTailRecursion(10000000, 1) // WORKS!
```

##### Fibonacci with tail recursion
Starts from the base case and stops when the limit (n) is reached:
So the base case is fibonacci(3) which is 1 + 1 = 2

If the user enters n = 3 which is the base case, in the first call of fiboTailrec, we already will have i = n and just return the initial last + nextToLast = 1 + 1 = 2
If is a bigger number, just call again fiboTailrec with last + nextToLast in the first parameter and last in the second one
```scala
  def fibonacci(n: Int): Long = {
    // Fibonacci: 1 1 2 3 5 8 13 21
    @tailrec
    def fiboTailrec(i: Int, last: Int, nextToLast: Int): Int = // last is fibonacci(n - 1), nextToLast is fibonacci(n - 2)
    if (i >= n) last
    else fiboTailrec(i + 1, last + nextToLast, last)

    if (n <= 2) 1
    else fiboTailrec(2, 1, 1)
  }
```