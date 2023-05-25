package com.graduate.odondong.util.operationTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OperationTimeValidation {

    public static String checkBathroomOpen(String operation){
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");
        LocalDateTime dateTime = LocalDateTime.now();
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime now = ZonedDateTime.of(dateTime, koreaZone);


        int dayOfWeekValue = now.getDayOfWeek().getValue();
        String nowTime = now.getHour() + ":" + now.getMinute();
        Date nowDateTime = null;

        try {
            nowDateTime = inputFormat.parse(nowTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (operation == null) {
            return "U";
        }
        if (operation.equals("24시간")) {
            return "Y";
        }
        log.error("현재시간 : " + now);
        log.error("현재시간 : " + nowDateTime);

        String pattern = "^\\d{2}:\\d{2}~\\d{2}:\\d{2}$";
        if (Pattern.matches(pattern, operation)) {
            String[] operationTime = operation.split("~");
            Date startDateTime = null;
            Date endDateTime = null;
            try {
                startDateTime = inputFormat.parse(operationTime[0]);
                endDateTime = inputFormat.parse(operationTime[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            log.error("화장실시간 : " + startDateTime);
            log.error("화장실시간 : " + endDateTime);

            log.error("화장실시간 : " + nowDateTime.before(startDateTime));
            log.error("화장실시간 : " + nowDateTime.after(endDateTime));

            if (nowDateTime.before(startDateTime) || nowDateTime.after(endDateTime)) {
                return "N";
            }
            return "Y";
        }

        return "U";
    }
}
