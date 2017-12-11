package lazLib.com.laz.lib.bigdata.hive;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class HiveTest {
	public static void main(String[] args) {
		Connection con = null;
		try {
			con = HiveDBUtil.getConnect();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			List list = HiveDBUtil
					.query(con,
							"select cs_ship_date_sk,cs_item_sk,cs_quantity from catalog_sales where cs_bill_customer_sk is not null  limit 0,10 ");
			System.out.println(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
