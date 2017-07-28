package scalanum

/**
 * A type class that defines operations on sequentially ordered types.
 */
trait Enum[A] {

  /**
   * Converts the integer value to a corresponding enumeration value.
   */
  def toEnum(i: Int): A

  /**
   * Converts the enumeration value to a corresponding integer value.
   */
  def fromEnum(a: A): Int

  /**
   * Returns a successor of the given value.
   */
  def succ(a: A): A =
    toEnum(fromEnum(a) + 1)

  /**
   * Returns a predecessor of the given value.
   */
  def pred(a: A): A =
    toEnum(fromEnum(a) - 1)

  /**
   * Generates a lazy collection of enumerated values.
   *
   * @param a1 the first element of the sequence.
   * @param an the last element of the sequence.
   * @return a lazy collection of enumerated values.
   */
  def fromTo(a1: A, an: A): Seq[A] =
    (fromEnum(a1) to fromEnum(an)).toStream.map(toEnum)

  /**
   * Generates a lazy collection of enumerated values with custom progression.
   *
   * @param a1 the first element of the sequence.
   * @param a2 the second element of the sequence.
   * @param an the last element of the sequence.
   * @return a lazy collection of enumerated values.
   */
  def fromThenTo(a1: A, a2: A, an: A): Seq[A] = {
    val from = fromEnum(a1)
    (from to fromEnum(an) by fromEnum(a2) - from).toStream.map(toEnum)
  }

  /**
   * Creates a lazy collection of enumerated values without specifying the last
   * element. In this case the last element is represented by an upper bound
   * of the specific type.
   *``
   * @param a1 the first element of the sequence.
   * @return a lazy collection of enumerated values.
   */
  def from(a1: A)(implicit u: UpperBounded[A]): Seq[A] =
    fromTo(a1, u.maxBound)

  /**
   * Same as [[from()]] but with a custom progression.
   *
   * @param a1 the first element of the sequence.
   * @param a2 the second element of the sequence.
   * @return  a lazy collection of enumerated values.
   */
  def fromThen(a1: A, a2: A)(implicit u: UpperBounded[A]): Seq[A] =
    fromThenTo(a1, a2, u.maxBound)
}

trait EnumIndexed[A] extends Enum[A] {
  def list: IndexedSeq[A]
  override def toEnum(i: Int): A = list(i)
  override def fromEnum(a: A): Int = list.indexOf(a)
}

object Enum {
  @inline def apply[A](implicit e: Enum[A]): Enum[A] = e

  implicit val intEnum: Enum[Int] = new Enum[Int] {
    override def toEnum(i: Int): Int = i
    override def fromEnum(a: Int): Int = a
  }

  implicit val charEnum: Enum[Char] = new Enum[Char] {
    override def toEnum(i: Int): Char = i.toChar
    override def fromEnum(a: Char): Int = a.toInt
  }
}
