package com.actrabajoequipo.recipesapp

class UiTestRecipesApplication : RecipesApp() {

    //The dagger component is not generated
    override fun initRecipesComponent() = DaggerUiTestComponent
        .factory()
        .create(this)
}