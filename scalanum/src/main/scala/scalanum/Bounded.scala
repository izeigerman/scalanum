package scalanum

trait UpperBounded[A] {
  def maxBound: A
}

object UpperBounded {
  @inline def apply[A](implicit u: UpperBounded[A]): UpperBounded[A] = u

  implicit val intUpperBound: UpperBounded[Int] = Bounded.intBound
  implicit val charUpperBound: UpperBounded[Char] = Bounded.charBound
  implicit def enumIndexedUpperBound[A](implicit e: EnumIndexed[A]): UpperBounded[A] =
    Bounded.enumIndexedBounds
}

trait LowerBounded[A] {
  def minBound: A
}

object LowerBounded {
  @inline def apply[A](implicit l: LowerBounded[A]): LowerBounded[A] = l

  implicit val intLowerBound: LowerBounded[Int] = Bounded.intBound
  implicit val charLowerBound: LowerBounded[Char] = Bounded.charBound
  implicit def enumIndexedLowerBound[A](implicit e: EnumIndexed[A]): LowerBounded[A] =
    Bounded.enumIndexedBounds
}


trait Bounded[A] extends LowerBounded[A] with UpperBounded[A]

object Bounded {
  @inline def apply[A](implicit b: Bounded[A]): Bounded[A] = b

  implicit val intBound: Bounded[Int] = new Bounded[Int] {
    override def minBound: Int = Integer.MIN_VALUE
    override def maxBound: Int = Integer.MAX_VALUE
  }

  implicit val charBound: Bounded[Char] = new Bounded[Char] {
    override def minBound: Char = Character.MIN_VALUE
    override def maxBound: Char = Character.MAX_VALUE
  }

  implicit def enumIndexedBounds[A](implicit e: EnumIndexed[A]): Bounded[A] = new Bounded[A] {
    override def minBound: A = e.list.head
    override def maxBound: A = e.list.last
  }
}
