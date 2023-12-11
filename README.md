# BE
## BEONSE

## 브랜드 네임 / LOGO IMAGE

- BEONSE (비 온 뒤에 세차 하자!!)
    
    ![Logo.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/a2a05ba9-5767-4ecf-b237-640ce95f1125/ecec4387-301e-486c-b6fa-3fa9161a8cb5/Logo.png)
    

## 팀 구성

[인원구성 및 역할분담 (3)](https://www.notion.so/6ce1dc916e8b48d1b57e36cb887b677a?pvs=21)

## 깃허브

- **FE**
    - https://github.com/BEONSE/FE
- BE
    - https://github.com/BEONSE/BE

## 프로젝트 아키텍처

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/a2a05ba9-5767-4ecf-b237-640ce95f1125/059657c9-48b8-4f51-b937-d67616d9edbf/Untitled.png)

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

예시)

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/a2a05ba9-5767-4ecf-b237-640ce95f1125/3c97c7e9-9ac9-4421-b944-1776c49d9a9e/Untitled.png)

### PR 후 merge 메시지

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/a2a05ba9-5767-4ecf-b237-640ce95f1125/a16bd36f-d345-4d4a-9520-577a616bf2c1/Untitled.png)

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

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/a2a05ba9-5767-4ecf-b237-640ce95f1125/70ce63ce-af21-4635-92fd-bd637af6465f/Untitled.png)

## 와이어 프레임

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/a2a05ba9-5767-4ecf-b237-640ce95f1125/dea4de2f-4af4-4b56-8a87-2a552e026248/Untitled.png)

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/a2a05ba9-5767-4ecf-b237-640ce95f1125/7d643252-f652-4a47-b275-036aa397731d/Untitled.png)

# 트러블슈팅

### **BE**

- MyBatis

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/a2a05ba9-5767-4ecf-b237-640ce95f1125/6df5f04b-9cbe-4e43-9d55-10a3c5bddde8/Untitled.png)

- AWS Build
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/a2a05ba9-5767-4ecf-b237-640ce95f1125/3d415608-3be0-4806-9b17-04d04f139aff/Untitled.png)
    
- AWS 배포 실행
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/a2a05ba9-5767-4ecf-b237-640ce95f1125/a2954912-2b27-417b-b502-23c034632351/Untitled.png)
    
- SSL 인증서 발급
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/a2a05ba9-5767-4ecf-b237-640ce95f1125/ad5ab000-d076-47cd-9930-3f2f70f36c1d/Untitled.png)
- Mapper Query
  
  ![image](https://github.com/BEONSE/BE/assets/104209781/872106b1-f59d-444c-922f-aa7811777963)
- Secret Key

  ![image](https://github.com/BEONSE/BE/assets/104209781/82801b8b-9505-4b67-ade1-bba80ee10fca)

