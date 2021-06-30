package net.rafaeltoledo.filasp.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FilaSpService {

    @FormUrlEncoded
    @POST("processadores/dados.php")
    suspend fun requestData(@Field("dados") data: String = "dados"): List<VaccinationPlace>
}