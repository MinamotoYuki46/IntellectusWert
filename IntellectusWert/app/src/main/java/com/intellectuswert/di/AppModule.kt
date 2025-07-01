package com.intellectuswert.di

import android.app.Application
import androidx.room.Room
import com.intellectuswert.data.api.ApiService
import com.intellectuswert.data.local.AppDatabase
import com.intellectuswert.data.local.dao.PredictionDao
import com.intellectuswert.data.repository.PredictionRepositoryImpl
import com.intellectuswert.domain.repository.PredictionRepository
import com.intellectuswert.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // --- API ---


//    Physical Devices
    const val BASE_URL = "http://192.168.212.91:8000"

//    AVD
//    const val BASE_URL = "http://10.0.2.2:8000"



    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // --- Room Database ---
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "prediction_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePredictionDao(db: AppDatabase): PredictionDao {
        return db.predictionDao()
    }

    // --- Repository ---
    @Provides
    @Singleton
    fun providePredictionRepository(
        api: ApiService,
        dao: PredictionDao
    ): PredictionRepository {
        return PredictionRepositoryImpl(api, dao)
    }

    // --- Use Cases ---
    @Provides
    @Singleton
    fun provideUseCases(repository: PredictionRepository): PredictionUseCases {
        return PredictionUseCases(
            fetchPrediction = FetchPredictionUseCase(repository),
            insertPrediction = InsertPredictionUseCase(repository),
            getAllPredictions = GetAllPredictionsUseCase(repository),
            getPredictionById = GetPredictionByIdUseCase(repository),
            deletePrediction = DeletePredictionUseCase(repository),
            clearAllPredictions = ClearAllPredictionsUseCase(repository)
        )
    }
}
