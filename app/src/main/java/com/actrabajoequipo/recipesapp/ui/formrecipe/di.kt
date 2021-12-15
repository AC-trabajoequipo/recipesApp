package com.actrabajoequipo.recipesapp.ui.formrecipe

import com.actrabajoequipo.data.repository.RecipesRepository
import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.usecases.FindUserByIdUseCase
import com.actrabajoequipo.usecases.PatchUserUseCase
import com.actrabajoequipo.usecases.PostRecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class FormRecipeModule {

    @Provides
    fun formRecipeViewModelProvider(
        postRecipeUseCase: PostRecipeUseCase,
        findUserByIdUseCase: FindUserByIdUseCase,
        patchUserUseCase: PatchUserUseCase,
        firebaseManager: FirebaseManager,
        urlManager: UrlManager
    ): FormRecipeViewModel {
        return FormRecipeViewModel(
            postRecipeUseCase,
            findUserByIdUseCase,
            patchUserUseCase,
            firebaseManager,
            urlManager
        )
    }

    @Provides
    fun postRecipeUseCaseProvider(recipesRepository: RecipesRepository) =
        PostRecipeUseCase(recipesRepository)

    @Provides
    fun findUserByIdUseCaseProvider(userRepository: UserRepository) =
        FindUserByIdUseCase(userRepository)

    @Provides
    fun patchUserUseCaseProvider(userRepository: UserRepository) =
        PatchUserUseCase(userRepository)

    @Provides
    fun urlManagerProvider() = UrlManager()
}

@Subcomponent(modules = [(FormRecipeModule::class)])
interface FormRecipeComponent {
    val formRecipeViewModel: FormRecipeViewModel
}