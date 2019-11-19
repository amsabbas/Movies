package com.thiqah.movies.presentation


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.thiqah.movies.base.RxSchedulersOverrideRule
import com.thiqah.movies.data.source.remote.model.post.Movie
import com.thiqah.movies.domain.interactor.GetMoviesUseCase
import com.thiqah.movies.presentation.viewmodel.MoviesViewModel
import io.reactivex.Observable
import org.junit.*
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
class MoviesViewModelUnitTest {

    private lateinit var viewModel: MoviesViewModel

    @Mock
    lateinit var  mockMoviesUseCase: GetMoviesUseCase

    @Rule
    @JvmField
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule() //needed to mock rx

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule  = InstantTaskExecutorRule() //needed to mock rx

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        viewModel = MoviesViewModel(mockMoviesUseCase)

    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    @Test
    fun testGetPostSuccess() {

        val movie1 = Movie(1, "title1", "body1")
        val movie2 = Movie(2, "title2", "body2")

        val movies = ArrayList<Movie>()
        movies.add(movie1)
        movies.add(movie2)

        //given
        whenever(mockMoviesUseCase.getMovies())
            .thenReturn(Observable.just(movies))

        // call
        viewModel.getMovies()

        // then
        Assert.assertEquals(movies, viewModel.moviesObservableResource.value)
    }


    @Test
    fun testGetPostFail() {

        //given
        whenever(mockMoviesUseCase.getMovies())
            .thenReturn(Observable.error( Exception()))

        // call
        viewModel.getMovies()

        // then
        Assert.assertEquals(false, (viewModel.moviesObservableResource.loading.value))
    }

}
