#!/bin/sh

echo "빌드 시작" \
&& /home/ubuntu/repositories/S07P12B205/backend/gradlew clean build \
&& echo "빌드 성공" \
&& echo "============" \
&& echo "============" \
&& echo "============" \
&& echo "============" \
&& echo "============" \
&& echo "jar 복사 시작" \
&& cp /home/ubuntu/repositories/S07P12B205/backend/build/libs/mbting-0.0.1-SNAPSHOT.jar /home/ubuntu/repositories/S07P12B205/backend/jar/ \
&& echo "jar 복사 성공" \
&& echo "============" \
&& echo "============" \
&& echo "============" \
&& echo "============" \
&& echo "============" \
&& echo "jar 실행" \
&& nohup java \
"-Dspring.profiles.active=production" \
"-Dcom.mbting.ddl_auto=create" \
-jar /home/ubuntu/repositories/S07P12B205/backend/jar/mbting-0.0.1-SNAPSHOT.jar &
