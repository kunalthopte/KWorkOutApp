package com.example.k_workoutapp

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.k_workoutapp.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    var mViewBinding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private  var restTimeDuration: Long = 10
    private  var exerciseTimeDuration: Long = 30

    private var restTimerExercise: CountDownTimer? = null
    private var restProgressExercise = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts:TextToSpeech? = null
    private var player:MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Todo 2 inflate the layout
        mViewBinding = ActivityExerciseBinding.inflate(layoutInflater)
        //Todo 3 pass in binding?.root in the content view
        setContentView(mViewBinding?.root)

//Todo 4: then set support action bar and get toolBarExcerciser using the binding
//variable
        setSupportActionBar(mViewBinding?.toolBarOfExercisePage)
        if (supportActionBar != null) {
            //THIS WILL ACTIVATE OUR BACK BUTTON
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        mViewBinding?.toolBarOfExercisePage?.setNavigationOnClickListener {
            onBackPressed()
        }

        exerciseList = Constants.deafaultExerciseList()

        setUpExerciseStatusRV()
        mViewBinding?.rvExerciseStatus?.adapter = exerciseAdapter

        tts  = TextToSpeech(this,this)
        speakOut("Now we are started")

        setUpRestView()
    }

    private fun setUpExerciseStatusRV(){
        mViewBinding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)


    }
    private fun setUpRestView(){

        try {
            val soundURI = Uri.parse("android.resource://com.example.k_workoutapp/"+R.raw.buttonclick)
            player = MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping=false
            player?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }


        mViewBinding?.flResetView?.visibility = View.VISIBLE
        mViewBinding?.tvTitle?.visibility = View.VISIBLE
        mViewBinding?.tvExerciseName?.visibility = View.INVISIBLE
        mViewBinding?.flExeciseView?.visibility = View.INVISIBLE
        mViewBinding?.ivImage?.visibility = View.INVISIBLE
        mViewBinding?.tvUpComingExercise?.visibility = View.VISIBLE
        mViewBinding?.upcomingLable?.visibility = View.VISIBLE


        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        mViewBinding?.tvUpComingExercise?.text =
            exerciseList!![currentExercisePosition+1].getName()
        setUpProgressBar()
    }

    private fun setUpExerciseView(){
       mViewBinding?.flResetView?.visibility = View.INVISIBLE
        mViewBinding?.tvTitle?.visibility = View.INVISIBLE
        mViewBinding?.tvExerciseName?.visibility = View.VISIBLE
        mViewBinding?.flExeciseView?.visibility = View.VISIBLE
        mViewBinding?.ivImage?.visibility = View.VISIBLE
        mViewBinding?.tvUpComingExercise?.visibility = View.INVISIBLE
        mViewBinding?.upcomingLable?.visibility = View.INVISIBLE


        if(restTimerExercise != null){
            restTimerExercise?.cancel()
            restProgressExercise = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        mViewBinding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        mViewBinding?.tvExerciseName?.setText(exerciseList!![currentExercisePosition].getName())
        setUpExerciseProgressBar()
    }

    private fun setUpExerciseProgressBar() {
        mViewBinding?.progressBar?.progress = restProgressExercise

        restTimerExercise = object : CountDownTimer(exerciseTimeDuration*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgressExercise++
                mViewBinding?.exerciseProgressBar?.progress = 30 - restProgressExercise
                mViewBinding?.tvTimerExercise?.text = (30 - restProgressExercise).toString()
            }

            override fun onFinish() {


                if(currentExercisePosition < exerciseList!!.size!! - 1 ){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                }
                else{
                  finish()
                    val intent = Intent(this@ExerciseActivity,EndActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()
    }

    private fun setUpProgressBar() {
        mViewBinding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(restTimeDuration*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                mViewBinding?.progressBar?.progress = 10 - restProgress
                mViewBinding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "10 Seconds are over ..", Toast.LENGTH_LONG
                )
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        if(restTimerExercise != null){
            restTimerExercise?.cancel()
            restProgressExercise = 0
        }

        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player !=null){
            player!!.stop()
        }

        mViewBinding = null
    }

    override fun onInit(status: Int) {

        if(status == TextToSpeech.SUCCESS){
            // set US English as language for tts
            val result  = tts?.setLanguage(Locale.US)


            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS", "Not Woking Speach")
        }


    }
    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }
}