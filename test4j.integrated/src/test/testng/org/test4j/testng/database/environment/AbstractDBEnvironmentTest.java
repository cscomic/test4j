package org.test4j.testng.database.environment;

import java.util.HashMap;
import java.util.Map;

import mockit.Mock;

import org.test4j.database.table.ITable;
import org.test4j.database.table.TddUserTable;
import org.test4j.module.database.environment.DBEnvironment;
import org.test4j.module.database.environment.DBEnvironmentFactory;
import org.test4j.module.database.environment.TableMeta;
import org.test4j.module.database.environment.TableMeta.ColumnMeta;
import org.test4j.testng.Test4J;
import org.testng.annotations.Test;

@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
@Test(groups = { "test4j", "database" })
public class AbstractDBEnvironmentTest extends Test4J {

    @Test
    public void testGetTableMetaData() throws Exception {
        new MockUp<ColumnMeta>() {
            @Mock
            public boolean isNullable() {
                return false;
            }
        };
        DBEnvironment db = DBEnvironmentFactory.getCurrentDBEnvironment();
        TableMeta meta = db.getTableMetaData(ITable.t_tdd_user);
        Map data = new HashMap() {
            {
                this.put(TddUserTable.IColumn.f_id, "123");
                this.put(TddUserTable.IColumn.f_first_name, "darui.wu");
            }
        };
        meta.fillData(data, db);
        want.object(meta).notNull();
        want.map(meta.getColumns()).sizeEq(13);
        want.map(data).sizeEq(13).reflectionEqMap(new TddUserTable() {
            {
                this.put(IColumn.f_id, "123");
                this.put(IColumn.f_first_name, "darui.wu");
                this.put(IColumn.f_post_code, "test4j");
                this.put(IColumn.f_address_id, 0);
                this.put(IColumn.f_last_name, "test4j");
                this.put(IColumn.f_sarary, 0.0);
            }
        });
    }
}
