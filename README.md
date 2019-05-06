# Rock-the-JVM

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
