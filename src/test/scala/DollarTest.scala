import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers._

class DollarTest extends AnyFlatSpec with should.Matchers {
  "Dollar" should "multiply with scalars" in {
    val five = Dollar(5)
    Dollar(10) shouldEqual five * 2
    five * 3 shouldEqual Dollar(15)
  }

  it should "testEquality" in {
    Dollar(5) shouldEqual Dollar(5)
    Dollar(6) shouldNot equal(Dollar(5))
  }

  // @me
  "Dollars" should "be equal" in {
    val firstTwo = Dollar(2)
    val secondTwo = Dollar(2)

    firstTwo shouldEqual secondTwo

    val three = Dollar(3)
    firstTwo shouldNot equal(three)
  }
}

class Dollar(private val amount: Int) {
  def *(multiplier: Int): Dollar = {
    Dollar(this.amount * multiplier)
  }
  override def equals(that: Any): Boolean = that match {
    case that: Dollar => this.amount == that.amount
    // am I getting too far ahead of myself?  There's no tests for this!
    case that: Pounds => ??? // TODO
    case _            => false
  }
}
class Pounds() {}
