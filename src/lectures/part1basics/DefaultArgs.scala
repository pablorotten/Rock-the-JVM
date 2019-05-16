package lectures.part1basics

object DefaultArgs extends App{

  def trFactorial(n: Int, acc: Int): Int =
    if (n <= 1) acc
    else trFactorial(n-1, n*acc)

  // To calculate factorial, we need to send the 1 to the accumulator which is only needed for the tail-recursive inner workings
  // But it pollutes the function signature because this one is not really needed for calculate a factorial from the user's point of view
  var fact10 = trFactorial(10, 1)

  // We can give to the accumulator a default value of 1
  def trFactorialDefaultArg(n: Int, acc: Int = 1): Int =
    if (n <= 1) acc
    else trFactorial(n-1, n*acc)

  // And now, the user can call the factorial correctly
  fact10 = trFactorialDefaultArg(10)
  // Of course, still can give a value to the accumulator which will override the default one
  fact10 = trFactorialDefaultArg(10, 2)

  // function for saving a picture with default value for format is string because is the most used one
  def savePicture(format: String = "jpg", width: Int, height: Int): Unit = println("saving picture")

  savePicture("jpg", 800, 600)
  // savePicture(800, 600) // Complains because thinks that 800 has to be assigned to format

  def savePicture2(format: String = "jpg", width: Int = 1920, height: Int = 1080): Unit = println("saving picture")
  savePicture2() // works, use all the default arguments
  // savePicture2(800) // fails because tries to assign 800 to the leading argument "format" which is a String

  // SOLUTIONS:
  // 1. pass in  every leading argument
  savePicture2("bmp")
  // 2. name the arguments
  savePicture2(width =  800)
  // Can also pass the arguments in a different order
  savePicture2(width = 1920, format = "jpg" , height = 1080)
}
