package com.actrabajoequipo.recipesapp.ui.signup

import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import com.actrabajoequipo.usecases.PatchUserUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class SignupModule {

    @Provides
    fun signupViewModelProvider(
        patchUserUseCase: PatchUserUseCase,
        firebaseManager: FirebaseManager
    ) = SignupViewModel(patchUserUseCase, firebaseManager)

    @Provides
    fun patchUserUseCaseProvider(
        userRepository: UserRepository
    ) = PatchUserUseCase(userRepository)
}

@Subcomponent(modules = [SignupModule::class])
interface SignupComponent {
    val signupViewModel: SignupViewModel
}