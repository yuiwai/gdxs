package com.yuiwai.gdxs

import com.badlogic.gdx.graphics.Texture
import com.yuiwai.gdxs.drawing.Drawing
import com.yuiwai.gdxs.style.{BackgroundStyle, FitTextureBackgroundStyle, FontStyle, NoStyle, Style}

package object component {
  sealed trait Component {
    val style: Style
    val region: Region
    def area: Area = region.area
  }

  // Label
  type LabelStyle = FontStyle with BackgroundStyle
  trait Label extends Component {
    val style: LabelStyle
    val text: String
  }
  object Label {
    def apply(labelText: String, labelStyle: LabelStyle, labelRegion: Region): Label = new Label {
      override val style: LabelStyle = labelStyle
      override val text: String = labelText
      override val region: Region = labelRegion
    }
  }

  // Button
  type ButtonStyle = BackgroundStyle
  trait Button[A] extends Component {
    val action: A
    val style: ButtonStyle
  }
  object Button {
    def apply[A](buttonStyle: ButtonStyle, buttonRegion: Region, buttonAction: A): Button[A] = new Button[A] {
      override val action: A = buttonAction
      override val style: ButtonStyle = buttonStyle
      override val region: Region = buttonRegion
    }
    def apply[A](texture: Texture, buttonRegion: Region, buttonAction: A): Button[A] = new Button[A] {
      override val action: A = buttonAction
      override val style: ButtonStyle = new FitTextureBackgroundStyle {
        override val backgroundTexture: Texture = texture
      }
      override val region: Region = buttonRegion
    }
    def apply[A](labelText: String, labelStyle: LabelStyle, buttonRegion: Region, buttonAction: A): Label with Button[A] =
      new Label with Button[A] {
        override val action: A = buttonAction
        override val text: String = labelText
        override val style: LabelStyle = labelStyle
        override val region: Region = buttonRegion
      }
  }

  // Arrow
  trait Arrow extends Component {
  }

  // Canvas
  trait Canvas extends Component {
    val drawings: Seq[Drawing]
  }
  object Canvas {
    def apply(canvasDrawings: Seq[Drawing]): Canvas = new Canvas {
      override val drawings: Seq[Drawing] = canvasDrawings
      override val style: Style = NoStyle
      override val region: Region = RelativeRegion(Pos.zero)
    }
  }

  // Container
  sealed trait Container extends Component {
    val children: Seq[Component]
  }
  /*
  final case class AbsoluteContainer(children: Seq[Component], mapping: Map[Component, Vector2]) extends Container
  final case class VerticalContainer(children: Seq[Component]) extends Container
  */
}
