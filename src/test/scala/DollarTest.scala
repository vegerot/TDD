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

  // @@@
  "Dollar" should "be immutable" in {
    val five = new Dollar(5)
    val ten = five * 2
    five.amount should be(5)
  }
}

class Dollar(var amount: Int) {
  def *(multiplier: Int): Dollar = {
    new Dollar(this.amount * multiplier)
  }
}
