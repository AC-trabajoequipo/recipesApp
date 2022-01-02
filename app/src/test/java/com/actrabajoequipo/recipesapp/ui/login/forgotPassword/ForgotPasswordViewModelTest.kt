package com.actrabajoequipo.recipesapp.ui.login.forgotPassword

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.server.FirebaseManager
import com.actrabajoequipo.recipesapp.ui.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ForgotPasswordViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var firebaseManager: FirebaseManager

    @Mock
    lateinit var observer: Observer<ForgotPasswordViewModel.ResultEditPassword>

    private lateinit var viewModel: ForgotPasswordViewModel

    @Before
    fun setUp() {
        viewModel = ForgotPasswordViewModel(firebaseManager)
    }
}