#!/bin/bash
set -e
./gradlew :app:assembleFossDebug
APK="$(find app/build/outputs/apk -name '*universal-debug.apk' | head -n 1)"
if [ -z "$APK" ]; then
    APK="$(find app/build/outputs/apk -name '*arm64-v8a-debug.apk' | head -n 1)"
fi
echo "APK compilada: $APK"
echo "Instalando en dispositivo conectado por ADB..."
adb install -r "$APK"
echo "¡Instalación completada!"
