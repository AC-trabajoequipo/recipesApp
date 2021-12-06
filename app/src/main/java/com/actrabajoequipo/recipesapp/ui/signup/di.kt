package com.actrabajoequipo.recipesapp.ui.signup

import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.usecases.PatchUserUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
class SignupModule {

    @Provides
    fun signupViewModelProvider(
        patchUserUseCase: PatchUserUseCase,
        firebaseManager: FirebaseManager,
        coroutineDispatcher: CoroutineDispatcher
    ) = SignupViewModel(patchUserUseCase, firebaseManager, coroutineDispatcher)

    @Provides
    fun patchUserUseCaseProvider(
        userRepository: UserRepository
    ) = PatchUserUseCase(userRepository)
}

@Subcomponent(modules = [SignupModule::class])
interface SignupComponent {
    val signupViewModel: SignupViewModel
}