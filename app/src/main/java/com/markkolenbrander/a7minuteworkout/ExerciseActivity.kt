package com.markkolenbrander.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var llRestView: LinearLayout
    private lateinit var llExerciseView: LinearLayout
    private lateinit var toolbarExercise: androidx.appcompat.widget.Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var tvTimer: TextView
    private lateinit var exerciseProgressBar: ProgressBar
    private lateinit var tvExerciseTimer: TextView
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restDuration: Long = 10 //10 sec
    private var exerciseDuration: Long = 30 // 30 sec
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private lateinit var ivImage: ImageView
    private lateinit var tvExerciseName: TextView
    private lateinit var tvUpcomingExercise: TextView
    private var exerciseAdapter: ExerciseStatusAdapter? = null
    private lateinit var rvExerciseStatus: RecyclerView
    private lateinit var btnStopExercise : Button
    private lateinit var btnStopRest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        rvExerciseStatus = findViewById(R.id.rvExerciseStatus)
        tvTimer = findViewById(R.id.tvTimer)
        llRestView = findViewById(R.id.ll_RestView)
        llExerciseView = findViewById(R.id.ll_ExerciseView)
        toolbarExercise = findViewById(R.id.toolbar_exercise_activity)
        progressBar = findViewById(R.id.progressBar)
        exerciseProgressBar = findViewById(R.id.exerciseProgressBar)
        tvExerciseTimer = findViewById(R.id.tvTimer2)
        ivImage = findViewById(R.id.ivImage)
        tvExerciseName = findViewById(R.id.tvExerciseName)
        tvUpcomingExercise = findViewById(R.id.tvUpcomingExercise)
        btnStopExercise = findViewById(R.id.btn_stop_exercise)
        btnStopRest = findViewById(R.id.btn_stop_rest)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        window.statusBarColor = ContextCompat.getColor(this,R.color.colorMain)

        setSupportActionBar(toolbarExercise)
        val actionbar = supportActionBar
        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        toolbarExercise.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        btnStopExercise.setOnClickListener {
            customDialogForBackButton()
        }

        btnStopRest.setOnClickListener {
            customDialogForBackButton()
        }

        tts = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()
        setUpRestView()

        setupExerciseStatusRecyclerView()
    }


    override fun onDestroy() {
        if (restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if (player != null){
            player!!.stop()
        }

        super.onDestroy()
    }


    private fun setRestProgressbar(){
        progressBar.progress = restProgress
        restTimer = object: CountDownTimer(restDuration *1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = restDuration.toInt()-restProgress
                tvTimer.text = (restDuration - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setUpExerciseView()
            }
        }.start()
    }

    private fun setUpRestView(){
        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()

        }catch (e: Exception){
            e.printStackTrace()
        }

        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }


        setRestProgressbar()
        tvUpcomingExercise.text = exerciseList!![currentExercisePosition +1].getName()

    }

    private fun setExerciseProgressBar(){
        exerciseProgressBar.progress = exerciseProgress
        exerciseTimer = object:
                CountDownTimer(exerciseDuration * 1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                exerciseProgressBar.progress = exerciseDuration.toInt() - exerciseProgress
                tvExerciseTimer.text = (exerciseDuration - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity,
                        FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun setUpExerciseView(){
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()

        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

    override fun onInit(status: Int){
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The Language specified is not supported")
            }
        }else{
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setupExerciseStatusRecyclerView(){

        rvExerciseStatus.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdapter

    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)

        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        customDialog.findViewById<Button>(R.id.tvYes).setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.findViewById<Button>(R.id.tvNo).setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()

    }

}