Terminal-Based Testing Using curl
Login Request
Command:
curl -i -c cookies.txt -X POST "http://localhost:8081/session/login" ^
-d "email=aravind@gmail.com" ^
-d "password=123" ^
-d "rememberMe=true"
Output:
HTTP/1.1 302
Set-Cookie: JSESSIONID=6034571915FB141270756FDD07A54868
Set-Cookie: email=aravind@gmail.com
Location: welcome.jsp

Observation:
• Login successful
• Session cookie created
• Remember Me cookie created

Access Protected Welcome Page
Command: curl -i -b cookies.txt "http://localhost:8081/session/welcome.jsp"
Output: HTTP/1.1 200
Observation: Valid session cookie allowed access.

Access Session Settings Page
Command: curl -i -b cookies.txt "http://localhost:8081/session/settings.jsp"
Output: HTTP/1.1 200
Observation: Protected page accessible using valid session.

Access Protected Page Without Cookie
Command:
curl -i "http://localhost:8081/session/settings.jsp"

Output:
HTTP/1.1 302

Location:
index.jsp?error=Session expired.

Observation:
Unauthenticated users are redirected.

Logout Without CSRF Token
Command:
curl -i -b cookies.txt -X POST http://localhost:8081/session/logout

Output:
HTTP/1.1 403 Forbidden

Message:
Invalid CSRF Token

Observation:
Cookie alone is not enough.
CSRF validation successfully blocked the request.

Logout With Valid CSRF Token
Command:
curl -i -b cookies.txt -X POST "http://localhost:8081/session/logout" ^
-d "csrfToken=b9566d0c-95cd-4054-b651-5c0615fa549f"

Output:
HTTP/1.1 302
Location:
index.jsp?logout=SUCCESS
Observation:
Valid token successfully completed logout.
