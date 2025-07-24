# SmartFlow - 전자결재 시스템

## 개요
SmartFlow는 기업용 전자결재 시스템입니다.  
사용자는 문서를 작성하고, 승인자(Admin)는 문서를 승인하거나 반려할 수 있습니다.  
문서 검색, 공개 문서 포함 필터링, 페이징 기능이 구현되어 있습니다.

## 주요 기능
- 사용자별 문서 목록 조회 및 페이징
- 문서 작성 (제목, 내용, 공개 여부 설정)
- 공개 문서 포함 여부 선택 가능
- 문서 승인 및 반려 (관리자 권한 필요)
- 문서 상세 조회 (제목 클릭 시 상세 페이지 이동)
- 로그인/로그아웃 기능 포함

## 기술 스택
- Spring Boot (Backend)
- Thymeleaf (템플릿 엔진)
- JPA / Hibernate (데이터베이스 ORM)
- H2 / MySQL (DB)
- Lombok 

## 프로젝트 구조
- `com.smartflow.domain`: JPA Entity (ApprovalDocument)
- `com.smartflow.dto`: 데이터 전송 객체 (Document, CreateApprovalRequest 등)
- `com.smartflow.repository`: 데이터 접근 레이어
- `com.smartflow.service`: 비즈니스 로직 처리 (DocumentService, DocumentServiceImpl)
- `com.smartflow.controller`: 웹 요청 처리 (Dashboard, Document 등)

## 향후 개선 예정
- 사용자 인증 및 권한 세분화 강화
- 문서별 댓글(코멘트) 기능 구현으로 협업 및 의사소통 강화
- 문서 첨부 파일 업로드 기능 추가
- UI/UX 개선 및 반응형 웹 지원
