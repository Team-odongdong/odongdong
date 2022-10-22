# odongdong frontend

### 초기 실행
> 최초 clone 이후, `npm i` 를 통해 npm을 설치해야 합니다.


**src/frontend/odongdong 디렉토리로 이동해, 웹 상에서 실행할 수 있습니다.**
```
ionic serve
```

- 기본적으로 localhost:8100에서 실행됩니다.
- 만약 특정 주소로 실행하고 싶다면, 아래와 같이 입력하면 됩니다.
    ```
    ionic serve --host=IP_ADDRESS:PORT_NUM
    ```


### 빌드 및 배포
- 빌드
```
ionic build (capacitor)
ionic build --prod (production version)
ionic build --prod --release (release version)
```

- 프로젝트 생성
```
ionic cap add android
ionic cap add ios
```

- 빌드 변경사항 반영
```
ionic cap copy
```

- sync
```
ionic cap sync
```

- 실행
    - ios (Xcode)
    ```
    ionic cap open ios
    ```

    - android (Android Studio)
    ```
    ionic cap open android
    ```


- 한번에 하기
    - ios
    ```
    ionic build --prod;ionic cap copy;ionic cap sync;ionic cap open ios
    ```

    - android
    ```
    ionic build --prod;ionic cap copy;ionic cap sync;ionic cap open android
    ```
