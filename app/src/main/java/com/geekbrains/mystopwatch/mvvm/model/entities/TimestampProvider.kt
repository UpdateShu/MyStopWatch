package com.geekbrains.mystopwatch.mvvm.model.entities

interface TimestampProvider {
    fun getMilliseconds(): Long
}