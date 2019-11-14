package com.thiqah.movies

import androidx.room.Room
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.thiqah.movies.core.db.ThiqahDatabase
import com.thiqah.movies.data.source.local.model.MoviesDao
import com.thiqah.movies.data.source.remote.model.post.Movie
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class RoomUnitTest {

    @Inject
    lateinit var database: ThiqahDatabase

    private lateinit var context: Context

    private lateinit var moviesDao: MoviesDao

    @Before
    @Throws(Exception::class)
    fun setUp() {

        context = InstrumentationRegistry.getInstrumentation().context

        database = Room.inMemoryDatabaseBuilder(
            context,
            ThiqahDatabase::class.java
        ).allowMainThreadQueries().build()

        moviesDao = database.postsDao()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        database.close()
    }


    @Test
    fun testDatabaseCreated() {
        assert(database.isOpen)
    }

    @Test
    fun testInsertPost() {

        val movie1 = Movie(1, "title1", "body1")
        val movie2 = Movie(2, "title2", "body2")

        val movies = ArrayList<Movie>()
        movies.add(movie1)
        movies.add(movie2)


        moviesDao.insertMovie(movies)

        val observer = moviesDao.getAllMovies().test()

        observer.assertNoErrors()
        observer.assertValueCount(1)
        observer.assertValue { movies: List<Movie> -> movies[0].title.equals(movie1.title, true) }
    }

}
