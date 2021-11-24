import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers._

class DollarTest extends AnyFlatSpec with should.Matchers {
  "Dollar" should "multiply with scalars" in {
    val five = new Dollar(5)
    val ten = five * 2
    ten.amount.should(be(10))
    val fifteen = five * 3
    fifteen.amount should be(15)
  }

  it should "testEquality" in {
    new Dollar(5) shouldEqual new Dollar(5)
  }

  // @me
  "Dollar" should "be immutable" in {
    val five = new Dollar(5)
    val ten = five * 2
    five.amount should be(5)
  }

  // @me
  "Dollars" should "be equal" in {
    val firstTwo = new Dollar(2)
    val secondTwo = new Dollar(2)

    firstTwo shouldEqual secondTwo

    val three = new Dollar(3)
    firstTwo shouldNot equal(three)
  }
}

class Dollar(var amount: Int) {
  def *(multiplier: Int): Dollar = {
    new Dollar(this.amount * multiplier)
  }
  override def equals(that: Any): Boolean = that match {
    case that: Dollar => this.amount == that.amount
    // am I getting too far ahead of myself?  There's no tests for this!
    case that /*: Pounds*/ => ??? // TODO
    case _                 => false
  }
}
