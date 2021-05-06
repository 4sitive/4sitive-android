package org.positive.sms.data.repository

import org.positive.sms.data.api.ServerTimeApi
import javax.inject.Inject

class ServerTimeRepositoryImpl @Inject constructor(
    private val serverTimeApi: ServerTimeApi
) : ServerTimeRepository {

    override fun getSeoulTime() = serverTimeApi.loadServerTime("Asia/Seoul")
}