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
import com.jobsity.android.challenge.ui.episode_details.EpisodeDetailsViewModel
import com.jobsity.android.challenge.ui.show_details.ShowDetailsViewModel
import com.jobsity.android.challenge.ui.shows.ShowsViewModel
import com.jobsity.android.challenge.ui.shows_search.ShowsSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModules = module {
    // Service APIs
    single { TvMazeApi() }
    factory { (get() as TvMazeApi).showsService }
    factory { (get() as TvMazeApi).episodesService }
    factory { (get() as TvMazeApi).searchService }

    // Mappers
    single<Mapper<Show, ShowAtList>>(named("show-at-list-mapper")) { ShowAtListMapper }
    single<Mapper<Show, ShowDetails>>(named("show-details-mapper")) { ShowDetailsMapper }
    single<Mapper<Episode, EpisodeDetails>>(named("episode-details-mapper")) { EpisodeDetailsMapper }
    single<Mapper<Episode, EpisodeOfShow>>(named("episode-of-show-mapper")) { EpisodeOfShowMapper }

    // Paging
    factory { ShowsPagingSource(get(), get(named("show-at-list-mapper"))) }

    // Repositories
    factory<ShowsRepository> {
        ShowsRepositoryImpl(
            get(),
            get(),
            get(),
            get(named("show-at-list-mapper")),
            get(named("show-details-mapper")),
        )
    }
    factory<EpisodesRepository> {
        EpisodesRepositoryImpl(
            get(),
            get(),
            get(named("episode-details-mapper")),
            get(named("episode-of-show-mapper"))
        )
    }

    viewModel { ShowsViewModel(get()) }
    viewModel { ShowDetailsViewModel(get(), get(), get()) }
    viewModel { ShowsSearchViewModel(get()) }
    viewModel { EpisodeDetailsViewModel(get()) }
}