package com.taz.customapplication

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.Target
import com.github.amlcurran.showcaseview.targets.ViewTarget
import java.time.temporal.TemporalAdjusters.next


class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private var showcaseView: ShowcaseView? = null
    private var counter = 0
    private var textView1: TextView? = null
    private var textView2: TextView? = null
    private var textView3: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        textView1 = findViewById<View>(R.id.textView) as TextView
        textView2 = findViewById<View>(R.id.textView2) as TextView
        textView3 = findViewById<View>(R.id.textView3) as TextView
        showcaseView = ShowcaseView.Builder(this)
            .setTarget(
                ViewTarget(
                    findViewById(
                        R.id.textView
                    )
                )
            )
            .setOnClickListener(this)
            .build()
        showcaseView!!.setButtonText(getString(R.string.next))
    }


    private fun setAlpha(alpha: Float, vararg views: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            for (view in views) {
                view.alpha = alpha
            }
        }
    }

    override fun onClick(v: View?) {
        when (counter) {
            0 -> showcaseView!!.setShowcase(
                ViewTarget(
                    textView2
                ), true
            )
            1 -> showcaseView!!.setShowcase(
                ViewTarget(
                    textView3
                ), true
            )
            2 -> {
                showcaseView!!.setTarget(Target.NONE)
                showcaseView!!.setContentTitle("Check it out")
                showcaseView!!.setContentText("You don't always need a target to showcase")
                showcaseView!!.setButtonText(getString(R.string.close))
                setAlpha(0.4f, textView1!!, textView2!!, textView3!!)
            }
            3 -> {
                showcaseView!!.hide()
                setAlpha(1.0f, textView1!!, textView2!!, textView3!!)
            }
        }
        counter++
    }
}
