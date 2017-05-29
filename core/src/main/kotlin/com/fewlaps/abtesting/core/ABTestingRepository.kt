package com.fewlaps.abtesting.core

interface ABTestingRepository {
    fun getOption(experiment: String): String?

    fun saveOption(experiment: String, option: String)
}