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
    // May not be true
    Money.franc(5) shouldNot equal(Money.dollar(5))
    Money.franc(0) shouldNot equal(Money.dollar(1))
  }

  it should "have currency" in {
    "USD" shouldEqual Money.dollar
  }

  it should "make a unit amount by default" in {
    Money
  }

}

abstract class Money(protected val amount: Int) { // more like Currency amirite?
  override def equals(that: Any): Boolean = that match {
    case that: Money =>
      this.amount == that.amount && this.getClass() == that.getClass()
    case _ => false
  }
  def *(mulitplier: Int): Money

}

object Money {
  def dollar(amount: Int) = new Dollar(amount)
  def franc(amount: Int) = new Franc(amount)
}

class Dollar(override val amount: Int) extends Money(amount) {
  def *(multiplier: Int): Dollar = {
    Money.dollar(this.amount * multiplier)
  }
}

class Franc(override val amount: Int) extends Money(amount) {
  def *(multiplier: Int): Franc = Money.franc(amount * multiplier)
}
