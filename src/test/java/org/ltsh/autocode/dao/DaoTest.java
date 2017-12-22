package org.ltsh.autocode.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by fengjb-it on 2016/2/14 0014.
 */

public class DaoTest extends BaseTest{
    @Test
    public void getColumn(){
        List ds_role = getBaseDao().queryColumns("ds_role");
        System.out.print(ds_role);
    }

}
