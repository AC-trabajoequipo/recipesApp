package com.actrabajoequipo.recipesapp.ui.login

import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.login.forgotPassword.ForgotPasswordViewModel
import com.actrabajoequipo.recipesapp.ui.login.usernameForGoogleAccount.UsernameForGoogleAccountViewModel
import com.actrabajoequipo.usecases.PatchUserUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
class LoginModule {

    @Provides
    fun loginViewModelProvider(
        firebaseManager: FirebaseManager,
        coroutineDispatcher: CoroutineDispatcher
    ) = LoginViewModel(firebaseManager, coroutineDispatcher)
}

@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {
    val loginViewModel: LoginViewModel
}

@Module
class ForgotPasswordModule {

    @Provides
    fun forgotPasswordProvider(
        firebaseManager: FirebaseManager,
        coroutineDispatcher: CoroutineDispatcher
    ) = ForgotPasswordViewModel(firebaseManager, coroutineDispatcher)
}

@Subcomponent(modules = [ForgotPasswordModule::class])
interface ForgotPasswordComponent {
    val forgotPasswordViewModel: ForgotPasswordViewModel
}

@Module
class UsernameForGoogleAccountModule {

    @Provides
    fun usernameForGoogleAccountViewModelProvider(
        patchUserUseCase: PatchUserUseCase,
        firebaseManager: FirebaseManager,
        coroutineDispatcher: CoroutineDispatcher
    ) = UsernameForGoogleAccountViewModel(patchUserUseCase, firebaseManager, coroutineDispatcher)

    @Provides
    fun patchUserUseCaseProvider(
        userRepository: UserRepository
    ) = PatchUserUseCase(userRepository)
}

@Subcomponent(modules = [UsernameForGoogleAccountModule::class])
interface UsernameForGoogleAccountComponent {
    val usernameForGoogleAccountViewModel: UsernameForGoogleAccountViewModel
}