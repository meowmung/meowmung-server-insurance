# Step 1: Base image 선택
FROM openjdk:17-alpine

# Step 2: 작업 디렉터리 설정
WORKDIR /app

# Step 3: Gradle Wrapper 및 소스 복사
COPY . .

# Step 4: Gradle 실행 파일에 권한 부여
RUN chmod +x ./gradlew

# Step 5: Gradle 빌드 실행
RUN ./gradlew clean build

# Step 6: JAR 파일 복사 (build/libs 경로에서 결과물 가져오기)
RUN ls -la build/libs # 디버깅용
COPY build/libs/*.jar app.jar

# Step 7: 실행 명령어 설정
ENTRYPOINT ["java", "-jar", "app.jar"]

# (옵션) 포트 노출
EXPOSE 8084