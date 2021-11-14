package com.markkolenbrander.a7minuteworkout

class Constants {
    companion object{
        fun defaultExerciseList(): ArrayList<ExerciseModel>{
            val exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(
                    1,
                    "Ball Wall Sit",
                    R.drawable.ic_back_ball_sit,
                    false,
                    false)
            exerciseList.add(jumpingJacks)

            val wallSit = ExerciseModel(
                    2,
                    "Band Leg Extend",
                    R.drawable.ic_band_leg_extend,
                    false,
                    false)
            exerciseList.add(wallSit)

            val pushUp = ExerciseModel(
                    3,
                    "Band Side Squat",
                    R.drawable.ic_band_side_squat,
                    false,
                    false)
            exerciseList.add(pushUp)

            val abdominalCrunch = ExerciseModel(
                    4,
                    "Band Side Step",
                    R.drawable.ic_band_side_step,
                    false,
                    false)
            exerciseList.add(abdominalCrunch)

            val stepUpOnChair = ExerciseModel(
                    5,
                    "Band Tricep",
                    R.drawable.ic_band_tricep,
                    false,
                    false)
            exerciseList.add(stepUpOnChair)

            val squat = ExerciseModel(
                    6,
                    "Fist Punch",
                    R.drawable.ic_fist_punch,
                    false,
                    false)
            exerciseList.add(squat)

            val tricepDipOnChair = ExerciseModel(
                    7,
                    "Knee Push Up",
                    R.drawable.ic_knee_push_up,
                    false,
                    false)
            exerciseList.add(tricepDipOnChair)

            val plank = ExerciseModel(
                    8,
                    "Leg Raise",
                    R.drawable.ic_leg_raise,
                    false,
                    false)
            exerciseList.add(plank)

            val highKneesRunningInPlace = ExerciseModel(
                    9,
                    "Running In Place",
                    R.drawable.ic_run_in_place,
                    false,
                    false)
            exerciseList.add(highKneesRunningInPlace)

            val lunges = ExerciseModel(
                    10,
                    "Side Step Arm Raise",
                    R.drawable.ic_side_step_arm_raise,
                    false,
                    false)
            exerciseList.add(lunges)

            val pushupAndRotation = ExerciseModel(
                    11,
                    "Squat Jump",
                    R.drawable.ic_squat_jump,
                    false,
                    false)
            exerciseList.add(pushupAndRotation)

            val sidePlank = ExerciseModel(
                    12,
                    "Squat",
                    R.drawable.ic_squat,
                    false,
                    false)
            exerciseList.add(sidePlank)

            return exerciseList


        }
    }
}