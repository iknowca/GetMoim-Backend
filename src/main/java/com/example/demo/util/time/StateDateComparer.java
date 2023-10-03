package com.example.demo.util.time;

import java.time.LocalDateTime;

public class StateDateComparer {
    public static boolean isSameDay(LocalDateTime now, LocalDateTime targetDate) {
        if(now.getMonth()==targetDate.getMonth()) {
            if (now.getDayOfMonth()==targetDate.getDayOfMonth()){
                return true;
            }
        }
        return false;
    }
}
