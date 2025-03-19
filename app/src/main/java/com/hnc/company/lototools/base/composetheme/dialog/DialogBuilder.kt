package com.hnc.company.lototools.base.composetheme.dialog

class DialogBuilder : IDialogBuilder {
    var title: String? = null
    var message: String? = null
    var appInfo: String? = null
    var deviceInfo: String? = null
    var type: TypeDialog = TypeDialog.SUCCESS
    var actionLeft: ActionDialog? = null
    var actionRight: ActionDialog? = null
    override fun addTitle(value: String): DialogBuilder {
        this.title = value
        return this
    }

    override fun addMessage(value: String): DialogBuilder {
        this.message = value
        return this
    }

    override fun addAppInfo(value: String): DialogBuilder {
        this.appInfo = value
        return this
    }

    override fun addDeviceInfo(value: String): DialogBuilder {
        this.deviceInfo = value
        return this
    }

    override fun addType(typeDialog: TypeDialog): DialogBuilder {
        this.type = typeDialog
        return this
    }

    override fun addActionLeft(action: ActionDialog): DialogBuilder {
        this.actionLeft = action
        return this
    }

    override fun addActionRight(action: ActionDialog): DialogBuilder {
        this.actionRight = action
        return this
    }
}

interface IDialogBuilder {
    fun addTitle(value: String): DialogBuilder
    fun addMessage(value: String): DialogBuilder
    fun addAppInfo(value: String): DialogBuilder
    fun addDeviceInfo(value: String): DialogBuilder
    fun addType(typeDialog: TypeDialog): DialogBuilder
    fun addActionLeft(action: ActionDialog): DialogBuilder
    fun addActionRight(action: ActionDialog): DialogBuilder
}
