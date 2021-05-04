package org.befour.sms.data.repository

import org.befour.sms.data.api.ServerTimeApi
import javax.inject.Inject

class ServerTimeRepositoryImpl @Inject constructor(
    private val serverTimeApi: ServerTimeApi
) : ServerTimeRepository {

    override fun getSeoulTime() = serverTimeApi.loadServerTime("Asia/Seoul")
}