package com.example.galleryviacatalog.ui.home.model

import java.util.Date

data class PhotoUploadResult(
    var localPath: String? = null,
    var uniqueKey: String = "$localPath-${Date().time}",
    val filename: String = "",
    var isChecked: Boolean = false,
    var orderNumber: Int = 0,
    var id: Int? = 0,
    var isBackendPhoto: Boolean = false
)

data class LocalPhoto(
    var localPath: String? = null,
    var uniqueKey: String = "$localPath-${Date().time}",
    var isChecked: Boolean = false,
    var orderNumber: Int = 0
)