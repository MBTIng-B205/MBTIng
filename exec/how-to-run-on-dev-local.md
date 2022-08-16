# 개발 환경에서 실행하는 방법

이 문서는 [how-to-build-and-deploy](./how-to-build-and-deploy.md) 를 기반으로 한다.

중복되는 내용은 생략한다.

## 사용한 소프트웨어 정보

| SW | 버전 |
| --- | --- |  
| Intellij IDEA | 2022.1.4 (Community Edition) |
| VS Code | 1.69.0 |
| Docker Desktop | 4.11.1 |
| Docker Engine | 20.10.17 |
| Docker Compose | 1.29.2 |

## Openvidu 이미지

[오픈비두 개발용 이미지](https://hub.docker.com/r/openvidu/openvidu-server-kms) (동일 LAN 접속만 가능)

### 컨테이너 실행시 환경 변수 추가

```properties
HTTP_PORT=____ # frontend가 사용하는 포트(80)와 겹치면 안 됨
```

### selfsigned

WebRTC 에서 화상, 음성 통신을 위해 HTTPS 가 강제되는데

개발 환경에서는 Openvidu 를 selfsigned 옵션으로 실행하기 때문에 브라우저가 기본적으로 차단한다.

브라우저에서 `https://localhost:4443/` 에 접속하여 `고급` 에서 허용해야 한다.

## 환경 변수 파일

로컬 환경 변수가 설정된 파일(.env.local)을 다음 경로에 넣는다.

- frontend/[.env.local](./server-files/frontend/.env.local)

.env.local
```properties
VUE_APP_API_SERVER_BASE_URL = "http://localhost:8080/api"
VUE_APP_KAKAO_CLIENT_ID = "여기_KAKAO_DEV_REST_API_KEY_넣기"
VUE_APP_KAKAO_LOGIN_REDIRECT_URI = "http://localhost/loginview"
VUE_APP_KAKAO_LOGOUT_REDIRECT_URI = "http://localhost/logoutview"
VUE_APP_WS_SERVER_BASE_URL = "http://localhost:8080"
VUE_APP_OPENVIDU_SERVER_URL = "https://localhost:4443"
VUE_APP_OPENVIDU_SERVER_SECRET = "MY_SECRET"
```

## Run Frontend

frontend 폴더에서 다음을 실행

```
npm install
npm run serve
```

## Run Backend

backend 폴더를 IntelliJ IDEA 에서 프로젝트로 연다.

우측 Gradle 패널에서 mbting > Tasks > application > bootRun 을 더블클릭하여 실행한다.

### (옵션) 데이터 초기화 작업 없이 실행하기

상단 툴바에서 Run > Edit Configurations... 클릭

"backend \[bootRun]" 항목을 클릭 후 복사 두 번 (두 개의 "bootRun" Run config 를 생성)

(유의 사항) 최소 한 번 이상 bootRun 을 실행해야 Run Configurations 에 항목이 생긴다.

두 Run config 의 이름 (Name) 을 적절히 바꾼다.

예를들어 bootRun, bootRunContinue 와 같이 설정

하나(bootRun)는 그대로 두고 하나(bootRunContinue)만 클릭하여 Run 항목의 값을 다음과 같이 바꾼다.

```
bootRun -Pddl_auto=update
```

OK 를 눌러 저장한다.

이제 우측 Gradle 패널에서 새로고침 후 Run Configurations 에 방금 전에 추가한 항목이 보일 것이다.

bootRun 실행 시 데이터 초기화가 진행되고

bootRunContinue 실행 시 데이터 초기화가 스킵된다.
