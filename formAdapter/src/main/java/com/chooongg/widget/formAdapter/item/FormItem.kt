package com.chooongg.widget.formAdapter.item

import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.enum.FormEnableMode
import com.chooongg.widget.formAdapter.enum.FormOutputMode
import com.chooongg.widget.formAdapter.enum.FormVisibilityMode
import com.chooongg.widget.formAdapter.typeset.Typeset
import org.json.JSONObject
import kotlin.reflect.KClass

abstract class FormItem(
    /**
     * 名称
     */
    val name: CharSequence?,
    /**
     * 字段
     */
    val field: String?,
) {

    /**
     * 扩展字段和内容
     */
    private var extensionFieldAndContent: HashMap<String, Any?>? = null

    /**
     * 只读展示类型
     */
    open var readOnlyType: KClass<out FormItem> = this::class
        protected set

    /**
     * 提示
     */
    open var hint: CharSequence? = null

    /**
     * 内容
     */
    open var content: Any? = null

    /**
     * 是否必填
     */
    open var isRequired: Boolean = false

    /**
     * 可见模式
     */
    open var visibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 启用模式
     */
    open var enableMode: FormEnableMode = FormEnableMode.ONLY_EDIT

    /**
     * 输出模式
     */
    open var outputMode: FormOutputMode = FormOutputMode.ALWAYS

    /**
     * 是否显示菜单
     */
    open var isShowMenu = false

    /**
     * 菜单文本
     */
    open var menuText: CharSequence? = null
        set(value) {
            field = value
            isShowMenu = field != null || menuIconRes != null
        }

    /**
     * 菜单图标
     */
    @DrawableRes
    open var menuIconRes: Int? = null
        set(value) {
            field = value
            isShowMenu = field != null || menuText != null
        }

    /**
     * 菜单图标大小
     */
    @Px
    open var menuIconSize: Int? = null

    /**
     * 是否在组边缘展示
     */
    open var isShowOnEdge = true

    /**
     * 是否需要排版
     */
    open var isNeedToTypeset = true

    /**
     * 排版样式
     */
    open var typeset: Typeset? = null

    /**
     * 自定义输出接口
     */
    private var customOutputBlock: ((json: JSONObject) -> Unit)? = null

    /**
     * 跨度权重
     */
    @androidx.annotation.IntRange(from = 1, to = 24)
    var spanWeight = 1

    /**
     * 边界信息
     */
    var boundary: Boundary = Boundary()
        internal set

    internal var spanSize = 120

    var singleLineCount = 1
        internal set
    var singleLineIndex = 0
        internal set

    /**
     * 组索引
     */
    var groupIndex = -1
        internal set

    /**
     * 组中的位置
     */
    var positionForGroup = -1
        internal set

    /**
     * 是否在组标题后面
     */
    var isAfterTheGroupTitle = false
        internal set

    /**
     * 当前part的索引
     */
    var partPosition = -1
        internal set

    /**
     * adapter的绝对索引
     */
    var adapterPosition = -1
        internal set

    /**
     * 设置扩展内容
     */
    fun putExtensionContent(key: String, value: Any?) {
        if (value != null) {
            if (extensionFieldAndContent == null) extensionFieldAndContent = HashMap()
            extensionFieldAndContent!![key] = value
        } else if (extensionFieldAndContent != null) {
            extensionFieldAndContent!!.remove(key)
            if (extensionFieldAndContent!!.isEmpty()) extensionFieldAndContent = null
        }
    }

    /**
     * 获取扩展内容
     */
    fun getExtensionContent(key: String): Any? = extensionFieldAndContent?.get(key)

    /**
     * 是否有扩展内容
     */
    fun hasExtensionContent(key: String): Boolean =
        extensionFieldAndContent?.containsKey(key) ?: false

    /**
     * 快照扩展字段和内容
     */
    fun snapshotExtensionFieldAndContent(): Map<String, Any?> =
        extensionFieldAndContent?.toMap() ?: emptyMap()

    /**
     * 设置自定义输出监听
     */
    fun setCustomOutputListener(block: ((json: JSONObject) -> Unit)?) {
        customOutputBlock = block
    }

    /**
     * 真实的可见性
     */
    open fun isRealVisible(isEditable: Boolean): Boolean {
        return when (visibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ONLY_EDIT -> isEditable
            FormVisibilityMode.ONLY_SEE -> !isEditable
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 真实的可用性
     */
    open fun isRealEnable(isEditable: Boolean): Boolean {
        return when (enableMode) {
            FormEnableMode.ALWAYS -> true
            FormEnableMode.ONLY_EDIT -> isEditable
            FormEnableMode.ONLY_SEE -> !isEditable
            FormEnableMode.NEVER -> false
        }
    }

    /**
     * 检查数据正确性
     */
    open fun checkDataCorrectness(): Boolean {
        return if (isRequired) content != null else true
    }

    /**
     * 执行输出
     */
    fun executeOutput(adapter: FormPartAdapter, json: JSONObject) {
        val isRealVisible = isRealVisible(adapter.globalAdapter.isEditable)
        val isRealEnable = isRealEnable(adapter.globalAdapter.isEditable)
        if (outputMode == FormOutputMode.VISIBLE && !isRealVisible) return
        if (outputMode == FormOutputMode.VISIBLE_AND_ENABLED && !isRealVisible && !isRealEnable) return
        if (outputMode == FormOutputMode.ENABLED && !isRealEnable) return
        if (customOutputBlock != null) {
            customOutputBlock!!.invoke(json)
            return
        }
        extensionFieldAndContent?.forEach {
            json.put(it.key, it.value)
        }
    }

    protected open fun outputData(adapter: FormPartAdapter, json: JSONObject) {
        json.putOpt(field, content)
    }

    /**
     * 创建内容视图
     */
    abstract fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup): View

    /**
     * 绑定内容视图
     */
    abstract fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder)

    /**
     * 绑定内容视图
     */
    open fun onBindContentView(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        payloads: MutableList<Any>?
    ) = onBindContentView(adapter, holder)

    /**
     * 排版是否忽略排版按钮
     */
    open fun typesetIgnoreMenuButtons() = false
}