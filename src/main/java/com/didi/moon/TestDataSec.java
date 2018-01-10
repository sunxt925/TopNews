package com.didi.moon;

import com.didichuxing.sec.data.dp.util.DPUtils;
import com.didichuxing.sec.data.dp.util.Rule;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by lyorall on 2018/1/4.
 */
public class TestDataSec {
/*
       Map<String, Object> paramsToCustom = Maps.newHashMap();
        paramsToCustom.put("regex","(\\d{3})(\\d+)(\\d{3})");
        paramsToCustom.put("pattern","items[1]+mask(items[2],'*')+items[3]");
        Map resultMap = DPUtils.replace("130928198809277313", Rule.ID_CARD, paramsToCustom);*
        System.out.println(resultMap);*/

    public static void main(String[] args) throws SQLException {

        com.didi.moon.DBUtil.getConnection();
        TestDataSec testCase = new TestDataSec();
        //testCase.hideInfo("sys_user_role_copy", new String[]{"user_id", "role_id", "role_name"}, new Rule[]{Rule.MD5, Rule.NORMAL, Rule.NAME});
        testCase.hideInfo("customer_copy", new String[]{"Customer_Name", "Customer_Tel", "Customer_Address"}, new Rule[]{Rule.NAME, Rule.MOBILE_NUMBER, Rule.ADDRESS});
        com.didi.moon.DBUtil.DBclose();
    }

    public boolean hideInfo(String tableName, String[] column, Rule[] ruleIds) throws SQLException {
        //查出该表的主键
       /* List<String> pkList = Lists.newArrayList();
        String selectPKSQL = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME='"
                + tableName + "' ";
        ResultSet rs_pk = DBUtil.excuteQuery(selectPKSQL);
        while (rs_pk.next()) {
            pkList.add(rs_pk.getString("COLUMN_NAME"));
        }

        //查询全量数据的指定列（加主键）
        String selectSQL = "select "; //手动添加各个主键
        StringBuilder sb = new StringBuilder(selectSQL);
        for(String s : pkList) {
            sb.append(s).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        for (int j = 0; j < column.length; j++) {
            sb.append(column[j]).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" from ").append(tableName);
        ResultSet rs = DBUtil.excuteQuery(sb.toString());
        */
       /** 最終使用
        //通配符查询，省去找主键过程，性能会稍受影响
        String selectSQL = "select * from " + tableName;
        ResultSet rs = DBUtil.excuteQuery(selectSQL);

        while (rs.next()) { //逐行处理，先取出数据，使用resultSet直接update
            for (int i = 0; i < column.length; i++) {
                String preValue = rs.getString(column[i]);
                rs.updateString(column[i], replaceWords(preValue, ruleIds[i]));
                rs.updateRow();
            }

        }*/
        /*for (int i = 0; i < ids.length; i++) { //逐行处理，先取出数据，得到该行各列的改前值，该行的主键集合，形成updateSQL

            String hideSQL = buildSQL(tableName, column, ruleIds);
            DBUtil.executeUpdate(hideSQL);
        }*/

        return true;
    }

    private String buildSQL(String tableName, String[] column, Rule[] ruleIds) {
        StringBuilder sb = new StringBuilder("update ");
        sb.append(tableName).append(" ");
        sb.append("set ");
        for (int i = 0; i < column.length; i++) {
            sb.append(column[i]).append("='").append(replaceWords(column[i], ruleIds[i])).append("', ");
        }
        return sb.substring(0, sb.length() - 2).toString();
    }

    private String replaceWords(String s, Rule rule) {
        Map resultMap = DPUtils.replace(s, rule);
        if (resultMap == null) {
            return ResInfo.UNDEFINED_ERROR;
        }
        if (resultMap.get("ok") == "false") {
            return resultMap.get("msg").toString();
        } else {
           return resultMap.get("cnt").toString();
        }
    }

    //推断是哪种类型，最好是能传入一个对应编码
    private Rule inferRule(String s) {
        return Rule.NAME;
    }

    private class ResInfo {
        public final static String UNDEFINED_ERROR = "未知错误";
    }
}
