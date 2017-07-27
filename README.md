# Scalanum

Scalanum - is the Enum and Bounded type classes implemented in Scala. The implementation is inspired by the corresponding type class in Haskell. These particular type classes are missing in Cats and I don't really want to bring Scalaz to my projects because of this small inconvenience. That's why I came up with my own minimalistic version of Enum and Bounded with zero external dependencies. Below are some useful examples.

For our example we use the following sum type:
```scala
sealed trait Animal
case object Cat extends Animal
case object Dog extends Animal
case object Rabbit extends Animal
case object Cow extends Animal
```
The Enum instance for the sum type above might look like following:
```scala
import scalanum._

implicit val animalEnum = new Enum[Animal] {
  val animals = Array(Cat, Dog, Rabbit, Cow)
  override def fromEnum(a: Animal): Int = animals.indexOf(a)
  override def toEnum(i: Int): Animal = animals(i)
}
```
Or the same implementation but shorter:
```scala
import scalanum._

implicit val animalEnum = new EnumIndexed[Animal] {
  override val list: IndexedSeq[Animal] = Array(Cat, Dog, Rabbit, Cow)
}
```
Now let's play with it:
```scala
scala> Enum[Animal].succ(Cat)
res0: Animal = Dog

scala> Enum[Animal].pred(Cow)
res1: Animal = Rabbit
```
Scalanum is able to generate collections of enumerated values that are evaluated lazily:
```scala
scala> Enum[Animal].fromTo(Cat, Rabbit).foreach(println)
Cat
Dog
Rabbit
```
Note: if you use a `EnumIndexed` approach you get upper and lower bounds for your enumeration automatically:
```scala
scala> Enum[Animal].from(Dog).foreach(println)
Dog
Rabbit
Cow
```
The Enum instance for integers and chars are already included into the library. Due to the lazy nature of generated collections we can produce "infinite" sequences:
```scala
scala> Enum[Char].from('a').take(5).foreach(println)
a
b
c
d
e

scala> Enum[Int].from(1).filter(_ % 2 == 0).takeWhile(_ < 20).foreach(println)
2
4
6
8
10
12
14
16
18
```
We can also specify a custom progression pattern:
```scala
scala> Enum[Char].fromThenTo('a', 'c', 'h').foreach(println)
a
c
e
g

scala> Enum[Int].fromThenTo(1, 5, 20).foreach(println)
1
5
9
13
17
```