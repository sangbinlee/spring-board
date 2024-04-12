# 1. config


	## 회원가입-로그인-accessToken
		{{base_url}}/member/signup
		회원가입

			{
				"email": "test4@test.com",
				"username": "username4",
				"role": "MEMBER",
			    "password": "1234567890123456789012345678901234567890"
			}

	{{base_url}}/member/login

		로그인 : {{base_url}}/member/login
			{
				"email": "test4@test.com",
			    "password": "1234567890123456789012345678901234567890"
			}

			로그인 응답 :

				{
				    "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InRlc3Q0QHRlc3QuY29tIiwibWVtYmVySWQiOjIsImlhdCI6MTcxMjg5NzU0NCwiZXhwIjoxNzk5Mjk3NTQ0fQ.LjtUrxWgFConbk5TcoJPbmcHHwc_MgEbyLzRNePHlZ9x9TEYZLrSIDhjGi93nDWm",
				    "accessToken2": "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InRlc3Q0QHRlc3QuY29tIiwibWVtYmVySWQiOjIsImlhdCI6MTcxMjg5NzU0NCwiZXhwIjoxNzk5Mjk3NTQ0fQ.LjtUrxWgFConbk5TcoJPbmcHHwc_MgEbyLzRNePHlZ9x9TEYZLrSIDhjGi93nDWm"
				}



	## SWAGGER Authorize 로그인 전 호출
		curl -X 'GET' \
		  'http://localhost:8080/api/v1/member' \
		  -H 'accept: application/hal+json'

	## SWAGGER Authorize 로그인 후 호출

		curl -X 'GET' \
		  'http://localhost:8080/api/v1/member' \
		  -H 'accept: application/hal+json'

# SWAGGER Authorize 로그인 후 호출
	api req header에 accessToken 값이 세팅 안됨

	/spring-board/src/main/resources/application.properties
		springdoc.show-login-endpoint=true

# refresh-token  ------- 진행중.

	https://github.com/sangbinlee/spring-boot-refresh-token-jwt
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#