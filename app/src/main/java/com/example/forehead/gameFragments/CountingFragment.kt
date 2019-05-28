package com.example.forehead.gameFragments

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.forehead.R
import com.example.forehead.sensor.OrientationSensor
import org.w3c.dom.Text

class CountingFragment : Fragment(), OrientationSensor.RotationSensorObserver {

    private var listener: OnCountdownFinished? = null
    private var timer : CountDownTimer? = null
    private var isCounting: Boolean = false

    companion object {
        const val COUNTING_TIME = 3100L
        const val SECOND_IN_MILIS = 1000L
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timer = object: CountDownTimer(COUNTING_TIME, SECOND_IN_MILIS) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished/SECOND_IN_MILIS
                String.format("00:%02d", secondsRemaining)
                view?.findViewById<TextView>(R.id.counting_main_TV)?.text = String.format("00:%02d", secondsRemaining)
            }

            override fun onFinish() {
                listener?.onCountdownFinished()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_counting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        OrientationSensor.registerListener(this)
    }

    override fun onRotationChanged(orientation: OrientationSensor.Orientation) {
        if(orientation == OrientationSensor.Orientation.PLAYABLE && !isCounting){
            isCounting = true
            timer?.start()
            view?.findViewById<TextView>(R.id.counting_main_TV)?.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                                                    resources.getDimension(R.dimen.counting_timer_fontSize))
        }
        else if (orientation != OrientationSensor.Orientation.PLAYABLE && isCounting){
            isCounting = false

            view?.findViewById<TextView>(R.id.counting_main_TV)?.also {
                it.setTextSize(TypedValue.COMPLEX_UNIT_PX,resources.getDimension(R.dimen.counting_alert_fontSize))
                it.text = getString(R.string.counting_prepare)
            }
                timer?.cancel()
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCountdownFinished) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCountdownFinished")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
   
    interface OnCountdownFinished {
        fun onCountdownFinished()
    }

}

