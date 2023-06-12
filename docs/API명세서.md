##게시물(포스트) 관련 API
|메서드|URL|설명|권한|
|------|---|---|---|
|POST|/api/v1/post|게시글 작성|OAuth 로그인 필요|
```
Request Body:
  Content-Type: multipart/form-data
  Request Parts:
    - dto: {"title": "My Post", "content": "Lorem ipsum"}
    - photo: photo1.jpg (file)
    - photo: photo2.jpg (file)
```
|메서드|URL|설명|권한|
|------|---|---|---|
|PUT|/api/v1/post|게시글 수정|OAuth 로그인 필요|
```
Request Body:
  Content-Type: multipart/form-data
  Request Parts:
    - dto: {"title": "My Post", "content": "Lorem ipsum", "postId": "107"}
    - photo: photo1.jpg (file)
    - photo: photo2.jpg (file)
```
|메서드|URL|설명|권한|
|------|---|---|---|
|GET|/api/v1/post/{id}|게시글 조회|로그인 불필요|
|DELETE|/api/v1/post/{id}|게시글 삭제|OAuth 로그인 필요|

##페이지(뷰) 관련 API
|메서드|URL|설명|권한|
|------|---|---|---|
|GET|/|"/main/1"로 리다이렉트|로그인 불필요|
|GET|/main/{pageNumber}|포스트를 10개씩 나눈 페이지 중 뒤에서 pageNumber에 해당하는 페이지를 요청함|로그인 불필요|
|GET|/post/save|게시글 작성 페이지로 이동|OAuth 로그인 필요|
|GET|/post/update/{id}|해당 id를 가진 게시글의 수정 페이지로 이동|OAuth 로그인 필요|
|GET|/post/detail/{id}|해당 id를 가진 게시글의 내용 페이지로 이동|OAuth 로그인 필요|