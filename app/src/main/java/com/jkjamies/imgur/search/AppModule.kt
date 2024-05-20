package com.jkjamies.imgur.search

import com.jkjamies.imgur.api.ImgurApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module

/**
 * The Koin module for the context.
 *
 * Injecting context is not possible via annotations, so we use a normal Koin module.
 * And we use this in the annotated
 */
val contextModule =
    module {
        single {
            ImgurApi(context = androidContext()) // pass context for sqldelight operations
        }
    }

@Module
@ComponentScan
internal class AppModule
