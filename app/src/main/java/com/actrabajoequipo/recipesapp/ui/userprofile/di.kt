package com.actrabajoequipo.recipesapp.ui.userprofile

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.usecases.FindRecipeByUserIdUseCase
import com.actrabajoequipo.usecases.FindRecipeFavouriteUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher


@Module
class UserProfileModule {

    @Provides
    fun userProfileViewModelProvider(
        firebaseManager: FirebaseManager,
        findRecipeFavouriteUseCase: FindRecipeFavouriteUseCase,
        findRecipeByUserIdUseCase: FindRecipeByUserIdUseCase,
        coroutineDispatcher: CoroutineDispatcher
    ) = UserProfileViewModel(
        firebaseManager,
        findRecipeFavouriteUseCase,
        findRecipeByUserIdUseCase,
        coroutineDispatcher
    )

    @Provides
    fun findRecipeFavouriteUseCaseProvider(recipesRespository: RecipesRepository) =
        FindRecipeFavouriteUseCase(recipesRespository)

    @Provides
    fun findRecipeByUserIdUseCaseProvides(recipesRespository: RecipesRepository) =
        FindRecipeByUserIdUseCase(recipesRespository)
}

@Subcomponent(modules = [(UserProfileModule::class)])
interface UserProfileComponent {
    val userProfileViewModel: UserProfileViewModel
}