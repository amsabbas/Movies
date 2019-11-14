package com.thiqah.movies

import androidx.room.Room
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.mock
import com.thiqah.movies.core.db.ThiqahDatabase
import com.thiqah.movies.data.source.local.model.MoviesDao
import com.thiqah.movies.data.source.remote.model.post.Movie
import com.thiqah.movies.domain.interactor.GetMoviesUseCase
import org.junit.After
import org.junit.Test
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thiqah.movies.presentation.viewmodel.MoviesViewModel
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class MoviesUnitTestUnitTest {

    private lateinit var viewModel: MoviesViewModel

    private lateinit var context: Context

    @Mock
    lateinit var  mockMoviesUseCase: GetMoviesUseCase

    @Before
    @Throws(Exception::class)
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        context = InstrumentationRegistry.getInstrumentation().context

        viewModel = MoviesViewModel(mockMoviesUseCase)

    }

    @After
    @Throws(Exception::class)
    fun tearDown() {

    }

    @Test
    fun testGetPost() {

        val movie1 = Movie(1, "title1", "body1")
        val movie2 = Movie(2, "title2", "body2")

        val movies = ArrayList<Movie>()
        movies.add(movie1)
        movies.add(movie2)

        //given
        whenever(mockMoviesUseCase.getMovies())
            .thenReturn(Observable.just(movies))

        // when
        viewModel.getMovies()

        // then
        verify(mockMoviesUseCase).getMovies()
        Assert.assertEquals(movies, viewModel.moviesObservableResource.value)
    }

}
