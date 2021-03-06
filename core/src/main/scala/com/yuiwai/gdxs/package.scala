package com.yuiwai

import com.badlogic.gdx.math.Vector3

package object gdxs {
  final case class Pos(x: Float, y: Float) {
    def toVector3: Vector3 = new Vector3(x, y, 0)
    def shiftY(dy: Float): Pos = copy(y = y + dy)
    def shiftX(dx: Float): Pos = copy(x = x + dx)
  }
  object Pos {
    def zero: Pos = apply(0, 0)
    def apply(vector3: Vector3): Pos = apply(vector3.x, vector3.y)
  }
  final case class Size(width: Float, height: Float)
  object Size {
    def zero: Size = apply(0, 0)
  }
  final case class Area(pos: Pos, size: Size) {
    def left: Float = pos.x
    def top: Float = pos.y
    def center: Pos = Pos(pos.x + size.width / 2, pos.y + size.height / 2)
    def width: Float = size.width
    def height: Float = size.height
    def hitTest(p: Pos): Boolean =
      p.x >= pos.x && p.x <= pos.x + size.width && p.y >= pos.y && p.y <= pos.y + size.height
    def shiftY(dy: Float): Area = copy(pos = pos.shiftY(dy))
    def shiftX(dx: Float): Area = copy(pos = pos.shiftX(dx))
  }
  object Area {
    def zero: Area = apply(Pos.zero, Size.zero)
  }

  sealed trait Region {
    val area: Area
    def x: Float = area.pos.x
    def y: Float = area.pos.y
    def width: Float = area.size.width
    def height: Float = area.size.height
  }
  final case class FixedRegion(area: Area) extends Region
  final case class RelativeRegion(pos: Pos) extends Region {
    override val area: Area = Area(pos, Size(0, 0))
  }
  object NoRegion extends Region {
    override val area: Area = Area.zero
  }
}
