import androidx.room.Room
import com.jobsity.android.challenge.data.TvMazeApi
import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.domain.mapper.*
import com.jobsity.android.challenge.domain.model.EpisodeDetails
import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.paging.ShowsPagingSource
import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.domain.repository.EpisodesRepositoryImpl
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.domain.repository.ShowsRepositoryImpl
import com.jobsity.android.challenge.persistence.SeriesDatabase
import com.jobsity.android.challenge.persistence.entity.FavoriteShow
import com.jobsity.android.challenge.ui.episode_details.EpisodeDetailsViewModel
import com.jobsity.android.challenge.ui.favorite_shows.FavoriteShowsViewModel
import com.jobsity.android.challenge.ui.show_details.ShowDetailsViewModel
import com.jobsity.android.challenge.ui.shows.ShowsViewModel
import com.jobsity.android.challenge.ui.shows_search.ShowsSearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


private val dataModule = module {
    single { TvMazeApi() }
    factory { (get() as TvMazeApi).showsService }
    factory { (get() as TvMazeApi).episodesService }
    factory { (get() as TvMazeApi).searchService }
}

private val mapperModule = module {
    single<Mapper<Show, ShowAtList>>(named("show-at-list-mapper")) { ShowAtListMapper }
    single<Mapper<Show, ShowDetails>>(named("show-details-mapper")) { ShowDetailsMapper }
    single<Mapper<Episode, EpisodeDetails>>(named("episode-details-mapper")) { EpisodeDetailsMapper }
    single<Mapper<Episode, EpisodeOfShow>>(named("episode-of-show-mapper")) { EpisodeOfShowMapper }
    single<Mapper<FavoriteShow, ShowAtList>>(named("favorite-show-to-show-at-list-mapper")) { FavShowToShowAtListMapper }
    single<Mapper<ShowAtList, FavoriteShow>>(named("favorite-show-mapper")) { FavoriteShowMapper }
}

private val repositoryModule = module {
    // Paging
    factory { ShowsPagingSource(get(), get(named("show-at-list-mapper"))) }

    // Repositories
    factory<ShowsRepository> {
        ShowsRepositoryImpl(
            showsService = get(),
            searchService = get(),
            showsPagingSource = get(),
            favoriteShowDao = get(),
            showAtListMapper = get(named("show-at-list-mapper")),
            showDetailsMapper = get(named("show-details-mapper")),
            favoriteShowMapper = get(named("favorite-show-mapper")),
            favShowToShowAtListMapper = get(named("favorite-show-to-show-at-list-mapper"))
        )
    }
    factory<EpisodesRepository> {
        EpisodesRepositoryImpl(
            episodesService = get(),
            showsService = get(),
            episodeDetailsMapper = get(named("episode-details-mapper")),
            episodeOfShowMapper = get(named("episode-of-show-mapper"))
        )
    }
}

private val persistenceModule = module {
    single {
        Room.databaseBuilder(androidContext(), SeriesDatabase::class.java, "series.db")
            .build()
    }
    single { (get() as SeriesDatabase).favoriteShowDao() }


    viewModel { ShowsViewModel(get()) }
    viewModel { ShowDetailsViewModel(get(), get(), get()) }
    viewModel { ShowsSearchViewModel(get()) }
    viewModel { EpisodeDetailsViewModel(get()) }
    viewModel { FavoriteShowsViewModel(get()) }

}

val appModules = listOf(dataModule, mapperModule, repositoryModule, persistenceModule)
