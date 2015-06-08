package ndimrtree

import NDimRTreeOps._
import distanceInstances._
import java.math.MathContext
import org.scalacheck._, Prop._, Shapeless._
import shapeless._, ops.hlist.{ ToTraversable, Mapper }
import spire._, implicits._, math._

object DistanceTest extends Properties("Distance") {

  def dist
    [T <: HList,
     U <: HList : Mapper.Aux[toBigDecimal.type, T, ?] : ToTraversable.Aux[?, List, BigDecimal]]
     (p1: Point[T], p2: Point[T]): Number = {
    p1.terms.map(toBigDecimal).toList.zip(p2.terms.map(toBigDecimal).toList)
      .map { case (i, j) => (i - j).abs.pow(2) }
      .sum
      .sqrt
  }

  property("1d numeric distance") = forAll {
    (i: Int, j: Int) =>
    val p1 = Point(i :: HNil)
    val p2 = Point(j :: HNil)

    p1.distance(p2) === ((i: Number) - j).abs
  }

  property("3d numeric distance") = forAll {
    type T = Int :: Double :: Long :: HNil
    (p1: Point[T], p2: Point[T]) =>

    p1.distance(p2) === dist(p1, p2)
  }

  property("5d numeric distance") = forAll {
    type T = Int :: Double :: Long :: Double :: Int :: HNil
    (p1: Point[T], p2: Point[T]) =>

    p1.distance(p2) === dist(p1, p2)
  }


}
