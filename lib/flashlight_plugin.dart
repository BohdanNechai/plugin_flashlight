import 'package:flutter/services.dart';

class FlashlightPlugin {
  // Канал для спілкування з Android
  static const MethodChannel _channel = MethodChannel('my_flashlight_plugin');

  // Статичний метод увімкнення
  static Future<void> turnOn() async {
    await _channel.invokeMethod('turnOn');
  }

  // Статичний метод вимкнення
  static Future<void> turnOff() async {
    await _channel.invokeMethod('turnOff');
  }
}