package com.example.flashlight_plugin

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class FlashlightPlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var channel : MethodChannel
  private lateinit var context: Context
  private lateinit var cameraManager: CameraManager

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {

    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "my_flashlight_plugin")
    channel.setMethodCallHandler(this)
    context = flutterPluginBinding.applicationContext
    cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "turnOn") {
      try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          val cameraId = cameraManager.cameraIdList[0]
          cameraManager.setTorchMode(cameraId, true)
          result.success(null)
        } else {
          result.error("UNAVAILABLE", "Android version too low", null)
        }
      } catch (e: Exception) {
        result.error("CAMERA_ERROR", e.message, null)
      }
    } else if (call.method == "turnOff") {
      try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          val cameraId = cameraManager.cameraIdList[0]
          cameraManager.setTorchMode(cameraId, false)
          result.success(null)
        } else {
          result.error("UNAVAILABLE", "Android version too low", null)
        }
      } catch (e: Exception) {
        result.error("CAMERA_ERROR", e.message, null)
      }
    } else {
      // Якщо викликають щось інше (наприклад getPlatformVersion)
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
