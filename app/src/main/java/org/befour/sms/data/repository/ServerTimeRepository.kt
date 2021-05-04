package org.befour.sms.data.repository

import io.reactivex.rxjava3.core.Single
import org.befour.sms.data.model.ServerTimeResponse

interface ServerTimeRepository {

    fun getSeoulTime(): Single<ServerTimeResponse>
}