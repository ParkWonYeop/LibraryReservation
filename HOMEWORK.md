# HOME WORK
같이 스터디를 진행하면서 프로젝트에 개선할 것이 있으면 정리해놓는 파일.

## 2024-02-20
1. TRANSACTION 스터디
- TRANSACTIONAL ANNOTATION 적용 / 스터디
2. Controller level 테스트 코드 작성
- Junit 스터디
- MockMvc 스터디 및 적용
3. Controller layer에 있는 ReponseEntity 제거
- 응답데이터를 별도 클래스에 포장해서 보내지 않게끔 리팩토링
- @ResponseStatus 공부 및 적용
4. MySQL 적용
5. 패키지 정리

## 2024-02-29
1. service layer에 transactional anotation 적용
2. 불필요한 코드 바로 바로 제거할것.
3. @ControllerAdvice 붙이는 클래스는 보통 네이밍 Advice
   - 패키지명이 controller X exception 이어야할듯
   - exception 패키지에 ErrorResponse 클래스를 하나만들어서
   - ErrorResponse {code:"", message:"""} << 이걸 ResponseEntity에 담아서 에러 응답.
4. 테스트코드에 parameter, property가 null, empty, blank, 특수문자 등등에 각 케이스별로 테스트 코드 작성 필요

## 2024-03-12
1. return "success"; 같이 불필요한 return 로직 제거 *
2. 가급적 약어 사용하지 말것. *
3. resource/data.sql 만들어서 테스트에 필요한 데이터 세팅되도록 수정 *
4. jsonPath("$.~~")를 사용해서 응답값 확인하는 로직을 테스트 코드에 추가 *
5. 테스트 코드에서 MockMvcResultMatchers. 참조하는 로직 제거 *
6. java record 공부 후 적용 *
7. 이력서 써서 제출 *

## 2024-03-25
1. 테스트코드 andExpectAll() 적용할 것. *
2. andDo(print())는 가급적 테스트 할 때 당시만 쓰고, 테스트가 끝나면 제거하기. *
3. DisplayName("예약 리스트") 테스트 코드에 수정해준것처럼 리팩토링 하기. *
4. DisplayName("예약 삭제 - 특수 문자") 같이 불친절한 에러 메세지는 예외처리 별도로 해서 친절한 에러 메세지를 보내게끔 수정하기. *
5. fixture 만들어서 공통적으로 쓰이는 세션 등을 집합화 해놓기*
6. 설정파일 .properties 에서 .yml로 바꿀것. *
7. 이력서 수백,수천 번 다시 쓰고, 고치기를 반복해서 다음 주 월요일 스터디할 떄 제출.


## 2024-04-01
1. 자기소개 부분에 당연한 말을 적지 말것.
2. 2번쨰 문단에서 '해결능력'을 어필하고 싶은거라면, 다른 각도로 접근하는 것이 좋아보임.
3. '강한 호기심'이라는 표현보단, 문장 내에서 너가 호기심이 강한 사람이라는 것을 은은하게 어필할 것.
4. 호기심이 강해서 새로운 분야에 대한 탐구를 즐깁니다. << 이런 식으로 을,를 라임을 피하라.
5. '수용하는 데에' 라고 썼으나 '유연한 사고방식'으로 문장을 마무리 하는것은 어색함.
6. 통합 테스트 / 단위 테스트 를 다시 한 번 공부하고 이력서에 반영할 것.
7. 어차피 앞으로 500번은 이력서를 더 수정할테니, 과감하게 작성하고 피드백 받을 것.
8. 다시 한 번 andExpectAll 로 통일해보기 *
9. MockMvcRequestBuilders 하위 get post delete put 등등의 static 메소드는 import로 리팩토링. *
10. TEST DB는 H2 를 적용할 것. 최소한 테스트 환경은 언제, 어디서나, 어느 컴퓨터에서나 돌아가게끔 세팅할 것. *
11. spring.profiles.active 는 local, dev, prod 세 개 값중 하나씩만 들어가게끔 수정할 것. *
12. reqeustParam 을 DTO화해서 @Valid 적용해볼 것. *


## 2024-04-08
1. 빌드 돌릴 때 test를 함께 하도록 세팅할 것. *
2. application-test.yml 따로 만들 것. *
3. local 환경은 h2 말고 mysql 사용하도록 개선할 것. *
4. AuthControllerTest 를 만들고, Validation 순서를 지정하는 방법에 대해 스터디 해보고 적용해볼 것. *
   - Validation 순서 정하기 *
   - AuthControllerTest 만들기 *
5. Kotlin 스터디 해볼 것. *
6. 이력서 수정할 것.

# HOME WORK
같이 스터디를 진행하면서 프로젝트에 개선할 것이 있으면 정리해놓는 파일.

## 2024-02-20
1. TRANSACTION 스터디
- TRANSACTIONAL ANNOTATION 적용 / 스터디
2. Controller level 테스트 코드 작성
- Junit 스터디
- MockMvc 스터디 및 적용
3. Controller layer에 있는 ReponseEntity 제거
- 응답데이터를 별도 클래스에 포장해서 보내지 않게끔 리팩토링
- @ResponseStatus 공부 및 적용
4. MySQL 적용
5. 패키지 정리

## 2024-02-29
1. service layer에 transactional anotation 적용
2. 불필요한 코드 바로 바로 제거할것.
3. @ControllerAdvice 붙이는 클래스는 보통 네이밍 Advice
   - 패키지명이 controller X exception 이어야할듯
   - exception 패키지에 ErrorResponse 클래스를 하나만들어서
   - ErrorResponse {code:"", message:"""} << 이걸 ResponseEntity에 담아서 에러 응답.
4. 테스트코드에 parameter, property가 null, empty, blank, 특수문자 등등에 각 케이스별로 테스트 코드 작성 필요

## 2024-03-12
1. return "success"; 같이 불필요한 return 로직 제거 *
2. 가급적 약어 사용하지 말것. *
3. resource/data.sql 만들어서 테스트에 필요한 데이터 세팅되도록 수정 *
4. jsonPath("$.~~")를 사용해서 응답값 확인하는 로직을 테스트 코드에 추가 *
5. 테스트 코드에서 MockMvcResultMatchers. 참조하는 로직 제거 *
6. java record 공부 후 적용 *
7. 이력서 써서 제출 *

## 2024-03-25
1. 테스트코드 andExpectAll() 적용할 것. *
2. andDo(print())는 가급적 테스트 할 때 당시만 쓰고, 테스트가 끝나면 제거하기. *
3. DisplayName("예약 리스트") 테스트 코드에 수정해준것처럼 리팩토링 하기. *
4. DisplayName("예약 삭제 - 특수 문자") 같이 불친절한 에러 메세지는 예외처리 별도로 해서 친절한 에러 메세지를 보내게끔 수정하기. *
5. fixture 만들어서 공통적으로 쓰이는 세션 등을 집합화 해놓기*
6. 설정파일 .properties 에서 .yml로 바꿀것. *
7. 이력서 수백,수천 번 다시 쓰고, 고치기를 반복해서 다음 주 월요일 스터디할 떄 제출.


## 2024-04-01
1. 자기소개 부분에 당연한 말을 적지 말것.
2. 2번쨰 문단에서 '해결능력'을 어필하고 싶은거라면, 다른 각도로 접근하는 것이 좋아보임.
3. '강한 호기심'이라는 표현보단, 문장 내에서 너가 호기심이 강한 사람이라는 것을 은은하게 어필할 것.
4. 호기심이 강해서 새로운 분야에 대한 탐구를 즐깁니다. << 이런 식으로 을,를 라임을 피하라.
5. '수용하는 데에' 라고 썼으나 '유연한 사고방식'으로 문장을 마무리 하는것은 어색함.
6. 통합 테스트 / 단위 테스트 를 다시 한 번 공부하고 이력서에 반영할 것.
7. 어차피 앞으로 500번은 이력서를 더 수정할테니, 과감하게 작성하고 피드백 받을 것.
8. 다시 한 번 andExpectAll 로 통일해보기 *
9. MockMvcRequestBuilders 하위 get post delete put 등등의 static 메소드는 import로 리팩토링. *
10. TEST DB는 H2 를 적용할 것. 최소한 테스트 환경은 언제, 어디서나, 어느 컴퓨터에서나 돌아가게끔 세팅할 것. *
11. spring.profiles.active 는 local, dev, prod 세 개 값중 하나씩만 들어가게끔 수정할 것. *
12. reqeustParam 을 DTO화해서 @Valid 적용해볼 것. *


## 2024-04-08
1. 빌드 돌릴 때 test를 함께 하도록 세팅할 것. *
2. application-test.yml 따로 만들 것. *
3. local 환경은 h2 말고 mysql 사용하도록 개선할 것. *
4. AuthControllerTest 를 만들고, Validation 순서를 지정하는 방법에 대해 스터디 해보고 적용해볼 것. *
   - Validation 순서 정하기 *
   - AuthControllerTest 만들기 *
5. Kotlin 스터디 해볼 것. *
6. 이력서 수정할 것.

## 2024-04-15
1. MVNRepository 에서 디펜던시들 검색 후 버전 명시 해줄 것. *
2. application-test.yml 을 테스트 패키지 하위로 옮길것 *
3. Enum을 사용하는 property는 Enum안에 선언된값인지 검증하는 커스텀 벨리데이션 작성해볼 것. *

## 2024-04-22
1. 이력서 재작성
2. Model vs Entity 스터디 할 것.
3. EnumValidation 로직 리팩토링.
4. macos에서도 빌드/테스트 돌아가게끔 수정.
5. 4번 까지 완료 후, 새 프로젝트를 생성 후 kotlin으로 다시 만들어 볼 것.