# 3차 프로젝트

SpringBoot을 적용시켜 고도화한 프로젝트입니다.
기존 Maven 을 Gradle로 변경하였고 이에 따라 repository도 변경하였습니다.

기존 iBatis를 JPA와 Mybatis로 고도화하고 둘을 혼합하여 적용시켰습니다.

   -> 단순한 쿼리문은 JPA로, 복잡하고 프로시저가 필요한 것들은 Mybatis로 처리하였습니다.
   
apache http 서버또는 Weblogic을 사용하여 정적파일과 동적파일을 분리하고 데이터 서버와 html서버를 분리하였습니다.
