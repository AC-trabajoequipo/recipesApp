package com.actrabajoequipo.recipesapp.model

import android.net.Uri
import com.actrabajoequipo.recipesapp.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object ManageFireBase {

    interface PhotoCallBack{
        fun onProgress(progress: Int)
        fun onComplete()
        fun onSuccess(imageURL: String)
        fun onFailure()
    }

    private lateinit var callBack : PhotoCallBack

    const val PATH_IMAGES = "images"
    const val PATH_REALTIME_DATABASE = "recipes"

    private var storageReference: StorageReference = FirebaseStorage.getInstance().reference
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(PATH_REALTIME_DATABASE)
    private var id : String? = null


    fun returnIdKeyEntry() : String?{
        return databaseReference.push().key
    }


   fun uploadPhotoRecipe(photoSelectedUri : Uri, callBack: PhotoCallBack){
       this.callBack = callBack
        id = returnIdKeyEntry()
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
                    callBack.onProgress(progress.toInt())
                }
                ?.addOnCompleteListener {
                    callBack.onComplete()
                }
                ?.addOnSuccessListener {
                    // código para una vez que la foto se ha subido exitosa al store
                    //podamos coger la url y ponerla en nuestra database
                    it.storage.downloadUrl.addOnSuccessListener { imageUri -> callBack.onSuccess(imageUri.toString()) }
                }
                ?.addOnFailureListener {
                    callBack.onFailure()
                }
        }
    }

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
