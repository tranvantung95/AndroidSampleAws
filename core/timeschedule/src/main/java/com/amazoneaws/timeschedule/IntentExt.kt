package com.amazoneaws.timeschedule

import android.content.Intent
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


fun Intent.putDataAsBytes(dataInput: Any, key: String) = this.apply {
    val bos = ByteArrayOutputStream()
    val out: ObjectOutputStream?
    try {
        out = ObjectOutputStream(bos)
        out.writeObject(dataInput)
        out.flush()
        val dataInBytes = bos.toByteArray()
        putExtra(key, dataInBytes)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            bos.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
}

fun <T> Intent.getDataFromBytes(key: String): T? {
    val bis = ByteArrayInputStream(getByteArrayExtra(key))
    var `in`: ObjectInput? = null
    try {
        `in` = ObjectInputStream(bis)
        return `in`.readObject() as T
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    } finally {
        try {
            `in`?.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
    return null
}