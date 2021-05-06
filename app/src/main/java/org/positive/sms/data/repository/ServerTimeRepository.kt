package org.positive.sms.data.repository

import io.reactivex.rxjava3.core.Single
import org.positive.sms.data.model.ServerTimeResponse

interface ServerTimeRepository {

    fun getSeoulTime(): Single<ServerTimeResponse>
}