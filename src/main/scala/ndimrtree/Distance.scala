package ndimrtree

import shapeless._, ops.hlist._
import spire._, math._, implicits._

trait Distance[T <: HList] {
  def distance(a: Point[T], b: Point[T]): Number
}

object Distance {
  def apply[T <: HList : Distance] = implicitly[Distance[T]]
}

object distanceInstances {

  // go with Number once RationalInstance is implemented
  // https://github.com/non/spire/blob/v0.10.1/core/src/main/scala/spire/math/Number.scala#L11
  object toBigDecimal extends Poly1 {
    implicit def default[T](implicit ev: T => BigDecimal) = at[T](ev)
  }

  implicit def twoNormDistance
    [T <: HList,
     U <: HList : Mapper.Aux[toBigDecimal.type, T, ?] : ToTraversable.Aux[?, List, BigDecimal]] =
    new Distance[T] {
      def distance(p1: Point[T], p2: Point[T]): Number =
        p1.terms.map(toBigDecimal).toList.toVector
          .distance(p2.terms.map(toBigDecimal).toList.toVector)
    }

}
