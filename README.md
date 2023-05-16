# VacationERP - MetaVC

<p align="center">
  <img src="https://user-images.githubusercontent.com/84393930/236721677-c71c0bd1-5a94-46b7-91ab-fb5fbaf4f2d1.JPG">
</p>
<p align="center">
  <a href="https://hits.seeyoufarm.com"><img src="https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FVacationERP%2FFrontend&count_bg=%233B82F6&title_bg=%23A2A1A1&icon=cliqz.svg&icon_color=%23E7E7E7&title=hits&edge_flat=true"/></a>
</p>

---

## 프로젝트 개요

> **주체기관** : **Metanet KOSA**  
> **팀명** : **MetaVacation**  
> **주제** : **휴가 신청, 각종 증명서 발급 서비스**  
> **진행 일정** : **2023.04.10 ~ 2023.04.24**  
> **목적** : **문서 작업 / 결재 처리 전자화 및 전직원에 대한 효과적인 휴가 관리를 위한 솔루션 개발**

---

## 팀원 소개

|이소정|윤성훈|백성준|여성빈|
|:---:|:---:|:---:|:---:|
|**Project Manager**|**UI Master**|**Git Master**|**DB Master**|
|프로젝트 관리|Figma 제작 및 UI 관리|GitHub 관리|DDL 생성 및 제약조건 설정|
|<p align="left">**팀원**<br>- 캘린더 설정<br>- 휴가 신청/취소<br>- 증명서 발급<br>&nbsp;&nbsp; PDF 다운로드<br>- 파일 첨부<br>&nbsp;&nbsp; 업로드/다운로드</p>|<p align="left">**관리자**<br>- 연차 촉진 관리<br>- 팀원 휴가 내역 관리<br>- 휴가 유형 관리<br>- 휴가 부여 관리</p>|<p align="left">**Quartz Scheduler**<br>- 연차 생성/촉진<br>- 퇴사 2년 후 데이터 삭제<br>**관리자**<br>- 결재 관리<br>**그 외**<br>- 이메일 서비스 설정<br>- AOP 설정</p>|<p align="left">**시큐리티**<br>- Vue Router 권한 설정<br>- Spring Security 설정(Jwt)<br>**관리자**<br>- 사용자 회원 가입<br>- 사용자 정보 수정<br>**Java Code Coverege**</p>|
|[@sojungleeee](https://github.com/sojungleee)|[@Sayh0](https://github.com/Sayh0)|[@100SeongJun](https://github.com/100SeongJun)|[@ysb4642](https://github.com/ysb4642)|

---

## Stacks

### Environment
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)
![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)
![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?style=for-the-badge&logo=Visual%20Studio%20Code&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)


### Config
![NPM](https://img.shields.io/badge/NPM-%23CB3837.svg?style=for-the-badge&logo=npm&logoColor=white)
![Vite](https://img.shields.io/badge/vite-%23646CFF.svg?style=for-the-badge&logo=vite&logoColor=white)


### Development
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)
![SpringSecurity](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)

![Vue](https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=Javascript&logoColor=white)
![TailwindCSS](https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white)


### Communication
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)

---

## 주요 기능

### 팀원/팀장
| 메인 페이지  |  휴가 신청 - 잔여 TO  |
| :---: | :---: |
| ![image](https://github.com/VacationERP/Frontend/assets/84393930/53ef474d-b67f-430e-9001-27048ca37871) | ![image](https://github.com/VacationERP/Frontend/assets/84393930/9631f9b7-ebf5-4da8-aae1-902d808f474c) |  
| 파일 첨부   |  증명서 PDF 발급   |  
| ![image](https://github.com/VacationERP/Frontend/assets/84393930/fd87ca18-9ddd-4589-95a0-2cd4e2b31c1b)   |  ![image](https://github.com/VacationERP/Frontend/assets/84393930/a9cef66e-369f-4a40-b6af-815c168ccca4)     |


### 관리자
| 사원(사용자) 관리  |  결재 관리  |
| :---: | :---: |
| ![image](https://github.com/VacationERP/Frontend/assets/84393930/8ce0efe9-2528-4eea-9dd9-e9a33bc91975) | ![image](https://github.com/VacationERP/Frontend/assets/84393930/260bc6d7-61a1-4b48-8a0e-7626f242b63d) |
| 직원 등록 및 이메일 발송   |  휴가 유형 관리   |  
| ![image](https://github.com/VacationERP/Frontend/assets/84393930/754d26d5-2a49-4004-98dd-5db8d4492171)   |  ![image](https://github.com/VacationERP/Frontend/assets/84393930/eaa46cac-b96d-4d9a-9782-8c518f62461a)     |
| 휴가 부여 관리   |  연차 촉진 문서   |  
| ![image](https://github.com/VacationERP/Frontend/assets/84393930/8411af51-25a2-401e-8ca2-e919f3d99b27)   |  ![image](https://github.com/VacationERP/Frontend/assets/84393930/1ed996d5-47ee-4358-a6bf-01c38939c6c0)     |

---

### 부록
| 접근 권한 예외 처리 |  404 예외 처리  |
| :---: | :---: |
| ![image](https://github.com/VacationERP/Frontend/assets/84393930/c979c5fd-2eb6-4eaf-9b3c-74542b1b9d98) | ![image](https://github.com/VacationERP/Frontend/assets/84393930/e9b1750d-30b6-4eed-9cb1-a9a415b33fde) |
| WEB URI Path  |  JaCoCo(Java Code Coverage)   |  
| ![image](https://github.com/VacationERP/Frontend/assets/84393930/2447874f-5d64-4325-a897-178db8eac846)   |  ![image](https://github.com/VacationERP/Frontend/assets/84393930/84a34f5a-e85a-46d9-940e-27a59b7deb10)     |
| Figma UI 설계   |  반응형 웹 설정   |  
| ![image](https://github.com/VacationERP/Frontend/assets/84393930/6b7739ab-5b79-4b93-8380-df4649b8433a)   |  ![image](https://github.com/VacationERP/Frontend/assets/84393930/c51a373d-a75d-40c3-91fa-24a5176768f6)     |

---

## 아키텍처 구조
| ![image](https://github.com/VacationERP/Backend/assets/84393930/12fae7fc-543b-4c41-83ec-9152ad3d36fb) | ![image](https://github.com/VacationERP/Frontend/assets/84393930/5d070b2e-e0ca-4a51-8b1d-7333615af4fc) |
| :---: | :---: |

---

## 개발 환경
<img src="https://github.com/VacationERP/Backend/assets/84393930/d231967f-1fab-42e2-ba1e-ed45ee192ee0">

---

## 개발 일정
<img src="https://github.com/VacationERP/Backend/assets/84393930/72cc0f41-c44a-4831-b65d-5e590d11796e">
<img src="https://github.com/VacationERP/Backend/assets/84393930/50426dc2-d3f0-47b3-957b-144984e7edfd">
<img src="https://github.com/VacationERP/Backend/assets/84393930/bcc71fdf-498b-45ae-a3b9-fe2a6a02a936">

---

## 유스케이스 다이어그램

<img src="https://github.com/VacationERP/Backend/assets/84393930/1c89667b-21a1-4974-82d6-a08689fc46f3">
