package com.thiqah.movies.domain


import com.nhaarman.mockitokotlin2.whenever
import com.thiqah.movies.data.repository.MoviesRepositoryImpl
import com.thiqah.movies.data.source.local.MoviesLocalDataSource
import com.thiqah.movies.data.source.remote.MoviesRemoteDataSource
import com.thiqah.movies.data.source.remote.model.post.Movie
import com.thiqah.movies.domain.repository.MoviesRepository
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class MoviesRepoUnitTest {

    private lateinit var repo: MoviesRepository

    @Mock
    lateinit var  moviesRemoteDataSource: MoviesRemoteDataSource

    @Mock
    lateinit var  moviesLocalDataSource: MoviesLocalDataSource

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        repo = MoviesRepositoryImpl(moviesRemoteDataSource,moviesLocalDataSource)

    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    @Test
    fun testGetRemoteMoviesSuccess() {

        val movie1 = Movie(1, "title1", "body1")
        val movie2 = Movie(2, "title2", "body2")

        val movies = ArrayList<Movie>()
        movies.add(movie1)
        movies.add(movie2)

        //given
        whenever(repo.getRemoteMovies())
            .thenReturn(Observable.just(movies))

        // call
        val repoTest = repo.getRemoteMovies()

        // then
        Assert.assertEquals(movies, repoTest.blockingSingle())
    }


    @Test
    fun testGetLocalMoviesSuccess() {

        val movie1 = Movie(1, "title1", "body1")
        val movie2 = Movie(2, "title2", "body2")

        val movies = ArrayList<Movie>()
        movies.add(movie1)
        movies.add(movie2)

        //given
        whenever(repo.getLocalMovies())
            .thenReturn(Single.just(movies))

        // call
        val repoTest = repo.getLocalMovies()

        // then
        Assert.assertEquals(movies, repoTest.blockingGet())
    }


}
