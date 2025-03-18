# REST API Spring boot cho website h2t english web

```bash
mvn clean package
java -jar target/h2t-backside-0.0.1-SNAPSHOT.jar --server.port=8080 --spring.config.location=classpath:/application.properties
```

# TTS Container
### --Run the first time--
```bash
sudo docker run -d -p 8880:8880 ghcr.io/remsky/kokoro-fastapi-cpu:v0.2.2
```