//package com.kotlin_jetpack_compose.happyfashion.components.addStory.display
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScope
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.layout.onGloballyPositioned
//import androidx.compose.ui.layout.positionInRoot
//import androidx.compose.ui.unit.sp
//import com.kotlin_jetpack_compose.happyfashion.components.addStory.LocalSavableListLabel
//import com.kotlin_jetpack_compose.happyfashion.components.addStory.State
//import com.kotlin_jetpack_compose.happyfashion.items.TransformGesturesItem
//import com.kotlin_jetpack_compose.happyfashion.models.TransformGestureModel
//
//@Composable
//fun BoxScope.LabelDisplay(
//    modifier: Modifier,
//    state: State,
//    onTap: (String) -> Unit,
//    onPress: (String) -> Unit,
//    onGloballyReturn: (Size, Offset) -> Unit ?= {_,_ ->}
//) {
//    // If story have no title, don't show anything
//    if(state == State.TITLE) return
//
//    val listLabel = LocalSavableListLabel.current
//
//    if(listLabel.listContent.isEmpty()) return
//
//    listLabel.listContent.forEach { fontState ->
//        var transformGestureModel by remember {  mutableStateOf(fontState.fontTransformGesture) }
//        // First time title was added
//        if (transformGestureModel.offset == Offset.Zero) {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = modifier
//                    .align(Alignment.Center)
//                    /// Get the offset of center of the screen
//                    .onGloballyPositioned { coordinates ->
//                        transformGestureModel =
//                            TransformGestureModel.Default.copy(offset = coordinates.positionInRoot())
//                    }) {
//                /// Have to use main text component to get the offset
//                Text(
//                    text = fontState.fontContent,
//                    fontSize = fontState.fontSize.sp,
//                    fontFamily = fontState.fontFamily,
//                    color = fontState.fontColor,
//                    modifier = Modifier.background(fontState.fontBackground)
//                )
//            }
//            // Rebuild when offset was added
//        } else {
//            TransformGesturesItem(
//                modifier = modifier,
//                onGloballyReturn = onGloballyReturn,
//                onTap = { onTap(fontState.id) },
//                onLongPress = { onPress(fontState.id) },
//                transformGestureModel = transformGestureModel,
//                onTransform = { scale, rotation, offset ->
//                    transformGestureModel = TransformGestureModel(scale, rotation, offset)
//                    fontState.fontTransformGesture = transformGestureModel
//                }
//            ) {
//                Text(
//                    text = fontState.fontContent,
//                    fontSize = fontState.fontSize.sp,
//                    fontFamily = fontState.fontFamily,
//                    color = fontState.fontColor,
//                    modifier = Modifier.background(fontState.fontBackground)
//                )
//            }
//        }
//    }
//}