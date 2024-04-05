/* 계정 생성 (관리자 계정으로 접속)*/
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;

CREATE USER SPRING_PIS IDENTIFIED BY SPRING1234;

GRANT CONNECT, RESOURCE TO SPRING_PIS;

ALTER USER SPRING_PIS
DEFAULT TABLESPACE USERS
QUOTA 20M ON USERS;

--> 계정 생성 후 접속 방법(새 데이터베이스) 추가



------------------------------------------------------------------------------------------------------------------

/*SPRING 계정 접속*/
-- "" : 내부에 작성된 글(모양) 그대로 인식 -> 대/소문자 구분
-- "" 작성 권장

--char(10) : 고정 길이 문자열 10바이트 (최대 2000바이트)
--varchar(10): 가변 길이 분자열 10바일터 (최댄 4000바이트)

--MVARCHAR (10) : 가변 길이 문자열 10글자 (유니코드 , 쇠대 400글자)

--CLOB : 가변 길이 문자열(최대 4GB)

/*MEMBER 테이블 생성*/
CREATE TABLE "MEMBER"(
	"MEMBER_NO" NUMBER CONSTRAINT "MEMBER_PK" PRIMARY KEY,
	"MEMBER_EMAIL" NVARCHAR2(50) NOT NULL,
	"MEMBER_PW" NVARCHAR2(100) NOT NULL,
	"MEMBER_NICKNAME" NVARCHAR2(10) NOT NULL,
	"MEMBER_TEL" CHAR(11) NOT NULL,
	"MEMBER_ADDRESS" NVARCHAR2(150),
	"PROFILE_IMG" VARCHAR2(300),
	"ENROLL_DATE" DATE DEFAULT SYSDATE NOT NULL,
	"MEMBER_DEL_FL" CHAR(1) DEFAULT 'N' CHECK ("MEMBER_DEL_FL" IN ('Y','N')),
	"AUTHORITY" NUMBER DEFAULT 1 CHECK("AUTHORITY" IN (1,2))
);

SELECT * FROM MEMBER;

--회원 번호 시퀀스 만들기
CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE;

--샘플 회원 데이터 삽입
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'member01@kh.or.kr',
		'$2a$10$MErn5EIMXuc4mj.nEe3bKOo.FCwwi.Fvf8XnxPs4lfgymNhRE9Wee',
		'샘플1',
		'01012341234',
		NULL,
		NULL,
		DEFAULT,
		DEFAULT,
		DEFAULT
		);
	
COMMIT;

--로그인

-- -> BCrypt 암호화 사용 중
--> 그래서 비밀번호 비교 불가능
-- 그래서 비밀번호를 조회
-- 이메일이 일치하는 회원 + 탈퇴 안한 회원 조건

SELECT MEMBER_NO,MEMBER_EMAIL,MEMBER_NICKNAME,MEMBER_PW,
		MEMBER_TEL,MEMBER_ADDRESS,PROFILE_IMG,AUTHORITY,
		TO_CHAR(ENROLL_DATE,'YYYY"년"MM"월"DD"일"HH24"시"MI"분"SS"초"' ) ENROLL_DATE
FROM "MEMBER"
WHERE MEMBER_EMAIL=?
AND MEMBER_DEL_FL='N';


--중복 검사 (탈퇴 안한 회원 중 같은 이메일이 있는지 죄회)
SELECT COUNT(*)
FROM "MEMBER"
WHERE MEMBER_DEL_FL ='N'
AND MEMBER_EMAIL ='member01@kh.or.kr';



DELETE FROM "MEMBER"
WHERE MEMBER_EMAIL ='baginseo400@gmail.com';
COMMIT;

UPDATE "MEMBER"
SET MEMBER_NICKNAME ='USER123123',
	MEMBER_TEL ='01055556666',
	MEMBER_ADDRESS = '12345^^^경기도 고양시일산동구^^^ 풍동'
WHERE MEMBER_NO ='5';


UPDATE "MEMBER"
SET MEMBER_DEL_FL='N'
WHERE MEMBER_NO = '5';


UPDATE "MEMBER"
SET MEMBER_ADDRESS ='78901^^^경기도 고양시 일산동구^^^식사동'
WHERE MEMBER_NO ='5';


UPDATE "MEMBER"
SET MEMBER_PW= '123123'
WHERE MEMBER_NO ='1';


SELECT * FROM "MEMBER";


/*이메일, 인증키 거장 테이블 생성*/
CREATE TABLE "TB_AUTH_KEY"(
	"KEY_NO" NUMBER PRIMARY KEY,
	"EMAIL" NVARCHAR2(50) NOT NULL,
	"AUTH_KEY" CHAR(6) NOT NULL,
	"CREATE_TIME" DATE DEFAULT SYSDATE NOT NULL
	);
	

COMMENT ON COLUMN  "TB_AUTH_KEY"."KEY_NO" IS '인증키 구분 번호(시퀀스)';
COMMENT ON COLUMN  "TB_AUTH_KEY"."EMAIL" IS '인증 이메일';
COMMENT ON COLUMN  "TB_AUTH_KEY"."AUTH_KEY" IS '인증 번호';
COMMENT ON COLUMN  "TB_AUTH_KEY"."CREATE_TIME" IS '인증 번호 생성 시간';

CREATE SEQUENCE SEQ_KEY_NO NOCACHE; --인증키 구분 번호 시퀀스


SELECT * FROM "TB_AUTH_KEY";


---------------------------------------------------------------------
--파일 업로드 테스트용 테이블
CREATE TABLE "UPLOAD_FILE"(
	FILE_NO NUMBER PRIMARY KEY,
	FILE_PATH VARCHAR2(500) NOT NULL,
	FILE_ORIGINAL_NAME VARCHAR2(300) NOT NULL,
	FILE_RENAME VARCHAR2(100) NOT NULL,
	FILE_UPLOAD_DATE DATE DEFAULT SYSDATE,
	MEMBER_NO NUMBER REFERENCES "MEMBER"

);


COMMENT ON COLUMN "UPLOAD_FILE".FIFLE_NO IS '파일 번호';
COMMENT ON COLUMN "UPLOAD_FILE".FIFLE_ORIGINAL IS '클라이언트 요청 경로';
COMMENT ON COLUMN "UPLOAD_FILE".FILE_UPLOAD_DATE '변경된 파일명';
COMMENT ON COLUMN "UPLOAD_FILE".TILEIS '변경된 파일명';
COMMENT ON COLUMN "UPLOAD_FILE".MEMBER_NO 'MEMBER 테이블의 PK ';

CREATE SEQUENCE SEQ_FILE_NO NOCACHE;

SELECT * FROM "UPLOAD_FILE";

SELECT FILE_NO,FILE_PATH ,FILE_ORIGINAL_NAME ,FILE_RENAME ,
	TO_CHAR(FILE_UPLOAD_DATE,'YYYY-MM-DD') FILE_UPLOAD_DATE ,
	MEMBER_NICKNAME
FROM UPLOAD_FILE
JOIN "MEMBER" USING(MEMBER_NO)
ORDER BY FILE_NO DESC;  











---------------------------------------------------------------------
