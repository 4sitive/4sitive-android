package org.positive.daymotion.presentation.my.model

enum class NickNameValidation(val reason: String) {
    OK(""),
    DENIED_SPECIAL_CHAR("유저 아이디에는 특수문자 사용이 불가합니다."),
    DENIED_TOO_LONG("유저 아이디는 8글자 이상을 초과 할 수 없습니다.")
}