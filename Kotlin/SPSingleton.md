```
package com.beans.base.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import androidx.preference.PreferenceManager
import java.util.*


class SPSingleton private constructor(name: String) {

    var sharedPreferences: SharedPreferences? = null
    private val editor: SharedPreferences.Editor

    //是否是执行apply的模式，false表示为commit保存数据
    private var isApplyMode = false

    init {
        sharedPreferences = if (DEFAULT == name) {
            PreferenceManager.getDefaultSharedPreferences(ToolsLibAPP.get())
        } else {
            ToolsLibAPP.get().getSharedPreferences(name, Context.MODE_PRIVATE)
        }
        editor = sharedPreferences!!.edit()
    }

    companion object {
        val instanceMap = HashMap<String, SPSingleton>()
        private val DEFAULT = "default"
        const val SP_SSO = "SP_SSO"

        @JvmStatic
        @JvmOverloads
        operator fun get(name: String = "text"): SPSingleton {
            if (instanceMap[name] == null) {
                synchronized(SPSingleton::class.java) {
                    if (instanceMap[name] == null) {
                        instanceMap[name] = SPSingleton(name)
                    }
                }
            }
            //这里每次get操作时强制将保存模式改为commit的方式
            instanceMap[name]?.isApplyMode = false
            return instanceMap[name]!!
        }
    }

    // 如果用apply模式的话，得要先调用这个方法，
    // 然后链式调用后续的存储方法，最后以commit方法结尾
    fun applyMode(): SPSingleton {
        isApplyMode = true
        return this
    }

    fun commit() {
        isApplyMode = false
        editor.commit()
    }

    fun putBoolean(key: String, value: Boolean): SPSingleton {
        editor.putBoolean(key, value)
        save()
        return this
    }

    private fun save() {
        if (isApplyMode) {
            editor.apply()
        } else {
            editor.commit()
        }
    }

    fun putFloat(key: String, value: Float): SPSingleton {
        editor.putFloat(key, value)
        save()
        return this
    }

    fun getFloat(key: String, defValue: Float): Float {
        return sharedPreferences!!.getFloat(key, defValue)
    }

    fun putLong(key: String, value: Long): SPSingleton {
        editor.putLong(key, value)
        save()
        return this
    }

    fun getLong(key: String, defValue: Long): Long {
        return sharedPreferences!!.getLong(key, defValue)
    }

    fun putInt(key: String, value: Int): SPSingleton {
        editor.putInt(key, value)
        save()
        return this
    }

    fun putString(key: String, value: String?): SPSingleton {
        editor.putString(key, value)
        save()
        return this
    }

    fun <T> getObject(key: String, clazz: Class<T>): T? {
        val result: String? = getString(key, "")
        return if (!TextUtils.isEmpty(result)) {
            try {
                JsonUtil.parseJsonToObject(result, clazz)
            } catch (e: Exception) {
                null
            }
        } else null
    }

    fun putObject(key: String, value: Any): SPSingleton {
        editor.putString(key, JsonUtil.objectToJson(value))
        save()
        return this
    }

    @JvmOverloads
    fun getString(key: String, defValue: String? = null): String? {
        return sharedPreferences!!.getString(key, defValue)
    }

    fun removeKey(key: String) {
        editor.remove(key)
        save()
    }

    fun getInt(key: String, defValue: Int): Int {
        return sharedPreferences!!.getInt(key, defValue)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPreferences!!.getBoolean(key, defValue)
    }


    fun <T> putList(key: String, list: List<T>) {
        editor.putString(key, JsonUtil.objectToJson(list))
        save()
    }

    inline fun <reified T> getList(key: String): List<T> {
        var list: ArrayList<T> = ArrayList()
        val listStr = sharedPreferences!!.getString(key, null)
        if (listStr != null) {
            list = JsonUtil.parseJsonToObject<ArrayList<T>>(listStr)
        }
        return list
    }

}

```