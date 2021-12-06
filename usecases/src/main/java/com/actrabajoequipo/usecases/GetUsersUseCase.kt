package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.domain.User

class GetUsersUseCase(private val userRepository: UserRepository) {
    suspend fun invoke() : Map<String, User> = userRepository.getUsers()
}