package scalanum

import org.scalatest.{FlatSpec, Matchers}
import EnumSpec._

class EnumSpec extends FlatSpec with Matchers {

  "Enum" should "work properly for integers and chars" in {
    // scalastyle:off magic.number
    val intValue = 1
    val intEnum = Enum[Int]

    intEnum.pred(intEnum.succ(intValue)) shouldBe intValue
    intEnum.succ(intEnum.pred(intValue)) shouldBe intValue
    intEnum.fromTo(1, 4) shouldBe Seq(1, 2, 3, 4)
    intEnum.fromThenTo(1, 3, 5) shouldBe Seq(1, 3, 5)
    intEnum.from(1).filter(_ % 2 == 0).take(4) shouldBe Seq(2, 4, 6, 8)
    intEnum.fromThen(1, 3).take(4) shouldBe Seq(1, 3, 5, 7)

    val charValue = 'a'
    val charEnum = Enum[Char]
    charEnum.pred(charEnum.succ(charValue)) shouldBe charValue
    charEnum.succ(charEnum.pred(charValue)) shouldBe charValue
    charEnum.fromTo('a', 'd') shouldBe Seq('a', 'b', 'c', 'd')
    charEnum.fromThenTo('a', 'c', 'e') shouldBe Seq('a', 'c', 'e')
    charEnum.from('a').filter(_ % 2 == 0).take(4) shouldBe Seq('b', 'd', 'f', 'h')
    charEnum.fromThen('a', 'c').take(4) shouldBe Seq('a', 'c', 'e', 'g')
    // scalastyle:on magic.number
  }

  it should "work properly with EnumIndexed" in {
    implicit val animalEnum = new EnumIndexed[Animal] {
      override val list: IndexedSeq[Animal] = Array(Cat, Dog, Rabbit, Cow)
    }

    val animalValue = Dog
    animalEnum.pred(animalEnum.succ(animalValue)) shouldBe animalValue
    animalEnum.succ(animalEnum.pred(animalValue)) shouldBe animalValue
    animalEnum.fromTo(Cat, Rabbit) shouldBe Seq(Cat, Dog, Rabbit)
    animalEnum.fromThenTo(Cat, Rabbit, Cow) shouldBe Seq(Cat, Rabbit)
    animalEnum.from(Cat).filter(_ != Dog) shouldBe Seq(Cat, Rabbit, Cow)
    animalEnum.fromThen(Cat, Rabbit).filter(_ != Dog) shouldBe Seq(Cat, Rabbit)
  }
}

object EnumSpec {
  sealed trait Animal
  case object Cat extends Animal
  case object Dog extends Animal
  case object Rabbit extends Animal
  case object Cow extends Animal
}
