package com.didi.moon;

import com.didichuxing.sec.data.dp.util.DPUtils;
import com.didichuxing.sec.data.dp.util.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by lyorall on 2018/1/9.
 */
public class SecureDataEraser {

    public boolean hideInfo(String tableName, String[] column, Rule[] ruleIds) throws SQLException {
        //通配符查询，省去找主键过程，性能会稍受影响
        String selectSQL = "select * from " + tableName;
        ResultSet rs = DBUtil.excuteQuery(selectSQL);

        //逐行处理，先取出数据，使用resultSet直接update
        while (rs.next()) {
            for (int i = 0; i < column.length; i++) {
                String preValue = rs.getString(column[i]);
                rs.updateString(column[i], replaceWords(preValue, ruleIds[i]));
                rs.updateRow();
            }
        }
        return true;
    }

    public boolean hideInfo(String tableName, List<TransPair> pairList) {
        String selectSQL = "select * from " + tableName;
        ResultSet rs = DBUtil.excuteQuery(selectSQL);

        //逐行处理，先取出数据，使用resultSet直接update
        try {
            while (rs.next()) {
                for (TransPair pair : pairList) {
                    String preValue = rs.getString(pair.getColoumName());
                    rs.updateString(pair.getColoumName(), replaceWords(preValue, pair.getTransRule()));
                    rs.updateRow();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
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

    private class ResInfo {
        public final static String UNDEFINED_ERROR = "未知错误";
    }
}
