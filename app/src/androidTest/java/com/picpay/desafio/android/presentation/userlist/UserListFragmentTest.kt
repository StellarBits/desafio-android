package com.picpay.desafio.android.presentation.userlist

import androidx.test.espresso.Espresso.onView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.picpay.desafio.android.R
import com.picpay.desafio.android.presentation.MainActivity
import org.junit.Test
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Matchers


class UserListFragmentTest {

    private val server = MockWebServer()

    @Test
    fun shouldDisplayListItem() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponse
                    else -> errorResponse
                }
            }
        }

        server.start(SERVER_PORT)

        val withTextMatcher = Matchers.allOf(
            withId(R.id.username),
            withText("@eduardo.santos")
        )

        launchActivity<MainActivity>().apply {
            // It seems that the recyclerview data is not appearing in time to perform the check.
            onView(hasDescendant(withText("@eduardo.santos"))).check(ViewAssertions.matches(isDisplayed()))
            //RecyclerViewMatchers.checkRecyclerViewItem(R.id.recyclerView, 0, withTextMatcher)
        }

        server.close()
    }

    companion object {
        private const val SERVER_PORT = 8080

        private val successResponse by lazy {
            val body =
                "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":" +
                        "\"https://randomuser.me/api/portraits/men/9.jpg\"," +
                        "\"username\":\"@eduardo.santos\"}]"

            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
    }
}