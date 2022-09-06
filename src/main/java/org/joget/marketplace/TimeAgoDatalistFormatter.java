package org.joget.marketplace;

import org.joget.apps.app.model.AppDefinition;
import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.app.service.AppUtil;
import org.joget.apps.datalist.model.DataList;
import org.joget.apps.datalist.model.DataListColumn;
import org.joget.apps.datalist.model.DataListColumnFormatDefault;
import java.time.LocalDate;
import java.time.Period;
import org.joget.apps.datalist.service.DataListService;

public class TimeAgoDatalistFormatter extends DataListColumnFormatDefault{

   private final static String MESSAGE_PATH = "messages/TimeAgoDatalistFormatter";

    public String getName() {
        return "Time Ago Datalist Formatter";
    }

    public String getVersion() {
        return "7.0.0";
    }

    public String getDescription() {
        //support i18n
        return AppPluginUtil.getMessage("org.joget.marketplace.TimeAgoDatalistFormatter.pluginDesc", getClassName(), MESSAGE_PATH);
    }

    @Override
    public String format(DataList dataList, DataListColumn column, Object row, Object value) {
        
        AppDefinition appDef = AppUtil.getCurrentAppDefinition();
        String result = (String) value;
        
        String duration = getPropertyString("duration");
        Period dateDiff;
        
        if (duration.equals("today")) {
            
            LocalDate date = LocalDate.parse(result); //Date
            LocalDate currentDate = LocalDate.now(); //Current Date
            
            //Duration From Column Date To Today
            try {
                
                dateDiff = Period.between(currentDate, date);
                                
                if (Math.abs(dateDiff.getYears()) > 0) {
                    return Math.abs(dateDiff.getYears()) + " year(s) " + Math.abs(dateDiff.getMonths()) + 
                            " month(s) " + Math.abs(dateDiff.getDays()) + " day(s)";
                } else if (Math.abs(dateDiff.getMonths()) > 0) {
                    return Math.abs(dateDiff.getMonths()) + " month(s) " + Math.abs(dateDiff.getDays()) + " day(s)";
                } else {
                    return Math.abs(dateDiff.getDays()) + " day(s)";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if (duration.equals("anotherDate")) {
            
            LocalDate date = LocalDate.parse(result); //Date
            
            String targetDate = getPropertyString("targetDate");
            String anotherDateField = (String) DataListService.evaluateColumnValueFromRow(row, targetDate);
            LocalDate anotherDate = LocalDate.parse(anotherDateField); //Another Date
            
            //From Column Date To Another Date
            try {
                
                dateDiff = Period.between(anotherDate, date);
                                
                if (Math.abs(dateDiff.getYears()) > 0) {
                    return Math.abs(dateDiff.getYears()) + " year(s) " + Math.abs(dateDiff.getMonths()) + 
                            " month(s) " + Math.abs(dateDiff.getDays()) + " day(s)";
                } else if (Math.abs(dateDiff.getMonths()) > 0) {
                    return Math.abs(dateDiff.getMonths()) + " month(s) " + Math.abs(dateDiff.getDays()) + " day(s)";
                } else {
                    return Math.abs(dateDiff.getDays()) + " day(s)";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if (duration.equals("twoDates")) {
            
            String targetFromDate = getPropertyString("fromDate");
            String fromDateField = (String) DataListService.evaluateColumnValueFromRow(row, targetFromDate);
            LocalDate fromDate = LocalDate.parse(fromDateField); //From Date
            
            String targetToDate = getPropertyString("toDate");
            String toDateField = (String) DataListService.evaluateColumnValueFromRow(row, targetToDate);
            LocalDate toDate = LocalDate.parse(toDateField); //To Date
            
            //Duration Between Two Dates
            try {
                
                dateDiff = Period.between(fromDate, toDate);
                                
                if (Math.abs(dateDiff.getYears()) > 0) {
                    return Math.abs(dateDiff.getYears()) + " year(s) " + Math.abs(dateDiff.getMonths()) + 
                            " month(s) " + Math.abs(dateDiff.getDays()) + " day(s)";
                } else if (Math.abs(dateDiff.getMonths()) > 0) {
                    return Math.abs(dateDiff.getMonths()) + " month(s) " + Math.abs(dateDiff.getDays()) + " day(s)";
                } else {
                    return Math.abs(dateDiff.getDays()) + " day(s)";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
        return result;
    }
    

    public String getLabel() {
        //support i18n
        return AppPluginUtil.getMessage("org.joget.marketplace.TimeAgoDatalistFormatter.pluginLabel", getClassName(), MESSAGE_PATH);
    }

    public String getClassName() {
        return getClass().getName();
    }

    public String getPropertyOptions() {
        return AppUtil.readPluginResource(getClassName(), "/properties/TimeAgoDatalistFormatter.json", null, true, MESSAGE_PATH);
    }
}
