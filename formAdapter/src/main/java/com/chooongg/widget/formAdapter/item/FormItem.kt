package com.chooongg.widget.formAdapter.item

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.enum.FormEnableMode
import com.chooongg.widget.formAdapter.enum.FormOutputMode
import com.chooongg.widget.formAdapter.enum.FormVisibilityMode
import com.chooongg.widget.formAdapter.typeset.Typeset
import kotlin.reflect.KClass

abstract class FormItem(
    /**
     * 名称
     */
    val name: CharSequence,
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

    /**
     * 菜单图标
     */
    @DrawableRes
    open var menuIconRes: Int? = null
        set(value) {
            field = value
            menuIcon = null
        }

    /**
     * 菜单图标
     */
    open var menuIcon: Drawable? = null
        set(value) {
            field = value
            menuIconRes = null
        }

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
     * 检查内容是否合法
     */
    open fun checkIfTheContentIsLegal(): Boolean {
        return content != null
    }

    /**
     * 输出内容
     */
    open fun outputContent(adapter: FormPartAdapter) {
        val isRealVisible = isRealVisible(adapter.globalAdapter.isEditable)
        val isRealEnable = isRealEnable(adapter.globalAdapter.isEditable)
        if (outputMode == FormOutputMode.VISIBLE && !isRealVisible) return
        if (outputMode == FormOutputMode.VISIBLE_AND_ENABLED && !isRealVisible && !isRealEnable) return
        if (outputMode == FormOutputMode.ENABLED && !isRealEnable) return
        TODO()
    }

    abstract fun onCreateItemView(adapter: FormPartAdapter, parent: ViewGroup): View

    abstract fun onBindItemView(adapter: FormPartAdapter, holder: FormViewHolder)

    abstract fun onBindItemView(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        payloads: MutableList<Any>?
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormItem) return false

        if (name != other.name) return false
        if (field != other.field) return false
        if (extensionFieldAndContent != other.extensionFieldAndContent) return false
        if (readOnlyType != other.readOnlyType) return false
        if (hint != other.hint) return false
        if (content != other.content) return false
        if (isRequired != other.isRequired) return false
        if (visibilityMode != other.visibilityMode) return false
        if (enableMode != other.enableMode) return false
        if (outputMode != other.outputMode) return false
        if (isShowMenu != other.isShowMenu) return false
        if (menuText != other.menuText) return false
        if (menuIconRes != other.menuIconRes) return false
        if (menuIcon != other.menuIcon) return false
        if (isShowOnEdge != other.isShowOnEdge) return false
        if (isNeedToTypeset != other.isNeedToTypeset) return false
        if (typeset != other.typeset) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (field?.hashCode() ?: 0)
        result = 31 * result + (extensionFieldAndContent?.hashCode() ?: 0)
        result = 31 * result + readOnlyType.hashCode()
        result = 31 * result + (hint?.hashCode() ?: 0)
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + isRequired.hashCode()
        result = 31 * result + visibilityMode.hashCode()
        result = 31 * result + enableMode.hashCode()
        result = 31 * result + outputMode.hashCode()
        result = 31 * result + isShowMenu.hashCode()
        result = 31 * result + (menuText?.hashCode() ?: 0)
        result = 31 * result + (menuIconRes ?: 0)
        result = 31 * result + (menuIcon?.hashCode() ?: 0)
        result = 31 * result + isShowOnEdge.hashCode()
        result = 31 * result + isNeedToTypeset.hashCode()
        result = 31 * result + (typeset?.hashCode() ?: 0)
        return result
    }
}