package com.graduate.odondong.util.operationTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

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

        if (operation == null) {
            return "unknown";
        }
        if (operation.equals("24시간")) {
            return "Y";
        }

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
            if (nowDateTime.before(startDateTime) || nowDateTime.after(endDateTime)) {
                return "N";
            }
            return "Y";
        }

        return "unknown";
    }
}
