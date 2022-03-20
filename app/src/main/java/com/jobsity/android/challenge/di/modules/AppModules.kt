import com.jobsity.android.challenge.data.TvMazeApi
import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.domain.mapper.EpisodeDetailsMapper
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.mapper.ShowAtListMapper
import com.jobsity.android.challenge.domain.mapper.ShowDetailsMapper
import com.jobsity.android.challenge.domain.model.EpisodeDetails
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.domain.repository.EpisodesRepositoryImpl
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.domain.repository.ShowsRepositoryImpl
import com.jobsity.android.challenge.ui.ShowsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModules = module {
    single { TvMazeApi() }
    factory { (get() as TvMazeApi).showsService }
    factory { (get() as TvMazeApi).episodesService }
    factory { (get() as TvMazeApi).searchService }

    single<Mapper<Show, ShowAtList>>(named("show-at-list-mapper")) { ShowAtListMapper }
    single<Mapper<Show, ShowDetails>>(named("show-details-mapper")) { ShowDetailsMapper }
    single<Mapper<Episode, EpisodeDetails>>(named("episode-details-mapper")) { EpisodeDetailsMapper }

    factory<ShowsRepository> {
        ShowsRepositoryImpl(
            get(),
            get(),
            get(named("show-at-list-mapper")),
            get(named("show-details-mapper"))
        )
    }
    factory<EpisodesRepository> {
        EpisodesRepositoryImpl(
            get(),
            get(named("episode-details-mapper"))
        )
    }

    viewModel { ShowsViewModel(get()) }
}