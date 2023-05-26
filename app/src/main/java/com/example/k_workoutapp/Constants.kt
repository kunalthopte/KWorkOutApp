package com.example.k_workoutapp

object Constants {
    fun deafaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val normalPushUps = ExerciseModel(
            1,
            "Normal Push Ups",
            R.drawable.normal_pushups
        )
        exerciseList.add(normalPushUps)
        val wideGripPushUps = ExerciseModel(
            2,
            "Wide Grip Push Ups",
            R.drawable.wide_grip_pushups
        )
        exerciseList.add(wideGripPushUps)
        val closeGripPushUps = ExerciseModel(
            3,
            "Close Grip Push Ups",
            R.drawable.close_grip
        )
        exerciseList.add(closeGripPushUps)
        val clappingPushUps = ExerciseModel(
            4,
            "Clapping Push Ups",
            R.drawable.clapping_upshups
        )
        exerciseList.add(clappingPushUps)
        val oneArmPushUps = ExerciseModel(
            5,
            "One Arm Push Ups",
            R.drawable.one_arm_push_ups
        )
        exerciseList.add(oneArmPushUps)


        return exerciseList
    }
}