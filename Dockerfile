FROM markhobson/maven-chrome:jdk-11

# Defaults. Set Docker/K8s env vars as necessary.
ENV TEST_SUITE=chromeSanity

#-e environment = 'Dev'
#-e IDP = 'Auth0'

COPY src /app/src
COPY pom.xml /app
COPY chromeRegression.xml /app
COPY chromeSanity.xml /app
COPY fireFoxRegression.xml /app
COPY selected.xml /app
COPY testng.xml /app
COPY run_automation.sh /app

WORKDIR /app

# Pre-packaged dependencies; saves time during debugging.
RUN  chmod u+x /app/run_automation.sh \
 && mkdir -p /root/.m2/repository \
 && mkdir -p /root/Downloads \
 && wget -q https://plainid-test-public.s3.amazonaws.com/automation_mvn_repos.tar.gz -P /root/.m2/repository/ \
 && tar -xzf /root/.m2/repository/automation_mvn_repos.tar.gz -C /root/.m2/repository/

ENTRYPOINT ["/app/run_automation.sh"]
