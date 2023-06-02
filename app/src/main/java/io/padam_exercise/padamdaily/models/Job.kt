package io.padam_exercise.padamdaily.models

class Job(
    private var job_title: String,
    private var job_desc: String,
    private var job_missions: String,
    private var job_tech: String
) {
    fun getJobTitle() : String {
        return job_title
    }

    fun getJobDesc(): String {
        return job_desc
    }

    fun getJobMissions() : String {
        return job_missions
    }

    fun getJobTech() : String {
        return job_tech
    }
}