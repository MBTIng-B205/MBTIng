package com.ssafy.mbting.db.initData;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDataLoadRunner implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private @Value("${com.mbting.ddl_auto}") String ddlAuto;
    private final FriendInitData friendInitData;
    private final MessageInitData messageInitData;
    private final AppMemberInitData appMemberInitData;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if ("create".equalsIgnoreCase(ddlAuto)) {
            logger.info("\n\n초기 데이터 로딩을 시작합니다.\n");
            messageInitData.create();
            friendInitData.create();
            appMemberInitData.create();
            logger.info("\n\n초기 데이터 로딩을 마쳤습니다.\n");
        } else {
            logger.info("\n\nddl_auto 속성이 create이 아닙니다. ddl_auto: {}\n", ddlAuto);
            logger.info("\n\n초기 데이터 로딩을 건너뜁니다.\n");
        }
    }
}
