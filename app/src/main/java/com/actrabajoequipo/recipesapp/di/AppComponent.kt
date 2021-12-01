package com.actrabajoequipo.recipesapp.di

import android.app.Application
import com.actrabajoequipo.recipesapp.ui.addrecipe.AddRecipeComponent
import com.actrabajoequipo.recipesapp.ui.addrecipe.AddRecipeModule
import com.actrabajoequipo.recipesapp.ui.detail.DetailActivity
import com.actrabajoequipo.recipesapp.ui.detail.DetailComponent
import com.actrabajoequipo.recipesapp.ui.detail.DetailModule
import com.actrabajoequipo.recipesapp.ui.formrecipe.FormRecipeComponent
import com.actrabajoequipo.recipesapp.ui.formrecipe.FormRecipeModule
import com.actrabajoequipo.recipesapp.ui.home.HomeComponent
import com.actrabajoequipo.recipesapp.ui.home.HomeModule
import com.actrabajoequipo.recipesapp.ui.login.*
import com.actrabajoequipo.recipesapp.ui.settings.SettingsComponent
import com.actrabajoequipo.recipesapp.ui.settings.SettingsModule
import com.actrabajoequipo.recipesapp.ui.signup.SignupComponent
import com.actrabajoequipo.recipesapp.ui.signup.SignupModule
import com.actrabajoequipo.recipesapp.ui.userprofile.UserProfileComponent
import com.actrabajoequipo.recipesapp.ui.userprofile.UserProfileModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {

    fun plus(module: HomeModule): HomeComponent
    fun plus(module: DetailModule): DetailComponent
    fun plus(module: FormRecipeModule): FormRecipeComponent
    fun plus(module: SignupModule): SignupComponent
    fun plus(module: SettingsModule): SettingsComponent
    fun plus(module: LoginModule): LoginComponent
    fun plus(module: ForgotPasswordModule): ForgotPasswordComponent
    fun plus(module: UsernameForGoogleAccountModule): UsernameForGoogleAccountComponent
    fun plus(module: AddRecipeModule): AddRecipeComponent
    fun plus(module: UserProfileModule): UserProfileComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}