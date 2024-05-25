FROM openjdk:17-jdk-alpine

# 定义工作目录
WORKDIR /app

# 复制项目的JAR文件到容器中
COPY target/springboot-0.0.1-SNAPSHOT.jar .

# 设置容器启动命令
CMD ["java", "-jar", "springboot-0.0.1-SNAPSHOT.jar"]
