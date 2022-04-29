package com.example.ChalkBoard

import android.app.ProgressDialog
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ProgressBar
import android.widget.Toast
import com.example.ChalkBoard.databinding.ActivityAadharUploadBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

class AadharUploadActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAadharUploadBinding
    lateinit var filepath : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAadharUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSelectImage.setOnClickListener {
            selectImage()
        }

        binding.btnUploadImage.setOnClickListener {

            uploadImage()
        }
    }

    private fun uploadImage() {


        if(filepath!=null){
            var pd = ProgressDialog(this)
            pd.setTitle("UPLOADING....")
            pd.show()
            var imageRef:StorageReference = FirebaseStorage.getInstance().reference.child("images/pic.jpg")
            imageRef.putFile(filepath)
                .addOnSuccessListener { p0 ->
                    pd.dismiss()
                    Toast.makeText(applicationContext,"File Uploaded",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{ p0 ->
                    pd.dismiss()
                    Toast.makeText(applicationContext,p0.message,Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { p0 ->
                    var progress:Double = (100.0*p0.bytesTransferred)/p0.totalByteCount
                    pd.setMessage("Uploaded ${progress.toInt()}%")
                }
        }

    }

    private fun selectImage() {

        var intent = Intent()
        intent.setType("images/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Choose Picture"),111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 111 && resultCode == Activity.RESULT_OK && data!=null){
            filepath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            binding.ivAadharImage.setImageBitmap(bitmap)
        }
    }
}