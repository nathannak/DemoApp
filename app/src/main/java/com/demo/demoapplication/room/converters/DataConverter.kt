package com.demo.demoapplication.room.converters

import androidx.room.TypeConverter
import com.demo.demoapplication.model.AcronymItem
import com.demo.demoapplication.model.Lf
import com.demo.demoapplication.model.Var
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataConverter {

    @TypeConverter
    fun fromAcronymItemList(lf: List<AcronymItem>): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<AcronymItem>>() {}.type
        return gson.toJson(lf, type)
    }

    @TypeConverter
    fun toAcronymItemList(lf: String): List<AcronymItem>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<AcronymItem>>() {}.type
        return gson.fromJson<List<AcronymItem>>(lf, type)
    }


    @TypeConverter
    fun fromLfList(lf: List<Lf>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Var>>() {}.type
        return gson.toJson(lf, type)
    }

    @TypeConverter
    fun toLfList(lf: String): List<Lf> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Var>>() {}.type
        return gson.fromJson<List<Lf>>(lf, type)
    }


    @TypeConverter
    fun fromVarList(countryLang: List<Var>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Var>>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toVarList(countryLangString: String): List<Var> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Var>>() {}.type
        return gson.fromJson<List<Var>>(countryLangString, type)
    }
}