package api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import io.restassured.response.Response;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.Assert;
import java.util.*;


import static utilities.Common.*;
import static utilities.Common.randomStringGenerator;

public class Apis extends BaseApi{

    public Apis(){
        application = getRepValue("application");
        environment = getRepValue("environment");
        baseUrl = getRepValue("baseUrl");
        envDomain = getRepValue("envDomain");
        envDatacenter = getRepValue("envDatacenter");
        baseUrl = String.format(baseUrl,envDatacenter,envDomain);
        userName = getRepValue("userName");
        ownerId = getRepValue("ownerId");
        schemaId = getRepValue("schemaId");
        tenantName = getRepValue("tenantName");
        siteGroupId = getRepValue("siteGroupId");
        jwtURLTemplate = getRepValue("jwtURLTemplate");
        registrationUrl = getRepValue("registrationUrl");
        if(application.toLowerCase().contains("partner")) {
            registrationUrl = String.format(registrationUrl, baseUrl);
        }else {
            registrationUrl = String.format(registrationUrl,baseUrl,tenantName,siteGroupId);
        }
        envClientId = getRepValue("envClientId");
        envApiKey = getRepValue("envApiKey");
        secret = getRepValue("secret");
        workspaceId = getRepValue("workspaceId");
        partnerId = getRepValue("partnerId");
        email = getRepValue("email");
        IDP = getRepValue("IDP");
        RTUrl =  getRepValue("RTUrl");
        hookUrl = getRepValue("hookUrl");
        if (application.equalsIgnoreCase("gigya")) {
            RTUrl = String.format(RTUrl, baseUrl, envApiKey, envClientId);
            gigyaRTJwt = getRepValue("GigyaRTjwt");
            gigyaJwtUsername = getRepValue("GigyaJwtUsername");
        }
        else {
            RTUrl = String.format(RTUrl,baseUrl,tenantName);
        }
        if(IDP.toLowerCase().contains("okta")) {
            hookUrl = String.format(hookUrl, tenantName, envDatacenter, envDomain, "okta");
        }else if(IDP.toLowerCase().contains("auth")) {
            hookUrl = String.format(hookUrl, tenantName, envDatacenter, envDomain, "auth0");
        }
        passWord = getRepValue("passWord");
        jwtURL = String.format(jwtURLTemplate, baseUrl, tenantName);
        host = tenantName + "." + envDatacenter + ".plainid" + "." + envDomain;
//        if(application.contains("gigya")){
//            registrationUrl = String.format(registrationUrl,baseUrl,tenantName,siteGroupId);
//        }
        partnerWsIdUrl = getRepValue("partnerWsIdUrl");
        schemaOwnerUrl = getRepValue("schemaOwnerUrl");
        oktaGetPlainIdEnvRedirectURI = getRepValue("oktaGetPlainIdEnvRedirectURI");
        oktaRedirectURI = getRepValue("oktaRedirectURI");
        oktaUrl = getRepValue("oktaUrl");
        oktaClientId = getRepValue("OktaIDPClientId");
        oktaRedirectURI = String.format(oktaRedirectURI,oktaUrl,oktaClientId);
        oktaApiKey = getRepValue("OktaIDPApiKey");
        oktaInlineHookId = getRepValue("oktaInlineHookId");
        oktaInlineHookURI = getRepValue("oktaInlineHookURI");
        oktaInlineHookURI = String.format(oktaInlineHookURI,oktaUrl,oktaInlineHookId);
        auth0ClientId = getRepValue("auth0ClientId");
        auth0ClientSecret = getRepValue("auth0ClientSecret");
        auth0DomainName = getRepValue("auth0DomainName");
        auth0Audience = getRepValue("auth0Audience");
        auth0GetTokenUrl = getRepValue("auth0GetTokenUrl");
        auth0GetTokenUrl = String.format(auth0GetTokenUrl,auth0DomainName);
        auth0GetRulesUrl = getRepValue("auth0GetRulesUrl");
        auth0GetRulesUrl = String.format(auth0GetRulesUrl,auth0DomainName);
        auth0UpdateRuleUrl = getRepValue("auth0UpdateRuleUrl");
        auth0ClientUrl = getRepValue("auth0ClientUrl");
        auth0ClientUrl = String.format(auth0ClientUrl,auth0DomainName,auth0ClientId);
    }



    Map<String,Object> body;
    Map<String,String> header;
    Map<String,Object> attributes;
    Map<String,Object> fields;
    Map<String,Object> requester;
    Map<String,String> map;
    String jwtURLTemplate;
    String registrationUrl;
    String siteGroupId;
    String IDP;
    String baseUrl;
    String RTUrl;
    String gigyaRTJwt;
    String gigyaJwtUsername;
    String hookUrl;
    String application;
    String environment;
    String envClientId;
    String envApiKey;
    String secret;
    String workspaceId;
    String partnerId;
    String email;
    String envDomain;
    String envDatacenter;
    String host;
    String emptyList = "[]";
    public String userName;
    public String passWord;
    public String grant_type = "password";
    public String runtime = "runtime";
    public String tenantName;
    public String jwtURL;
    String ownerId;
    String schemaId;
    String access_token;
    String auth0_token;
    String oktaInlineHookId;
    String oktaInlineHookURI;
    String partnerWsIdUrl;
    String schemaOwnerUrl;
    String oktaGetPlainIdEnvRedirectURI;
    String oktaRedirectURI;
    String oktaUrl;
    String oktaClientId;
    String oktaApiKey;
    String auth0ClientId;
    String auth0ClientSecret;
    String auth0DomainName;
    String auth0Audience;
    String auth0GetTokenUrl;
    String auth0GetRulesUrl;
    String auth0ClientUrl;
    String auth0UpdateRuleUrl;
    String plainid_internal = "plainid_internal";
    String delegated_admin = "delegated_admin";
    String delegatedAdmin = "Delegated admin";
    String hookResponseEntitlementKey = "commands.value.path";
    String hookResponseEntitlementValue = "commands.value.value";
    String hookResponseValue = "commands.value";
    String RTResponseRoleName = "response.access.actions.permission";
    String RTResponseEntitlementResponseKey = "response.access.attributes.'Response Key'";
    String RTResponseEntitlementResponseValue = "response.access.attributes.'Response Value'";
    String RTResponseMemberJobFunction = "identity.attributes.jobFunction";
    String RTResponseMemberDepartment = "identity.attributes.department";
    String RTResponse = "response";
    String RTRolesAssociated = "identity.attributes.roles";
    String errorSummary = "ErrorSummary";
    String noAccessTokenIsEmpty = "no access ,token is empty";
    String failedToGetToken = "failed to GetToken";
    String responseMemberNotFound = "errors.name";
    String memberNotFoundName = "MemberNotFoundError";
    String invalidClientId = "Invalid clientId";
    String invalidSecret = "Invalid api key";
    String partnerNotActiveError = "PartnerNotActiveError";
    String identity = "identity";
    String permission = "permission";
    String oktaHookBody = "{\n" +
            "   \"eventId\":\"ew5qTGckRV27Q9a6DrrIaw\",\n" +
            "   \"eventTime\":\"2019-11-28T13:00:22.000Z\",\n" +
            "   \"eventType\":\"com.okta.oauth2.tokens.transform\",\n" +
            "   \"eventTypeVersion\":\"1.0\",\n" +
            "   \"contentType\":\"application/json\",\n" +
            "   \"cloudEventVersion\":\"0.1\",\n" +
            "   \"source\":\"https://atko-plainid.oktapreview.com/oauth2/ausoof6pze1WgMFAx0h7/v1/authorize\",\n" +
            "   \"data\":{\n" +
            "      \"context\":{\n" +
            "         \"request\":{\n" +
            "            \"id\":\"Xd-E5awdmOZ-tCRgleHr@gAADNM\",\n" +
            "            \"method\":\"GET\",\n" +
            "            \"url\":{\n" +
            "               \"value\":\"https://atko-plainid.oktapreview.com/oauth2/ausoof6pze1WgMFAx0h7/v1/authorize?scope=openid&response_type=code&state=XO8GWWnBsfWlrwlpcCGRf1DQeWhVaQSxV5BMqOm5dlE.sdhiw4jygDM.account&redirect_uri=https%3A%2F%2Flocalhost%3A2080%2Fauth%2Frealms%2FPlainID%2Fbroker%2Fokta-plainid%2Fendpoint&nonce=2c0e94f1-dcca-46ef-a0ac-4adb5c4787c8&client_id=0oaxbec7srCiB5kcr0h7\"\n" +
            "            },\n" +
            "            \"ipAddress\":\"5.102.205.94\"\n" +
            "         },\n" +
            "         \"protocol\":{\n" +
            "            \"type\":\"OAUTH2.0\",\n" +
            "            \"request\":{\n" +
            "               \"scope\":\"openid\",\n" +
            "               \"state\":\"XO8GWWnBsfWlrwlpcCGRf1DQeWhVaQSxV5BMqOm5dlE.sdhiw4jygDM.account\",\n" +
            "               \"redirect_uri\":\"https://localhost:2080/auth/realms/PlainID/broker/okta-plainid/endpoint\",\n" +
            "               \"response_mode\":\"query\",\n" +
            "               \"response_type\":\"code\",\n" +
            "               \"client_id\":\"0oaxbec7srCiB5kcr0h7\"\n" +
            "            },\n" +
            "            \"issuer\":{\n" +
            "               \"uri\":\"https://atko-plainid.oktapreview.com/oauth2/ausoof6pze1WgMFAx0h7\"\n" +
            "            },\n" +
            "            \"client\":{\n" +
            "               \"id\":\"0oaxbec7srCiB5kcr0h7\",\n" +
            "               \"name\":\"TestIntegration\",\n" +
            "               \"type\":\"PUBLIC\"\n" +
            "            }\n" +
            "         },\n" +
            "         \"session\":{\n" +
            "            \"id\":\"102IfJkDXvNRGS59DlkmHIP4g\",\n" +
            "            \"userId\":\"00uohx7j5cr6i2QYT0h7\",\n" +
            "            \"login\":\"vladi.berger@plainid.com\",\n" +
            "            \"createdAt\":\"2019-11-28T13:00:21.000Z\",\n" +
            "            \"expiresAt\":\"2019-11-28T15:00:21.000Z\",\n" +
            "            \"status\":\"ACTIVE\",\n" +
            "            \"lastPasswordVerification\":\"2019-11-28T13:00:21.000Z\",\n" +
            "            \"lastFactorVerification\":\"2019-11-28T12:22:45.000Z\",\n" +
            "            \"amr\":[\n" +
            "               \"PASSWORD\"\n" +
            "            ],\n" +
            "            \"idp\":{\n" +
            "               \"id\":\"00oog645m0wNLruoy0h7\",\n" +
            "               \"type\":\"OKTA\"\n" +
            "            },\n" +
            "            \"mfaActive\":false\n" +
            "         },\n" +
            "         \"user\":{\n" +
            "            \"id\":\"00uohx7j5cr6i2QYT0h7\",\n" +
            "            \"passwordChanged\":\"2019-11-18T14:08:30.000Z\",\n" +
            "            \"profile\":{\n" +
            "               \"login\":\"kuku@riku.com\",\n" +
            "               \"firstName\":\"QA\",\n" +
            "               \"lastName\":\"Test\",\n" +
            "               \"locale\":\"en\",\n" +
            "               \"timeZone\":\"America/Los_Angeles\",\n" +
            "               \"title\":\"QA\",\n" +
            "               \"userType\":\"Prescription lenses procurer\",\n" +
            "               \"pid_partner_id\":\"110022\"\n" +
            "            },\n" +
            "            \"_links\":{\n" +
            "               \"groups\":{\n" +
            "                  \"href\":\"https://atko-plainid.oktapreview.com/00uohx7j5cr6i2QYT0h7/groups\"\n" +
            "               },\n" +
            "               \"factors\":{\n" +
            "                  \"href\":\"https://atko-plainid.oktapreview.com/api/v1/users/00uohx7j5cr6i2QYT0h7/factors\"\n" +
            "               }\n" +
            "            }\n" +
            "         },\n" +
            "         \"policy\":{\n" +
            "            \"id\":\"00poof6ajhsgFBKKd0h7\",\n" +
            "            \"rule\":{\n" +
            "               \"id\":\"0proof6sbh5iUkrc90h7\"\n" +
            "            }\n" +
            "         }\n" +
            "      },\n" +
            "      \"identity\":{\n" +
            "         \"claims\":{\n" +
            "            \"sub\":\"vladi.berger+1@plainid.com\",\n" +
            "            \"ver\":1,\n" +
            "            \"iss\":\"https://atko-plainid.oktapreview.com/oauth2/ausoof6pze1WgMFAx0h7\",\n" +
            "            \"aud\":\"0oaoobacupwMwff7T0h7\",\n" +
            "            \"jti\":\"ID.cZqhmaCpCcpJRUOXdu7qKkTNgZIxyfWpwdsdPD_xOw4\",\n" +
            "            \"amr\":[\n" +
            "               \"pwd\"\n" +
            "            ],\n" +
            "            \"idp\":\"00oog645m0wNLruoy0h7\",\n" +
            "            \"nonce\":\"2c0e94f1-dcca-46ef-a0ac-4adb5c4787c8\",\n" +
            "            \"auth_time\":1574946021,\n" +
            "            \"title\":\"Banker\",\n" +
            "            \"location\":\"USA\",\n" +
            "            \"pid_partner_id\":\"93831fa4-7e93-4b1d-90f4-fa14aa8cbcf3\"\n" +
            "         },\n" +
            "         \"token\":{\n" +
            "            \"lifetime\":{\n" +
            "               \"expiration\":3600\n" +
            "            }\n" +
            "         }\n" +
            "      },\n" +
            "      \"access\":{\n" +
            "         \"claims\":{\n" +
            "            \"ver\":1,\n" +
            "            \"jti\":\"AT.RG52HiQwpmNHSXOTBYwzRfakEZdYTmVxXm55aXeSWIo\",\n" +
            "            \"iss\":\"https://atko-plainid.oktapreview.com/oauth2/ausoof6pze1WgMFAx0h7\",\n" +
            "            \"aud\":\"api://plainid\",\n" +
            "            \"cid\":\"0oaoobacupwMwff7T0h7\",\n" +
            "            \"uid\":\"00uohx7j5cr6i2QYT0h7\",\n" +
            "            \"sub\":\"vladi.berger@plainid.com\"\n" +
            "         },\n" +
            "         \"token\":{\n" +
            "            \"lifetime\":{\n" +
            "               \"expiration\":3600\n" +
            "            }\n" +
            "         },\n" +
            "         \"scopes\":{\n" +
            "            \"openid\":{\n" +
            "               \"id\":\"scpoof6pzfk7h45ch0h7\",\n" +
            "               \"action\":\"GRANT\"\n" +
            "            }\n" +
            "         }\n" +
            "      }\n" +
            "   }\n" +
            "}";

    String auth0HookBody = "{\n" +
            "    \"user\": {\n" +
            "        \"_id\": \"3b677bb293c3cd0903eb28a4ee92c3d5\",\n" +
            "        \"clientID\": \"VYquN3dw2psCEBN8polil7dIKWLrcnoi\",\n" +
            "        \"created_at\": \"2021-03-15T22:16:35.527Z\",\n" +
            "        \"email\": \"bnfiqbts@sharklasers.com\",\n" +
            "        \"email_verified\": true,\n" +
            "        \"identities\": [\n" +
            "            {\n" +
            "                \"connection\": \"Username-Password-Authentication\",\n" +
            "                \"provider\": \"auth0\",\n" +
            "                \"user_id\": \"604fdcc3c8441c00696caae8\",\n" +
            "                \"isSocial\": false\n" +
            "            }\n" +
            "        ],\n" +
            "        \"name\": \"bnfiqbts111@sharklasers.com\",\n" +
            "        \"nickname\": \"vladi.berger\",\n" +
            "        \"picture\": \"https://s.gravatar.com/avatar/e5bf9081f73c313db43ba57ed71435b1?s=480u0026r=pgu0026d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fvl.png\",\n" +
            "        \"updated_at\": \"2021-03-15T23:10:47.371Z\",\n" +
            "        \"user_id\": \"auth0|604fdcc3c8441c00696caae8\",\n" +
            "        \"user_metadata\": {\n" +
            "            \"pid_partner_id\": [\n" +
            "                \"f75dfd1b-bbb7-447c-b3c5-6fea704f61a6\"\n" +
            "            ],\n" +
            "            \"userType\": [\n" +
            "                \"Prescription lenses procurer\"\n" +
            "            ]\n" +
            "        },\n" +
            "        \"global_client_id\": \"VYquN3dw2psCEBN8polil7dIKWLrcnoi\",\n" +
            "        \"app_metadata\": {},\n" +
            "        \"persistent\": {}\n" +
            "    },\n" +
            "    \"context\": {\n" +
            "        \"tenant\": \"dev-b1g9hxdw\",\n" +
            "        \"clientID\": \"VYquN3dw2psCEBN8polil7dIKWLrcnoi\",\n" +
            "        \"clientName\": \"All Applications\",\n" +
            "        \"clientMetadata\": {},\n" +
            "        \"connection\": \"Username-Password-Authentication\",\n" +
            "        \"connectionStrategy\": \"auth0\",\n" +
            "        \"connectionID\": \"con_Kn4iqDHHWSAOigVo\",\n" +
            "        \"connectionOptions\": {},\n" +
            "        \"connectionMetadata\": {},\n" +
            "        \"samlConfiguration\": {},\n" +
            "        \"jwtConfiguration\": {},\n" +
            "        \"protocol\": \"oidc-basic-profile\",\n" +
            "        \"stats\": {\n" +
            "            \"loginsCount\": 1\n" +
            "        },\n" +
            "        \"sso\": {\n" +
            "            \"with_auth0\": false,\n" +
            "            \"with_dbconn\": false,\n" +
            "            \"current_clients\": [\n" +
            "                \"VYquN3dw2psCEBN8polil7dIKWLrcnoi\"\n" +
            "            ]\n" +
            "        },\n" +
            "        \"accessToken\": {},\n" +
            "        \"idToken\": {},\n" +
            "        \"authentication\": {\n" +
            "            \"methods\": [\n" +
            "                {\n" +
            "                    \"name\": \"pwd\",\n" +
            "                    \"timestamp\": 1615846744406\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        \"sessionID\": \"NPzhwxzJs_KICQrhSZZG2NMHb7re9x9d\",\n" +
            "        \"request\": {\n" +
            "            \"userAgent\": \"Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36\",\n" +
            "            \"ip\": \"5.102.205.25\",\n" +
            "            \"hostname\": \"dev-b1g9hxdw.us.auth0.com\",\n" +
            "            \"query\": {\n" +
            "                \"client_id\": \"VYquN3dw2psCEBN8polil7dIKWLrcnoi\",\n" +
            "                \"response_type\": \"code\",\n" +
            "                \"connection\": \"Username-Password-Authentication\",\n" +
            "                \"prompt\": \"consent\",\n" +
            "                \"redirect_uri\": \"https://manage.auth0.com/tester/callback?connection=Username-Password-Authentication\",\n" +
            "                \"_ga\": \"2.260013071.483275949.1615830391-929048721.1587358074\"\n" +
            "            },\n" +
            "            \"body\": {},\n" +
            "            \"geoip\": {\n" +
            "                \"country_code\": \"IL\",\n" +
            "                \"country_code3\": \"ISR\",\n" +
            "                \"country_name\": \"Israel\",\n" +
            "                \"city_name\": \"Tel Aviv\",\n" +
            "                \"latitude\": 32.0695,\n" +
            "                \"longitude\": 34.7621,\n" +
            "                \"time_zone\": \"Asia/Jerusalem\",\n" +
            "                \"continent_code\": \"AS\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"authorization\": {\n" +
            "            \"roles\": []\n" +
            "        }\n" +
            "    },\n" +
            "    \"config\": {\n" +
            "        \"PLAINID_CLIENT_ID\": \"PFGVGORWUOTW00KPKVYG\",\n" +
            "        \"PLAINID_CLIENT_SECRET\": \"wQsbUY4XsyCN2Ie1NhvzVKJKe620z0bv9OLTiFrG\",\n" +
            "        \"x-plainid-workspace\": \"d17be640-39e6-4c00-bda1-0ee06964aac3\"\n" +
            "    }\n" +
            "}";


    // get the JWL
    public String getJWT(){
        body = new HashMap<>();
        body.put("grant_type",grant_type);
        body.put("client_id",runtime);
        body.put("password",passWord);
        if (application.contains("partner")) {
            body.put("username", userName);
        } else if (application.equalsIgnoreCase("gigya")) {
            body.put("username", gigyaJwtUsername);
        }
        else {
            body.put("username", runtime);
        }

        header = new HashMap<>();
        header.put("Content-Type","application/x-www-form-urlencoded");
        if(application.contains("partner")) {
            header.put("Cookie", "INGRESSCOOKIE=1610630690.751.3278.659628");
        }

        Response res = getRelaxedPostResponse(header,body,jwtURL);
        assertStatusCode(res,200);
        access_token = getValueFromResponseBody(res,"access_token");
        return access_token;
    }

    public String getAuth0AccessToken(){
        header = new HashMap<>();
        header.put("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
//        header.put("Cookie","did=s%3Av0%3A22bc6fd0-5124-11ec-95f6-fd18243aab98.tFrq%2Fwiqby80k8J3GWMxmxogcXIa4oTA%2FhiK1a7pri0; did_compat=s%3Av0%3A22bc6fd0-5124-11ec-95f6-fd18243aab98.tFrq%2Fwiqby80k8J3GWMxmxogcXIa4oTA%2FhiK1a7pri0");
//        header.put("Cookie","__cf_bm=D5nFZLOqQqGW1It0ljsVdAvGLLD8o.MpJU8RK66Hgpg-1638176678-0-AfIUZlnrx+PHEEddcFFFgqI+BDA6Iu/TzZ6F6E8X476XgrofE9Fs4a3NCRN99+LXWZHQTqEPLGrl9rplMDHix00=; did=s%3Av0%3Aa1cc0f90-d41c-11eb-ac29-75a6c3f2cd14.3RBBp9MFojPSJ2t4HbqGz5m7wWlhalzZSTtCohzS6MU; did_compat=s%3Av0%3Aa1cc0f90-d41c-11eb-ac29-75a6c3f2cd14.3RBBp9MFojPSJ2t4HbqGz5m7wWlhalzZSTtCohzS6MU");

        body = new HashMap<>();
        body.put("grant_type","client_credentials");
        body.put("client_id",auth0ClientId);
        body.put("client_secret",auth0ClientSecret);
        body.put("audience",auth0Audience);
        Response res = getRelaxedPostResponse(header,body,auth0GetTokenUrl);
        assertStatusCode(res,200);
        auth0_token = getValueFromResponseBody(res,"access_token");
        return auth0_token;
    }

    public String getAuth0Rules(){
        header = new HashMap<>();
        header.put("Authorization", "Bearer " + auth0_token);
        Response res = getResponse(header,auth0GetRulesUrl);
        assertStatusCode(res,200);
        String rules = getValueFromResponseBody(res,"script");
        String ruleId = getValueFromResponseBody(res,"id");
        return rules;
    }

    public void updateAuth0Client(String envRedirectURI){
        header = new HashMap<>();
        header.put("Authorization", "Bearer " + auth0_token);
        Response res = getResponse(header, auth0ClientUrl);
        assertStatusCode(res,200);
        header.put("Content-Type","application/json");
        Map<String, Object> responseMap =  res.getBody().as(Map.class);
        ((List<String>)responseMap.get("callbacks")).add(envRedirectURI);
        responseMap.remove("callback_url_template");
        responseMap.remove("client_id");
        responseMap.remove("signing_keys");
        responseMap.remove("global");
        responseMap.remove("tenant");
        ((Map<String,String>)responseMap.get("jwt_configuration")).remove("secret_encoded");
        Response updateRes = patch(header,responseMap,auth0ClientUrl);
        assertStatusCode(updateRes,200);
    }

    public void updateAuth0Rules(Map<String,String> map){
        header = new HashMap<>();
        header.put("Authorization", "Bearer " + auth0_token);
        Response res = getResponse(header,auth0GetRulesUrl);
        assertStatusCode(res,200);
        String oldBody = res.body().asPrettyString();
//        String newBody = oldBody;
        int firstCurl = oldBody.indexOf("{");
        int lastCurl = oldBody.lastIndexOf("}");
        oldBody = oldBody.substring(firstCurl,lastCurl+1);
        String [] bodyLines = oldBody.split(",");
        bodyLines = ArrayUtils.removeElement(bodyLines, bodyLines[0]);
        bodyLines = ArrayUtils.removeElement(bodyLines, bodyLines[bodyLines.length-1]);
        oldBody = "{\t" +  String.join(",",bodyLines) + "\t}";
        String newBody = oldBody;
        String rules = getValueFromResponseBody(res,"script");
        String oldRules = rules;
        String ruleId = getValueFromResponseBody(res,"id");
        ruleId = ruleId.replaceAll("[\\[\\]]", "");
//        ruleId = ruleId.replaceAll("rul_", "");
        header.put("Content-Type","application/json; charset=utf-8");
        auth0UpdateRuleUrl = String.format(auth0UpdateRuleUrl,auth0DomainName,ruleId);

        int varConfigurationStart = rules.indexOf("var configuration");
        String varConfiguration = rules.substring(varConfigurationStart,rules.indexOf(";",varConfigurationStart));
        String [] varConfigurations = varConfiguration.split("\n");
        String [] varConfClientIds = varConfigurations[1].split("\"");
//        varConfClientIds[3] = map.get("envClientId");
        String oldClientId = varConfClientIds[3];
        newBody = newBody.replace(oldClientId,map.get("envClientId"));

        String [] varConfClientSecrets = varConfigurations[2].split("\"");
        String oldSecret  = varConfClientSecrets[3];
        newBody = newBody.replace(oldSecret,map.get("secret"));

//        varConfClientSecrets[3] = map.get("secret");
        String [] varConfWorkspaces = varConfigurations[3].split("\"");
        String oldWsId = varConfWorkspaces[3];
        newBody = newBody.replace(oldWsId,map.get("workspaceId"));
//
////        varConfWorkspaces[3] = map.get("workspaceId");
//        String varConfClientId = String.join("\"",varConfClientIds);
//        String varConfClientSecret = String.join("\"",varConfClientSecrets);
//        String varConfWorkspace = String.join("\"",varConfWorkspaces) + "\"";
//        String newVarConfig = String.join("\n",varConfigurations[0],varConfClientId,varConfClientSecret,varConfWorkspace,varConfigurations[4]);
//        rules = rules.replace(varConfiguration,newVarConfig);

        int urlStart = rules.indexOf("url");
        String url = rules.substring(urlStart,rules.indexOf(",",urlStart));
        String [] urls = url.split("'");
        urls[2] = hookUrl;
        String newUrl = String.join("'",urls) + "'";
//        rules = rules.replace(url,newUrl);
        newBody = newBody.replace(url,newUrl);

        int xPlainIdClientStart = rules.indexOf("x-plainid-client");
        String xPlainIdClient = rules.substring(xPlainIdClientStart,rules.indexOf(",",xPlainIdClientStart));
        String [] xPlainIdClients = xPlainIdClient.split("'");
        xPlainIdClients[2] = map.get("envClientId");
        String newXPlainIdClient = String.join("'",xPlainIdClients) + "'";
//        rules = rules.replace(xPlainIdClient,newXPlainIdClient);
        newBody = newBody.replace(xPlainIdClient,newXPlainIdClient);

        int xPlainIdWorkspaceStart = rules.indexOf("x-plainid-workspace'");
        String xPlainIdWorkspace = rules.substring(xPlainIdWorkspaceStart,rules.indexOf(",",xPlainIdWorkspaceStart));
        String [] xPlainIdWorkspaces = xPlainIdWorkspace.split("'");
        xPlainIdWorkspaces[2] = map.get("workspaceId");
        String newXPlainIdWorkspace = String.join("'",xPlainIdWorkspaces) + "'";
//        rules = rules.replace(xPlainIdWorkspace,newXPlainIdWorkspace);
        newBody = newBody.replace(xPlainIdWorkspace,newXPlainIdWorkspace);

        int xPlainIdSecretStart = rules.indexOf("x-plainid-secret");
        String xPlainIdSecret = rules.substring(xPlainIdSecretStart,rules.indexOf(",",xPlainIdSecretStart));
        String [] xPlainIdSecrets = xPlainIdSecret.split("'");
        xPlainIdSecrets[2] = map.get("secret");
        String newXPlainIdSecret = String.join("'",xPlainIdSecrets) + "'";
//        rules = rules.replace(xPlainIdSecret,newXPlainIdSecret);
        newBody = newBody.replace(xPlainIdSecret,newXPlainIdSecret);

//        String newBody = oldBody.replace(oldRules,rules);
//        JSONParser parser = new JSONParser();
//        JSONObject json = null;
//        try {
//            json = (JSONObject) parser.parse(newBody);
//        }catch (Throwable t){
//            t.printStackTrace();
//        }


        Response updateRes = patch(header,newBody,auth0UpdateRuleUrl);
        assertStatusCode(updateRes,200);

        header = new HashMap<>();
        header.put("Authorization", "Bearer " + auth0_token);
        res = getResponse(header,auth0GetRulesUrl);
    }

    // Create registration request
    public String registrationRequest(){
        String name = "";
        if(application.contains("partner")) {
            name = fillRequestAttributes();
        }
        String orgName = completeRegistration(IDP);
        if(!name.isEmpty()){
            return name;
        }
        return orgName;
    }

    public String registrationRequest(String partnerName){

        fillRequestAttributes(partnerName);
        completeRegistration(IDP);
        return partnerName;
    }

    public String registrationRequestPMMissAttribute(String attName){

        String name = fillRequestAttributes();
        removeAttribute(attName);
        completeRegistration(IDP);
        return name;
    }

    public String getPlainIdEnvRedirectURI(String envId){
        oktaGetPlainIdEnvRedirectURI = String.format(oktaGetPlainIdEnvRedirectURI,baseUrl,envId);
        header = fillGetRedirectURIHeader();
        Response response = getResponse(header, oktaGetPlainIdEnvRedirectURI);
        String data = response.jsonPath().get("data");
        return data;
    }

    public Response updateOktaRedirectionURIs(String envRedirectURI){
        header = fillOktaHeaders();
        Response response = getResponse(header, oktaRedirectURI);

        Map<String, Object> responseMap =  response.getBody().as(Map.class);
        ((List<String>)((Map<String, Object>)((Map<String, Object>)responseMap.get("settings")).get("oauthClient")).get("redirect_uris")).add(envRedirectURI);
        Response putResponse = put(header,responseMap,oktaRedirectURI);
        assertStatusCode(putResponse, 200);
        return putResponse;
    }

    public Response updateOktaInlineHook(Map<String,String> map){
        header = fillOktaHeaders();
        Response getResponse = getResponse(header,oktaInlineHookURI);
        Map<String,String> client = new LinkedTreeMap<>();
        client.put("key","x-plainid-client");
        client.put("value",map.get("envClientId"));
        Map<String,String> ws = new LinkedTreeMap<>();
        ws.put("key","x-plainid-workspace");
        ws.put("value",map.get("workspaceId"));
        List<Map<String,String>> headers = new ArrayList<>();
        headers.add(client);
        headers.add(ws);
        Map<String, Object> responseMap =  getResponse.getBody().as(Map.class);
        ((Map<String, Object>)((Map<String, Object>)responseMap.get("channel")).get("config")).put("headers",headers);
        ((Map<String, Object>)((Map<String, Object>)responseMap.get("channel")).get("config")).put("uri",hookUrl);
        ((Map<String, String>)((Map<String, Object>)((Map<String, Object>)responseMap.get("channel")).get("config")).get("authScheme")).put("value",map.get("secret"));

        Response putResponse = put(header, responseMap, oktaInlineHookURI);
        assertStatusCode(putResponse, 200);
        return putResponse;
    }


    public Map<String,String> fillGetRedirectURIHeader(){
        header = new HashMap<>();
        String authority = "api.%s.%s";
        String origin = "https://%s.%s.%s/";
        origin = String.format(origin,tenantName,envDatacenter,envDomain);
        authority = String.format(authority,envDatacenter,envDomain);
        header.put("authority",authority);
        header.put("sec-ch-ua","\"Google Chrome\";v=\"89\", \"Chromium\";v=\"89\", \";Not A Brand\";v=\"99\"");
        header.put("authorization","Bearer " + access_token);
        header.put("sec-ch-ua-mobile", "?0");
        header.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36");
        header.put("content-type","application/json");
        header.put("accept","*/*");
        header.put("origin",origin);
        header.put("sec-fetch-site","same-site");
        header.put("sec-fetch-mode","cors");
        header.put("sec-fetch-dest","empty");
        header.put("referer",origin);
        return header;
    }

    public Map<String,String> fillOktaHeaders(){
        header = new HashMap<>();
        header.put("Accept","application/json");
        header.put("Content-Type","application/json");
        header.put("Authorization","SSWS " + oktaApiKey);
        return header;
    }

    public Map<String,String> fillOktaInlineHookHeader(){
        header = new HashMap<>();
        header.put("Authorization","SSWS " + oktaApiKey);
        return header;
    }

    public String getOwnerId(String envId){
        header = new HashMap<>();
        header.put("Authorization", "Bearer " + access_token);
        partnerWsIdUrl = String.format(partnerWsIdUrl, baseUrl, envId);
        Response response = getResponse(header,partnerWsIdUrl);
        assertStatusCode(response, 200);
        String ownerId = getValueFromResponseBody(response,"id");
        return  ownerId;
    }

    public void setEnvParams(String wsId){
        header = new HashMap<>();
        header.put("Authorization", "Bearer " + access_token);
        schemaOwnerUrl = String.format(schemaOwnerUrl, baseUrl, wsId);
        Response response = getResponse(header,schemaOwnerUrl);
        assertStatusCode(response, 200);
        ownerId = getValueFromResponseBody(response,"data.ownerId");
        schemaId = getValueFromResponseBody(response,"data.schemaId");
    }

    public void callHook(Map<String, String> map, String options) {
        header = fillOktaHookHeader(map);
        String body = "";
        if(IDP.toLowerCase().contains("okta")) {
            body = formatOktaHookBody(oktaHookBody, map);
        }else if(IDP.toLowerCase().contains("auth")) {
            body = formatAuth0HookBody(auth0HookBody, map);
        }

        Response response = getPostResponse(header,body, hookUrl);

        if(options.length()<1) {
            assertStatusCode(response, 200);
            if(IDP.toLowerCase().contains("okta")) {
                Assert.assertTrue(validateParameterValueInResponseBodyHook(response, hookResponseEntitlementKey, map.get("responseKey"), true));
                Assert.assertTrue(validateParameterValueInResponseBodyHook(response, hookResponseEntitlementValue, map.get("responseValue"), false));
            }else if(IDP.toLowerCase().contains("auth")) {
                validateParameterValueInResponseBody(response,map.get("responseKey"),map.get("responseValue"));
            }
        }else if(options.contains("no roles")) {
            assertStatusCode(response, 200);
            validateParameterValueInResponseBodyEquals(response, errorSummary, noAccessTokenIsEmpty);
            validateParameterValueInResponseBodyEquals(response, hookResponseValue, null);
        }else if(options.contains("no member")) {
            assertStatusCode(response, 400,550);
            validateParameterValueInResponseBody(response, errorSummary, failedToGetToken);
        } else if(options.contains("invalid clientId")){
            assertStatusCode(response, 400, 550);
            validateParameterValueInResponseBody(response, errorSummary, failedToGetToken);
            validateParameterValueInResponseBody(response, errorSummary, invalidClientId);
        }else if(options.contains("invalid secret")){
            assertStatusCode(response, 400, 550);
            validateParameterValueInResponseBody(response, errorSummary, failedToGetToken);
            validateParameterValueInResponseBody(response, errorSummary, invalidSecret);
        }else if(options.contains("member details")){
            assertStatusCode(response, 200);
        }else if(options.contains("delegated_admin")){
            assertStatusCode(response, 200);
            if(IDP.toLowerCase().contains("okta")){
                Assert.assertTrue(validateParameterValueInResponseBodyHook(response,hookResponseEntitlementKey,plainid_internal,true));
                Assert.assertTrue(validateParameterValueInResponseBodyHook(response,hookResponseEntitlementValue,delegated_admin,false));
            }else if(IDP.toLowerCase().contains("auth")){
 //               Assert.assertTrue(validateParameterValueInResponseBodyHook(response,".",plainid_internal,true));
                validateParameterValueInResponseBody(response,plainid_internal,delegated_admin);
            }

        }
    }

    public void callRT(Map<String, String> map, String options){

        Response response;
        if (application.equalsIgnoreCase("gigya")) {
            fillGigyaRTBody(map);
            response = getPostResponse(Map.of("Authorization", "Bearer " + gigyaRTJwt), body, RTUrl);
        } else {
            fillRTBody(map);
            response = getPostResponse(body,RTUrl);
        }

        if(options.length()<1) {
            assertStatusCode(response, 200);
            if (application.equalsIgnoreCase("gigya")) {
                Assert.fail(); // Add validations here
            }
            else {
                validateParameterValueInResponseBody(response, RTResponseRoleName, map.get("roleName"));
                validateParameterValueInResponseBody(response, RTResponseEntitlementResponseKey, map.get("responseKey"));
                validateParameterValueInResponseBody(response, RTResponseEntitlementResponseValue, map.get("responseValue"));
            }
        }else if(options.contains("no roles")){
            assertStatusCode(response, 200);
            if (application.equalsIgnoreCase("gigya")) {
                Assert.fail(); // Add validations here
            }
            else {
                validateParameterValueInResponseBodyEquals(response, RTResponse, emptyList);
                validateParameterValueInResponseBodyEquals(response, RTRolesAssociated, emptyList);
            }
        } else if(options.contains("no member")){
            assertStatusCode(response, 400, 500);
            if (application.equalsIgnoreCase("gigya")) {
                Assert.fail(); // Add validations here
            }
            else {
                validateResponseError(response, memberNotFoundName);
            }
        }else if(options.contains("member details")){
            assertStatusCode(response, 200);
            if (application.equalsIgnoreCase("gigya")) {
                Assert.fail(); // Add validations here
            }
            else {
                validateParameterValueInResponseBody(response, RTResponseRoleName, map.get("roleName"));
                validateParameterValueInResponseBody(response, RTResponseMemberJobFunction, map.get("jobFunction"));
                validateParameterValueInResponseBody(response, RTResponseMemberDepartment, map.get("department"));
            }
        } else if(options.contains("invalid clientId")){
            assertStatusCode(response, 400, 500);
            if (application.equalsIgnoreCase("gigya")) {
                Assert.fail(); // Add validations here
            }
            else {
                validateResponseError(response, invalidClientId);
            }
        } else if(options.contains("invalid secret")) {
            assertStatusCode(response, 400, 500);
            if (application.equalsIgnoreCase("gigya")) {
                Assert.fail(); // Add validations here
            }
            else {
                validateResponseError(response, invalidSecret);
            }
        } else if(options.contains("other workspaceId")) {
            assertStatusCode(response, 200);
            if (application.equalsIgnoreCase("gigya")) {
                Assert.fail(); // Add validations here
            }
            else {
                validateParameterValueInResponseBodyEquals(response, RTResponse, emptyList);
            }
        }else if(options.contains("suspended partner")) {
            assertStatusCode(response, 400, 500);
            if (application.equalsIgnoreCase("gigya")) {
                Assert.fail(); // Add validations here
            }
            else {
                validateResponseError(response, partnerNotActiveError);
            }
        } else if(options.contains("excludeIdentity")) {
            body.put("includeIdentity", false);
            response = getPostResponse(body,RTUrl);
            assertStatusCode(response, 200);
            validateResponseMissing(response,identity);
        }else if(options.contains("excludePermissions")) {
            body.put("includeAccessPolicy", false);
            response = getPostResponse(body,RTUrl);
            assertStatusCode(response, 200);
            validateResponseMissing(response,permission);
        }else if(options.contains("delegatedAdmin")){
            validateParameterValueInResponseBody(response, RTResponseRoleName, delegatedAdmin);
            validateParameterValueInResponseBody(response, RTResponseMemberJobFunction, map.get("jobFunction"));
            validateParameterValueInResponseBody(response, RTResponseMemberDepartment, map.get("department"));
        }
    }

    public void callRT(Map<String, String> map){
        callRT(map,"");
    }

    public String completeRegistration(String IDP){
        String name = fillRequestBody(IDP);
        fillRequestHeader();
        Response response = getPostResponse(header,body,registrationUrl);
        assertStatusCode(response,200);
        return name;
    }

    public void fillGigyaFields(){
        fields = new HashMap<>();
        fields.put("street_address", List.of(getRandomStreet() + " " + randomNumberInRange(1, 200)));
        fields.put("city",List.of(getRandomCity()));
        fields.put("state",List.of(getRandomState()));
        fields.put("zip_code", List.of(randomNumberGenerator(7)));
        fields.put("country", List.of(getRandomCountry()));
        fields.put("number", List.of(Integer.parseInt(randomNumberGenerator(6))));
    }

    public String fillRequestAttributes(){
        String name = randomStringGenerator(12);
        attributes = new HashMap<>();
        attributes.put("name", List.of(name));
        attributes.put("description", List.of(randomStringGenerator(10)));
        attributes.put("member_limit", List.of(randomNumberInRange(100)));
        attributes.put("status", List.of("DRAFT"));
        attributes.put("type", List.of(getRandomTypeAllUpper()));
        attributes.put("city", List.of(getRandomCity()));
        attributes.put("country", List.of(getRandomCountry()));
        attributes.put("state", List.of(getRandomState()));
        attributes.put("street_address", List.of(getRandomStreet() + " " + randomNumberInRange(1, 200)));
        attributes.put("zip_code", List.of(randomNumberGenerator(7)));
        attributes.put("external_id", List.of(randomNumberGenerator(10)));
        return name;
    }

    public void fillRequestAttributes( String partnerName){
        attributes = new HashMap<>();
        attributes.put("name", List.of(partnerName));
        attributes.put("description", List.of(randomStringGenerator(10)));
        attributes.put("member_limit",List.of(randomNumberInRange(100)));
        attributes.put("status",List.of("DRAFT"));
        attributes.put("type", List.of(getRandomTypeAllUpper()));
        attributes.put("city", List.of(getRandomCity()));
        attributes.put("country",List.of(getRandomCountry()));
        attributes.put("state",List.of(getRandomState()));
        attributes.put("street_address",List.of(getRandomStreet() + " " + randomNumberInRange(1,200)));
        attributes.put("zip_code",List.of(randomNumberGenerator(7)));
        attributes.put("external_id",List.of(randomNumberGenerator(10)));
    }

    public void removeAttribute(String attName){
        for(Map.Entry<String, Object> entry : attributes.entrySet()){
            if(entry.getKey().contains(attName.toLowerCase())){
                attributes.put(entry.getKey(),List.of(" "));
            }
        }
    }

    public String fillRequestBody(String IDP){
        if(application.contains("partner")) {
            fillRequester(IDP);
            body = new HashMap<>();
            body.put("active", true);
            body.put("source", "SELF_REGISTRATION");
            body.put("ownerId", ownerId);
            body.put("schemaId", schemaId);
            body.put("attributes", attributes);
            body.put("requester", requester);
            return "";
        }else {
            fillRequester(IDP);
            fillGigyaFields();
            body = new HashMap<>();
            String name = randomStringGenerator(12);
            body.put("name", name);
            body.put("externalId",randomStringGenerator(12));
            body.put("membersLimit",randomNumberInRange(10,1000));
            body.put("source", "SELF_REGISTRATION");
            body.put("description", randomStringGenerator(10));
            body.put("type", getRandomTypeAllUpper());
            body.put("fields",fields);
            body.put("requesterAttributes",requester);
            return name;
        }
    }

    public void fillRequester(String IDP){
        if(application.contains("partner")) {
            if (IDP.toLowerCase().contains("okta")) {
                requester = new HashMap<>();
                requester.put("department", List.of("QA"));
                requester.put("jobFunction", List.of("Automation Developer"));
                requester.put("firstName", List.of(randomStringGenerator(10)));
                requester.put("lastName", List.of(randomStringGenerator(10)));
                requester.put("login", List.of(randomStringGenerator(10)));
                requester.put("email", List.of(randomEmailGenerator()));
            } else if (IDP.toLowerCase().contains("auth0")) {
                requester = new HashMap<>();
                requester.put("family_name", List.of(randomStringGenerator(10)));
                requester.put("given_name", List.of(randomStringGenerator(10)));
                requester.put("email", List.of(randomEmailGenerator()));
                requester.put("department", List.of(randomStringGenerator(10)));
                requester.put("jobFunction", List.of(randomStringGenerator(10)));
                requester.put("password", List.of(passWord));
            }
        }else {
            requester = new HashMap<>();
            requester.put("lastName", List.of(randomStringGenerator(10)));
            requester.put("firstName", List.of(randomStringGenerator(10)));
            requester.put("department", List.of("QA"));
            requester.put("jobFunction", List.of("Automation Developer"));
            requester.put("email", List.of(randomEmailGenerator()));
        }
    }

    public void fillRequestHeader(){
        header = new HashMap<>();
        header.put("X-Tenant", tenantName);
        header.put("Authorization", "Bearer " + access_token);
        header.put("Content-Type","application/json");
        if(application.contains("partner")) {
            header.put("JSESSIONID", "NgYFvHJF3_BJYXs9HHzIkVG5RvvVT5S_eaQEn2w4.ip-10-100-29-170; header_csrf_token=C2nGnAHTZ0GAGDxWRbqzP1V79kyDPzZ6");
            header.put("header_csrf_token", "IwVslSt1RcxjSyZxSWxFEeOQsssAqw5M");
            header.put("Cookie", "JSESSIONID=4628F865E8F3F5C3E952F2BC2DBD4689");
        }
    }

    public void fillRTBody(Map<String,String> map){
        body = new HashMap<>();
        body.put("entityId", map.get("email"));
        body.put("clientId", map.get("envClientId"));
        body.put("clientSecret", map.get("secret"));
        body.put("includeAccessPolicy", true);
        body.put("entityTypeId", map.get("workspaceId"));
        body.put("includeIdentity", true);

        attributes = new HashMap<>();
        attributes.put("partner_id", List.of(map.get("partnerId")));

        body.put("contextData",attributes);
    }

    public void fillGigyaRTBody(Map<String,String> map){
        body = new HashMap<>();

        Map<String,Object> identity = new HashMap<>();
        getJWT();
        String memberSearchUrl = "https://api.us1.b2b-gigya.net/delegated-admin/identities/search?limit=30&offset=0&name=" + map.get("email");
        String memberId = getValueFromResponseBody(getResponse(Map.of("partner-id", partnerId, "Authorization","Bearer " + access_token), memberSearchUrl), "data[0].id");
        identity.put("id", memberId);

        Map<String,Object> context = new HashMap<>();
        context.put("organization", map.get("partnerId"));

        Map<String,Object> response = new HashMap<>();
        response.put("include_identity", true);
        response.put("include_reasons", true);

        body.put("identity",identity);
        body.put("context",context);
        body.put("response",response);
    }

    public Map<String,String> getRTData(){
        map = new HashMap<>();
        map.put("envClientId",envClientId);
        map.put("secret",secret);
        map.put("workspaceId",workspaceId);
        map.put("partnerId",partnerId);
        map.put("email",email);
        return map;
    }

    public Map<String,String> fillOktaHookHeader(Map<String,String> map){
        header = new HashMap<>();
        header.put("Content-Type","application/json");
        header.put("x-plainid-client",map.get("envClientId"));
        header.put("x-plainid-workspace",map.get("workspaceId"));
        header.put("x-plainid-secret",map.get("secret"));
        header.put("host",host);
        return header;
    }

    public String formatOktaHookBody(String template, Map<String, String> map) {
        String login = map.get("email");
        String partnerId = map.get("partnerId");
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(template, JsonElement.class);
        JsonObject profile = jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("context").getAsJsonObject().get("user").getAsJsonObject().get("profile").getAsJsonObject();
        profile.remove("login");
        profile.addProperty("login", login);
        profile.remove("pid_partner_id");
        profile.addProperty("pid_partner_id",partnerId);

        String result =  gson.toJson(jsonElement);
        return result;
    }

    public String formatAuth0HookBody(String template, Map<String, String> map) {
        String email = map.get("email");
        String partnerId = map.get("partnerId");
        String clientId = map.get("envClientId");
        String clientSecret = map.get("secret");
        String workspaceId = map.get("workspaceId");
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(template, JsonElement.class);

        JsonObject user = jsonElement.getAsJsonObject().get("user").getAsJsonObject();
        user.remove("email");
        user.addProperty("email", email);

        JsonObject user_metadata = jsonElement.getAsJsonObject().get("user").getAsJsonObject().get("user_metadata").getAsJsonObject();
        user_metadata.remove("pid_partner_id");
        JsonArray pidPartnerId = new JsonArray();
        pidPartnerId.add(partnerId);
        user_metadata.add("pid_partner_id", pidPartnerId);

        JsonObject config = jsonElement.getAsJsonObject().get("config").getAsJsonObject();
        config.remove("PLAINID_CLIENT_ID");
        config.addProperty("PLAINID_CLIENT_ID", clientId);
        config.remove("PLAINID_CLIENT_SECRET");
        config.addProperty("PLAINID_CLIENT_SECRET", clientSecret);
        config.remove("x-plainid-workspace");
        config.addProperty("x-plainid-workspace", workspaceId);



        String result =  gson.toJson(jsonElement);
        return result;
    }

    public void resetGigyaMemberPassword(String loginId, String tempPassword, String newPassword) {
        String regToken = getValueFromResponseBody(getResponse(Collections.emptyMap(), "https://accounts." + envDatacenter + ".gigya.com/accounts.login?loginID=" + loginId + "&password=" + tempPassword + "&ApiKey=" + envApiKey), "regToken");
        getResponse(Collections.emptyMap(), "https://accounts." + envDatacenter + ".gigya.com/accounts.setAccountInfo?regToken=" + regToken + "&APIKey=" + envApiKey + "&format=json&password=" + tempPassword + "&newPassword=" + newPassword);
        getResponse(Collections.emptyMap(), "https://accounts." + envDatacenter + ".gigya.com/accounts.finalizeRegistration?regToken=" + regToken + "&APIKey=" + envApiKey + "&format=json");
    }

    public void activateGigyaMember(String email) {
        getJWT();
        String memberSearchUrl = "https://api." + envDatacenter + ".b2b-gigya.net/delegated-admin/identities/search?limit=30&offset=0&name=" + email;
        String memberId = getValueFromResponseBody(getResponse(Map.of("partner-id", partnerId, "Authorization", "Bearer " + access_token), memberSearchUrl), "data[0].id");
        getResponse(Map.of("Authorization", "Bearer " + access_token), "https://api." + envDatacenter + ".b2b-gigya.net/delegated-admin/identities/" + memberId + "/status/active");
    }
}