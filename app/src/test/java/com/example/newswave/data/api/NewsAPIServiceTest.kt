package com.example.newswave.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewsAPIServiceTest {

    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)


    }

    private fun enqueueMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)

    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=b7122b5c5f8948eda9715867b6240ce6")


        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val articlesList = responseBody!!.articles
            assertThat(articlesList.size).isEqualTo(20)

        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us",1).body()
            val articlesList = responseBody!!.articles
            val article = articlesList[0]
            assertThat(article.author).isEqualTo("Saheli Roy Choudhury")
            assertThat(article.url).isEqualTo("https://www.cnbc.com/2021/01/04/samsung-galaxy-unpacked-2021.html")
            assertThat(article.publishedAt).isEqualTo("2021-01-04T03:25:00Z")


        }
    }


    @After
    fun tearDown() {
        server.shutdown()
    }
}

//class NewsAPIServiceTest {
//    private lateinit var service:NewsAPIService
//    private lateinit var server:MockWebServer
//
//    @Before
//    fun setUp() {
//        server = MockWebServer()
//        service = Retrofit.Builder()
//            .baseUrl(server.url(""))
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(NewsAPIService::class.java)
//    }
//
//
//    private fun enqueueMockResponse(
//        fileName:String
//    ){
//        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
//        //Get the data from stream and set it into memory buffer then create instance of mock response
//        val source = inputStream.source().buffer()
//        val mockResponse = MockResponse()
//        //Then set the body of mock response passing the string format of the source.
//        mockResponse.setBody(source.readString(Charsets.UTF_8))
//        //Finally enqueue the mock response to mock web server response.
//        server.enqueue(mockResponse)
//
//    }
//
//
//    //Ready to write test cases
//
//    @Test
//    fun getTopHeadlines_sentRequest_receivedExcepted(){
//        //runBlocking - Co-routine builder for testing, it runs the new co-routine and blocks the current thread
//        //until its completion.
//        runBlocking {
//            enqueueMockResponse("newsresponse.json") //local json file - newsresponse.json
//            //This will start the MWS and prepare a fake response using local resource file then send
//            //request to mock server and getting the return responseBody of the request.
//            val responseBody = service.getTopHeadlines("us",1).body()
//            //GET THE REQ received by mws.
//            val request = server.takeRequest()
//            //implemnt truth to use assertThat
//            assertThat(responseBody).isNotNull()
//
//            //ANOTHER asserstion to check request
//            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=229c0ddf5ed240bdbef87ecf4198c802")
//
//        }
//
//    }
//    //test the available quantity of articles. According to site, the default page size is 20.
//
//    @Test
//    fun getTopHeadlines_receivedResponse_correctPageSize(){
//        //runBlocking - Co-routine builder for testing, it runs the new co-routine and blocks the current thread
//        //until its completion.
//        runBlocking {
//            enqueueMockResponse("newsresponse.json") //local json file - newsresponse.json
//            //This will start the MWS and prepare a fake response using local resource file then send
//            //request to mock server and getting the return responseBody of the request.
//            val responseBody = service.getTopHeadlines("us", 1).body()
//            //GET THE REQ received by mws.
//            val articlesList = responseBody!!.articles
//            //implement truth to use assertThat
//            //assertThat(articlesList.size).isEqualTo(20)
//            val article = articlesList[0]
//
//            assertThat(article.author).isEqualTo("Des Bieler")
//            assertThat(article.url).isEqualTo("https://www.washingtonpost.com/sports/2024/05/19/tyson-fury-oleksandr-usyk/")
//            assertThat(article.publishedAt).isEqualTo("2024-05-20T03:34:43Z")
//        }
//
//    }
//
//
//    @After
//    fun tearDown() {
//       server.shutdown()
//    }
//}

/**
 We need to create object reference variables for NewsAPIService and MockWebServer.

 Setup is generated and functions annotated with before and after annotations are tear down.

     When test cases are written there are situations where several tests need similar objects
     created before they can run.
     Annotating a public void method with @Before annotation like this, causes that method to run
     before the methods annotated with @Test annotation.
     whereas annotating a public void method with @After annotation like this, causes that method
    to run after the methods annotated with @Test annotation.

 Inside the fun setUp():
 MockWebServer() is constructed and service is constructed using Retrofit. Normally we add base url
 of NewsApI but we gonna add MockWebServer.url and it is kept empty to use default settings.
 Next add converter factory and build then create the NewsAPIService instance.

 We don't have to write the code to start the fun as when we are invoking server's functions
 it will start automatically. But codes to shutdown the server is required after all testing are done.

 Inside the fun tearDown():
 We will write the codes to shut down the MockWebServer.











 */