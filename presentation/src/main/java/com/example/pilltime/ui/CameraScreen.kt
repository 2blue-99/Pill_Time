package com.example.pilltime.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.example.pilltime.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 2023-11-07
 * pureum
 */

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    permissionState: PermissionState
) {
    var myCameraExecutor: Executor = Executors.newSingleThreadExecutor()
    val context = LocalContext.current
    val cameraController = remember { LifecycleCameraController(context) }
    val imageCapture = remember { ImageCapture.Builder().build()}
    var capturedImage by remember { mutableStateOf(Uri.EMPTY) }
    Log.e("TAG", "capturedImage: $imageCapture", )

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        CameraPreview(imageCapture = imageCapture)
        Image(
            painter = painterResource(R.drawable.pill_time_camera_grid),
            modifier = Modifier
                .fillMaxWidth()
                .height(402.dp)
                .align(Alignment.Center)
                .padding(horizontal = 20.dp),
            contentDescription = null
        )
        IconButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp),
            onClick = {
                CaptureImage(imageCapture = imageCapture, cameraExecutor = myCameraExecutor, capturedImage = {
                    Log.e("TAG", "CameraScreen: $capturedImage", )
                    capturedImage = it
                    Log.e("TAG", "CameraScreen: $capturedImage", )
                })
//                val mainExecutor = ContextCompat.getMainExecutor(context)
//                cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback(){
//                    override fun onCaptureSuccess(image: ImageProxy) {
//                        val correctedBitmap: Bitmap = image.image
//
//                    }
//                })
            }) {
            Icon(
                imageVector = Icons.Sharp.CheckCircle,
                contentDescription = "",
                Modifier.fillMaxSize()
            )
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = rememberImagePainter(cameraController),
                    contentDescription = "",
                    Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    imageCapture: ImageCapture
){
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    Scaffold(Modifier.fillMaxSize()) { paddingValue ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValue),
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    this.scaleType = scaleType
                }

                val previewUseCase = Preview.Builder().build()
                previewUseCase.setSurfaceProvider(previewView.surfaceProvider)

                coroutineScope.launch {
                    val cameraProvider = context.cameraProvider()
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, previewUseCase, imageCapture)
                }

                val listenableFormat = ProcessCameraProvider.getInstance(context)
                listenableFormat.addListener({
                    val cameraProvider = listenableFormat.get()
                }, ContextCompat.getMainExecutor(context))

                previewView
            })
    }
}

suspend fun Context.cameraProvider(): ProcessCameraProvider = suspendCoroutine {continuation ->
    val listenableFuture = ProcessCameraProvider.getInstance(this)
    listenableFuture.addListener({
        continuation.resume(listenableFuture.get())
    }, ContextCompat.getMainExecutor(this))
}

fun CaptureImage(
    imageCapture: ImageCapture,
    cameraExecutor: Executor,
    capturedImage: (Uri) -> Unit
){
    val file = File.createTempFile("img",".jpg")
    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
//    File.createTempFile("img",".jpg"), cameraExecutor
    imageCapture.takePicture(outputFileOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback{
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            Log.e("TAG", "onImageSaved: the Uri is ${outputFileResults.savedUri}", )
            capturedImage(outputFileResults.savedUri?: Uri.EMPTY)
        }

        override fun onError(exception: ImageCaptureException) {
            TODO("Not yet implemented")
        }
    })
}

private fun takePhoto(
    filenameFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {

    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
        override fun onError(exception: ImageCaptureException) {
            Log.e("kilo", "Take photo error:", exception)
            onError(exception)
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            onImageCaptured(savedUri)
        }
    })
}