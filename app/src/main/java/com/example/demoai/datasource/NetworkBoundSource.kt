package com.example.demoai.datasource

import androidx.annotation.WorkerThread
import com.example.demoai.Utils.CoreUtility
import com.example.demoai.datasource.remote.entity.ErrorModel
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

/**
 * A repository which provides resource from remote end point.
 *
 * [API_RESPONSE_TYPE] represents the type for network.
 * [MAPPED_RETURN_TYPE] represents the final type to be mapped into before returning.
 */
abstract class NetworkBoundSource<API_RESPONSE_TYPE, MAPPED_RETURN_TYPE> {

    /* @Inject
     lateinit var changePassWordModuleRoute: ChangePasswordRoute*/

    fun asFlow(shouldRepeat: Boolean = false, intervalInMillis: Long = 0) = flow<ResourceState<MAPPED_RETURN_TYPE>> {
        if (shouldRepeat) {
            while (true) {
                executeFlow()
                delay(intervalInMillis)
            }
        } else {
            executeFlow()
        }
    }

    suspend fun asData(): ResourceState<MAPPED_RETURN_TYPE> {
        // Emit Loading State
        try {
            // Fetch latest data from server
            val apiResponse = fetchFromRemote()

            // Parse body
            val remoteData = apiResponse.body()

            // Check for response
            if (apiResponse.isSuccessful && remoteData != null) {
                // Emit success state with data
                return ResourceState.success(postProcess(remoteData))
            } else {
                val gson = Gson()
                var errorResponse: ErrorModel? = null
                val adapter: TypeAdapter<ErrorModel> = gson.getAdapter(ErrorModel::class.java)
                try {
                    if (apiResponse.errorBody() != null) {
                        errorResponse = adapter.fromJson(
                            apiResponse.errorBody()?.string()
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                var errorData = ""
                errorResponse?.data?.info?.document?.let {
                    errorData = it
                }

                // Emit Error state
              val message = if (errorResponse?.message.isNullOrEmpty()) {
                    "Error"
                } else {
                    errorResponse?.message.toString()
                }

                return ResourceState.error(
                    message,
                    errorData,
                    errorResponse
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Emit Exception occurred
            return ResourceState.error("Network error!", "Can't get latest data.", null)
        }
    }

    /**
     * Fetches [Response] from the remote end point.
     */
    @WorkerThread
    protected abstract suspend fun fetchFromRemote(): Response<API_RESPONSE_TYPE>

    /**
     * Maps the [API_RESPONSE_TYPE] to a required [MAPPED_RETURN_TYPE].
     */
    @WorkerThread
    protected abstract suspend fun postProcess(originalData: API_RESPONSE_TYPE): MAPPED_RETURN_TYPE

    private suspend fun FlowCollector<ResourceState<MAPPED_RETURN_TYPE>>.executeFlow() {
        // Emit Loading State
        emit(ResourceState.loading())

        try {
            // Fetch latest data from server
            val apiResponse = fetchFromRemote()

            // Parse body
            val remoteData = apiResponse.body()

            // Check for response
            CoreUtility.printLog("RRRRRR test headers", apiResponse.headers().toString())

            if (apiResponse.isSuccessful && remoteData != null) {
                // Emit success state with data
                emit(ResourceState.success(postProcess(remoteData)))
            } else {
                val gson = Gson()
                var errorResponse: ErrorModel? = null
                val adapter: TypeAdapter<ErrorModel> = gson.getAdapter(ErrorModel::class.java)
                try {
                    if (apiResponse.errorBody() != null) {
                        errorResponse = adapter.fromJson(
                            apiResponse.errorBody()?.string()
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                var errorData = ""
                errorResponse?.data?.info?.document?.let {
                    errorData = it
                }
                // Emit Error state
               val message = if (errorResponse?.message.isNullOrEmpty()) {
                    "Error"
                } else {
                    errorResponse?.message.toString()
                }
                // Emit Error state
                emit(ResourceState.error(message, errorData, errorResponse))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Emit Exception occurred
            emit(ResourceState.error("Network error!", "Can't get latest data.", null))
        }
    }
}
