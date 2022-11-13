package com.graduate.odondong.util.operationTime;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class OperationTimeValidation {

    public String checkBathroomOpen(String operation){
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        int dayOfWeekValue = now.getDayOfWeek().getValue();
        String nowTime = now.getHour() + ":" + now.getMinute();
        Date nowDateTime = null;

        try {
            nowDateTime = inputFormat.parse(nowTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (operation.equals("24시간")) {
            return "Y";
        }
        if (operation.contains("평일")) {
            if (dayOfWeekValue == 6 || dayOfWeekValue == 7) {
                return "N";
            }
            return "Y";
        }

        if (operation.length() == 11) {
            String[] operationTime = operation.split("~");
            Date startDateTime = null;
            Date endDateTime = null;
            try {
                startDateTime = inputFormat.parse(operationTime[0]);
                endDateTime = inputFormat.parse(operationTime[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (nowDateTime.before(startDateTime) || nowDateTime.after(endDateTime)) {
                return "N";
            }
            return "Y";
        }

        return "Y";
    }
}
