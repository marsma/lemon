package com.mossle.bpm.calendar;

import java.text.ParseException;

import java.util.Date;

import javax.xml.datatype.Duration;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.calendar.CronExpression;
import org.activiti.engine.impl.calendar.DurationHelper;
import org.activiti.engine.impl.util.ClockUtil;

public class CycleBusinessCalendar extends AdvancedBusinessCalendar {
    public Date resolveDuedate(String duedate) {
        String textWithoutBusiness = duedate;
        boolean isBusinessTime = textWithoutBusiness.startsWith("business");

        if (isBusinessTime) {
            textWithoutBusiness = textWithoutBusiness.substring(
                    "business".length()).trim();
        }

        try {
            if (textWithoutBusiness.startsWith("R")) {
                return new DurationUtil(duedate, this).getDateAfter();
            } else {
                CronExpression ce = new CronExpression(duedate);

                return ce.getTimeAfter(ClockUtil.getCurrentTime());
            }
        } catch (Exception e) {
            throw new ActivitiException("Failed to parse cron expression: "
                    + duedate, e);
        }
    }

    public String getName() {
        return "cycle";
    }
}
