package com.taz.customapplication

import android.graphics.Color
import android.graphics.Path
import java.util.*
import kotlin.collections.ArrayList

class SerializablePath : Path, PostProcessable {
    private var actions: MutableList<Action> =
        ArrayList()

    var color:Int = Color.BLUE
    var strokeWidth:Float = 20f

    constructor()

    constructor(src: SerializablePath?) : super(src) {
        if (src != null) {
            actions = ArrayList(src.actions)
            color = src.color
            strokeWidth = src.strokeWidth
        }
    }

    fun performActions() {
        for (action in actions) {
            if (action.isLine) {
                super.lineTo(action.x, action.y)
            } else {
                super.moveTo(action.x, action.y)
            }
        }
    }

    override fun lineTo(x: Float, y: Float) {
        actions.add(Action(true,x, y))
        super.lineTo(x, y)
    }

    override fun moveTo(x: Float, y: Float) {
        actions.add(Action(false,x, y))
        super.moveTo(x, y)
    }

    override fun gsonPostProcess() {
        performActions()
    }

    override fun toString(): String {
        return "SerializablePath(actions=$ actions, color=$color, strokeWidth=$strokeWidth)"
    }


    data class Action(
        val isLine:Boolean,
        val x: Float,
        val y: Float
    )

}