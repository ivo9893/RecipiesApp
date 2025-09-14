package com.example.myrecipies.di

import android.content.Context
import com.example.myrecipies.data.api.CategoriesApi
import com.example.myrecipies.data.api.UnitsApi
import com.example.myrecipies.data.api.UsersApi
import com.example.myrecipies.data.api.dto.ApiService
import com.example.myrecipies.data.db.dao.CategoriesDao
import com.example.myrecipies.data.db.dao.RecipiesDao
import com.example.myrecipies.data.db.dao.UnitsDao
import com.example.myrecipies.data.db.entity.RecipiesDatabase
import com.example.myrecipies.data.repository.CategoriesRepository
import com.example.myrecipies.data.repository.RecipiesRepository
import com.example.myrecipies.data.repository.UnitsRepository
import com.example.myrecipies.domain.repository.ICategoriesRepository
import com.example.myrecipies.domain.repository.IRecipiesRepository
import com.example.myrecipies.domain.repository.IUnitsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://192.168.0.5:5000/"  // Replace with your API base URL

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY  // Logs request & response bodies
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): UsersApi =
        retrofit.create(UsersApi::class.java)

    @Provides
    @Singleton
    fun provideCategoriesApi(retrofit: Retrofit): CategoriesApi =
        retrofit.create(CategoriesApi::class.java)

    @Provides
    @Singleton
    fun provideUnitsApi(retrofit: Retrofit): UnitsApi =
        retrofit.create(UnitsApi::class.java)


    @Provides
    @Singleton
    fun provideUnitsRepository(
        api: UnitsApi,
        dao : UnitsDao
    ) : IUnitsRepository = UnitsRepository(api, dao)

    @Provides
    @Singleton
    fun provideRecipiesRepository(
        api: ApiService,
        dao: RecipiesDao
    ): IRecipiesRepository = RecipiesRepository(api, dao)

    @Provides
    @Singleton
    fun provideCategoriesRepository(
        api: CategoriesApi,
        dao: CategoriesDao
    ): ICategoriesRepository = CategoriesRepository(api, dao)

    @Provides
    @Singleton
    fun provideRecipiesDao(appDatabase: RecipiesDatabase): RecipiesDao {
        return appDatabase.recipiesDao()
    }

    @Provides
    @Singleton
    fun provideUnitsDao(appDatabase: RecipiesDatabase): UnitsDao {
        return appDatabase.unitsDao()
    }

    @Provides
    @Singleton
    fun provideCategoriesDao(appDatabase: RecipiesDatabase): CategoriesDao {
        return appDatabase.categoriesDao()
    }

    @Provides
    @Singleton
    fun provideRecipiesDatabase(@ApplicationContext context: Context): RecipiesDatabase { // Added @ApplicationContext and Context
        return RecipiesDatabase.getDatabase(context)
    }
}