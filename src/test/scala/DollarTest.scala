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

  "Money.+" should "return sum" in {
    val five = Money.dollar(5)
    val result: Expression = five + five
    val sum: Sum = result.asInstanceOf[Sum]
    five shouldEqual sum.augend
    five shouldEqual sum.addend
  }

  "Bank" should "reduce Sum" in {
    val sum = Sum(Money.dollar(3), Money.dollar(4))
    val bank = Bank()
    val result = bank.reduce(sum, "USD")
    result shouldEqual Money.dollar(7)
  }

  "Bank" should "reduce Money" in {
    val bank = Bank()
    val result: Money = bank.reduce(Money.dollar(1), "USD")
    result shouldEqual Money.dollar(1)
  }

  "Bank" should "reduce Money in different currency" in {
    val bank = Bank()
    bank.setRate("CHF", "USD", 2)
    val result: Money = bank.reduce(Money.franc(2), "USD")
    result shouldEqual Money.dollar()
  }

  "Bank" should "reduce identity to 1" in {
    Bank().rate("USD", "USD") shouldEqual Some(1)
  }

}

// sue me
import scala.collection.mutable.Map
type AmountType = Int

class Money(val amount: AmountType, val currency: String) extends Expression { // more like Currency amirite?
  def *(multiplier: AmountType): Money = Money(amount * multiplier, currency)
  def +(addend: Money): Expression =
    Sum(this, addend)
  override def reduce(bank: Bank, to: String) =
    bank.rate(currency, to) match {
      case Some(rate) => Money(amount / rate, to)
      case None       => ???
    }

  override def toString(): String = s"$amount $currency"
  override def equals(that: Any): Boolean = that match {
    case that: Money =>
      this.amount == that.amount && this.currency == that.currency
    case _ => false
  }

}

object Money {
  def apply(amt: AmountType, currency: String) = new Money(amt, currency)
  def dollar(amount: AmountType = 1) = new Money(amount, "USD")
  def franc(amount: AmountType = 1) = Money(amount, "CHF")
}

class Bank() {
  private val rates = Map[(String, String), AmountType]()
  def reduce(source: Expression, to: String): Money = source.reduce(this, to)
  def rate(from: String, to: String): Option[AmountType] =
    if (from == to) Some(1) else rates get from -> to
  def setRate(from: String, to: String, rate: AmountType) =
    rates += ((from -> to) -> rate)

}

trait Expression {
  def reduce(bank: Bank, to: String): Money
}

class Sum(val augend: Money, val addend: Money) extends Expression {
  override def reduce(bank: Bank, to: String) = {
    val amount = augend.amount + addend.amount
    Money(amount, to)
  }
}
