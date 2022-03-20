package com.jobsity.android.challenge.data.service

import com.jobsity.android.challenge.data.TvMazeApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import java.io.File
import java.net.HttpURLConnection

abstract class ServiceTest {

    protected val server = MockWebServer()

    protected val api = TvMazeApi(apiUrl = "http://localhost:8082/")

    @Before
    fun setUp() = server.start(port = 8082)

    @After
    fun tearDown() = server.shutdown()

    protected fun prepareResponse(path: String? = null, sc: Int = HttpURLConnection.HTTP_OK) {
        server.enqueue(mockResponse(path, sc))
    }

    private fun mockResponse(path: String? = null, sc: Int = HttpURLConnection.HTTP_OK) =
        MockResponse().apply {
            if (path != null) {
                setBody(File(this@ServiceTest.javaClass.classLoader?.getResource(path)!!.file).readText())
            }
            setResponseCode(sc)
        }

}
