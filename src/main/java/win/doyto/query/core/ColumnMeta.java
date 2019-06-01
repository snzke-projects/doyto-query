package win.doyto.query.core;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static win.doyto.query.core.CommonUtil.convertColumn;
import static win.doyto.query.core.CommonUtil.wrapWithParenthesis;

/**
 * ColumnMeta
 *
 * @author f0rb
 */
@AllArgsConstructor
final class ColumnMeta {
    final String fieldName;
    final Object value;
    final List<Object> argList;

    final String defaultSql(QuerySuffix querySuffix) {
        return defaultSql(querySuffix, QueryBuilder.REPLACE_HOLDER);
    }

    final String defaultSql(QuerySuffix querySuffix, String ex) {
        if (!ex.isEmpty()) {
            ex = SPACE + ex;
        }
        String columnName = querySuffix.resolveColumnName(fieldName);
        if (columnName.contains("Or")) {
            String[] ors = splitByOr(columnName);
            List<String> columns = new ArrayList<>(ors.length);
            for (String or : ors) {
                columns.add(convertColumn(camelize(or)) + SPACE + querySuffix.getOp() + ex);
                appendArgs(ex, value, argList);
            }
            return wrapWithParenthesis(StringUtils.join(columns, " OR "));
        }

        appendArgs(ex, value, argList);
        return convertColumn(columnName) + SPACE + querySuffix.getOp() + ex;
    }

    static String camelize(String or) {
        return or.substring(0, 1).toLowerCase() + or.substring(1);
    }

    static String[] splitByOr(String columnName) {
        return columnName.split("Or(?=[A-Z])");
    }

    @SuppressWarnings("unchecked")
    private static void appendArgs(String ex, Object value, List<Object> argList) {
        if (value instanceof Collection) {
            argList.addAll((Collection<Object>) value);
        } else if (ex.contains("?")){
            argList.add(value);
        }
    }

}
