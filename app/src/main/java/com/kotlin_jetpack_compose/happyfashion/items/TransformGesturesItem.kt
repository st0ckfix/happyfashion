package com.kotlin_jetpack_compose.happyfashion.items

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toSize
import com.kotlin_jetpack_compose.happyfashion.models.TransformGestureModel
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

enum class TransformType {
    LABEL, OTHERS, EDIT_HOLDER
}

/**
 * A layout composable with [content].
 * The [TransformGesturesItem] will control transform gestures of the [content].
 * The [offset] is the current offset of the [content].
 * The [rotate] is the current rotate of the [content].
 * The [scale] is the current scale of the [content].
 * When dragging, the [offset] will be changed and return amount of dragging, it still drag by device x's axis and y's axis.
 * When rotating, the [rotate] will be changed and return amount of rotating, it will rotate z's axis.
 * When zooming, the [scale] will be changed and return amount of zooming, it will scale x and y at same [scale].
 *
 * @param modifier
 * @param content
 */
@Composable
fun BoxScope.TransformGesturesItem(
    modifier: Modifier,
    type: TransformType = TransformType.OTHERS,
    inEditing: Boolean = false,
    transformGestureModel: TransformGestureModel,
    onTransform: (Float, Float, Offset) -> Unit,
    //onGloballyReturn: (Size, Offset) -> Unit ?= { _ , _ ->},
    onTap: (TransformType) -> Unit ?= {},
    onLongPress: () -> Unit ?= {},
    content: @Composable () -> Unit
) {
    Box(modifier = modifier
//        .onGloballyPositioned {
//            onGloballyReturn(it.size.toSize(), it.positionInRoot())
//        }
        // Offset by dragging
        .offset(offset = {
            IntOffset(
                transformGestureModel.offset.x.roundToInt(),
                transformGestureModel.offset.y.roundToInt(),
            )
        })
        // Rotate by rotation
        .rotate(transformGestureModel.rotation)
        // Scale by zoom
        .scale(transformGestureModel.scale)
        // Detect transform to change font state
        .pointerInput(Unit) {
            if (!inEditing) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    // Because when rotating, the x's axis and y's axis are changed by direction of rotation
                    // So we have to calculate the angle to drag the exactly x's axis and y's axis of the device
                    val angle = Math.toRadians(transformGestureModel.rotation.toDouble())
                    val cos = cos(angle)
                    val sin = sin(angle)
                    val newDragX = pan.x * cos - pan.y * sin
                    val newDragY = pan.x * sin + pan.y * cos
                    transformGestureModel.offset += Offset(
                        newDragX.toFloat(),
                        newDragY.toFloat()
                    ) * transformGestureModel.scale
                    transformGestureModel.scale *= zoom
                    transformGestureModel.rotation += rotation
                    onTransform(
                        min(transformGestureModel.scale * zoom, 3f),
                        transformGestureModel.rotation + rotation,
                        transformGestureModel.offset + Offset(
                            newDragX.toFloat(),
                            newDragY.toFloat()
                        ) * transformGestureModel.scale
                    )
                }
            }
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    if (type == TransformType.LABEL && !inEditing)
                        onTap(TransformType.LABEL)
                    else if (type == TransformType.EDIT_HOLDER)
                        onTap(TransformType.EDIT_HOLDER)
                },
                onLongPress = {
                    if (!inEditing)
                        onLongPress()
                }
            )
        }

    ) {
        content()
    }
}