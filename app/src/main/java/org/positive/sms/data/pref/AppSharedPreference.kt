package org.positive.sms.data.pref

interface AppSharedPreference {

    var unixTime: Long

    var authToken: String?
}