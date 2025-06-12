#Build
FROM gradle:8.9-jdk21-jammy AS build
WORKDIR /app
# キャッシュを効率的に利用するために、変更頻度の低いファイルを先にコピー
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
#COPY settings.gradle.kts .

RUN ./gradlew dependencies --no-daemon
COPY src src
# Spring Bootアプリケーションをビルド (JARファイルを生成)
RUN ./gradlew bootJar --no-daemon

# Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# ビルドステージで生成されたJARファイルをコピー
# bootJar タスクは通常 build/libs/ ディレクトリに JAR を生成
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]