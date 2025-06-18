FROM maven:3.8.6-openjdk-18-slim AS build
WORKDIR /home/app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:18-slim
WORKDIR /app

ENV TZ=Asia/Ho_Chi_Minh
RUN apt-get update && apt-get install -y tzdata && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone && \
    apt-get clean

RUN mkdir -p /app/wordnet /app/voiceAudio

COPY --from=build /home/app/target/*.jar app.jar
COPY --from=build /home/app/src/main/resources/wordnet/ /app/wordnet/
COPY --from=build /home/app/voiceAudio/ /app/voiceAudio/

ENV WORDNET_DICTIONARY_PATH=/app/wordnet

EXPOSE 8080
ENTRYPOINT ["java", "-Dwordnet.dictionary.path=/app/wordnet", "-jar", "app.jar"]
