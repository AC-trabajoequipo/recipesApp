package com.actrabajoequipo.recipesapp.ui.settings

import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.usecases.DeleteUserUseCase
import com.actrabajoequipo.usecases.FindUserByIdUseCase
import com.actrabajoequipo.usecases.GetUsersUseCase
import com.actrabajoequipo.usecases.PatchUserUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class SettingsModule {

    @Provides
    fun settingsViewModelProvider(
        getUsersUseCase: GetUsersUseCase,
        patchUserUseCase: PatchUserUseCase,
        deleteUserUseCase: DeleteUserUseCase,
        findUserByIdUseCase: FindUserByIdUseCase,
        firebaseManager: FirebaseManager
    ) = SettingsViewModel(
        getUsersUseCase, patchUserUseCase,
        deleteUserUseCase, findUserByIdUseCase, firebaseManager
    )

    @Provides
    fun getUsersUseCaseProvider(
        userRepository: UserRepository
    ) = GetUsersUseCase(userRepository)

    @Provides
    fun patchUserUseCaseProvider(
        userRepository: UserRepository
    ) = PatchUserUseCase(userRepository)

    @Provides
    fun deleteUserUseCaseProvider(
        userRepository: UserRepository
    ) = DeleteUserUseCase(userRepository)

    @Provides
    fun findUserByIdUseCaseProvider(
        userRepository: UserRepository
    ) = FindUserByIdUseCase(userRepository)

}

@Subcomponent(modules = [SettingsModule::class])
interface SettingsComponent {
    val settingsViewModel: SettingsViewModel
}