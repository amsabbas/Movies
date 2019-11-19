package com.thiqah.movies.data


import com.thiqah.movies.data.source.remote.model.post.Movie
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class MoviesDataUnitTest {



    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testMovieSuccess() {

        val movie= Movie(1, "title1", "body1")

        // then
        Assert.assertEquals(movie.id, 1)
    }





}
