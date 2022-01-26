import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers._

class Test extends AnyFlatSpec with should.Matchers {
  "Dollar" should "multiply with scalars" in {
    val five: Money = Money.dollar(5)
    Money.dollar(10) shouldEqual five * 2
    five * 3 shouldEqual Money.dollar(15)
  }
  it should "testEquality" in {
    Money.dollar(5) shouldEqual Money.dollar(5)
    Money.dollar(6) shouldNot equal(Money.dollar(5))

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

  it should "test addition" in {
    val five = Money.dollar(5)
    val sum: Expression = five + five
    val bank: Bank = Bank()
    val reduced: Money = bank.reduce(sum, "USD")
    Money.dollar(10) shouldEqual reduced
  }
}

class Money(protected val amount: Int, val currency: String)
    extends Expression { // more like Currency amirite?
  override def equals(that: Any): Boolean = that match {
    case that: Money =>
      this.amount == that.amount && this.currency == that.currency
    case _ => false
  }
  def *(multiplier: Int): Money = Money(amount * multiplier, currency)
  def +(addend: Money): Expression =
    Money(this.amount + addend.amount, currency)

}

object Money {
  def apply(amt: Int, currency: String) = new Money(amt, currency)
  def dollar(amount: Int = 1) = new Money(amount, "USD")
  def franc(amount: Int = 1) = Money(amount, "CHF")
}

class Bank {
  def reduce(source: Expression, currency: String): Money = Money.dollar(10)
}

trait Expression {}
