FROM eclipse-temurin:17-jdk
ENV ANDROID_HOME=/opt/android-sdk
ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV PATH=${PATH}:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:/opt/gradle/gradle-8.9/bin
RUN apt-get update && apt-get install -y --no-install-recommends curl unzip zip git ca-certificates libc6 libstdc++6 && rm -rf /var/lib/apt/lists/*
RUN curl -fsSL https://services.gradle.org/distributions/gradle-8.9-bin.zip -o /tmp/gradle.zip && mkdir -p /opt/gradle && unzip -q /tmp/gradle.zip -d /opt/gradle && rm /tmp/gradle.zip
RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && curl -fsSL https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -o /tmp/android-tools.zip && mkdir -p /tmp/android-tools && unzip -q /tmp/android-tools.zip -d /tmp/android-tools && mv /tmp/android-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest && rm -rf /tmp/android-tools /tmp/android-tools.zip
RUN yes | sdkmanager --licenses >/dev/null || true
RUN sdkmanager "platform-tools" "platforms;android-35" "build-tools;35.0.0"
