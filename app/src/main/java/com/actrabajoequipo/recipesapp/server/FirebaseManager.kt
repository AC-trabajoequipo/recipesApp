package com.actrabajoequipo.recipesapp.server

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseManager {

    interface PhotoCallBack {
        fun onProgress(progress: Int)
        fun onComplete()
        fun onSuccess(imageURL: String)
        fun onFailure()
    }

    companion object {
        private const val PATH_IMAGES = "images"
        private const val PATH_REALTIME_DATABASE = "recipes"
    }

    private lateinit var callBack: PhotoCallBack

    private var storageReference: StorageReference = FirebaseStorage.getInstance().reference
    private var databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child(
            PATH_REALTIME_DATABASE
        )

    val fbAuth = FirebaseAuth.getInstance()

    fun returnIdKeyEntry(): String? {
        return databaseReference.push().key
    }

    fun returnUserUID(): String?{
        return fbAuth.currentUser?.uid
    }

    fun signOut(){
        fbAuth.signOut()
    }

    fun getEmailUser(): String{
        return fbAuth.currentUser?.email.toString()
    }

   fun uploadPhotoRecipe(
       id: String?,
       imageUri: Uri?,
       callBack: PhotoCallBack
   ){
       this.callBack = callBack
        val storageReference = FirebaseAuth.getInstance().currentUser?.let { user->
            id?.let { idGenerated ->
                storageReference.child(Companion.PATH_IMAGES).child(
                    user.uid
                ).child(idGenerated)
            }
        }

        if (imageUri != null) {
            storageReference?.putFile(imageUri)
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
}
