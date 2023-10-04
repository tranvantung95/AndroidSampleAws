package com.amazonaws.testing.data

import com.amazonaws.model.TeamMatchesModel
import com.amazonaws.model.TeamsModel
import com.amazonaws.network.model.matches.MatchesResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object TestData {
    fun getJsonFromResource(jsonFileName: String): String {
        val url = this::class.java.classLoader?.getResource(jsonFileName)
        return url?.readText().orEmpty()
    }

    fun mockTeamsData(): List<TeamsModel> {
        val teamsJson = getJsonFromResource("teams.json")
        return stringToObject(teamsJson)
    }

    fun mockMatchesData(): TeamMatchesModel {
        val matchesJson = getJsonFromResource("matches.json")
        return stringToObject(matchesJson)
    }

    fun getMatchesAsString(): String {
        val matches = stringToObject<MatchesResponse>(getMatchesResponseAsString())
        return objectToString(matches.matchesDetailResponse?.previousResponses?.first())
    }

    fun getMatchesResponseAsString(): String {
        return getJsonFromResource("matches_response.json")
    }

    fun getTeamsResponseAsString(): String {
        return getJsonFromResource("teams_response.json")
    }

    inline fun <reified T> stringToObject(json: String): T {
        val gson = GsonBuilder().create()
        return gson.fromJson(json, genericType<T>())
    }
    fun <TargetClass> objectToString(objectIn: TargetClass): String {
        return Gson().toJson(objectIn)
    }

    inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type
}