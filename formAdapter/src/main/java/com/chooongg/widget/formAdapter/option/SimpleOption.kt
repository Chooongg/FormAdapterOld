package com.chooongg.widget.formAdapter.option

/**
 * 简单的选项
 */
class SimpleOption(private val key: String, private val value: Any?) : Option {
    override fun getKey() = key
    override fun getValue() = value
}