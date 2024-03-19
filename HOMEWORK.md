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
6. java record 공부 후 적용
7. 이력서 써서 제출 *
