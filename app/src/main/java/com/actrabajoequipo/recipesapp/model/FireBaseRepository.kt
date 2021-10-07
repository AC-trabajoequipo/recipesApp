package com.actrabajoequipo.recipesapp.model

import android.net.Uri
import android.view.View
import android.widget.Toast
import com.actrabajoequipo.recipesapp.Recipe
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object FireBaseRepository {

    const val PATH_IMAGES = "images"
    const val PATH_REALTIME_DATABASE = "recipes"

    private var storageReference: StorageReference = FirebaseStorage.getInstance().reference
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(PATH_REALTIME_DATABASE)
    private var id : String? = null

    /*fun uploadPhotoRecipe(photoSelectedUri : Uri) : String{
        id = databaseReference.push().key
        val storageReference = FirebaseAuth.getInstance().currentUser?.let {
            id?.let { idGenerated ->
                storageReference.child(PATH_IMAGES).child(
                    it.uid
                ).child(idGenerated)
            }
        }

        if (photoSelectedUri != null) {
            storageReference?.putFile(photoSelectedUri!!)
                ?.addOnProgressListener {
                    val progress =
                        (100 * it.bytesTransferred / it.totalByteCount).toDouble()
                    binding.pbUploadImage.progress = progress.toInt()
                    binding.tvProgressPercent.text = "$progress%"
                }
                ?.addOnCompleteListener {
                    binding.pbUploadImage.visibility = View.INVISIBLE
                    binding.tvProgressPercent.visibility = View.INVISIBLE
                }
                ?.addOnSuccessListener {
                    // código para una vez que la foto se ha subido exitosa al store
                    //podamos coger la url y ponerla en nuestra database
                    Snackbar.make(binding.root, "imagen subida", Snackbar.LENGTH_SHORT)
                        .show()
                    it.storage.downloadUrl.addOnSuccessListener { photoUrl = it.toString() }
                }
                ?.addOnFailureListener {
                    Snackbar.make(
                        binding.root,
                        "No se pudo subir, inténtelo de nuevo",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
        }
        return ""
    }*/

    fun uploadRecipe(user: String, recipe: Recipe) : Boolean{
        var isValid = false
        databaseReference.child(user).setValue(recipe).addOnSuccessListener {
            isValid = true
            //finish()
        }.addOnFailureListener {
            isValid = false
        }
        return isValid
    }
}
