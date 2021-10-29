package com.example.victoria_intersvyaz_test.repository

import com.example.victoria_intersvyaz_test.api.ApiHelper
import com.example.victoria_intersvyaz_test.api.CommentApiState
import com.example.victoria_intersvyaz_test.api.RetrofitBuilder
import com.example.victoria_intersvyaz_test.api.RetrofitBuilder.apiService
import com.example.victoria_intersvyaz_test.model.Photos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class DataRepository(private val apiHelper: ApiHelper) {
   /* fun getPhotos(): Flow<ArrayList<Photos>> = flow {
        val postList= apiHelper.getPhotos()
        emit(postList)
    }.flowOn(Dispatchers.IO)
*/
    suspend fun getPhotos(id:Int): Flow<CommentApiState<Photos>> {
        return flow {
            // get the comment Data from the api
            val comment=apiService.getPhotos(id)
            // Emit this data wrapped in
            // the helper class [CommentApiState]
            emit(CommentApiState.success(comment))
        }.flowOn(Dispatchers.IO)
    }

    /*suspend fun fetchTrendingMovies(): Flow<Result<TrendingMovieResponse>?> {
        return flow {
            emit(fetchTrendingMoviesCached())
            emit(Result.loading())
            val result = movieRemoteDataSource.fetchTrendingMovies()

            //Cache to database if response is successful
            if (result.status == Result.Status.SUCCESS) {
                result.data?.results?.let { it ->
                    movieDao.deleteAll(it)
                    movieDao.insertAll(it)
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }*/
}