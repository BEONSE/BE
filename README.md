# BE
## BEONSE

## 브랜드 네임 / LOGO IMAGE

- BEONSE (비 온 뒤에 세차 하자!!)
    
    ![image](https://github.com/BEONSE/BE/assets/104209781/47359d70-34f7-40db-a42c-c33e0ab02072)

    

## 팀 구성

![image](https://github.com/BEONSE/BE/assets/104209781/d913391a-e114-4023-886d-0db80efeec07)

## 깃허브

- FE
    - https://github.com/BEONSE/FE
- BE
    - https://github.com/BEONSE/BE

## 프로젝트 아키텍처

![image](https://github.com/BEONSE/BE/assets/104209781/0867c7c4-054e-45cf-a51b-0a293b9b78d8)

## Git Convention

### Branch Naming

- feat/board

### Commit Convention(커밋 컨벤션)

| Tag Name | Description |
| --- | --- |
| Feat | 새로운 기능 추가 |
| Fix | 버그 수정 |
| Design | CSS등 사용자 UI 디자인 변경 |
| Style | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우 |
| Refactor | 코드 리펙토링 |
| Comment | 필요한 주석 추가 및 변경 |
| Test | 테스트 코드, 리펙토링 테스트 코드 추가, 실제로 사용하는 코드 변경 없음 |
| Rename | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우 |
| Remove | 파일을 삭제하는 작업만 수행한 경우 |

```java
ex) feat : 게시들 등록 기능 추가
```

### Pull Request Convention (PR 컨벤션)

```java
### PR 타입
- [ ] 기능 추가
- [ ] 기능 삭제
- [ ] 코드 리펙토링
- [ ] 버그 수정
- [ ] 의존성, 환경 변수, 빌드 관련 코드 업데이트
- [ ] 테스트 케이스 추가

### 반영 브랜치
- feat/login -> dev

### 변경 사항
- 

### 특이 사항
- 
```

### PR 후 merge 메시지

![image](https://github.com/BEONSE/BE/assets/104209781/a9c6c92c-dfe6-4043-ac94-65da5f6d114a)

- merge title - 프로젝트 명 #1(pull request 번호) / 기능 대분류 - 기능 상세
- merge content - 그대로 사용

## 스프링 부트 메서드 네이밍

### Controller

| 메서드 이름 | HTTP 메서드 |
| --- | --- |
| postBoard | POST |
| getBoard | GET |
| getBoardList | GET |
| getBoardPage | GET |
| putBoard | PUT |
| patchBoard | PATCH |
| deleteBoard | DELETE |

```java
ex)
@PostMapping
public ResopnseEntity<Board> postBoard() {}
```

### Service

| 메서드 이름 | 기능 |
| --- | --- |
| createBoard | 게시글 작성 |
| findBoard | 게시글 단일 조회 |
| findBoardList | 게시글 전체 조회 List |
| findBoardPage | 게시글 전체 조회 Paging |
| updateBoard | 게시글 수정 |
| removeBoard | 게시글 삭제 |

```java
ex)
@Transactional
public ResponseEntity<Board> createBoard() {}
```

- 이외의 메서드 네이밍은 해당 기능에 맞게 작성

## ERD

![image](https://github.com/BEONSE/BE/assets/104209781/b929eb97-0971-410a-a1d9-de0c94808d66)

## 와이어 프레임

![image](https://github.com/BEONSE/BE/assets/104209781/4fc9a866-ca9f-4dd3-8d51-d010b1a69012)

![image](https://github.com/BEONSE/BE/assets/104209781/e8fed509-3589-42e9-b305-39e9d7ba7d4e)

# 트러블슈팅

### **BE**

- MyBatis

  ![image](https://github.com/BEONSE/BE/assets/104209781/b3d61ca3-79c2-4c9b-98cb-8ac6f2c5dead)

- AWS Build
    
  ![image](https://github.com/BEONSE/BE/assets/104209781/1d29ebef-dbc4-482b-abcf-83e6e034f26f)

- AWS 배포 실행

  ![image](https://github.com/BEONSE/BE/assets/104209781/9bca525b-3a86-4d0e-b4e0-67a376230fa9)

- SSL 인증서 발급

  ![image](https://github.com/BEONSE/BE/assets/104209781/59500be3-d339-426a-bc24-ae1d157f92f0)

- Mapper Query
  
  ![image](https://github.com/BEONSE/BE/assets/104209781/872106b1-f59d-444c-922f-aa7811777963)
- Secret Key

  ![image](https://github.com/BEONSE/BE/assets/104209781/82801b8b-9505-4b67-ade1-bba80ee10fca)

