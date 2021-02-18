package helpers;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiHelper {

    public Map<String, String> getCookies() {
        return given()
                .header("User-Agent", "PostmanRuntime/7.26.10")
                .header("Connection", "keep-alive")
                .header("Accept", "*/*")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .cookie("__Secure-ab-group=92; __Secure-access-token=3.0.qDpu9PmFT9eu4e6AGb726A.92.l8cMBQAAAABgLlk5I5NMTKN3ZWKgAICQoA..20210218190134.9vagXgFcHhvJjevVA9i35TXP0fhZI-oQ8eaQ2hjJsvM; __Secure-ext_xcid=badb7761537501c9f69c7a93792f9a94; __Secure-refresh-token=3.0.qDpu9PmFT9eu4e6AGb726A.92.l8cMBQAAAABgLlk5I5NMTKN3ZWKgAICQoA..20210218190134.1AU9Fh-jfmbCp_sgbhA-FoZBv1k6mjLHRPIcwjX1X78; __Secure-user-id=0; incap_ses_1339_1101384=UF//GKRL9XTIa0rTfBWVEm6dLmAAAAAADnpaM2tyzapidgCKQiGspw==; incap_ses_277_1101384=uJHDHY9FC2MDAlFWjxrYA3WcLmAAAAAAX3GsVPI7jA8vAFtnK1Stug==; nlbi_1101384=xZcLOFgvBHuC9h+SyZtWRQAAAAAFzIs61PKq+nFz7VPGuRsW; visid_incap_1101384=HA5YILRURhCuCcZucRhfZzlZLmAAAAAAQUIPAAAAAADKrjp0IJnAVvEKk2Rn9K8n; SessionID=qDpu9PmFT9eu4e6AGb726A; abGroup=92; access_token=3.0.qDpu9PmFT9eu4e6AGb726A.92.l8cMBQAAAABgLlk5I5NMTKN3ZWKgAICQoA..20210218190134.9vagXgFcHhvJjevVA9i35TXP0fhZI-oQ8eaQ2hjJsvM; refresh_token=3.0.qDpu9PmFT9eu4e6AGb726A.92.l8cMBQAAAABgLlk5I5NMTKN3ZWKgAICQoA..20210218190134.1AU9Fh-jfmbCp_sgbhA-FoZBv1k6mjLHRPIcwjX1X78; token_expiration=2021-02-18T22:01:34.688084+03:00; xcid=f149647bccd44494d27814cdbcfc1eb2")
                .when()
                .get("https://www.ozon.ru")
                .then()
                .log().all()
                .statusCode(200)
                .extract().cookies();
    }
}
