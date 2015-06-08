import spire.algebra.Eq

package object ndimrtree {

  def eqInstance[T] = new Eq[T] { def eqv(x: T, y: T) = x == y }

}
