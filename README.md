# Rock-the-JVM

## Section 1: The Absolute Scala Basics
### 2. Values, Variables and Types
### 3. Expressions
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