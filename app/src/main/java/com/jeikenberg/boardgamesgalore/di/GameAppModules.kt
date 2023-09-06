package com.jeikenberg.boardgamesgalore.di

import android.content.Context
import com.jeikenberg.boardgamesgalore.data.GameDatabase
import com.jeikenberg.boardgamesgalore.data.game.GameDao
import com.jeikenberg.boardgamesgalore.data.game.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GameAppModules {

    @Provides
    @Singleton
    fun provideGameDatabase(@ApplicationContext appContext: Context) : GameDatabase {
        return GameDatabase.getInstance(appContext)
    }

    @Provides
    @Singleton
    fun provideGameDao(gameDatabase: GameDatabase): GameDao {
        return gameDatabase.gameDao()
    }

    @Provides
    @Singleton
    fun provideGameRepository(gameDao: GameDao): GameRepository {
        return GameRepository.getInstance(gameDao)
    }

//    @Provides
//    @Singleton
//    fun provideGameImageDao(gameDatabase: GameDatabase): GameImageDao {
//        return gameDatabase.gameImageDao()
//    }
//
//    @Provides
//    @Singleton
//    fun providesGameImageRepository(gameImageDao: GameImageDao): GameImageRepository {
//        return GameImageRepository.getInstance(gameImageDao)
//    }
}