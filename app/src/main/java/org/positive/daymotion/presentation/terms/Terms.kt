package org.positive.daymotion.presentation.terms

enum class Terms(
    val title: String,
    val htmlPath: String
) {
    TERMS_OF_USE("서비스 약관", "file:///android_asset/terms_of_use_text.html"),
    PRIVACY_POLICY("개인정보 보호정책", "file:///android_asset/privacy_policy_text.html"),
}