import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers._

class DollarTest extends AnyFlatSpec with should.Matchers {
  "Dollar" should "multiply with scalars" in {
    val five: Money = Money.dollar(5)
    Money.dollar(10) shouldEqual five * 2
    five * 3 shouldEqual Money.dollar(15)
  }

  it should "testEquality" in {
    Money.dollar(5) shouldEqual Money.dollar(5)
    Money.dollar(6) shouldNot equal(Money.dollar(5))
  }

}

class FrancTest extends AnyFlatSpec with should.Matchers {
  "Franc" should "multiply with scalars" in {
    val five = Money.franc(5)
    Money.franc(10) shouldEqual five * 2
    five * 3 shouldEqual Money.franc(15)
  }

  it should "testEquality" in {
    Money.franc(5) shouldEqual Money.franc(5)
    Money.franc(6) shouldNot equal(Money.franc(5))
  }
}

class Test extends AnyFlatSpec with should.Matchers {
  it should "testEquality" in {
    Money.dollar(5) shouldEqual Money.dollar(5)
    Money.dollar(6) shouldNot equal(Money.dollar(5))
    Money.franc(5) shouldEqual Money.franc(5)
    Money.franc(6) shouldNot equal(Money.franc(5))

    Money.dollar(2) shouldEqual Money(2, "USD")
    Money.franc(2) shouldEqual Money(2, "CHF")

    // May not be true
    Money.franc(5) shouldNot equal(Money.dollar(5))
    Money.franc(0) shouldNot equal(Money.dollar(1))
  }

  it should "have currency" in {
    "USD" shouldEqual Money.dollar().currency
    "CHF" shouldEqual Money.franc().currency

  }

  it should "make a unit amount by default" in {
    Money.dollar() shouldEqual Money.dollar(1)
    Money.franc() shouldEqual Money.franc(1)
  }

}

class Money(protected val amount: Int, val currency: String) { // more like Currency amirite?
  override def equals(that: Any): Boolean = that match {
    case that: Money =>
      this.amount == that.amount && this.currency == that.currency
    case _ => false
  }
  def *(multiplier: Int): Money = Money(amount * multiplier, currency)

}

object Money {
  def apply(amt: Int, currency: String) = new Money(amt, currency)
  def dollar(amount: Int = 1) = new Dollar(amount)
  def franc(amount: Int = 1) = new Franc(amount)
}

class Dollar(override val amount: Int) extends Money(amount, "USD") {}

class Franc(override val amount: Int) extends Money(amount, "CHF") {}
