package com.actrabajoequipo.recipesapp.ui.login

import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.recipesapp.data.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.login.forgotPassword.ForgotPasswordViewModel
import com.actrabajoequipo.recipesapp.ui.login.usernameForGoogleAccount.UsernameForGoogleAccountViewModel
import com.actrabajoequipo.recipesapp.ui.signup.SignupViewModel
import com.actrabajoequipo.usecases.FindUserByIdUseCase
import com.actrabajoequipo.usecases.PatchUserUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class LoginModule {

    @Provides
    fun loginViewModelProvider(
        findUserByIdUseCase: FindUserByIdUseCase,
        firebaseManager: FirebaseManager,
        patchUserUseCase: PatchUserUseCase,
    ) = LoginViewModel(findUserByIdUseCase, firebaseManager, patchUserUseCase)

    @Provides
    fun findUserByIdUseCaseProvider(userRepository: UserRepository) =
        FindUserByIdUseCase(userRepository)

    @Provides
    fun patchUserUseCaseProvider(userRepository: UserRepository) =
        PatchUserUseCase(userRepository)
}



@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {
    val loginViewModel: LoginViewModel
}

@Module
class ForgotPasswordModule {

    @Provides
    fun forgotPasswordProvider(
        firebaseManager: FirebaseManager
    ) = ForgotPasswordViewModel(firebaseManager)
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
        firebaseManager: FirebaseManager
    ) = UsernameForGoogleAccountViewModel(patchUserUseCase, firebaseManager)

    @Provides
    fun patchUserUseCaseProvider(
        userRepository: UserRepository
    ) = PatchUserUseCase(userRepository)
}

@Subcomponent(modules = [UsernameForGoogleAccountModule::class])
interface UsernameForGoogleAccountComponent {
    val usernameForGoogleAccountViewModel: UsernameForGoogleAccountViewModel
}